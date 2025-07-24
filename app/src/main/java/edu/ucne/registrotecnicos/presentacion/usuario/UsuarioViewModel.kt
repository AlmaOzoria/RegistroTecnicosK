package edu.ucne.registrotecnicos.presentacion.usuario


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity
import edu.ucne.registrotecnicos.data.local.repository.UsuarioRepository
import edu.ucne.registrotecnicos.presentacion.remote.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> get() = _uiState

    init {
        getUsuarios()
    }

    fun getUsuarios() {
        viewModelScope.launch {
            repository.getAllLocal().collect { usuarios ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    usuarios = usuarios,
                    errorMessage = null
                )
            }
        }
    }

    fun saveUsuario(usuario: UsuarioEntity) {
        viewModelScope.launch {
            try {
                if (usuario.id == 0) {
                    repository.save(usuario)
                } else {
                    repository.update(usuario)
                }
                getUsuarios()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteUsuario(usuario: UsuarioEntity) {
        viewModelScope.launch {
            try {
                repository.delete(usuario)
                getUsuarios()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun getUsuarioById(id: Int): UsuarioEntity? {
        return _uiState.value.usuarios.find { it.id == id }
    }
}
