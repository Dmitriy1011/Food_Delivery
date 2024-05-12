package ru.testapp.models

sealed class LoadResult<out T> {
    data object Loading : LoadResult<Nothing>()
    data class Done<out T>(val result: T?) : LoadResult<T>()
    data class Error(val message: String) : LoadResult<Nothing>()

    companion object {

        fun createError(code: Int, message: String): LoadResult<Nothing> {
            return Error("Unsuccessful response; code: $code; message: $message")
        }
    }
}