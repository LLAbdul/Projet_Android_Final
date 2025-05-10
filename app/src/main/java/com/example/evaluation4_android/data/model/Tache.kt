package com.example.evaluation4_android.data.model

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
    val dateCreation: String,
    val priorite: Priorite,
    val nom: String,
    val note: String?,
    val isCompleted : Boolean,
    val expectedDueDate: String,

)