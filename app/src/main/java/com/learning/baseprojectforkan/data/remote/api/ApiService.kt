package com.learning.baseprojectforkan.data.remote.api

import com.learning.baseprojectforkan.common.utils.Constants.Companion.USERS_ENDPOINT
import com.learning.baseprojectforkan.data.remote.model.UserRes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(USERS_ENDPOINT)
    suspend fun getUsers(): Response<UserRes>
}