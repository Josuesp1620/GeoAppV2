package com.geosolution.geoapp.data.repository

import com.geosolution.geoapp.data.local.dao.UserDao
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataBase: UserDao
): UserRepository {
    override suspend fun userSaveStore(user: User) {
        dataBase.userSaveStore(user.asDatabaseEntity())
    }

    override fun userGetStore(): Flow<User?> = dataBase.userGetStore().map { it?.asDomain() }
}