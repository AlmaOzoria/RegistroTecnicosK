package edu.ucne.registrotecnicos.data.local.repository

import android.util.Log
import edu.ucne.registrotecnicos.data.local.dao.UsuarioDao
import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity
import edu.ucne.registrotecnicos.presentacion.remote.Resource
import edu.ucne.registrotecnicos.presentacion.remote.UsuarioApi
import edu.ucne.registrotecnicos.presentacion.remote.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val api: UsuarioApi,
    private val dao: UsuarioDao
) {
    fun getUsuarios(): Flow<Resource<List<UsuarioEntity>>> = flow {
        emit(Resource.Loading())

        try {
            val usuariosRemotos = api.getUsuarios()

            val entities = usuariosRemotos.map { dto ->
                UsuarioEntity(
                    id = dto.usuarioId ?: 0,
                    nombre = dto.nombre,
                    apellido = dto.apellido,
                    email = dto.email
                )
            }

            dao.insertUsuarios(entities)

            dao.getAll().map { lista: List<UsuarioEntity> ->
                Resource.Success(lista)
            }.collect { emit(it) }

        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener usuarios: ${e.message}"))
        }
    }

    suspend fun save(usuario: UsuarioEntity) {
        dao.save(usuario)
        try {
            api.createUsuario(usuario.toDto())
        } catch(e: Exception) {
            Log.e("UsuarioRepo", "Error guardando en API: ${e.message}")
        }
    }

    suspend fun update(usuario: UsuarioEntity) {
        dao.update(usuario)
        try {
            api.updateUsuario(usuario.id, usuario.toDto())
        } catch(e: Exception) {
            Log.e("UsuarioRepo", "Error actualizando en API: ${e.message}")
        }
    }

//    suspend fun save(usuario: UsuarioEntity) = dao.save(usuario)
//    suspend fun update(usuario: UsuarioEntity) = dao.update(usuario)
    suspend fun delete(usuario: UsuarioEntity) = dao.delete(usuario)
    suspend fun find(id: Int): UsuarioEntity? = dao.find(id)
    fun getAllLocal(): Flow<List<UsuarioEntity>> = dao.getAll()
}
