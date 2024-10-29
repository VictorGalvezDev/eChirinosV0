package com.dam.echirinosv1.ui.screens.incs

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
import com.dam.echirinosv1.data.model.Incidencia
import com.dam.echirinosv1.data.model.TipoInc
import com.dam.echirinosv1.data.repository.IncsRepository

class IncsVM(private val incsRepository: IncsRepository) : ViewModel() {

    var uiIncBus by mutableStateOf(IncBusState())
        private set
    var uiIncMto by mutableStateOf(IncMtoState())
        private set


    /* Funciones Estado del Buscador
       **************************************************************/
    fun setIncSelected(pos: Int) {
        uiIncBus = uiIncBus.copy(
            incSelected = pos,
            showDlgBorrar = false
        )
        uiIncMto = if (pos != -1) {
            val inc = Datasource.incs[pos]
            uiIncMto.copy(
                idDpto = inc.idDpto.toString(),
                fecha = inc.fecha,
                id = inc.id,
                descripcion = inc.descripcion,
                tipo = inc.tipo,
                idAula = if (inc.idAula != null) inc.idAula.toString() else "",
                estado = inc.estado,
                resolucion = inc.resolucion,
                datosObligatorios = true
            )
        } else {
            uiIncMto.copy(
                idDpto = "",
                fecha = "",
                id = "",
                descripcion = "",
                tipo = TipoInc.RMI,
                idAula = "",
                estado = false,
                resolucion = "",
                datosObligatorios = false
            )
        }
    }

    fun setShowDlgBorrar(show: Boolean) {
        uiIncBus = uiIncBus.copy(
            showDlgBorrar = show
        )
    }


    /* Funciones Estado del Mantenimiento
*********************************************************/
    fun setDptoId(dptoId: String) {
        uiIncMto = uiIncMto.copy(
            idDpto = dptoId,
            datosObligatorios = (dptoId != "" && uiIncMto.fecha != "" && uiIncMto.id != "" && uiIncMto.descripcion != "")
        )
    }

    fun setFecha(fecha: String) {
        uiIncMto = uiIncMto.copy(
            fecha = fecha,
            datosObligatorios = (uiIncMto.idDpto != "" && fecha != "" && uiIncMto.id != "" && uiIncMto.descripcion != "")
        )
    }

    fun setId(id: String) {
        uiIncMto = uiIncMto.copy(
            id = id,
            datosObligatorios = (uiIncMto.idDpto != "" && uiIncMto.fecha != "" && id != "" && uiIncMto.descripcion != "")
        )
    }

    fun setDescripcion(descripcion: String) {
        uiIncMto = uiIncMto.copy(
            descripcion = descripcion,
            datosObligatorios = (uiIncMto.idDpto != "" && uiIncMto.fecha != "" && uiIncMto.id != "" && descripcion != "")
        )
    }

    fun setTipo(tipo: TipoInc) {
        uiIncMto = uiIncMto.copy(
            tipo = tipo,
        )
    }

    fun setAula(aula: String) {
        uiIncMto = uiIncMto.copy(
            idAula = aula,
        )
    }

    fun setEstado(estado: Boolean) {
        uiIncMto = uiIncMto.copy(
            estado = estado,
        )
    }

    fun setResolucion(resolucion: String) {
        uiIncMto = uiIncMto.copy(
            resolucion = resolucion,
        )
    }

    fun getNombreAula(idAula: String?): String {
        if (!idAula.isNullOrEmpty()) {
            return Datasource.aulas.first { it.id.toString() == idAula }.nombre
        }
        return ""
    }


    /* Funciones Lógica Aulas
   *********************************************************************/
    fun resetDatos() {
        uiIncBus = uiIncBus.copy(
            incSelected = -1,
            showDlgBorrar = false
        )
        uiIncMto = uiIncMto.copy(
            idDpto = "",
            fecha = "",
            id = "",
            descripcion = "",
            tipo = TipoInc.RMI,
            idAula = "",
            estado = false,
            resolucion = "",
            datosObligatorios = false
        )
    }

    fun alta(): Boolean {
        if (!uiIncMto.datosObligatorios) return false
        val inc = Incidencia(
            uiIncMto.idDpto.toInt(),
            uiIncMto.fecha,
            uiIncMto.id,
            uiIncMto.descripcion,
            uiIncMto.tipo,
            if (uiIncMto.idAula.isNotEmpty()) uiIncMto.idAula.toInt() else null,
            uiIncMto.estado,
            uiIncMto.resolucion,
        )
        return incsRepository.alta(inc, Datasource.incs)
    }

    fun editar(): Boolean {
        if (!uiIncMto.datosObligatorios) return false
        val inc = Incidencia(
            uiIncMto.idDpto.toInt(),
            uiIncMto.fecha,
            uiIncMto.id,
            uiIncMto.descripcion,
            uiIncMto.tipo,
            if (uiIncMto.idAula.isNotEmpty()) uiIncMto.idAula.toInt() else null,
            uiIncMto.estado,
            uiIncMto.resolucion,
        )
        return incsRepository.editar(inc, Datasource.incs)
    }

    fun baja(): Boolean {
        if (!uiIncMto.datosObligatorios) return false
        val inc = Incidencia(
            uiIncMto.idDpto.toInt(),
            uiIncMto.fecha,
            uiIncMto.id,
        )
        return incsRepository.baja(inc, Datasource.incs)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as MainApplication)
                val incsRepository = application.container.incsRepository
                IncsVM(incsRepository = incsRepository)
            }
        }
    }
}