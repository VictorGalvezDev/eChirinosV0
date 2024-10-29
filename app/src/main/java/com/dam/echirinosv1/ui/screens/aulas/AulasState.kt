package com.dam.echirinosv1.ui.screens.aulas

data class AulasBusState(
    val showDlgBorrar: Boolean = false,
    val aulaSelected: Int = -1
)

data class AulasMtoState(
    val dptoId: String = "",
    val id: String = "",
    val nombre: String = "",
    val datosObligatorios: Boolean = false
)