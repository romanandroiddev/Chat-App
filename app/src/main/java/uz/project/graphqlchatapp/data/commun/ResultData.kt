package uz.project.graphqlchatapp.data.commun

sealed class ResultData<out T> {
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Error(val exception: Throwable) : ResultData<Nothing>()
    data class Message<out T>(val message: String) : ResultData<T>()
}
