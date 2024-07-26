package cz.nuanced.composemindframe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Meme::class, Tag::class], version = 2)
@TypeConverters(Converters::class)
abstract class MemeDB: RoomDatabase() {
    abstract fun getMemeDAO(): MemeDAO
    abstract fun getTagDAO(): TagDAO
}

object MemeDatabaseSingleton {
    @Volatile
    private var INSTANCE:MemeDB? = null

    fun getDatabase(context: Context): MemeDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MemeDB::class.java,
                "Meme Database"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}