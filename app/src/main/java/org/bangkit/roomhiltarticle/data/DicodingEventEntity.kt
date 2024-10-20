package org.bangkit.roomhiltarticle.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bangkit.roomhiltarticle.data.model.DicodingEventModel

@Entity(tableName = "dicoding_event")
data class DicodingEventEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val name: String? = null,
    val mediaCover: String? = null,
) {
    fun toModel() = DicodingEventModel(
        id = id,
        name = name,
        mediaCover = mediaCover
    )
}