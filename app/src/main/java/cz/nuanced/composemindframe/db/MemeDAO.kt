package cz.nuanced.composemindframe.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemeDAO {

    @Insert
    suspend fun insert(meme: Meme): Long

    @Delete
    suspend fun delete(meme: Meme)

    @Query("SELECT * from meme")
    suspend fun getAll(): List<Meme>

    @Query("DELETE from meme")
    suspend fun deleteAll();
}