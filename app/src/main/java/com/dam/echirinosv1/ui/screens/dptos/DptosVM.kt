package com.dam.echirinosv1.ui.screens.dptos


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dam.echirinosv1.MainApplication
import com.dam.echirinosv1.data.Datasource
import com.dam.echirinosv1.data.model.Departamento
import com.dam.echirinosv1.data.repository.DptosRepository

class DptosVM(private val dptosRepository: DptosRepository) : ViewModel() {
    var uiBusState by mutableStateOf(DptosBusState())
        private set
    var uiMtoState by mutableStateOf(DptosMtoState())
        private set


    /* Funciones Estado del Buscador
    **************************************************************/
    fun setDptoSelected(pos: Int) {
        uiBusState = uiBusState.copy(
            dptoSelected = pos,
            showDlgBorrar = false
        )
        uiMtoState = if (pos != -1) {
            val dpto = Datasource.departamentos[pos]
            uiMtoState.copy(
                id = dpto.id.toString(),
                nombre = dpto.nombre,
                clave = dpto.clave,
                datosObligatorios = true
            )
        } else {
            uiMtoState.copy(
                id = "",
                nombre = "",
                clave = "",
                datosObligatorios = false
            )
        }
    }

    fun setShowDlgBorrar(show: Boolean) {
        uiBusState = uiBusState.copy(
            showDlgBorrar = show
        )
    }


    /* Funciones Estado del Mantenimiento
*********************************************************/
    fun setId(id: String) {
        uiMtoState = uiMtoState.copy(
            id = id,
            datosObligatorios = (id != "" && uiMtoState.nombre != "" && uiMtoState.clave != "")
        )
    }

    fun setNombre(nombre: String) {
        uiMtoState = uiMtoState.copy(
            nombre = nombre,
            datosObligatorios = (uiMtoState.id != "" && nombre != "" && uiMtoState.clave != "")
        )
    }

    fun setClave(clave: String) {
        uiMtoState = uiMtoState.copy(
            clave = clave,
            datosObligatorios = (uiMtoState.id != "" && uiMtoState.nombre != "" && clave != "")
        )
    }

    /* Funciones Lógica Dptos
   *********************************************************************/
    fun resetDatos() {
        uiBusState = uiBusState.copy(
            dptoSelected = -1,
            showDlgBorrar = false
        )
        uiMtoState = uiMtoState.copy(
            id = "",
            nombre = "",
            clave = "",
            datosObligatorios = false
        )
    }

    fun alta(): Boolean {
        if (!uiMtoState.datosObligatorios) return false
        val dpto = Departamento(uiMtoState.id.toInt(), uiMtoState.nombre, uiMtoState.clave)
        return dptosRepository.alta(dpto, Datasource.departamentos)
    }

    fun editar(): Boolean {
        if (uiMtoState.id == "0") return false // admin!!
        if (!uiMtoState.datosObligatorios) return false
        val dpto = Departamento(uiMtoState.id.toInt(), uiMtoState.nombre, uiMtoState.clave)
        return dptosRepository.editar(dpto, Datasource.departamentos)
    }

    fun baja(): Boolean {
        if (uiMtoState.id == "0") return false // admin!!
        if (!uiMtoState.datosObligatorios) return false
        val dpto = Departamento(uiMtoState.id.toInt())
        return dptosRepository.baja(dpto, Datasource.departamentos)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val dptosRepository = application.container.dptosRepository
                DptosVM(dptosRepository = dptosRepository)
            }
        }
    }
}
