package com.example.evaluation4_android.ui.state

import com.example.evaluation4_android.model.Priorite

data class AjoutTacheDTO(
    val nom: String = "",
    val note: String? = null,
    val expectedDueDate: Long? = null,
    val priorite: Priorite = Priorite.MOYEN
)