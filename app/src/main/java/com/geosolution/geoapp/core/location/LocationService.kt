package com.geosolution.geoapp.core.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.room.Room
import com.geosolution.geoapp.R
import com.geosolution.geoapp.data.local.database.Database
import com.geosolution.geoapp.data.local.datastore.AuthDataStore
import com.geosolution.geoapp.data.repository.AuthRepositoryImpl
import com.geosolution.geoapp.data.repository.DeviceDataRepositoryImpl
import com.geosolution.geoapp.data.repository.UserRepositoryImpl
import com.geosolution.geoapp.di.authStore
import com.geosolution.geoapp.domain.model.DeviceData
import com.geosolution.geoapp.domain.use_case.auth.AuthGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.device_data.DeviceDataDeleteStore
import com.geosolution.geoapp.domain.use_case.device_data.DeviceDataGetStore
import com.geosolution.geoapp.domain.use_case.device_data.DeviceDataSaveStoreUseCase
import com.geosolution.geoapp.domain.use_case.user.UserGetStoreUseCase
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geolocation.GeoLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationService: LifecycleService() {

    companion object {
        const val NOTIFICATION_ID = 787
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: throw Exception("No notification manager found")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.e("BIRJU", "Temp Service started")
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return START_NOT_STICKY
    }

    private fun stop() {
        GeoLocation.stopLocationUpdates()
        this@LocationService.stopService(Intent(this@LocationService, LocationService::class.java))
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun getCurrentTimeFormatted(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val currentTime = Date(System.currentTimeMillis())
        return dateFormat.format(currentTime)
    }

    private fun getBatteryPercentage(): Int {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = applicationContext.registerReceiver(null, intentFilter)
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level != -1 && scale != -1) {
            (level / scale.toFloat() * 100).toInt()
        } else {
            -1 // Error case
        }
    }


    private fun getSignalStrength(): Int {
        val telephonyManager = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        @RequiresApi(Build.VERSION_CODES.P)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Para Android 8 y superior
            val signalStrength = telephonyManager.signalStrength
            return signalStrength?.level ?: -1 // Retorna un valor entre 0 y 4, o -1 si signalStrength es null
        } else {
            // Para versiones anteriores a Android 8
            @Suppress("DEPRECATION")
            val signalStrength = telephonyManager.signalStrength
            if (signalStrength == null) {
                return -1
            } else {
                return when {
                    signalStrength.isGsm -> {
                        // Si es GSM, se obtiene la intensidad de la señal en dBm
                        signalStrength.gsmSignalStrength * 2 - 113 // Convertir a nivel de 0-4
                    }
                    else -> {
                        // Si es CDMA, se obtiene la intensidad de la señal en dBm
                        signalStrength.cdmaDbm
                    }
                }
            }
        }
    }


    private fun send_location_api_service(dataStore:AuthDataStore, database: Database) : LocationApiServiceImpl {

        val authRepository = AuthRepositoryImpl(dataStore)
        val authGetCacheUseCase = AuthGetCacheUseCase(authRepository)

        val userRepository = UserRepositoryImpl(database.userDao)
        val userGetStoreUseCase = UserGetStoreUseCase(userRepository)


        val service = LocationApiServiceImpl(applicationContext, authGetCacheUseCase, userGetStoreUseCase)

        return service
    }

    private fun save_location_local_service(database: Database, deviceData: DeviceData) {
        CoroutineScope(Dispatchers.IO).launch {
            val deviceDataRepository = DeviceDataRepositoryImpl(database.deviceDataDao)
            val deviceDataSaveStore = DeviceDataSaveStoreUseCase(deviceDataRepository)
            deviceDataSaveStore(deviceData)
        }
    }


    private fun delete_location_local_service(database: Database) {
        val deviceDataRepository = DeviceDataRepositoryImpl(database.deviceDataDao)
        val deviceDataDeleteStore = DeviceDataDeleteStore(deviceDataRepository)

        CoroutineScope(Dispatchers.IO).launch {

            // Después de que la coroutine de get termine, ejecuta la de delete
            withContext(Dispatchers.IO) {
                deviceDataDeleteStore()
            }
        }
    }

    private fun send_location_local_service(dataStore: AuthDataStore, database: Database) {
        Log.e("deviceDataGetStore", "get_location_local_service")
        val deviceDataRepository = DeviceDataRepositoryImpl(database.deviceDataDao)
        val deviceDataGetStore = DeviceDataGetStore(deviceDataRepository)
        val deviceDataDeleteStore = DeviceDataDeleteStore(deviceDataRepository)

        CoroutineScope(Dispatchers.IO).launch {
            // Primero ejecuta la coroutine de get
            withContext(Dispatchers.IO) {
                deviceDataGetStore()
                    .catch { e ->
                        Log.e("Error", "Error getting device data: ${e.message}")
                    }
                    .onEach { dt ->
                        send_location_api_service(dataStore, database).sendLocationArray(dt)
                    }
                    .collect {  /* No-op, just collecting to trigger flow */ }
            }

            // Después de que la coroutine de get termine, ejecuta la de delete
            withContext(Dispatchers.IO) {
                deviceDataDeleteStore()
            }
        }
    }



    private fun start() {
        startForeground(NOTIFICATION_ID, getNotification())
        GeoLocation.configure {
            enableBackgroundUpdates = true
        }
        GeoLocation.startLocationUpdates(this).observe(this) { result ->
            CoroutineScope(Dispatchers.IO).launch {

                try {


                    // Pass the context and coroutine scope properly
                    val networkConnection = NetworkTrackerLocation(applicationContext)
                    val network_state: NetworkTracker.State? = networkConnection.flow
                        .catch { e ->
                            Log.e("LocationService", "Failed Service Networkstate:", e)
                        }
                        .firstOrNull()


                    val dataBase = Room.databaseBuilder(
                        context = applicationContext,
                        klass = Database::class.java,
                        name = "GeoAppDataBase"
                    ).build()

                    val dataStore = AuthDataStore(dataStore = authStore)

                    val service = send_location_api_service(dataStore, dataBase)

                    val data_api = DeviceData(
                        id=0,
                        name = service.getUserData()?.name!!,
                        state = "conectado",
                        team = "Villa el salvador",
                        gender = "male",
                        time = getCurrentTimeFormatted(),
                        angle = result.location?.bearing!!,
                        latitud = result.location?.latitude!!,
                        longitud = result.location?.longitude!!,
                        battery = getBatteryPercentage(),
                        network = getBatteryPercentage(),
                    )

                    when (network_state) {
                        is NetworkTracker.Unavailable -> {
                            save_location_local_service(dataBase, deviceData = data_api)
                            Log.e("LocationService", "Failed to send location: NetworkTracker.Unavailable")
                        }
                        is NetworkTracker.Available -> {
                            send_location_local_service(dataStore, dataBase)
                            service.sendLocation(data_api)
                            delete_location_local_service(dataBase)
                        }
                        NetworkTracker.Init -> TODO()
                        null -> TODO()
                    }
                } catch (e: Exception) {
                    Log.e("LocationService", "Failed to send location", e)
                }
            }
            manager.notify(NOTIFICATION_ID, getNotification(result))

        }
    }


    private fun getNotification(result: Any? = null): Notification {
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                ?: throw Exception("No notification manager found")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "location",
                    "Location Updates",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        return with(NotificationCompat.Builder(this, "location")) {
            setContentTitle("Location Service")
            setContentText("$result")
//            result?.apply {
//                location?.let {
//                    setContentText("Sharing Current Location")
//                } ?: setContentText("Error: ${error?.message}")
//            } ?: setContentText("Trying to get location updates")
            setSmallIcon(R.drawable.ic_location)
            setAutoCancel(false)
            setOnlyAlertOnce(true)
            build()
        }
    }
}
