package com.learning.baseprojectforkan.data.remote.api

import com.learning.baseprojectforkan.data.remote.model.UserRes
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<UserRes> = apiService.getUsers()

}