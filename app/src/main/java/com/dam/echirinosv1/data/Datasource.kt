package com.dam.echirinosv1.data

import com.dam.echirinosv1.data.model.Aula
import com.dam.echirinosv1.data.model.Departamento
import com.dam.echirinosv1.data.model.Incidencia

object Datasource {
    val departamentos = mutableListOf(
        Departamento(0, "admin", "a"),
    )

    val aulas = mutableListOf<Aula>()
    val incs = mutableListOf<Incidencia>()
}


