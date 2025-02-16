package ua.honchar.common

sealed interface Resource<out T> {
    data class Success<out T : Any>(val data: T) : Resource<T>
    data class Error(val exception: BaseException) : Resource<Nothing>
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onFailure(action: (BaseException) -> Unit): Resource<T> {
    if (this is Resource.Error) action(exception)
    return this
}