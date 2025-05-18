package edu.ucne.registrotecnicos.presentacion.ticket

import android.R.attr.label
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    ticket: TicketEntity?,
    prioridades: List<PrioridadEntity>,
    tecnicos: List<TecnicoEntity>,
    agregarTicket: (String, Int, String, String, String, Int) -> Unit,
    onCancel: () -> Unit
) {
    var fecha by remember { mutableStateOf(ticket?.fecha ?: "") }
    var prioridadId by remember { mutableStateOf(ticket?.prioridadId ?: 0) }
    var cliente by remember { mutableStateOf(ticket?.cliente ?: "") }
    var asunto by remember { mutableStateOf(ticket?.asunto ?: "") }
    var descripcion by remember { mutableStateOf(ticket?.descripcion ?: "") }
    var tecnicoId by remember { mutableStateOf(ticket?.tecnicoId ?: 0) }
    var error by remember { mutableStateOf<String?>(null) }

    var expanded by remember { mutableStateOf(false) }
    var selectedPrioridad by remember {
        mutableStateOf(
            prioridades.find { it.prioridadId == ticket?.prioridadId }
                ?: prioridades.firstOrNull() ?: PrioridadEntity(0, "Seleccionar")
        )
    }

    var expandedTecnico by remember { mutableStateOf(false) }
    var selectedTecnico by remember {
        mutableStateOf(
            tecnicos.find { it.tecnicoId == ticket?.tecnicoId }
                ?: tecnicos.firstOrNull() ?: TecnicoEntity(0, "Seleccionar")
        )
    }
    ExposedDropdownMenuBox(
        expanded = expandedTecnico,
        onExpandedChange = { expandedTecnico = !expandedTecnico }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedTecnico.nombre,
            onValueChange = {},
            label = { Text("Técnico") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTecnico)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expandedTecnico,
            onDismissRequest = { expandedTecnico = false }
        ) {
            tecnicos.forEach { tecnico ->
                DropdownMenuItem(
                    text = { Text(tecnico.nombre) },
                    onClick = {
                        selectedTecnico = tecnico
                        tecnicoId = tecnico.tecnicoId!!
                        expandedTecnico = false
                    }
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (ticket == null) "Registrar Ticket" else "Editar Ticket",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFEDE7F6), Color(0xFF00BCD4))
                    )
                )
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Black.copy(alpha = 0.95f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )
                var expanded by remember { mutableStateOf(false) }
                var selectedPrioridad by remember {
                    mutableStateOf(
                        prioridades.find { it.prioridadId == ticket?.prioridadId }
                            ?: prioridades.firstOrNull() ?: PrioridadEntity(0, "Seleccionar")
                    )
                }

                if (prioridades.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedPrioridad.descripcion,
                            onValueChange = {},
                            label = { Text("Prioridad") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            prioridades.forEach { prioridad ->
                                DropdownMenuItem(
                                    text = { Text(prioridad.descripcion) },
                                    onClick = {
                                        selectedPrioridad = prioridad
                                        prioridadId = prioridad.prioridadId
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = cliente,
                        onValueChange = { cliente = it },
                        label = { Text("Nombre del cliente") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = asunto,
                        onValueChange = { asunto = it },
                        label = { Text("Asunto") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripcion") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedTecnico,
                        onExpandedChange = { expandedTecnico = !expandedTecnico }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedTecnico.nombre,
                            onValueChange = {},
                            label = { Text("Técnico") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTecnico)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedTecnico,
                            onDismissRequest = { expandedTecnico = false }
                        ) {
                            tecnicos.forEach { tecnico ->
                                DropdownMenuItem(
                                    text = { Text(tecnico.nombre) },
                                    onClick = {
                                        selectedTecnico = tecnico
                                        tecnicoId = tecnico.tecnicoId!!
                                        expandedTecnico = false
                                    }
                                )
                            }
                        }
                    }


                    error?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onCancel() },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text("Cancelar")
                        }
                        Button(
                            onClick = {
                                when {
                                    fecha.isBlank() -> error = "La fecha es requerida"
                                    selectedPrioridad.descripcion.isBlank() -> error =
                                        "La prioridad es requerida"

                                    prioridadId <= 0 -> error =
                                        "La prioridad debe ser un número positivo"

                                    cliente.isBlank() -> error = "El cliente es requerido"
                                    asunto.isBlank() -> error = "El asunto es requerido"
                                    descripcion.isBlank() -> error = "La descripción es requerida"
                                    tecnicoId <= 0 -> error = "El técnico es requerido"
                                    else -> try {
                                        agregarTicket(
                                            fecha,
                                            prioridadId,
                                            cliente,
                                            asunto,
                                            descripcion,
                                            tecnicoId
                                        )
                                    } catch (e: Exception) {
                                        error = "Error al agregar el ticket: ${e.message}"
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketScreen(
        ticket = null,
        prioridades = listOf(
            PrioridadEntity(1, "Baja"),
            PrioridadEntity(2, "Media"),
            PrioridadEntity(3, "Alta")
        ),
        tecnicos = listOf(
            TecnicoEntity(1, "Carlos"),
            TecnicoEntity(2, "María"),
            TecnicoEntity(3, "Luis")
        ),
        agregarTicket = { fecha, prioridadId, cliente, asunto, descripcion, tecnicoId ->
            println("Nuevo ticket: $fecha, $prioridadId, $cliente, $asunto, $descripcion, $tecnicoId")
        },
        onCancel = { println("Cancelado") }
    )
}

