package com.geosolution.geoapp.core.location

import com.geosolution.geoapp.data.remote.dto.ResponseDto

interface LocationApiService {
    suspend fun sendLocation() : ResponseDto<Any?>

}