package com.dam.echirinosv1.data

import com.dam.echirinosv1.data.repository.AulasRepository
import com.dam.echirinosv1.data.repository.DptosRepository
import com.dam.echirinosv1.data.repository.IncsRepository

interface AppContainer {
    val dptosRepository: DptosRepository
    val aulasRepository: AulasRepository
    val incsRepository: IncsRepository
}

class DefaultAppContainer : AppContainer {
    override val dptosRepository: DptosRepository by lazy {
        DptosRepository()
    }

    override val aulasRepository: AulasRepository by lazy {
        AulasRepository()
    }

    override val incsRepository: IncsRepository by lazy {
        IncsRepository()
    }
}