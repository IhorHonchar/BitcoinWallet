package ua.honchar.network

import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ua.honchar.common.AuthorizationException
import ua.honchar.common.BadRequestException
import ua.honchar.common.NotFoundException
import ua.honchar.common.Resource
import java.io.IOException
import ua.honchar.common.UnknownException
import ua.honchar.common.NetworkException

suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiToBeCalled())
        } catch (e: HttpException) {
            val message = JsonParser.parseString(
                e.response()?.errorBody()?.string()
            ).asJsonObject["message"]?.asString.orEmpty()
            when (e.code()) {
                400 -> Resource.Error(BadRequestException(message))
                401 -> Resource.Error(AuthorizationException(message))
                404 -> Resource.Error(NotFoundException(message))
                else -> Resource.Error(UnknownException(message))
            }
        } catch (e: IOException) {
            Resource.Error(NetworkException())
        } catch (e: Exception) {
            Resource.Error(UnknownException())
        }
    }
}