package edu.ucne.registrotecnicos.presentacion.enfermedad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto

@Composable
fun EnfermedadScreen(
    enfermedad: EnfermedadDto?,
    onSave: (String, Double) -> Unit,
    onCancel: () -> Unit
) {
    var descripcion by remember { mutableStateOf(enfermedad?.descripcion ?: "") }
    var monto by remember { mutableStateOf(enfermedad?.monto?.toString() ?: "") }
    var inputError by remember { mutableStateOf<String?>(null) }

    EnfermedadBodyScreen(
        descripcion = descripcion,
        monto = monto,
        onDescripcionChange = { descripcion = it },
        onMontoChange = { monto = it },
        saveEnfermedad = {
            val montoDouble = monto.toDoubleOrNull()
            if (descripcion.isBlank()) {
                inputError = "La descripción es obligatoria"
            } else if (montoDouble == null) {
                inputError = "El monto debe ser un número válido"
            } else {
                inputError = null
                onSave(descripcion, montoDouble)
            }
        },
        inputError = inputError,
        goBack = { onCancel() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnfermedadBodyScreen(
    descripcion: String,
    monto: String,
    onDescripcionChange: (String) -> Unit,
    onMontoChange: (String) -> Unit,
    saveEnfermedad: () -> Unit,
    inputError: String?,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Registar Enfermedad",
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
                        colors = listOf(Color(0xFFF5F5F5), Color(0xFF00BCD4))
                    )
                )
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.95f), shape = MaterialTheme.shapes.medium)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = onDescripcionChange,
                    label = { Text("Descripción de la enfermedad") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = monto,
                    onValueChange = onMontoChange,
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                inputError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { goBack() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = { saveEnfermedad() },
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
