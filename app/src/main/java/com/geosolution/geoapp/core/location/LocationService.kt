package com.geosolution.geoapp.core.location

import android.app.Notification
import android.app.NotificationChannel
import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: throw Exception("No notification manager found")
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        } catch (e: Exception) {
            Log.e("LocationService", "Failed to remove location updates", e)
        }
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
//                        send_location_api_service(dataStore, database).sendLocationArray(dt)
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
        startForeground(NOTIFICATION_ID, getNotification("Starting location updates..."))

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000L)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
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

                            val userName = service.getUserData()?.name ?: "Unknown User"

                            val data_api = DeviceData(
                                id = 0,
                                name = userName,
                                state = "conectado",
                                team = "Villa el salvador", // Consider making this dynamic or configurable
                                gender = "male", // Consider making this dynamic or configurable
                                time = getCurrentTimeFormatted(),
                                angle = location.bearing,
                                latitud = location.latitude,
                                longitud = location.longitude,
                                battery = getBatteryPercentage(),
                                network = getSignalStrength(), // Assuming getSignalStrength() is appropriate here
                            )

                            when (network_state) {
                                is NetworkTracker.Unavailable -> {
                                    save_location_local_service(dataBase, deviceData = data_api)
                                    Log.e("LocationService", "Network unavailable. Location saved locally.")
                                }
                                is NetworkTracker.Available -> {
                                    send_location_local_service(dataStore, dataBase) // Send pending local data
                                    // service.sendLocation(data_api) // Send current location
                                    delete_location_local_service(dataBase) // Clear sent local data
                                    Log.i("LocationService", "Network available. Location sent.")
                                }
                                NetworkTracker.Init, null -> {
                                    Log.w("LocationService", "Network state is Init or null.")
                                    // Optionally, save locally if network state is uncertain
                                    save_location_local_service(dataBase, deviceData = data_api)
                                }
                            }
                            manager.notify(NOTIFICATION_ID, getNotification("Location: ${location.latitude}, ${location.longitude}"))
                        } catch (e: Exception) {
                            Log.e("LocationService", "Failed to process location or send data", e)
                            manager.notify(NOTIFICATION_ID, getNotification("Error: ${e.message}"))
                        }
                    }
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("LocationService", "Location permissions not granted.")
            // Consider stopping the service or notifying the user if permissions are critical
            manager.notify(NOTIFICATION_ID, getNotification("Error: Location permissions not granted."))
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("LocationService", "Background location permission not granted.")
                // Notify user or stop service if background permission is essential for the app's functionality
                manager.notify(NOTIFICATION_ID, getNotification("Error: Background location permission not granted."))
                // Depending on app requirements, you might stop the service here or limit functionality
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } catch (e: SecurityException) {
            Log.e("LocationService", "SecurityException while requesting location updates.", e)
            manager.notify(NOTIFICATION_ID, getNotification("Error: SecurityException requesting updates."))
        }
    }

    private fun getNotification(message: String? = null): Notification {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                ?: throw Exception("No notification manager found")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "location_channel_id", // Consistent channel ID
                    "Location Updates",
                    NotificationManager.IMPORTANCE_LOW // Use LOW to avoid sound/vibration for ongoing task
                ).apply {
                    description = "Provides ongoing location updates"
                }
            )
        }
        return with(NotificationCompat.Builder(this, "location_channel_id")) {
            setContentTitle("Location Service Active")
            setContentText(message ?: "Tracking location...")
            setSmallIcon(R.drawable.ic_location)
            setOngoing(true) // Makes the notification non-dismissible
            setAutoCancel(false)
            setOnlyAlertOnce(true)
            setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            build()
        }
    }
}
