package edu.ucne.registrotecnicos.presentacion.enfermedad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.repository.EnfermedadRepository
import edu.ucne.registrotecnicos.presentacion.remote.Resource
import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EnfermedadViewModel @Inject constructor(
    private val repository: EnfermedadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnfermedadUiState())
    val uiState: StateFlow<EnfermedadUiState> get() = _uiState

    init {
        getEnfermedades()
    }

    fun getEnfermedades() {
        viewModelScope.launch {
            repository.getEnfermedades().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            enfermedades = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun saveEnfermedad(enfermedadDto: EnfermedadDto) {
        viewModelScope.launch {
            if (enfermedadDto.enfermedadId == null) {
                repository.createEnfermedad(enfermedadDto)
            } else {
                repository.updateEnfermedad(enfermedadDto)
            }
            getEnfermedades()
        }
    }

    fun deleteEnfermedad(enfermedadId: Int) {
        viewModelScope.launch {
            repository.deleteEnfermedad(enfermedadId)
            getEnfermedades()
        }
    }

    fun getEnfermedadById(enfermedadId: Int?): EnfermedadDto? {
        return _uiState.value.enfermedades.find { it.enfermedadId == enfermedadId }
    }
}




