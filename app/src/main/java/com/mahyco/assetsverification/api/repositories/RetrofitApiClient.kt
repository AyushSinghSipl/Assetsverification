package com.mahyco.isp.repositories

import com.mahyco.cmr_app.core.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

object RetrofitApiClient {

    private val logger: Logger = Logger.getLogger("CMR RetrofitApiClient") //Logger.getLogger(RetrofitApiClient::class.java.simpleName)
//    private const val BASE_URL = "https://cmrapi.mahyco.com/"
    private const val BASE_URL = "http://10.80.50.153/AssetsTracking/api/"
//    private const val BASE_URL = "https://cmrapinxg.mahyco.com/"

    private var apiClient: Retrofit? = null

    private fun createClient(): Retrofit {
        apiClient = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()

        return apiClient!!
    }


    private fun getOkHttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()

        okHttpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                var originalRequest = chain.request()
                var request = originalRequest.newBuilder()
                    .addHeader("Cache-Control", "no-cache")
                    .build()

                return chain.proceed(request)
            }
        })

        okHttpClient.connectTimeout(70, TimeUnit.SECONDS)
        okHttpClient.readTimeout(45, TimeUnit.SECONDS)

        if (Constant.IS_DEBUGGABLE) { //BuildConfig.DEBUG
            var loggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    logger.info(
                        message
                    )
                })

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        return okHttpClient.build()
    }

    @JvmStatic
    fun getAPIClient(): Retrofit {
        return apiClient
            ?: createClient()
    }


}