package uz.project.graphqlchatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.project.graphql.*
import uz.project.graphqlchatapp.data.commun.ResultData

interface MainRepository {

    fun getAllMessage(): Flow<ResultData<MessageListQuery.Data>>

    fun addMessage(
        message: String, userID: Int
    ): Flow<ResultData<AddMessageMutation.Data?>>

    fun observeMessage(): Flow<ResultData<ObserveMessageSubscription.Data?>>

    fun signUp(userName: String, password: String): Flow<ResultData<SignUpMutation.Data>>


    fun login(userName: String, password: String): Flow<ResultData<LoginMutation.Data>>

}
