package com.ovan.raionbattlepass.model.repository.network

import com.ovan.raionbattlepass.data.ChangePasswordRequest
import com.ovan.raionbattlepass.data.CreatePostRequest
import com.ovan.raionbattlepass.data.GuestLoginRequest
import com.ovan.raionbattlepass.data.GuestRegisterRequest
import com.ovan.raionbattlepass.data.UpdatePostRequest
import com.ovan.raionbattlepass.data.UpdateUserRequest
import com.ovan.raionbattlepass.model.repository.network.response.ChangePasswordResponse
import com.ovan.raionbattlepass.model.repository.network.response.CreatePostResponse
import com.ovan.raionbattlepass.model.repository.network.response.CurrentUserResponse
import com.ovan.raionbattlepass.model.repository.network.response.DeletePostResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetPostIdResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetPostsByUserIdResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetPostsResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetUserResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetUsersResponse
import com.ovan.raionbattlepass.model.repository.network.response.GuestLoginResponse
import com.ovan.raionbattlepass.model.repository.network.response.GuestRegisterResponse
import com.ovan.raionbattlepass.model.repository.network.response.SearchPostsResponse
import com.ovan.raionbattlepass.model.repository.network.response.SearchUsersResponse
import com.ovan.raionbattlepass.model.repository.network.response.UpdatePostResponse
import com.ovan.raionbattlepass.model.repository.network.response.UpdateUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://raion-battlepass.elginbrian.com/"

interface ApiService {
    
    @PUT("/api/auth/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest,
    ): Response<ChangePasswordResponse>
    
    @GET("/api/auth/current-user")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<CurrentUserResponse>
    
    @POST("/api/auth/login")
    suspend fun login(@Body request: GuestLoginRequest): Response<GuestLoginResponse>
    
    @POST("/api/auth/register")
    suspend fun register(@Body request: GuestRegisterRequest): Response<GuestRegisterResponse>
    
    @GET("/api/posts")
    suspend fun getPosts(): Response<GetPostsResponse>
    
    @POST("/api/posts")
    suspend fun createPost(@Body createPostRequest: CreatePostRequest): Response<CreatePostResponse> // formData
    
    @GET("/api/posts/user/{user_id}")
    suspend fun getPostsByUserId(@Path("user_id") userId: String): Response<GetPostsByUserIdResponse>
    
    @GET("/api/posts/{id}")
    suspend fun getPostId(@Path("id") id: String): Response<GetPostIdResponse>
    
    @PUT("/api/posts/{id}")
    suspend fun updatePost(
        @Path("id") id: String,
        request: UpdatePostRequest,
    ): Response<UpdatePostResponse>
    
    @DELETE("/api/posts/{id}")
    suspend fun deletePost(@Path("id") id: String): Response<DeletePostResponse>
    
    @GET("/api/search/posts")
    suspend fun searchPosts(@Query("query") query: String): Response<SearchPostsResponse>
    
    @GET("/api/search/users")
    suspend fun searchUsers(@Query("query") query: String): Response<SearchUsersResponse>
    
    @GET("/api/users/")
    suspend fun getUsers(): Response<GetUsersResponse>
    
    @PUT("/api/users/")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest,
    ): Response<UpdateUserResponse>
    
    @GET("/api/users/{id}")
    suspend fun getUser(@Path("id") id: String): Response<GetUserResponse>
}