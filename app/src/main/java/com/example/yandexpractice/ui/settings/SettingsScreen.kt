package com.example.yandexpractice.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandexpractice.R
import com.example.yandexpractice.ui.theme.YandexPracticeTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val shareMessage = stringResource(id = R.string.settings_share_message)
    val developerEmail = stringResource(id = R.string.developer_email)
    val emailSubject = stringResource(id = R.string.developer_email_subject)
    val emailBody = stringResource(id = R.string.developer_email_body)
    val termsUrl = stringResource(id = R.string.terms_url)

    LaunchedEffect(Unit) {
        viewModel.initState(
            shareMessage = shareMessage,
            developerEmail = developerEmail,
            emailSubject = emailSubject,
            emailBody = emailBody,
            termsUrl = termsUrl
        )
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.shareApp(context) },
                enabled = state != null
            ) {
                Text(text = stringResource(id = R.string.settings_share))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.contactDevelopers(context) },
                enabled = state != null
            ) {
                Text(text = stringResource(id = R.string.settings_feedback))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.openTerms(context) },
                enabled = state != null
            ) {
                Text(text = stringResource(id = R.string.settings_terms))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    YandexPracticeTheme {
        SettingsScreen()
    }
}

