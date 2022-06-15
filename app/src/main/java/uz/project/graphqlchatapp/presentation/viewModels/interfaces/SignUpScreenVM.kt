package uz.project.graphqlchatapp.presentation.viewModels.interfaces

import kotlinx.coroutines.flow.Flow
import uz.project.graphql.LoginMutation
import uz.project.graphql.ObserveMessageSubscription
import uz.project.graphql.SignUpMutation

interface SignUpScreenVM {
    val addedUser: Flow<SignUpMutation.Data>
    val loginUser: Flow<LoginMutation.Data>
    val messageFlow: Flow<String>
    val errorFlow: Flow<Throwable>

    val loaderFlow: Flow<Boolean>


    suspend fun signUp(userName: String, password: String)
    suspend fun login(userName: String, password: String)


}