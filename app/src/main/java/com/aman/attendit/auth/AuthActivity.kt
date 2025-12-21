package com.aman.attendit.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.aman.attendit.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthHost(viewModel)
        }
    }
}

@Composable
private fun AuthHost(viewModel: AuthViewModel) {
    var isLogin by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (isLogin) {
        LoginScreen(
            viewModel = viewModel,
            onNavigateRegister = { isLogin = false },
            onLoginSuccess = {
                context.startActivity(
                    Intent(context, MainActivity::class.java)
                )
                (context as Activity).finish()
            }
        )
    } else {
        RegisterScreen(
            viewModel = viewModel,
            onRegisterSuccess = {
                context.startActivity(
                    Intent(context, MainActivity::class.java)
                )
                (context as Activity).finish()
            }
        )
    }
}
