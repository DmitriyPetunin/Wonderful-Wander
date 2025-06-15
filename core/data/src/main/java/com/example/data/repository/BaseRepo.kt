package com.example.data.repository

import com.example.network.model.error.ExampleErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo() {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Result<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    Result.success(response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    val errorResponse: ExampleErrorResponse? = convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    Result.failure(Exception(errorResponse?.message ?: "Something went wrong"))
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Result.failure(Exception(e.message ?: "Something went wrong"))
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Result.failure(Exception("Please check your network connection"))
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Result.failure(Exception("Something went wrong"))
            }
        }
    }

    // If you don't wanna handle api's own
    // custom error response then ignore this function
    private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
        return try {
            errorBody?.string()?.let { Json.decodeFromString<ExampleErrorResponse>(it) }
        } catch (exception: Exception) {
            null
        }
    }
}