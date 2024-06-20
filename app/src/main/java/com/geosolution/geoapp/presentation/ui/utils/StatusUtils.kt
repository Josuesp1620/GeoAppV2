package com.geosolution.geoapp.presentation.ui.utils

import com.geosolution.geoapp.core.utils.Action
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <T> Throwable?.toAction(): Action<T> = when (this) {
    is UnknownHostException -> Action.Error("No internet connection")
    is ConnectException -> Action.Error("Connection timed out")
    is SocketTimeoutException -> Action.Error("Read timeout")
    else -> Action.Error(this?.message.toString())
}