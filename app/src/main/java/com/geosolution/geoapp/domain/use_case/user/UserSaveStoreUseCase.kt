package com.geosolution.geoapp.domain.use_case.user

import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.domain.repository.UserRepository
import javax.inject.Inject

class UserSaveStoreUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) = repository.userSaveStore(user)
}