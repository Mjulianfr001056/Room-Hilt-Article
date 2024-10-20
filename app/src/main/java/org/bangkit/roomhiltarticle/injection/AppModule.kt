package org.bangkit.roomhiltarticle.injection

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.bangkit.roomhiltarticle.data.DicodingEventDao
import org.bangkit.roomhiltarticle.data.DicodingEventDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DicodingEventDatabase {
        Log.i(TAG, "Database created!")
        return Room.databaseBuilder(
            context,
            DicodingEventDatabase::class.java,
            "dicodingevent_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: DicodingEventDatabase) : DicodingEventDao {
        return database.dicodingEventDao
    }

    companion object {
        private const val TAG = "AppModule"
    }
}