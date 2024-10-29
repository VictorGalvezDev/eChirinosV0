package com.dam.echirinosv1.ui.screens.dptos

data class DptosBusState(
    val showDlgBorrar: Boolean = false,
    val dptoSelected: Int = -1
)

data class DptosMtoState(
    val id: String = "",
    val nombre: String = "",
    val clave: String = "",
    val datosObligatorios: Boolean = false
)

