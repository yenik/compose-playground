package cz.nuanced.composemindframe.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TagDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: Tag)

    @Query("SELECT * from tag")
    suspend fun getAllTags(): List<Tag>

    @Query("SELECT * from tag where id = :tag")
    suspend fun getByTag(tag: String): Tag

    @Query("SELECT * from tag where id like :tag")
    suspend fun getByTagLike(tag: String): List<Tag>

    @Query("DELETE from tag")
    suspend fun deleteAll()
}