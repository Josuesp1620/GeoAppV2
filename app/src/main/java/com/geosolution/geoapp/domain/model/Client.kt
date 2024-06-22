package com.geosolution.geoapp.domain.model;

import com.geosolution.geoapp.domain.utils.Domain

data class Client(
    val id: String?,
    val name : String?,
    val fullName : String?,
    val vat : String?,
    val businessName : String?,
    val address : String?,
    val coordinates : String?,
    val image : String?,
): Domain