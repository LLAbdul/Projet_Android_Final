package com.example.evaluation4_android.model
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.evaluation4_android.R

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
    val expectedDueDate: Long?,

    )

fun Priorite.toLocalizedString(context: Context): String {
    return when (this) {
        Priorite.ELEVE -> context.getString(R.string.priorite_elevee)
        Priorite.MOYEN -> context.getString(R.string.priorite_moyenne)
        Priorite.BAS -> context.getString(R.string.priorite_basse)
    }
}
