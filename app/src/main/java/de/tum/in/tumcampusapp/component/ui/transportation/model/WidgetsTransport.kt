package de.tum.`in`.tumcampusapp.component.ui.transportation.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.RoomWarnings

@Entity(tableName = "widgets_transport")
@SuppressWarnings(RoomWarnings.DEFAULT_CONSTRUCTOR)
data class WidgetsTransport(@PrimaryKey
                            var id: Int = 0,
                            var station: String = "",
                            @ColumnInfo(name = "station_id")
                            var stationId: String = "",
                            var location: Boolean = false,
                            var reload: Boolean = false)