package com.dam.echirinosv1.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dam.echirinosv1.data.Datasource
import com.dam.echirinosv1.data.model.Departamento

class MainVM() : ViewModel() {
    var uiMainState by mutableStateOf(MainState())
        private set

    var uiLoginState by mutableStateOf(LoginState())
        private set


    /* Funciones Lógica Main
   **********************************************************************/
    fun setShowDlgSalir(show: Boolean) {
        uiMainState = uiMainState.copy(showDlgSalir = show)
    }

    fun setShowMenu(show: Boolean) {
        uiMainState = uiMainState.copy(showMenu = show)
    }

    /* Funciones Lógica Login
**********************************************************************/

    fun setIdDpto(idDpto: String) {
        uiLoginState = uiLoginState.copy(
            idDpto = idDpto,
            datosObligatorios = (idDpto != "" && uiLoginState.clave != "")
        )
    }

    fun setClave(clave: String) {
        uiLoginState = uiLoginState.copy(
            clave = clave,
            datosObligatorios = (uiLoginState.idDpto != "" && clave != "")
        )
    }

    fun getNombre(idDpto: String): String {
        if (idDpto.isNotEmpty()) {
            val dpto = Datasource.departamentos.firstOrNull() { it.id == idDpto.toInt() }
            return dpto?.nombre ?: ""
        }
        return ""
    }

    fun setLogin(dpto: Departamento?): Boolean {
        if (!uiLoginState.datosObligatorios) return false
        if (dpto == null) {
            return false
        } else if (dpto.clave == uiLoginState.clave) {
            uiMainState = uiMainState.copy(
                login = dpto
            )
            return true
        }
        return false
    }

    fun resetDatos() {
        uiLoginState = uiLoginState.copy(
            idDpto = "",
            clave = "",
            datosObligatorios = false
        )
    }
}
