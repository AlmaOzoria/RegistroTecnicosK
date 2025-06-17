package edu.ucne.registrotecnicos.presentacion.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
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
    onEdit: (TicketEntity) -> Unit,
    onMessage: (TicketEntity) -> Unit
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
                    TicketRow(ticket, tecnicos, onDelete, onEdit, onMessage)
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
    onEdit: (TicketEntity) -> Unit,
    onSendMessage: (TicketEntity) -> Unit
) {
    val prioridadTexto = when (ticket.prioridadId) {
        1 -> "Baja"
        2 -> "Media"
        3 -> "Alta"
        else -> "Desconocida"
    }

    val tecnicoNombre = tecnicos.find { it.tecnicoId == ticket.tecnicoId }?.nombre ?: "Desconocido"

    Card(
        elevation = CardDefaults.cardElevation(14.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${ticket.fecha}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF000000)
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ticket #: ${ticket.ticketId}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Cliente: ${ticket.cliente}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Asunto: ${ticket.asunto}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onSendMessage(ticket) }) {
                    Icon(
                        imageVector = Icons.Filled.MailOutline,
                        contentDescription = "Enviar Mensaje",
                        tint = Color(0xFF2196F3)
                    )
                }
                IconButton(onClick = { onEdit(ticket) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF4CAF50)
                    )
                }
                IconButton(onClick = { onDelete(ticket) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
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
//                prioridadId = 3, // Alta
//                cliente = "Juan Pérez",
//                asunto = "Problema de red",
//                descripcion = "No hay acceso a internet",
//                tecnicoId = 3
//            ),
//            TicketEntity(
//                fecha = "2025-05-16",
//                prioridadId = 2, // Media
//                cliente = "María García",
//                asunto = "Error de software",
//                descripcion = "El sistema no responde",
//                // tecnicoId = 6
//            )
//        )
//    }
//
//    TicketListScreen(
//        ticketList = sampleTickets,
//        onCreate = {
//            sampleTickets.add(
//                TicketEntity(
//                    fecha = "2025-05-18",
//                    prioridadId = 1, // Baja
//                    cliente = "Carlos López",
//                    asunto = "Consulta",
//                    descripcion = "Consulta sobre soporte técnico",
//                    // tecnicoId = 1
//                )
//            )
//        },
//        onDelete = { ticket -> sampleTickets.remove(ticket) },
//        onEdit = { /* Simulación de edición */ }
//    )
//}
