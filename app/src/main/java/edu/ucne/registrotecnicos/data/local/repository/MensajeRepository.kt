package edu.ucne.registrotecnicos.data.local.repository

import edu.ucne.registrotecnicos.data.local.dao.MensajeDao
import edu.ucne.registrotecnicos.data.local.dao.TecnicoDao
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.MensajeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MensajeRepository @Inject constructor (
    private val tecnicoDb: TecnicoDb
) {
    suspend fun save(mensaje: MensajeEntity) {
        tecnicoDb.mensajeDao().save(mensaje)
    }

    suspend fun find(id: Int): MensajeEntity? {
        return tecnicoDb.mensajeDao().find(id)
    }

    suspend fun delete(mensaje: MensajeEntity) {
        tecnicoDb.mensajeDao().delete(mensaje)
    }

    fun getAll(): Flow<List<MensajeEntity>> {
        return tecnicoDb.mensajeDao().getAll()
    }



}
