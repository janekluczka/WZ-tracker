package com.example.wz_tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Match::class],
    version = 2,
    exportSchema = false
)
abstract class WzDatabase : RoomDatabase() {

    abstract val wzDao: WzDao

    companion object {

        @Volatile
        private var INSTANCE: WzDatabase? = null

        fun getInstance(context: Context): WzDatabase = synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = buildRoomDatabaseInstance(context)
                INSTANCE = instance
            }

            return instance
        }

        private fun buildRoomDatabaseInstance(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WzDatabase::class.java,
                "warzone_games_database"
            ).fallbackToDestructiveMigration().build()

    }

}
