package com.example.wz_tracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Match (
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "place")
    val place: Int,

    @ColumnInfo(name = "kills")
    val kills: Int,

    @ColumnInfo(name = "deaths")
    val deaths: Int,

    @ColumnInfo(name = "damage_done")
    val damageDone: Int,

    @ColumnInfo(name = "damage_taken")
    val damageTaken: Int,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "mode")
    val mode: String = "Unknown"
) {
    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0L
}