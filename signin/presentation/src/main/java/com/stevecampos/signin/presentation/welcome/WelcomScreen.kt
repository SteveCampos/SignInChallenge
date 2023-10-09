package com.stevecampos.signin.presentation.welcome

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stevecampos.signin.domain.entities.User
import com.stevecampos.signin.presentation.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    state: WelcomeScreenState,
    handleEvent: (WelcomeScreenEvent) -> Unit,
    actions: SharedFlow<WelcomeScreenAction>,
    navigateToLogin: (User) -> Unit,
    navigateToSignUp: (String, Boolean) -> Unit,
    onBackPressed: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        actions.collect { action ->
            when (action) {
                is WelcomeScreenAction.NavigateToLogin -> {
                    navigateToLogin(action.user)
                }

                is WelcomeScreenAction.NavigateToSignUp -> {
                    navigateToSignUp(action.email, action.canEditEmail)
                }

                is WelcomeScreenAction.DisplayMessage -> {
                    scope.launch {
                        Toast.makeText(context, R.string.msg_default_error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color(0xff1B1517))
    ) {

        IconButton(
            onClick = onBackPressed
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
                text = stringResource(id = R.string.msg_hi),
                style = MaterialTheme.typography.h3.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 24.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 24.dp)
                    .fillMaxSize()
                    .background(
                        color = Color.DarkGray.copy(.5f), shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top)
            ) {
                TextField(
                    value = state.email,
                    label = { Text(stringResource(id = R.string.msg_email)) },
                    onValueChange = {
                        handleEvent.invoke(WelcomeScreenEvent.OnEmailChanged(it))
                    },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    enabled = state.emailMeetRequirements(),
                    onClick = { handleEvent.invoke(WelcomeScreenEvent.OnSubmitEmailButtonClicked) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        stringResource(id = R.string.msg_continue),
                        style = MaterialTheme.typography.button.copy(color = Color.White),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.msg_or),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                SocialButton(text = stringResource(id = R.string.msg_facebook_signin),
                    drawableResId = R.drawable.ic_facebook,
                    onClick = { handleEvent.invoke(WelcomeScreenEvent.OnFacebookButtonClicked) })
                SocialButton(text = stringResource(id = R.string.msg_google_signin),
                    drawableResId = R.drawable.ic_google,
                    onClick = { handleEvent.invoke(WelcomeScreenEvent.OnGoogleButtonClicked) })
                SocialButton(text = stringResource(id = R.string.msg_apple_signin),
                    drawableResId = R.drawable.ic_apple,
                    onClick = { handleEvent.invoke(WelcomeScreenEvent.OnAppleButtonClicked) })

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.msg_dont_have_an_account),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.msg_sign_up),
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.clickable(onClick = {
                            handleEvent.invoke(
                                WelcomeScreenEvent.OnSignUpLinkClicked
                            )
                        })
                    )
                }
                Text(
                    text = stringResource(id = R.string.msg_forgot_your_password),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.clickable(onClick = { handleEvent.invoke(WelcomeScreenEvent.OnForgetPasswordLinkClicked) })
                )
            }
        }
    }
}

@Composable
fun SocialButton(
    text: String,
    @DrawableRes drawableResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffE4FAF2))
    ) {
        Box(
            Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(drawableResId),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart),
                contentDescription = null
            )

            Text(
                text = text,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(state = WelcomeScreenState(email = "steve.campos@email.com"),
        handleEvent = {},
        actions = MutableSharedFlow(replay = 0),
        navigateToLogin = {},
        navigateToSignUp = { _, _ -> },
        onBackPressed = {})
}