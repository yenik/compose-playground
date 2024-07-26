package cz.nuanced.composemindframe.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_MIND_FETCH = "https://mind-fetch.qda.cz"
private val mindFetch = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL_MIND_FETCH)
    .build()

interface MindFetchApiService{
    @GET("/")
    suspend fun getMindFetch(@Query("i") param: String): MindFetch
}

object MindFetchApi{
    val retrofitService: MindFetchApiService by lazy {
        mindFetch.create(MindFetchApiService::class.java)
    }

}