package com.geosolution.geoapp.domain.use_case.user

import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserGetStoreUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() : Flow<User?> = repository.userGetStore()
}