package com.lalapanbulaos.nutric.features.auth.data.remote

import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.auth.data.models.AuthResponse
import com.lalapanbulaos.nutric.features.auth.data.models.SignInRequest
import com.lalapanbulaos.nutric.features.auth.data.models.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("auth/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<ApiResponse<AuthResponse>>

    @POST("auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<ApiResponse<AuthResponse>>

//    @POST("auth/google-login")
//    suspend fun googleLogin(@Body request: GoogleLoginRequest): Response<GoogleLoginResponse>
}