package com.example.branchapp.components


//@Composable
//fun EmailInput(modifier: Modifier = Modifier,
//               emailState: MutableState<String>,
//               labelId: String="Email",
//               enabled: Boolean=true,
//               imeAction: ImeAction = ImeAction.Next,
//               onAction: KeyboardActions = KeyboardActions.Default
//
//){
//    InputField(modifier=modifier,
//        valueState = emailState,
//        labelId=labelId,
//        enabled=enabled,
//        keyboardType = KeyboardType.Email,
//        imeAction = imeAction,
//        onAction = onAction
//    )
//}
//
//@Composable
//fun InputField(
//    modifier: Modifier = Modifier,
//    valueState: MutableState<String>,
//    labelId: String,
//    enabled: Boolean,
//    isSingleLine: Boolean=true,
//    keyboardType: KeyboardType = KeyboardType.Text,
//    imeAction: ImeAction = ImeAction.Next,
//    onAction: KeyboardActions = KeyboardActions.Default
//
//) {
//    OutlinedTextField(
//        value=(valueState.value),
//        onValueChange ={ valueState.value = it},
//        label ={ Text(text=labelId) },
//        singleLine = isSingleLine,
//        textStyle = TextStyle(fontSize = 18.sp,
//            color= MaterialTheme.colorScheme.onBackground),
//        modifier= Modifier
//            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
//            .fillMaxWidth(),
//        enabled=enabled,
//        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
//        keyboardActions = onAction,
//    )
//}
//
//
//@Composable
//fun PasswordInput(modifier: Modifier,
//                  passwordState: MutableState<String>,
//                  labelId: String,
//                  enabled: Boolean,
//                  passwordVisibility: MutableState<Boolean>,
//                  onAction: KeyboardActions = KeyboardActions.Default,
//                  imeAction: ImeAction = ImeAction.Done) {
//    val visualTransformation=if(passwordVisibility.value) VisualTransformation.None else
//        PasswordVisualTransformation()
//
//    OutlinedTextField(value = passwordState.value,
//        onValueChange ={
//            passwordState.value=it} ,
//        label={ Text(text=labelId) },
//        singleLine=true,
//        textStyle = TextStyle(fontSize=18.sp, color= MaterialTheme.colorScheme.onBackground),
//        modifier= modifier
//            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
//            .fillMaxWidth(),
//        enabled=enabled,
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Password,
//            imeAction = imeAction,
//        ),
//        visualTransformation = visualTransformation, trailingIcon = {PasswordVisibility(passwordVisibility)},
//        keyboardActions = onAction
//    )
//
//}
//
//@Composable
//fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
//    val visible=passwordVisibility.value
//    IconButton(onClick = { passwordVisibility.value= !visible}) {
//        Icons.Default.Close
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppBar(title: String, icon: ImageVector?=null,
//           showProfile:Boolean=true,
//           navController: NavController,
//           onBackArrowClicked: ()->Unit={}
//){
//    TopAppBar(title = {
//        Row(verticalAlignment = Alignment.CenterVertically){
//            Text(text = title, modifier = Modifier.padding(start=20.dp), color = Color.Red.copy(alpha = 0.7f),
//                style= TextStyle( fontWeight = FontWeight.Bold, fontSize = 25.sp))
//            Spacer(modifier=Modifier.width(150.dp))
//
//        }
//
//    }
//    )
//}