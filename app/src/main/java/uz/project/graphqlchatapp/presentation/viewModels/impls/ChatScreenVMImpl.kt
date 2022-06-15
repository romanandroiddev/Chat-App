package uz.project.graphqlchatapp.presentation.viewModels.impls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import uz.project.graphql.MessageListQuery
import uz.project.graphql.ObserveMessageSubscription
import uz.project.graphqlchatapp.data.commun.ResultData
import uz.project.graphqlchatapp.domain.usecase.GetFoodsUseCase
import uz.project.graphqlchatapp.presentation.viewModels.interfaces.ChatScreenVM
import javax.inject.Inject


@HiltViewModel
class ChatScreenVMImpl @Inject constructor(
    private val useCase: GetFoodsUseCase
) : ChatScreenVM, ViewModel() {

    override val allMessageFlow = MutableSharedFlow<MessageListQuery.Data>()
    override val observeFoodFlow = MutableSharedFlow<ObserveMessageSubscription.Data>()

    override val messageFlow = MutableSharedFlow<String>()
    override val errorFlow = MutableSharedFlow<Throwable>()
    override val loaderFlow = MutableSharedFlow<Boolean>()

    override suspend fun getAll() {
        useCase.getAllMessage().onEach {
            loaderFlow.emit(true)
            Timber.d("view model food = $it")
            when (it) {
                is ResultData.Success -> allMessageFlow.emit(it.data)

                is ResultData.Message -> messageFlow.emit(it.message)

                is ResultData.Error -> errorFlow.emit(it.exception)

            }
            loaderFlow.emit(false)
        }.launchIn(viewModelScope)


    }

    override suspend fun observeNewMessage() {
        useCase.observeMessage().onEach {
            loaderFlow.emit(true)
            when (it) {
                is ResultData.Success -> {
//                    observeFoodFlow.emit(it.data!!)
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }

                is ResultData.Error -> {
                    errorFlow.emit(it.exception)
                }
            }
            loaderFlow.emit(false)
        }
    }


}