package com.zengcheng.getyourshittogether.ui.entry.api

import com.zengcheng.getyourshittogether.database.entity.UserEntity
import com.zengcheng.getyourshittogether.ui.entry.model.ResponseMessageEntity
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApiService {
    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Flowable<UserEntity?>?

    @POST("reg")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Flowable<UserEntity?>?

    @POST("user/update/nickname")
    @FormUrlEncoded
    fun updateNickname(
        @Field("id") id: Long,
        @Field("nickname") nickname: String?
    ): Flowable<ResponseMessageEntity?>?
}
