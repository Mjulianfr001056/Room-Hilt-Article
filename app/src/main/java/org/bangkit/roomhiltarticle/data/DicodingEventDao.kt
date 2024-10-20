package org.bangkit.roomhiltarticle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DicodingEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(dicodingEventEntity: DicodingEventEntity)

    @Query("SELECT * FROM dicoding_event")
    suspend fun getEvents(): List<DicodingEventEntity>
}