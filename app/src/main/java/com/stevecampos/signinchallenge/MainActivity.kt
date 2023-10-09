package com.stevecampos.signinchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.stevecampos.core.designsystem.theme.SignInChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInChallengeTheme {
                AppNavigation()
            }
        }
    }
}