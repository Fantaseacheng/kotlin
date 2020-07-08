package com.zengcheng.getyourshittogether.ui.entry.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofit {
    private val mRetrofit: Retrofit
    fun <T> getService(clz: Class<T>?): T {
        return mRetrofit.create(clz)
    }

    companion object {
        private const val URL = "http://192.168.31.116:8080/MyBackEnd/"
        private val sLock = Any()
        private var sApiRetrofit: ApiRetrofit? = null
        fun getInstance(): ApiRetrofit? {
            if (sApiRetrofit == null) {
                synchronized(sLock) {
                    if (null == sApiRetrofit) {
                        sApiRetrofit = ApiRetrofit()
                    }
                }
            }
            return sApiRetrofit
        }
    }

    init {
        val builder = Retrofit.Builder()
        builder.baseUrl(URL)
        builder.addConverterFactory(GsonConverterFactory.create())
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        mRetrofit = builder.build()
    }

}