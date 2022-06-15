package uz.project.graphqlchatapp.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.project.graphqlchatapp.utils.BASE_URL
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @[Singleton Provides]
    fun provideApolloClient(cacheFactory: MemoryCacheFactory) =
        ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .normalizedCache(cacheFactory)
            .build()



    @[Provides Singleton]
    fun provideCacheFactory() = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)


}