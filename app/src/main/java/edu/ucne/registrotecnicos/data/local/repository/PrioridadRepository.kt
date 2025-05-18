package edu.ucne.registrotecnicos.data.local.repository

import edu.ucne.registrotecnicos.data.local.dao.PrioridadDao
import edu.ucne.registrotecnicos.data.local.entities.PrioridadEntity

class PrioridadRepository(private val dao: PrioridadDao) {

    suspend fun obtenerPrioridades(): List<PrioridadEntity> {
        return dao.getPrioridades()
    }

    suspend fun insertarPrioridades(prioridades: List<PrioridadEntity>) {
        dao.insertarPrioridades(prioridades)
    }
}

