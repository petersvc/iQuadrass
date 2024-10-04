package com.example.iquadras.ui.telas.home

import android.location.Location
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.model.court.Court
import com.example.iquadras.restClient.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.home.component.CourtsListColumn
import com.example.iquadras.ui.telas.home.component.Header
import com.example.iquadras.ui.telas.home.component.SubHeader
import com.example.iquadras.ui.telas.themeColor
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivity(
    modifier: Modifier = Modifier,
    onLogoffClick: () -> Unit,
    onCourtClick: (Court) -> Unit,
    user: User,
    onReservationsClick: () -> Unit,
    currentLocation: Location?
) {
    val scope = rememberCoroutineScope()
    val courts = remember { mutableStateListOf<Court>() }
    var filteredCourts by remember { mutableStateOf(listOf<Court>()) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val courtsFetched = getAllCourts()
            courts.clear()
            courts.addAll(courtsFetched)
            filteredCourts = courtsFetched
//            courtDAO.getAll(callback = { courtsFetched ->
//                courts.clear()
//                courts.addAll(courtsFetched)
//            })
        }
    }

    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Header(onLogoffClick = onLogoffClick, modifier = Modifier, user = user, onReservationsClick = onReservationsClick)

        Spacer(modifier = Modifier.height(8.dp))

        SubHeader("Encontre a sua ", "quadra.")

        var searchQuery by remember { mutableStateOf("") }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    // Atualiza as quadras filtradas com base na pesquisa
                    filteredCourts = courts.filter { court ->
                        court.name.contains(query, ignoreCase = true)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .height(64.dp),
                label = {
                    Text(
                        text = "Buscar quadras",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.6f)

                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black.copy(alpha = 0.4f),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = themeColor.copy(alpha = 0.8f),
                    unfocusedBorderColor = Color.Black.copy(alpha = 0.1f),
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // Remove o foco ao pressionar "Done"
                    }
                ),
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = { /* Ação do ícone de filtros */ },
                modifier = Modifier
                    .size(56.dp)
                    .fillMaxHeight()
                    .border(
                        width = 1.dp,
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(64.dp)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtros Icon",
                    tint = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.size(28.dp)

                )
            }
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
        ) {
            CourtsListColumn(courts = filteredCourts, onCourtClick, currentLocation)
        }

    }
}

suspend fun getAllCourts(): List<Court> {
    return withContext(Dispatchers.IO) {
        RetrofitClient.courtService.getAllCourts()
    }
}
