package com.example.marik.urlslist.api

import com.example.marik.urlslist.model.RequestResult
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.HEAD
import retrofit2.http.Path
import retrofit2.http.Url
import java.io.IOException
import java.util.concurrent.TimeUnit

private var result: RequestResult = RequestResult()

/**
 *  Function for network request and saving availability status and response time in a
 *  RequestResult object
 *  @param service UrlAvailabilityService for network communication
 *  @param queryString for network query
 *  @param assignResult function-type for handling result
 */
fun checkUrl(service: UrlAvailabilityService, queryString: String,
             assignResult: (result: RequestResult) -> Unit) {

    if (service.getResponse(queryString).execute().isSuccessful) {
        result.isAvailable = true
        assignResult(result)
    }
    assignResult(result)
}

/**
 *  Service class for checking url's availability
 */
interface UrlAvailabilityService {
    @HEAD
    fun getResponse(@Url url: String): retrofit2.Call<*>

    companion object {
        fun create(): UrlAvailabilityService{
            val interceptor = LoggingInterceptor()

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl("http://host/path/")
                .client(client)
                .build()
                .create(UrlAvailabilityService::class.java)
        }
    }
}

/**
 *  OkHttp interceptor which calculates response time
 */
internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.currentTimeMillis()

        val response = chain.proceed(request)

        val t2 = System.currentTimeMillis()

        result.responseTime = t2-t1

        return response
    }
}

