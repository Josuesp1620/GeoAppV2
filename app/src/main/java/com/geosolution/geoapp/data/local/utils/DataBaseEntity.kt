package com.geosolution.geoapp.data.local.utils

import com.geosolution.geoapp.domain.utils.Domain

interface DatabaseEntity {
    fun asDomain(): Domain
}