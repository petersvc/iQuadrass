package com.example.iquadras.model.restClient

import com.example.iquadras.model.user.DTOUser
import com.example.iquadras.model.user.DTOUserLogin
import com.example.iquadras.model.user.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserServiceIF {

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Long): DTOUser

    @GET("user")
    suspend fun getAllUsers(): List<User>

    @POST("user/login")
    suspend fun login(@Body dtoUserLogin: DTOUserLogin): DTOUser

    @POST("user")
    suspend fun createUser(@Body user: User): DTOUser

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): DTOUser

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") id: Long): String
}
