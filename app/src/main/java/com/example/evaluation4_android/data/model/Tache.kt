package com.example.evaluation4_android.dao
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Priorite {
    ELEVE,
    MOYEN,
    BAS
}
@Entity(tableName = "tache")
data class Tache(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateCreation: Long = System.currentTimeMillis(),
    val priorite: Priorite,
    val nom: String,
    val note: String?,
    val isCompleted : Boolean = false,
    val expectedDueDate: Long,

    )