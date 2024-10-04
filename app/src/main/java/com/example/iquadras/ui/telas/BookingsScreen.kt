import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.model.booking.Booking
import com.example.iquadras.restClient.RetrofitClient
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.home.component.Header
import com.example.iquadras.ui.telas.home.component.SubHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    onLogoffClick: () -> Unit,
    user: User,
    onReservationsClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bookingsList = remember { mutableStateListOf<Booking>() }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val bookingsFetched = getAllBookings(user.id.toLong())

            // Ordena as reservas pela data
            val sortedBookings = bookingsFetched.sortedBy { booking ->
                LocalDate.parse(booking.date)
            }

            bookingsList.clear()
            bookingsList.addAll(sortedBookings)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(34.dp))

        Header(
            onLogoffClick = onLogoffClick,
            modifier = Modifier,
            user = user,
            onReservationsClick = onReservationsClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        SubHeader(
            text = "Minhas ",
            text2 = "reservas"
        )

        Spacer(modifier = Modifier.height(10.dp))
//
//        Spacer(modifier = Modifier.height(24.dp))

        if (bookingsList.isEmpty()) {
            Text(
                text = "Você não tem reservas ativas no momento.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                //verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(bookingsList) { booking ->
                    BookingCard(
                        booking = booking,
                        user = user,
                        onClick = { },
                        scope = scope,
                        bookingsList = bookingsList
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingCard(
    booking: Booking,
    user: User,
    onClick: () -> Unit,
    scope: CoroutineScope,
    bookingsList: MutableList<Booking>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalDivider(
            color = Color.Black.copy(alpha = 0.2f),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp).width(30.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.0f),
            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = booking.date.split("-")[2],
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 28.sp,
                        color = Color.Black.copy(alpha = 0.6f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = formatDateForMonthName(booking.date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.6f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = formatDateForWeekDay(booking.date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = booking.startTime + " - " + booking.durationHours?.let { addHoursToTime(booking.startTime, it.toLong()) },
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 13.sp,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                        IconButton(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    booking.id?.let { deleteBooking(it) }
                                    withContext(Dispatchers.Main) {
                                        bookingsList.remove(booking) // Remove visualmente a reserva da lista
                                    }
                                }
                            },
                            modifier = Modifier.height(24.dp)
                        ) {
                            Icon(
                                Icons.Outlined.MoreHoriz,
                                contentDescription = "Delete",
                                tint = Color.Black.copy(alpha = 0.4f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        booking.court?.let {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 18.sp,
                                color = Color.Black.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val adress = booking.court?.address?.split(",")
                        val street = adress?.get(0)?.trim()
                        val number = adress?.get(1)?.trim()
                        val city = adress?.get(3)?.trim()
                        val district = adress?.get(2)?.trim()
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(50))
                                    .padding(horizontal = 12.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "$street, $number - $city, $district",
                                    color = Color.Black.copy(alpha = 0.8f),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 11.sp
                                )
                            }
                    }
                }

            }
        }
    }

}

suspend fun getAllBookings(userId: Long?): List<Booking> {
    return withContext(Dispatchers.IO) {
        RetrofitClient.bookingService.getAllBookings(userId)
    }
}

//suspend fun deleteBooking(bookingId: Long): String {
//    return withContext(Dispatchers.IO) {
//        RetrofitClient.bookingService.deleteBooking(bookingId)
//    }
//}

suspend fun deleteBooking(bookingId: Long): Any {
    return withContext(Dispatchers.IO) {
        val response = RetrofitClient.bookingService.deleteBooking(bookingId)
        if (response.isSuccessful) {
            response.body() ?: "Reserva deletada com sucesso" // Lida com resposta nula ou string vazia
        } else {
            throw Exception("Falha ao deletar reserva: ${response.errorBody()?.string()}")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatDateForMonthName(dateString: String): String {
    // Converte a string em uma instância de LocalDate
    val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    // Formata o mês com as 3 primeiras letras
    val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())

    // Retorna o nome do mês formatado
    return date.format(monthFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateForWeekDay(dateString: String): String {
    // Converte a string em uma instância de LocalDate
    val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    // Formata o dia da semana com as 3 primeiras letras
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.getDefault())

    // Retorna o nome do dia da semana formatado
    return date.format(dayOfWeekFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun addHoursToTime(startTime: String, hoursToAdd: Long): String {
    // Define o formatador para o formato hh:mm
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Converte a string startTime em um objeto LocalTime
    val localTime = LocalTime.parse(startTime, timeFormatter)

    // Adiciona o número de horas ao LocalTime
    val updatedTime = localTime.plusHours(hoursToAdd)

    // Retorna o novo horário formatado
    return updatedTime.format(timeFormatter)
}