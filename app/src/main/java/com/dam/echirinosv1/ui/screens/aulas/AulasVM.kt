package com.dam.echirinosv1.ui.screens.aulas

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
import com.dam.echirinosv1.data.model.Aula
import com.dam.echirinosv1.data.repository.AulasRepository

class AulasVM(private val aulasRepository: AulasRepository) : ViewModel() {
    var uiAulasBus by mutableStateOf(AulasBusState())
        private set
    var uiAulasMto by mutableStateOf(AulasMtoState())
        private set


    /* Funciones Estado del Buscador
       **************************************************************/
    fun setAulaSelected(pos: Int) {
        uiAulasBus = uiAulasBus.copy(
            aulaSelected = pos,
            showDlgBorrar = false
        )
        uiAulasMto = if (pos != -1) {
            val aula = Datasource.aulas[pos]
            uiAulasMto.copy(
                dptoId = aula.idDpto.toString(),
                id = aula.id.toString(),
                nombre = aula.nombre,
                datosObligatorios = true
            )
        } else {
            uiAulasMto.copy(
                dptoId = "",
                id = "",
                nombre = "",
                datosObligatorios = false
            )
        }
    }

    fun setShowDlgBorrar(show: Boolean) {
        uiAulasBus = uiAulasBus.copy(
            showDlgBorrar = show
        )
    }


    /* Funciones Estado del Mantenimiento
*********************************************************/
    fun setDptoId(dptoId: String) {
        uiAulasMto = uiAulasMto.copy(
            dptoId = dptoId,
            datosObligatorios = (dptoId != "" && uiAulasMto.id != "" && uiAulasMto.nombre != "")
        )
    }

    fun setId(id: String) {
        uiAulasMto = uiAulasMto.copy(
            id = id,
            datosObligatorios = (uiAulasMto.dptoId != "" && id != "" && uiAulasMto.nombre != "")
        )
    }

    fun setNombre(nombre: String) {
        uiAulasMto = uiAulasMto.copy(
            nombre = nombre,
            datosObligatorios = (uiAulasMto.dptoId != "" && uiAulasMto.id != "" && nombre != "")
        )
    }

    /* Funciones Lógica Aulas
   *********************************************************************/
    fun resetDatos() {
        uiAulasBus = uiAulasBus.copy(
            aulaSelected = -1,
            showDlgBorrar = false
        )
        uiAulasMto = uiAulasMto.copy(
            dptoId = "",
            id = "",
            nombre = "",
            datosObligatorios = false
        )
    }

    fun alta(): Boolean {
        if (!uiAulasMto.datosObligatorios) return false
        val aula = Aula(uiAulasMto.dptoId.toInt(), uiAulasMto.id.toInt(), uiAulasMto.nombre)
        return aulasRepository.alta(aula, Datasource.aulas)
    }

    fun editar(): Boolean {
        if (!uiAulasMto.datosObligatorios) return false
        val aula = Aula(uiAulasMto.dptoId.toInt(), uiAulasMto.id.toInt(), uiAulasMto.nombre)
        return aulasRepository.editar(aula, Datasource.aulas)
    }

    fun baja(): Boolean {
        if (!uiAulasMto.datosObligatorios) return false
        val aula = Aula(uiAulasMto.dptoId.toInt(), uiAulasMto.id.toInt())
        return aulasRepository.baja(aula, Datasource.aulas)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val aulasRepository = application.container.aulasRepository
                AulasVM(aulasRepository = aulasRepository)
            }
        }
    }
}