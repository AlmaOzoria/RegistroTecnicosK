package edu.ucne.registrotecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Upsert
    suspend fun save(usuario: UsuarioEntity)

    @Query("SELECT * FROM Usuarios")
    fun getAll(): Flow<List<UsuarioEntity>>

    @Query("""
        SELECT * FROM Usuarios
        WHERE id = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): UsuarioEntity?

    @Update
    suspend fun update(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Upsert
    suspend fun insertUsuarios(usuarios: List<UsuarioEntity>)

}
