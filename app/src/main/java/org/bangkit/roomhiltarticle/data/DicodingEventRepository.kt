package org.bangkit.roomhiltarticle.data

import kotlinx.coroutines.flow.Flow
import org.bangkit.roomhiltarticle.util.Result
import org.bangkit.roomhiltarticle.data.model.DicodingEventModel

interface DicodingEventRepository {
    suspend fun addEvent(event: DicodingEventModel) : Flow<Result<Boolean>>
    suspend fun getEvents() : Flow<Result<List<DicodingEventModel>>>
}