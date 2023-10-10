package com.stevecampos.signin.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stevecampos.core.designsystem.theme.Black90
import com.stevecampos.core.designsystem.theme.Green40
import com.stevecampos.signin.presentation.R
import com.stevecampos.signin.presentation.welcome.PrimaryButton
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    state: SignUpScreenState,
    handleEvent: (SignUpScreenEvent) -> Unit,
    actions: SharedFlow<SignUpScreenAction>,
    navigateToHome: () -> Unit,
    onBackClicked: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        actions.collect { action ->
            when (action) {
                is SignUpScreenAction.NavigateToHome -> {
                    navigateToHome()
                }

                is SignUpScreenAction.DisplayDefaultError -> {
                    scope.launch {
                        Toast.makeText(context, R.string.msg_default_error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("Steve Campos") }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Black90)
    ) {
        IconButton(
            onClick = onBackClicked
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.msg_back),
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.bg_girl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .align(Alignment.TopEnd),
            contentScale = ContentScale.FillHeight
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 172.dp)
        ) {
            Text(
                text = stringResource(id = R.string.msg_sign_up),
                style = MaterialTheme.typography.h3.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 24.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 24.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.DarkGray.copy(.5f), shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top)
            ) {
                YouDontHaveAccountText(email = state.initialEmail)
                TextField(
                    value = name,
                    label = { Text(stringResource(id = R.string.msg_name)) },
                    onValueChange = {
                        name = it
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedLabelColor = Color.DarkGray,
                        focusedIndicatorColor = Green40,
                        cursorColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = state.password,
                    label = { Text(stringResource(id = R.string.msg_password)) },
                    onValueChange = {
                        handleEvent.invoke(SignUpScreenEvent.OnPasswordChanged(it))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedLabelColor = Color.DarkGray,
                        focusedIndicatorColor = Green40,
                        cursorColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Text(text = stringResource(id = if (isPasswordVisible) R.string.msg_hide else R.string.msg_show),
                            style = MaterialTheme.typography.overline,
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            })
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                TermsOfServiceText()

                PrimaryButton(text = stringResource(id = R.string.msg_agree_and_continue),
                    enabled = state.passwordMeetRequirements(),
                    onClick = { handleEvent.invoke(SignUpScreenEvent.OnSignUpButtonClicked) })
            }
        }
    }
}

@Composable
fun TermsOfServiceText() {
    val text = stringResource(id = R.string.msg_terms_of_service)

    val highlightText = "Terms of Service and Privacy Policy"

    val annotatedText = remember {
        buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold, color = Green40),
                start = text.indexOf(highlightText),
                end = text.indexOf(highlightText) + highlightText.length
            )
        }
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.body1.copy(
            color = Color.White,
            fontWeight = FontWeight.Light
        )
    )
}

@Composable
fun YouDontHaveAccountText(email: String) {
    val text = stringResource(id = R.string.msg_you_dont_have_an_account, email)

    val annotatedText = remember {
        buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold),
                start = text.indexOf(email),
                end = text.indexOf(email) + email.length
            )
        }
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.body1.copy(
            color = Color.White,
            fontWeight = FontWeight.Light
        )
    )
}

