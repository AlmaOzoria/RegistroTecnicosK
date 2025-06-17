package edu.ucne.registrotecnicos.data.local.repository


import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto
import edu.ucne.registrotecnicos.presentacion.remote.Resource
import edu.ucne.registrotecnicos.presentacion.remote.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EnfermedadRepository @Inject constructor(
    private val dataSource: DataSource
) {
    fun getEnfermedades(): Flow<Resource<List<EnfermedadDto>>> = flow {
        try {
            emit(Resource.Loading())
            val enfermedades = dataSource.getEnfermedades()
            emit(Resource.Success(enfermedades))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    suspend fun createEnfermedad(enfermedadDto: EnfermedadDto) =
        dataSource.createEnfermedad(enfermedadDto)

    suspend fun updateEnfermedad(enfermedadDto: EnfermedadDto) =
        dataSource.updateEnfermedad(enfermedadDto.enfermedadId ?: 0, enfermedadDto)

    suspend fun deleteEnfermedad(id: Int) =
        dataSource.deleteEnfermedad(id)
}

