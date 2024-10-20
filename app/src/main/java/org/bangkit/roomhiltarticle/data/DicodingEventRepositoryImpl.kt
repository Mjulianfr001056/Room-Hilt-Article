package org.bangkit.roomhiltarticle.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.bangkit.roomhiltarticle.util.Result
import org.bangkit.roomhiltarticle.data.model.DicodingEventModel
import javax.inject.Inject

class DicodingEventRepositoryImpl @Inject constructor(
    private val dao : DicodingEventDao,
) : DicodingEventRepository {
    override suspend fun addEvent(event: DicodingEventModel): Flow<Result<Boolean>> {
        val eventEntity = event.toEntity()
        return flow {
            try {
                dao.addEvent(eventEntity)
                emit(Result.Success(true))
            } catch (e: Exception) {
                Log.d(TAG, "addFavorite: ${e.message}")
                emit(Result.Error(message = e.message ?: "Terjadi kesalahan"))
            }
        }
    }

    override suspend fun getEvents(): Flow<Result<List<DicodingEventModel>>> {
        return flow {
            try {
                val listEvents = dao.getEvents().map {
                    it.toModel()
                }
                emit(Result.Success(listEvents))
            } catch (e: Exception) {
                Log.d(TAG, "getAllFavoriteEvents: ${e.message}")
                emit(Result.Error(message = e.message ?: "Terjadi kesalahan"))
            }
        }
    }

    companion object {
        private const val TAG = "DicodingEventRepositoryImpl"
    }
}