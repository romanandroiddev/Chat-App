package uz.project.graphqlchatapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.project.graphqlchatapp.domain.repository.MainRepository
import uz.project.graphqlchatapp.domain.repository.impl.MainReposirtoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @[Binds Singleton]
    fun provideMainRepository(impl: MainReposirtoryImpl): MainRepository
}