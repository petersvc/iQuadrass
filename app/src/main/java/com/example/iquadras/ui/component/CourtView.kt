package com.example.iquadras.ui.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.iquadras.R
import com.example.iquadras.model.booking.Booking
import com.example.iquadras.model.booking.DTOBooking
import com.example.iquadras.model.court.Court
import com.example.iquadras.model.restClient.RetrofitClient
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.themeColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourtView(court: Court, user: User) {
    val robotoCondensed = FontFamily(
        Font(R.font.roboto_condensed),
    )

    // Estado para controlar o modal de reserva
    var openDialog by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

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
                .height(250.dp)
                .fillMaxWidth()
        )

        // Coluna com cantos arredondados sobreposta à imagem
        Column(
            modifier = Modifier
                .padding(top = 225.dp) // Mover a coluna para que sobreponha a imagem
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
                            text = "3km",
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
                            text = court.address , //"Avenida Mar Vermelho, 123, Cabedelo - PB",
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .width(0.dp),
                        thickness = 2.dp,
                        color = Color.Gray.copy(alpha = 0.15f)
                    )
                    Text(
                        text = "Reservar",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 18.sp,
                        color = themeColor.copy(alpha = 0.7f)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .fillMaxWidth(),
                        thickness = 2.dp,
                        color = Color.Gray.copy(alpha = 0.15f),

                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Coluna para escolher o dia
                    Column(
                        modifier = Modifier // Faz a coluna ocupar metade da largura
                            .weight(1f)
                    ) {
                        val dateBtn = selectedDate.ifEmpty { "Escolha o Dia" }
                        Button(
                            onClick = { showDatePicker = true },  // Abre o modal ao clicar
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .border(1.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black.copy(alpha = 0.8f)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(), // Para preencher a largura total do botão
                                horizontalArrangement = Arrangement.Start // Para alinhar o conteúdo à esquerda
                            ) {
                                Text(
                                    text = dateBtn,
                                    style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray.copy(alpha = 0.8f))                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Coluna para duração
                    Column(
                        modifier = Modifier // Faz a coluna ocupar metade da largura
                            .weight(1f)
                    ) {
                        OutlinedTextField(
                            value = duration, //duration,
                            onValueChange = { duration = it },
                            placeholder = {
                                Text(
                                    text = "Duração em horas",
                                    style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray.copy(alpha = 0.8f))
                                )
                            },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Gray.copy(alpha = 0f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .border(1.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                        )
                    }
                }

                Row {
                    val timeBtn = selectedTime.ifEmpty { "Escolha o Horário" }
                    Button(
                        onClick = { showTimePicker = true },  // Abre o modal ao clicar
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(1.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black.copy(alpha = 0.8f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(), // Para preencher a largura total do botão
                            horizontalArrangement = Arrangement.Start // Para alinhar o conteúdo à esquerda
                        ) {
                            Text(
                                text = timeBtn,
                                style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray.copy(alpha = 0.8f))                                )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                // Botão para reservar a quadra
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            try {
                                val booking = DTOBooking(
                                    userId = user.id.toLong(),
                                    courtId = court.id.toLong(),
                                    date = selectedDate,
                                    startTime = selectedTime,
                                    durationHours = duration.toInt() // se necessário
                                )

                                val createdBooking = createBooking(booking)
                                withContext(Dispatchers.Main) {
                                    // Atualizar UI ou exibir mensagem de sucesso
                                    Log.d("Booking", "Booking created: $createdBooking")
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    // Lidar com o erro na UI (ex: mostrar um Toast)
                                    Log.e("Booking", "Error creating booking: ${e.message}")
                                }
                            }
                        }
                    },
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

                if (showDatePicker) {
                    DatePickerDialog(
                        showDatePicker = showDatePicker,
                        onDismissRequest = { showDatePicker = false },
                        onDateSelected = { date ->
                            selectedDate = date // Atualiza a data selecionada
                            showDatePicker = false // Fecha o DatePicker
                        }
                    )
                }
                if (showTimePicker) {
                    TimePickerDialog(
                        showTimePicker = showTimePicker,
                        onDismissRequest = { showTimePicker = false },
                        onTimeSelected = { time ->
                            selectedTime = time // Atualiza a hora selecionada
                            showTimePicker = false // Fecha o TimePicker
                        }
                    )
                }
            }
        }
    }
    // Modal de reserva
    if (openDialog) {
        //ReserveCourtDialog(onDismiss = { openDialog = false })
    }
}

// Composable para o DatePickerDialog
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit,
    showDatePicker: Boolean
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Usar um remember para manter a referência do dialog
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Formatação para garantir que o dia e o mês tenham dois dígitos
                val formattedDay = String.format("%02d", dayOfMonth)
                val formattedMonth = String.format("%02d", month + 1) // O mês é zero-based
                val date = "$formattedDay/$formattedMonth/$year" // Formato final: dd/MM/yyyy
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener {
                onDismissRequest() // Notifica quando o dialogo é fechado
            }
        }
    }

    // Mostra o dialog se o dialog ainda não tiver sido exibido
    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            datePickerDialog.show()
        }
    }
}


@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (String) -> Unit,
    showTimePicker: Boolean
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Usar um remember para manter a referência do dialog
    val timePickerDialog = remember {
        android.app.TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // Formata a hora em HH:mm
                val time = String.format("%02d:%02d", hourOfDay, minute)
                onTimeSelected(time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // true para formato 24 horas, false para formato 12 horas
        ).apply {
            setOnDismissListener {
                onDismissRequest() // Notifica quando o dialogo é fechado
            }
        }
    }

    // Mostra o dialog se o dialog ainda não tiver sido exibido
    LaunchedEffect(showTimePicker) {
        if (showTimePicker) {
            timePickerDialog.show()
        }
    }
}

suspend fun createBooking(booking: DTOBooking): Booking {
    return withContext(Dispatchers.IO) {
        RetrofitClient.bookingService.createBooking(booking)
    }
}









//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReserveCourtDialog(onDismiss: () -> Unit) {
//    var selectedDate by remember { mutableStateOf("") }
//    var selectedTime by remember { mutableStateOf("") }
//    var duration by remember { mutableStateOf("") }
//    var showDatePicker by remember { mutableStateOf(false) }
//
//    // AlertDialog
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(text = "Reservar Quadra", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//        },
//        text = {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                OutlinedTextField(
//                    value = selectedDate,
//                    onValueChange = { /* Não faz nada aqui, o valor é atualizado pelo DatePicker */ },
//                    label = { Text("Escolha o Dia") },
//                    modifier = Modifier.clickable { showDatePicker = true }, // Mostra o DatePicker ao clicar
//                    readOnly = true // Impede digitação manual
//                )
//
//                OutlinedTextField(
//                    value = selectedTime,
//                    onValueChange = { selectedTime = it },
//                    label = { Text("Escolha o Horário") }
//                )
//                OutlinedTextField(
//                    value = duration,
//                    onValueChange = { duration = it },
//                    label = { Text("Duração (em horas)") }
//                )
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//                    // Lógica para reservar quadra com as informações inseridas
//                    onDismiss()
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Confirmar Reserva")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancelar")
//            }
//        }
//    )
//
//    // Mostrar o DatePickerDialog se showDatePicker for verdadeiro
//
//}
//