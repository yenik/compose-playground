package cz.nuanced.composemindframe.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Meme (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate? = null
)