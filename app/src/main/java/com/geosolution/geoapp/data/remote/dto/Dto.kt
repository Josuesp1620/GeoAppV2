package com.geosolution.geoapp.data.remote.dto

import com.geosolution.geoapp.domain.utils.Domain

interface Dto {
    fun asDomain(): Domain
}