package com.example.iquadras.ui.component

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GolfCourse
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.iquadras.R
import com.example.iquadras.model.court.Court
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.themeColor
import java.util.Calendar
import java.util.Locale

@Composable
fun CourtView(court: Court, user: User) {
    val robotoCondensed = FontFamily(
        Font(R.font.roboto_condensed),
    )

    // Estado para controlar o modal de reserva
    var openDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        // Imagem principal
        Image(
            painter = rememberAsyncImagePainter(court.imageUrl),
            contentDescription = "Court Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )

        // Coluna com cantos arredondados sobreposta à imagem
        Column(
            modifier = Modifier
                .padding(top = 275.dp) // Mover a coluna para que sobreponha a imagem
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .fillMaxSize()
        ) {
            // Conteúdo da quadra (nome, preço, etc.)
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 18.dp)
                            .width(80.dp),
                        thickness = 2.dp,
                        color = Color.Gray.copy(alpha = 0.35f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = court.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color.Black.copy(alpha = 0.6f)
                        ),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .width(200.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = robotoCondensed,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = themeColor.copy(alpha = 0.8f),
                                )
                            ) {
                                append("R$${court.price}/")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = robotoCondensed,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = themeColor.copy(alpha = 0.8f)
                                )
                            ) {
                                append("Hora.")
                            }
                        },
                        style = MaterialTheme.typography.displaySmall,
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                // Endereço
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Court Location",
                            tint = themeColor,
                            modifier = Modifier.size(56.dp)
                        )
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .width(200.dp)
                    ) {
                        Text(
                            text = "Avenida Mar Vermelho, 123, Cabedelo - PB",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            color = Color.Black.copy(alpha = 0.4f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.End,
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Distância e outros detalhes (ícones e informações)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(100.dp)
                            .height(120.dp)
                            .border(1.dp, Color.Black.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Distance",
                            tint = Color.Black.copy(alpha = 0.4f),
                            modifier = Modifier
                                .size(32.dp)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = court.capacity.toString(), // Exemplo, pode ser dinâmico
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(100.dp)
                            .height(120.dp)
                            .border(1.dp, Color.Black.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.GolfCourse,
                            contentDescription = "Distance",
                            tint = Color.Black.copy(alpha = 0.4f),
                            modifier = Modifier
                                .size(32.dp)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = court.type.lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }, // Exemplo, pode ser dinâmico
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(100.dp)
                            .height(120.dp)
                            .border(1.dp, Color.Black.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = "Distance",
                            tint = Color.Black.copy(alpha = 0.4f),
                            modifier = Modifier
                                .size(32.dp)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = court.score.toString(), // Exemplo, pode ser dinâmico
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Descrição da quadra
                Text(
                    text = court.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botão para reservar a quadra
                Button(
                    onClick = { openDialog = true },  // Abre o modal ao clicar
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4169E1),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(text = "Reservar", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
    // Modal de reserva
    if (openDialog) {
        ReserveCourtDialog(onDismiss = { openDialog = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserveCourtDialog(onDismiss: () -> Unit) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Reservar Quadra", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { /* Não faz nada aqui, o valor é atualizado pelo DatePicker */ },
                    label = { Text("Escolha o Dia") },
                    modifier = Modifier.clickable { showDatePicker = true }, // Mostra o DatePicker ao clicar
                    readOnly = true // Impede digitação manual
                )

                // Exibir DatePicker se showDatePicker for verdadeiro
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        onDateSelected = { date ->
                            selectedDate = date // Atualiza a data selecionada
                            showDatePicker = false // Fecha o DatePicker
                        }
                    )
                }

                OutlinedTextField(
                    value = selectedTime,
                    onValueChange = { selectedTime = it },
                    label = { Text("Escolha o Horário") }
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duração (em horas)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Lógica para reservar quadra com as informações inseridas
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Reserva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

// Composable para o DatePickerDialog
@Composable
fun DatePickerDialog(onDismissRequest: () -> Unit, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Criar um DatePickerDialog apenas quando a função é chamada
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Dismiss listener para o dialog
    datePickerDialog.setOnDismissListener { onDismissRequest() }

    // Mostra o DatePickerDialog
    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}
