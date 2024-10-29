package com.dam.echirinosv1.data.repository

import com.dam.echirinosv1.data.model.Aula

class AulasRepository {

    private fun existe(aula: Aula, aulas: List<Aula>): Boolean {
        return aulas.find { it.idDpto == aula.idDpto && it.id == aula.id } != null
    }

    fun alta(aula: Aula, aulas: MutableList<Aula>): Boolean {
        if (existe(aula, aulas)) return false
        return aulas.add(aula)
    }

    fun editar(aula: Aula, aulas: MutableList<Aula>): Boolean {
        if (!existe(aula, aulas)) return false
        aulas.find { it.idDpto == aula.idDpto && it.id == aula.id }?.apply {
            idDpto = aula.idDpto
            id = aula.id
            nombre = aula.nombre
        }
        return true
    }

    fun baja(aula: Aula, aulas: MutableList<Aula>): Boolean {
        if (!existe(aula, aulas)) return false
        return aulas.remove(aulas.find { it.idDpto == aula.idDpto && it.id == aula.id })
    }
}