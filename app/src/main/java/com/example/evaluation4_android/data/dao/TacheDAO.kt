package com.example.evaluation4_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.evaluation4_android.model.Tache
import kotlinx.coroutines.flow.Flow

@Dao
interface TacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterTache(tache: Tache)

    @Update
    suspend fun updateTache(tache: Tache)

    @Delete
    suspend fun deleteTache(tache: Tache)

    @Query("SELECT * FROM tache WHERE id = :id")
    fun getTacheById(id: Int): Flow<Tache?>

    @Query("SELECT * FROM tache ORDER BY expectedDueDate ASC")
    fun getAllTaches(): Flow<List<Tache>>

    @Query("UPDATE tache SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTacheCompletedStatus(id: Int, isCompleted: Boolean)

}