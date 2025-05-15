package com.example.evaluation4_android.ui.actions

import android.content.Context
import android.widget.Toast
import com.example.evaluation4_android.R
import com.example.evaluation4_android.model.Tache
import com.example.evaluation4_android.ui.viewmodel.TacheViewModel

fun supprimerTache(
    context: Context,
    tacheViewModel: TacheViewModel,
    tache: Tache,
    onFinish: () -> Unit
) {
    tacheViewModel.supprimerTache(tache)
    Toast.makeText(context, context.getString(R.string.message_tache_supprimee), Toast.LENGTH_SHORT).show()

    onFinish()
}
