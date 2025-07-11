package edu.ucne.registrotecnicos.presentacion.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity

@Composable
fun TicketListScreen(
    ticketList: List<TicketEntity>,
    tecnicos: List<TecnicoEntity>,
    onCreate: () -> Unit,
    onDelete: (TicketEntity) -> Unit,
    onEdit: (TicketEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate,
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF5F5F5), Color(0xFF00BCD4))
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Lista de Ticket",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF311B92),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 39.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                items(ticketList) { ticket ->
                    TicketRow(ticket, tecnicos, onDelete, onEdit)
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    ticket: TicketEntity,
    tecnicos: List<TecnicoEntity>,
    onDelete: (TicketEntity) -> Unit,
    onEdit: (TicketEntity) -> Unit
) {
        val prioridadTexto = when (ticket.prioridadId) {
            1 -> "Baja"
            2 -> "Media"
            3 -> "Alta"
            else -> "Desconocida"
        }

    val tecnicoNombre = tecnicos.find { tecnico -> tecnico.tecnicoId == ticket.tecnicoId }?.nombre ?: "Desconocido"

    Card(
        elevation = CardDefaults.cardElevation(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(22.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Fecha: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = ticket.fecha, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Prioridad: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = prioridadTexto, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Cliente: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = ticket.cliente, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Asunto: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = ticket.asunto, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "Descripcion: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = ticket.descripcion,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .weight(1f),
                        softWrap = true
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Técnico: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = tecnicoNombre, fontSize = 16.sp)
                }

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { onEdit(ticket) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(28.dp)
                    )
                }

                IconButton(
                    onClick = { onDelete(ticket) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TicketListScreenPreview() {
//    val sampleTickets = remember {
//        mutableStateListOf(
//            TicketEntity(
//                fecha = "2025-05-17",
//                prioridadId = 3,
//                cliente = "Juan Pérez",
//                asunto = "Problema de red",
//                descripcion = "No hay acceso a internet",
//                tecnicoId = 3
//            ),
//            TicketEntity(
//                fecha = "2025-05-16",
//                prioridadId = 2,
//                cliente = "María García",
//                asunto = "Error de software",
//                descripcion = "El sistema no responde",
//                tecnicoId = 6
//            )
//        )
//    }
//
//    val sampleTecnicos = listOf(
//        TecnicoEntity(tecnicoId = 1, nombre = "Carlos"),
//        TecnicoEntity(tecnicoId = 3, nombre = "Ana"),
//        TecnicoEntity(tecnicoId = 6, nombre = "Luis")
//    )
//
//    TicketListScreen(
//        ticketList = sampleTickets,
//        tecnicos = sampleTecnicos,
//        onCreate = { /* ... */ },
//        onDelete = { ticket -> sampleTickets.remove(ticket) },
//        onEdit = { /* ... */ }
//    )
//}
