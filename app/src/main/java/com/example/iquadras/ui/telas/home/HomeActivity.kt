package com.example.iquadras.ui.telas.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.iquadras.model.court.Court
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
