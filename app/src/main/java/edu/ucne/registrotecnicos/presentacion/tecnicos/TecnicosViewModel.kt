package edu.ucne.registrotecnicos.presentacion.tecnicos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.repository.TecnicoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(
    private val repository: TecnicoRepository
) : ViewModel() {


    val tecnicoList: StateFlow<List<TecnicoEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarTecnico(nombre: String, sueldo: Double) {
        val tecnico = TecnicoEntity(
            nombre = nombre,
            sueldo = sueldo
        )
        saveTecnico(tecnico)
    }

    fun saveTecnico(tecnico: TecnicoEntity) {
        viewModelScope.launch {
            repository.save(tecnico)
        }
    }

    fun delete(tecnico: TecnicoEntity) {
        viewModelScope.launch {
            repository.delete(tecnico)
        }
    }

    fun update(tecnico: TecnicoEntity) {
        saveTecnico(tecnico)
    }

    fun getTecnicoById(id: Int?): TecnicoEntity? {
        return tecnicoList.value.find { it.tecnicoId == id }
    }
}