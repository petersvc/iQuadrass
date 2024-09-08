package com.example.iquadras.ui.telas.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.iquadras.R
import com.example.iquadras.model.court.Court
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.courtDAO
import com.example.iquadras.ui.telas.home.component.Header
import com.example.iquadras.ui.telas.home.component.NearbyCourts
import com.example.iquadras.ui.telas.home.component.PopularCourts
import com.example.iquadras.ui.telas.home.component.SubHeader

@Composable
fun HomeActivity(modifier: Modifier = Modifier, onLogoffClick: () -> Unit, user: User) {
    val scope = rememberCoroutineScope()
    val courts = remember { mutableStateListOf<Court>() }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            courtDAO.getAll(callback = { courtsFetched ->
                courts.clear()
                courts.addAll(courtsFetched)
            })
        }
    }

    Column(modifier = modifier.padding(16.dp)) {

        Header(onLogoffClick = onLogoffClick, modifier = Modifier, user = user)

        Spacer(modifier = Modifier.height(16.dp))

        SubHeader()

        Spacer(modifier = Modifier.height(16.dp))

        PopularCourts()

        NearbyCourts(modifier, courts)

    }
}
