import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.model.booking.Booking
import com.example.iquadras.model.restClient.RetrofitClient
import com.example.iquadras.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(
    modifier: Modifier = Modifier,
    user: User,
) {
    val scope = rememberCoroutineScope()
    val bookingsList = remember { mutableStateListOf<Booking>() }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val bookingsFetched = getAllBookings()
            // filtre as bookings para mostrar apenas as do usuário logado
            val bookingsFiltered = bookingsFetched.filter { it.user?.id == user.id }
            bookingsList.clear()
            bookingsList.addAll(bookingsFiltered)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Minhas Reservas",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
            modifier = Modifier.padding(top = 56.dp, bottom = 16.dp)
        )

        if (bookingsList.isEmpty()) {
            Text(
                text = "Você não tem reservas ativas no momento.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(bookingsList) { booking ->
                    BookingCard(
                        booking = booking,
                        user = user,
                        onClick = { "implementar" }
                    )
                }
            }
        }
    }
}

@Composable
fun BookingCard(
    booking: Booking,
    user: User,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Quadra: ${booking.court?.name}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.Black.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Data: ${booking.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Horário: ${booking.startTime}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Duração: ${booking.durationHours} horas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }
    }
}


suspend fun getAllBookings(): List<Booking> {
    return withContext(Dispatchers.IO) {
        RetrofitClient.bookingService.getAllBookings()
    }
}