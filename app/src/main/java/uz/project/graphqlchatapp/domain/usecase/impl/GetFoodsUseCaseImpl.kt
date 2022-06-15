package uz.project.graphqlchatapp.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.project.graphql.LoginMutation
import uz.project.graphql.MessageListQuery
import uz.project.graphql.SignUpMutation
import uz.project.graphqlchatapp.data.commun.ResultData
import uz.project.graphqlchatapp.domain.repository.MainRepository
import uz.project.graphqlchatapp.domain.usecase.GetFoodsUseCase
import javax.inject.Inject

class GetMessagesUseCaseImpl @Inject constructor(private val mainRepository: MainRepository) :
    GetFoodsUseCase {

    override fun getAllMessage(): Flow<ResultData<MessageListQuery.Data>> =
        mainRepository.getAllMessage()

    override fun addMessage(message: String, userID: Int) =
        mainRepository.addMessage(message, userID)

    override fun signUp(userName: String, password: String): Flow<ResultData<SignUpMutation.Data>> =
        mainRepository.signUp(userName, password)

    override fun login(userName: String, password: String): Flow<ResultData<LoginMutation.Data>> =
        mainRepository.login(userName, password)

    override fun observeMessage() = mainRepository.observeMessage()
}