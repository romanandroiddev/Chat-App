package uz.project.graphqlchatapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.project.graphqlchatapp.domain.usecase.GetFoodsUseCase
import uz.project.graphqlchatapp.domain.usecase.impl.GetMessagesUseCaseImpl


@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {


    @Binds
    fun provideGetFoodUseCase(impl: GetMessagesUseCaseImpl): GetFoodsUseCase
}