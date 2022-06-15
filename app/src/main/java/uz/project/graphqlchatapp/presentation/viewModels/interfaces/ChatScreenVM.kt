package uz.project.graphqlchatapp.presentation.viewModels.interfaces

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import uz.project.graphql.MessageListQuery
import uz.project.graphql.ObserveMessageSubscription

interface ChatScreenVM {

    val allMessageFlow: Flow<MessageListQuery.Data>
    val observeFoodFlow: MutableSharedFlow<ObserveMessageSubscription.Data>

    val messageFlow: Flow<String>
    val errorFlow: Flow<Throwable>

    val loaderFlow: Flow<Boolean>


    suspend fun getAll()

    suspend fun observeNewMessage()
}
