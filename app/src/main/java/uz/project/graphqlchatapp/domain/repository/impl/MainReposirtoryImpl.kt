package uz.project.graphqlchatapp.domain.repository.impl


import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber
import uz.project.graphql.*
import uz.project.graphqlchatapp.R
import uz.project.graphqlchatapp.data.commun.DataSourceException
import uz.project.graphqlchatapp.data.commun.ResultData
import uz.project.graphqlchatapp.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainReposirtoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) :
    MainRepository {


    override fun getAllMessage() = flow<ResultData<MessageListQuery.Data>> {
        apolloClient.query(MessageListQuery()).fetchPolicy(FetchPolicy.CacheAndNetwork).toFlow()
            .collect { response ->
                val result = response.data

                if (result?.messages != null) {
                    Timber.d("food = ${result?.messages}")
                    emit(ResultData.Success(result!!))
                } else {
                    emit(ResultData.Message("empty"))
                }

            }
    }.catch { emit(ResultData.Error(it)) }.flowOn(Dispatchers.IO)


    override fun addMessage(
        message: String,
        userID: Int
    ) = flow<ResultData<AddMessageMutation.Data?>> {
        val response =
            apolloClient.mutation(AddMessageMutation(message = message, userID = userID)).execute()
        if (response.data?.writeMessage != null) {
            emit(ResultData.Success(response.data))
        }
    }.catch {
        ResultData.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
    }.flowOn(Dispatchers.IO)

    override fun observeMessage() = flow<ResultData<ObserveMessageSubscription.Data>> {
        apolloClient.subscription(ObserveMessageSubscription()).toFlow()
            .retryWhen { cause, attempt ->
                Log.d("TTT", "cause: ${cause.message}")
                kotlinx.coroutines.delay(attempt * 2000)
                true
            }.collect { response ->
                val result = response.data

                if (result?.newMessage != null) {
                    emit(ResultData.Success(result))
                }
            }
    }.catch {
        Log.d("TTT", it.toString())
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)

    override fun signUp(userName: String, password: String) =
        flow<ResultData<SignUpMutation.Data>> {
            val response =
                apolloClient.mutation(SignUpMutation(userName, password)).execute()
            if (response.data?.signUp != null) {
                emit(ResultData.Success(response.data!!))
            }
        }.catch {
            ResultData.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }.flowOn(Dispatchers.IO)

    override fun login(userName: String, password: String) = flow<ResultData<LoginMutation.Data>> {
        val response =
            apolloClient.mutation(LoginMutation(userName, password)).execute()
        if (response.data?.login != null) {
            emit(ResultData.Success(response.data!!))
        }
    }.catch {
        ResultData.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
    }.flowOn(Dispatchers.IO)


}
