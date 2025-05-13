package com.example.evaluation4_android

import android.app.Application
import com.example.evaluation4_android.data.db.TacheDatabase
import com.example.evaluation4_android.data.repository.TacheRepository
import com.example.evaluation4_android.ui.viewmodel.TacheViewModelFactory


class TachesApplication : Application() {

    private val database by lazy { TacheDatabase.getDatabase(this) }

    val tacheRepository by lazy { TacheRepository(database.tacheDao()) }

    val tacheViewModelFactory by lazy { TacheViewModelFactory(tacheRepository) }

    override fun onCreate() {
        super.onCreate()
    }
}
