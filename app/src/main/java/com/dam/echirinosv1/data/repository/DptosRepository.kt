package com.dam.echirinosv1.data.repository

import com.dam.echirinosv1.data.model.Departamento

class DptosRepository {

    private fun existe(dpto: Departamento, dptos: List<Departamento>): Boolean {
        return dptos.find { it.id == dpto.id } != null
    }

    fun alta(dpto: Departamento, dptos: MutableList<Departamento>): Boolean {
        if (existe(dpto, dptos)) return false
        return dptos.add(dpto)
    }

    fun editar(dpto: Departamento, dptos: MutableList<Departamento>): Boolean {
        if (!existe(dpto, dptos)) return false
        dptos.find { it.id == dpto.id }?.apply {
            id = dpto.id
            nombre = dpto.nombre
            clave = dpto.clave
        }
        return true
    }

    fun baja(dpto: Departamento, dptos: MutableList<Departamento>): Boolean {
        if (!existe(dpto, dptos)) return false
        return dptos.remove(dptos.find { it.id == dpto.id })
    }
}
