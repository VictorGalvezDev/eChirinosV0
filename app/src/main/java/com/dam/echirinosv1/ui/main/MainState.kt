package com.dam.echirinosv1.ui.main

import com.dam.echirinosv1.data.model.Departamento

data class MainState(
    val showDlgSalir: Boolean = false,
    val showMenu: Boolean = false,
    val login: Departamento? = null,
)

data class LoginState(
    val idDpto: String = "",
    val clave: String = "",
    val datosObligatorios: Boolean = false
)
