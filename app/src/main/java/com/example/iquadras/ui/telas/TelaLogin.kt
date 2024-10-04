package com.example.iquadras.ui.telas

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.R
import com.example.iquadras.model.court.CourtDAO
import com.example.iquadras.restClient.RetrofitClient
import com.example.iquadras.model.user.DTOUser
import com.example.iquadras.model.user.DTOUserLogin
import com.example.iquadras.model.user.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

val userDAO: UserDAO = UserDAO()
val courtDAO: CourtDAO = CourtDAO()
val themeColor = Color(0xFF6C56F2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(modifier: Modifier = Modifier, onSignInClick: (DTOUser) -> Unit, onSignUpClick: () -> Unit) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var login by remember { mutableStateOf("") } // email
    var password by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var mensagemSucesso by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Row {
            Text(
                text = "iQuadras",
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.0f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.loginimg),
                        contentDescription = "Descrição do ícone",
                        modifier = Modifier.size(220.dp)
                            .graphicsLayer(alpha = 0.5f) // Ajusta a opacidade da imagem
                    )
                }
//                Spacer(modifier = Modifier.height(0.dp))
//                Text(
//                    text = "Encontre sua quadra",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Light,
//                        fontSize = 22.sp,
//                    ),
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = "Nós te ajudamos a encontrar a quadra perfeita para você.",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 14.sp,
//                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
//                        color = Color.Gray.copy(alpha = 0.8f)
//                    ),
//                    modifier = Modifier
//                        .padding(bottom = 16.dp)
//                        .width(270.dp)
//                )
            }
        }
        Row {
            Column {
                TextField(
                    value = login,
                    onValueChange = { login = it },
                    placeholder = {
                        Text(
                            text = "Login",
                            style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray.copy(alpha = 0.8f))
                        )
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Gray.copy(alpha = 0.1f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = {
                        Text(
                            text = "Senha",
                            style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray.copy(alpha = 0.8f))
                        )
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Gray.copy(alpha = 0.1f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Esqueceu a senha?",
                    color = themeColor.copy(alpha = 0.7f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            // val loginn = "joana@mail.com"
                            // val passwordd = "12345"
                            val dtoUserLogin = DTOUserLogin(login, password)
                            val dtoUser = login(dtoUserLogin)

                            if (dtoUser.id.isEmpty()) {
                                mensagemErro = "Não foi possível fazer o login!"
                            } else {
                                // Navegação deve ocorrer na main thread
                                withContext(Dispatchers.Main) {
                                    mensagemSucesso = "Login realizado com sucesso!"
                                    onSignInClick(dtoUser)
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor)
                ) {
                    Text("LOGIN", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(5f),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.15f)
                    )
                    Text(
                        text = "ou",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray.copy(alpha = 0.99f),
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(5f),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.15f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                GoogleLoginButton(
                    onClick = { /* TODO */ }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Não tem uma conta? ",
                        color = themeColor,
                        style = TextStyle(fontWeight = FontWeight.Normal),
                    )
                    Text(
                        text = "Cadastre-se",
                        color = themeColor, // Cor verde do Starbucks
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .clickable { onSignUpClick() }
                    )
                }
            }

        }
    }
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Email, // Substitua pelo ícone do Google
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.1f)),
            border = BorderStroke(0.dp, Color.Gray.copy(alpha = 0.0f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = icon, // Ícone do Google
                contentDescription = "Google Icon",
                tint = themeColor.copy(alpha = 0.8f), // Para manter as cores originais do ícone
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Continue with Google",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = themeColor.copy(alpha = 0.8f)// Color.Gray.copy(alpha = 0.99f)
            )
        }
    }
}

suspend fun login(dtoUserLogin: DTOUserLogin): DTOUser {
    return withContext(Dispatchers.IO) {
        val response = RetrofitClient.userService.login(dtoUserLogin)
        if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Exception("Falha ao fazer login: ${response.errorBody()?.string()}")
        }
    }
}