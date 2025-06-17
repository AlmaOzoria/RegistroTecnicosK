package edu.ucne.registrotecnicos.presentacion.remote

import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val api: EnfermedadApi
){
    suspend fun getEnfermedades(): List<EnfermedadDto> = api.getEnfermedades()

    suspend fun createEnfermedad(enfermedad: EnfermedadDto): EnfermedadDto =
        api.createEnfermedad(enfermedad)

    suspend fun getEnfermedad(id: Int): EnfermedadDto =api.getEnfermedad(id)

    suspend fun updateEnfermedad(id: Int, enfermedad: EnfermedadDto): EnfermedadDto =
        api.updateEnfermedad(id, enfermedad)

    suspend fun deleteEnfermedad(id: Int) = api.deleteEnfermedad(id)
}
