package com.example.branchapp.screens.LoginScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.branchapp.navigation.AppScreens


@SuppressLint("RememberReturnType")
@Composable
fun LoginScreen(navController: NavController,
                viewModel: LoginScreenViewModel= hiltViewModel()) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val authToken = remember {  mutableStateOf(viewModel.authToken) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))
        var logging= "Login"
        Button(
            onClick = {
//                CircularProgressIndicator(color = Color.White)
                viewModel.loginUser(username.value, password.value)
                logging="Logging In.."
//                val authToken by viewModel.authToken.collectAsState()
//
//                LaunchedEffect(authToken) {
//                    authToken?.let {
//                        navController.navigate(AppScreens.MessagesScreen.name + "/${authToken}") }
//                }


            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = logging)
        }
        if (viewModel.authToken != null) {
            LaunchedEffect(authToken) {
                authToken.let {
                    navController.navigate(AppScreens.MessagesScreen.name + "/${viewModel.authToken}") {
                        popUpTo(AppScreens.LoginScreen.name) { inclusive = true }
                    }
                }
            }
        }
////        Display error message if any
        errorMessage?.let {
            ShowToast(message = "Invalid Username or Password")
        }
    }
}

@Composable
fun ShowToast(message: String) {
    // Get the current context
    val context = LocalContext.current
    Toast.makeText(context,message , Toast.LENGTH_SHORT).show()

}

//@ExperimentalComposeUiApi
//@Composable
//fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel= viewModel()) {
//
//    Surface(modifier = Modifier.fillMaxSize()) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top
//        ) {
//            UserForm(loading = false, isCreateAccount = false) { email, password ->
//                //TODO FB login
//                viewModel.signInWithEmailANdPassword(email, password,) {
//                    navController.navigate(AppScreens.MessagesScreen.name) {
//                        popUpTo(AppScreens.LoginScreen.name) {
//                            inclusive = true
//                        }
//                    }
//                }
//                Log.d("Form", "ReaderLoginScreen: $email $password")
//            }
//            Spacer(modifier = Modifier.height(15.dp))
////        Row(
////            modifier= Modifier.padding(15.dp),
////            horizontalArrangement= Arrangement.Center,
////            verticalAlignment= Alignment.CenterVertically
////        ){
////            val text2=if(showLoginForm.value) "Sign Up" else "Login"
////            val text1=if(showLoginForm.value) "New User ?" else "Have an account ?"
////            Text(text1)
////            Text(text2, modifier= Modifier
////                .clickable {
////                    showLoginForm.value = !showLoginForm.value
////                }
////                .padding(start = 5.dp),
////                fontWeight = FontWeight.Bold,
////                color= MaterialTheme.colorScheme.tertiary
////            )
////        }
//        }
//    }
//}
//
//
//
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun UserForm(loading: Boolean=false,
//             onDone:(String,String)-> Unit= {email, pwd ->}
//) {
//    val email = rememberSaveable { mutableStateOf("") }
//    val password= rememberSaveable { mutableStateOf("") }
//    val passwordVisibility= rememberSaveable{ mutableStateOf(false) }
//    val passwordFocusRequest= FocusRequester.Default
//    val keyboardController= LocalSoftwareKeyboardController.current
//    val valid= remember(email.value, password.value){
//        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
//    }
//
//    val modifier= Modifier
//        .height(300.dp)
//        .background(MaterialTheme.colorScheme.background)
//        .verticalScroll(rememberScrollState())
//    Column(modifier,
//        horizontalAlignment = Alignment.CenterHorizontally)
//    {
//        Text(text= "Enter your Credentials", modifier= Modifier.padding(4.dp) )
//
//        EmailInput(emailState = email,
//            enabled = !loading,
//            onAction = KeyboardActions{
//                passwordFocusRequest.requestFocus()
//            }, imeAction =ImeAction.Next
//        )
//        PasswordInput(modifier= Modifier.focusRequester(passwordFocusRequest),
//            passwordState=password,
//            labelId="Password",
//            enabled= !loading,
//            passwordVisibility=passwordVisibility,
//            onAction= KeyboardActions{
//                if(!valid) return@KeyboardActions
//                onDone(email.value.trim(), password.value.trim())
//            }
//        )
//
//        SubmitButton(
//            textId="Login",
//            loading=loading,
//            validInputs=valid,
//        ){
//            onDone(email.value.trim(), password.value.trim())
//            keyboardController?.hide()
//        }
//    }
//
//
//}
//
//@Composable
//fun SubmitButton(textId: String,
//                 loading: Boolean,
//                 validInputs: Boolean,
//                 onClick: ()-> Unit
//) {
//    Button(onClick=onClick,
//        modifier= Modifier
//            .padding()
//            .fillMaxWidth(),
//        enabled = !loading && validInputs,
//        shape= CircleShape
//    ){
//        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
//        else Text(text=textId, modifier= Modifier.padding(5.dp))
//    }
//
//}