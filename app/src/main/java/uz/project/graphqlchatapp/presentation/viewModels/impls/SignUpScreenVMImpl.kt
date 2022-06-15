package uz.project.graphqlchatapp.presentation.viewModels.impls

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import uz.project.graphql.LoginMutation
import uz.project.graphql.SignUpMutation
import uz.project.graphqlchatapp.data.commun.ResultData
import uz.project.graphqlchatapp.domain.repository.MainRepository
import uz.project.graphqlchatapp.presentation.viewModels.interfaces.SignUpScreenVM
import javax.inject.Inject


@HiltViewModel
class SignUpScreenVMImpl @Inject constructor(
    private val repo: MainRepository
) : SignUpScreenVM, ViewModel() {
    override val addedUser = MutableSharedFlow<SignUpMutation.Data>()
    override val loginUser = MutableSharedFlow<LoginMutation.Data>()


    override val messageFlow = MutableSharedFlow<String>()
    override val errorFlow = MutableSharedFlow<Throwable>()
    override val loaderFlow = MutableSharedFlow<Boolean>()
    override suspend fun signUp(userName: String, password: String) {
        repo.signUp(userName, password).onEach {
            loaderFlow.emit(true)
            when (it) {
                is ResultData.Success -> {
                    addedUser.emit(it.data!!)
                    Log.d("TTT", it.data.signUp.id.toString())
                    Log.d("TTT", it.data.signUp.name.toString())
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    errorFlow.emit(it.exception)
                }
            }
            loaderFlow.emit(false)
        }.launchIn(viewModelScope)
    }

    override suspend fun login(userName: String, password: String) {
        repo.login(userName, password).onEach {
            loaderFlow.emit(true)
            when (it) {
                is ResultData.Success -> {
                    loginUser.emit(it.data)
                    Timber.d(it.data.login.id.toString())
                    Timber.tag("TTT").d(it.data.login.name.toString())
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    errorFlow.emit(it.exception)
                }
            }
            loaderFlow.emit(false)
        }.launchIn(viewModelScope)
    }


}