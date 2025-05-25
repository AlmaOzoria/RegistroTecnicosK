package edu.ucne.registrotecnicos.presentacion.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.data.local.repository.TicketRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TicketViewModel @Inject constructor(
    private val repository: TicketRepository
) : ViewModel() {

    // Exponemos el listado de ticket como StateFlow
    val ticketList: StateFlow<List<TicketEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val prioridades: List<Pair<Int, String>> = listOf(
        1 to "Alta",
        2 to "Media",
        3 to "Baja"
    )

    // Agregar un nuevo ticket
    fun agregarTicket(fecha: String, prioridadId: Int, cliente: String, asunto: String, descripcion: String, tecnicoId: Int) {
        val ticket = TicketEntity(
            ticketId = null,
            fecha = fecha,
            prioridadId = null,
            cliente = cliente,
            asunto = asunto,
            descripcion = descripcion,
            tecnicoId = null
        )
        saveTitcket(ticket)
    }

    // Guardar o actualizar un ticket
    fun saveTitcket(ticket: TicketEntity) {
        viewModelScope.launch {
            repository.saveTicket(ticket)
        }
    }

    // Eliminar un ticket
    fun delete(tecnico: TicketEntity) {
        viewModelScope.launch {
            repository.delete(tecnico)
        }
    }

    fun update(ticket: TicketEntity) {
        saveTitcket(ticket)
    }

    fun getTicketById(ticketId: Int?): TicketEntity? {
        return ticketList.value.find { it.ticketId == ticketId }
    }
}