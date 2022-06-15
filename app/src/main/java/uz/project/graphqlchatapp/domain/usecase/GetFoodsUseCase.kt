package uz.project.graphqlchatapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.project.graphql.*
import uz.project.graphqlchatapp.data.commun.ResultData

interface GetFoodsUseCase {
    fun getAllMessage(): Flow<ResultData<MessageListQuery.Data>>

    fun addMessage(
        name: String,
        price: Int
    ): Flow<ResultData<AddMessageMutation.Data?>>

    fun signUp(userName: String, password: String): Flow<ResultData<SignUpMutation.Data>>


    fun login(userName: String, password: String): Flow<ResultData<LoginMutation.Data>>


    fun observeMessage(): Flow<ResultData<ObserveMessageSubscription.Data?>>
}
