package com.dam.echirinosv1.ui.screens.incs

import com.dam.echirinosv1.data.model.TipoInc


data class IncBusState(
    val showDlgBorrar: Boolean = false,
    val incSelected: Int = -1
)

data class IncMtoState(
    var idDpto: String = "", // PK
    var fecha: String = "", // PK yyyyMMdd
    var id: String = "", // PK HHmmss
    var descripcion: String = "",
    var tipo: TipoInc = TipoInc.RMI,
    var idAula: String = "",
    var estado: Boolean = false,
    var resolucion: String = "",
    val datosObligatorios: Boolean = false
)
