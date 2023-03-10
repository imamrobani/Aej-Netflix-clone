package aej.dino.netflixcloneapps.ui.screen.auth.login

import aej.dino.netflixcloneapps.R
import aej.dino.netflixcloneapps.ui.Routers
import aej.dino.netflixcloneapps.ui.component.GhostButton
import aej.dino.netflixcloneapps.ui.component.MovieAppBar
import aej.dino.netflixcloneapps.ui.component.OutlineButton
import aej.dino.netflixcloneapps.ui.component.TextFieldEmail
import aej.dino.netflixcloneapps.ui.component.TextFieldPassword
import aej.dino.netflixcloneapps.ui.screen.auth.AuthViewModel
import aej.dino.netflixcloneapps.ui.screen.auth.register.RegisterScreenState
import aej.dino.netflixcloneapps.ui.theme.NetflixCloneAppsTheme
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AuthViewModel.Factory)
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val userLoginResponse by viewModel.loginScreenState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(userLoginResponse) {
        when (userLoginResponse) {
            is LoginScreenState.Success -> {
                viewModel.storeToken((userLoginResponse as LoginScreenState.Success).user.token)
                navHostController.navigate(Routers.HOME)
            }
            is LoginScreenState.Error -> {
                Toast.makeText(
                    context,
                    (userLoginResponse as LoginScreenState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        MovieAppBar()
    }) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center
            ) {
                TextFieldEmail(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    email = email,
                    onValueChange = { email = it }
                )

                TextFieldPassword(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    password = password,
                    onValueChange = { password = it }
                )

                OutlineButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 32.dp),
                    text = stringResource(R.string.login).uppercase()
                ) {
                    viewModel.login(email, password)
                }

                GhostButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 64.dp),
                    text = stringResource(id = R.string.register_new_account).uppercase()
                ) {
                    navHostController.navigate(Routers.REGISTER)
                }
            }

            if (userLoginResponse is LoginScreenState.Loading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }

        }

    }

}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NetflixCloneAppsTheme {
        LoginScreen(navHostController = rememberNavController())
    }
}