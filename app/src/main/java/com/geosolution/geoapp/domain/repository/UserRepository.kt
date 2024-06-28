package com.geosolution.geoapp.domain.repository

import com.geosolution.geoapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun userSaveStore(user: User)

    fun userGetStore(email: String) : Flow<User?>

}