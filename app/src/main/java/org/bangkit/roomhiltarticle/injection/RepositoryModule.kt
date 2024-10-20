package org.bangkit.roomhiltarticle.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bangkit.roomhiltarticle.data.DicodingEventRepository
import org.bangkit.roomhiltarticle.data.DicodingEventRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repository: DicodingEventRepositoryImpl): DicodingEventRepository
}