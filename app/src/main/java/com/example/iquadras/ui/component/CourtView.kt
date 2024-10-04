package com.example.iquadras.ui.component

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.example.iquadras.R
import com.example.iquadras.model.booking.Booking
import com.example.iquadras.model.booking.DTOBooking
import com.example.iquadras.model.court.Court
import com.example.iquadras.restClient.RetrofitClient
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.themeColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CourtView(court: Court, user: User, currentLocation: Location?) {
    val robotoCondensed = FontFamily(
        Font(R.font.roboto_condensed),
    )

    // Estado para controlar o modal de reserva
    var openDialog by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }
    var showDurationPicker by remember { mutableStateOf(false) }
    var duration by remember { mutableIntStateOf(0) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var mensagemSucesso by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

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
                            modifier = Modifier
                                .size(56.dp)
                                .clickable {
                                    // Abre o Google Maps na localização da quadra
                                    val latitude = court.latitude
                                    val longitude = court.longitude
                                    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(${court.name})")
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    context.startActivity(intent)
                                }
                        )
                        Text(
                            text = showDistanceToTarget(currentLocation, court.latitude, court.longitude),
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
                            text = court.score.toString(),
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
                        modifier = Modifier.weight(1f)
                    ) {
                        val durationBtnTxt = if (duration > 0) "$duration horas" else "Escolha a Duração"
                        Button(
                            onClick = { showDurationPicker = true }, // Abre o modal de duração
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
                                    text = durationBtnTxt,
                                    style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray.copy(alpha = 0.8f))                                )
                            }
                        }

                        // Exibe o Dialog de Duração quando openDurationPicker é verdadeiro
                        if (showDurationPicker) {
                            DurationPickerDialog(
                                onDismiss = { showDurationPicker = false },
                                onDurationSelected = { selectedDuration ->
                                    duration = selectedDuration
                                    showDurationPicker = false
                                },
                                initialDuration = duration
                            )
                        }
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
                                    date = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                                    startTime = selectedTime,
                                    durationHours = duration.toInt()
                                )

                                val createdBooking = createBooking(booking)
                                withContext(Dispatchers.Main) {
                                    // Atualizar UI ou exibir mensagem de sucesso
                                    Log.d("Booking", "Booking created: $createdBooking")
                                    mensagemSucesso = "Reserva criada com sucesso!"
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    // Lidar com o erro na UI (ex: mostrar um Toast)
                                    Log.e("Booking", "Error creating booking: ${e.message}")
                                    mensagemErro = e.message
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

                mensagemErro?.let {
                    LaunchedEffect(it) {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        mensagemErro = null
                    }
                }

                mensagemSucesso?.let {
                    LaunchedEffect(it) {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        mensagemSucesso = null
                    }
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
                val date = "$year-$formattedMonth-$formattedDay" // Formato final: "yyyy-MM-dd"
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
    val calendar = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    // Usar um remember para manter a referência do dialog
    val timePickerDialog = remember {
        android.app.TimePickerDialog(
            context,
            { _, hourOfDay, _ ->
                // Formata a hora em HH:mm
                val time = String.format("%02d:00", hourOfDay)
                // val time = String.format("%02d:%02d", hourOfDay)
                onTimeSelected(time)
            },
            calendar,
            0,
            // calendar.get(Calendar.MINUTE),
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

@Composable
fun DurationPickerDialog(
    onDismiss: () -> Unit,
    onDurationSelected: (Int) -> Unit,
    initialDuration: Int
) {
    val currentDuration = remember { mutableIntStateOf(initialDuration) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Escolha a Duração") },
        text = {
            Column {
                NumberPicker(
                    value = currentDuration.intValue,
                    onValueChange = { currentDuration.intValue = it },
                    range = 1..15, // Limita a duração de 1 a 15 horas
                    modifier = Modifier.fillMaxWidth(),
                    context = LocalContext.current
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onDurationSelected(currentDuration.intValue) // Passa o valor selecionado
            }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    modifier: Modifier,
    context: android.content.Context
) {
    val numberPicker = remember { NumberPicker(context) }

    numberPicker.minValue = range.first
    numberPicker.maxValue = range.last
    numberPicker.value = value

    numberPicker.setOnValueChangedListener { _, _, newVal ->
        onValueChange(newVal)
    }

    AndroidView(
        factory = { numberPicker },
        modifier = modifier
    )
}

@Composable
fun showDistanceToTarget(currentLocation: Location?, lat: Double, lon: Double): String {
    var locationText by remember { mutableStateOf("") }

    // Cria a localização alvo a partir das coordenadas fornecidas
    val targetLocation = Location("").apply {
        latitude = lat
        longitude = lon
    }

    currentLocation?.let {
        // Calcula a distância entre a localização atual e a de destino
        val distanceInKm = calculateDistance(it, targetLocation) / 1000

        // Formata a distância, mostrando apenas o dígito após o ponto se não for zero
        locationText = formatDistance(distanceInKm)
    } ?: run {
        locationText = "?"
    }

    return locationText
}

// Função para calcular a distância entre duas localizações
fun calculateDistance(location1: Location, location2: Location): Float {
    return location1.distanceTo(location2) // Retorna a distância em metros
}

@SuppressLint("DefaultLocale")
fun formatDistance(distance: Float): String {
    val formattedDistance = String.format("%.1f", distance)

    // Verifica se os dois primeiros dígitos após o ponto são zeros
    return if (formattedDistance.split(".")[1].startsWith("0")) {
        "${formattedDistance.split(".")[0]} km"
    } else {
        "$formattedDistance km"
    }
}

suspend fun createBooking(booking: DTOBooking): Booking {
    return withContext(Dispatchers.IO) {
        RetrofitClient.bookingService.createBooking(booking)
    }
}
