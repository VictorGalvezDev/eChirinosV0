package com.dam.echirinosv1.data.repository

import com.dam.echirinosv1.data.model.Incidencia

class IncsRepository {

    private fun existe(inc: Incidencia, incs: List<Incidencia>): Boolean {
        return incs.find { it.idDpto == inc.idDpto && it.fecha == inc.fecha && it.id == inc.id } != null
    }

    fun alta(inc: Incidencia, incs: MutableList<Incidencia>): Boolean {
        if (existe(inc, incs)) return false
        return incs.add(inc)
    }

    fun editar(inc: Incidencia, incs: MutableList<Incidencia>): Boolean {
        if (!existe(inc, incs)) return false
        incs.find { it.idDpto == inc.idDpto && it.fecha == inc.fecha && it.id == inc.id }?.apply {
            idDpto = inc.idDpto
            fecha = inc.fecha
            id = inc.id
            descripcion = inc.descripcion
            tipo = inc.tipo
            idAula = inc.idAula
            estado = inc.estado
            resolucion = inc.resolucion
        }
        return true
    }

    fun baja(inc: Incidencia, incs: MutableList<Incidencia>): Boolean {
        if (!existe(inc, incs)) return false
        return incs.remove(incs.find { it.idDpto == inc.idDpto && it.fecha == inc.fecha && it.id == inc.id })
    }
}