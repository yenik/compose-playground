package cz.nuanced.composemindframe.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Tag (
    @PrimaryKey
    val id: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate? = null,
)