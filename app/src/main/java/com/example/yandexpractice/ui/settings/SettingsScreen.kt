package com.example.yandexpractice.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yandexpractice.R
import com.example.yandexpractice.ui.theme.YandexPracticeTheme

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val shareText = stringResource(id = R.string.settings_share_message)
    val recipient = stringResource(id = R.string.developer_email)
    val subject = stringResource(id = R.string.developer_email_subject)
    val body = stringResource(id = R.string.developer_email_body)
    val termsUrl = stringResource(id = R.string.terms_url)

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { shareApp(context, shareText) }
            ) {
                Text(text = stringResource(id = R.string.settings_share))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { contactDevelopers(context, recipient, subject, body) }
            ) {
                Text(text = stringResource(id = R.string.settings_feedback))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openTerms(context, termsUrl) }
            ) {
                Text(text = stringResource(id = R.string.settings_terms))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun shareApp(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.safeStartActivity(Intent.createChooser(shareIntent, null))
}

private fun contactDevelopers(
    context: Context,
    recipient: String,
    subject: String,
    body: String
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    context.safeStartActivity(Intent.createChooser(intent, null))
}

private fun openTerms(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.safeStartActivity(intent)
}

private fun Context.safeStartActivity(intent: Intent) {
    runCatching {
        startActivity(intent)
    }.onFailure {
        Toast.makeText(this, it.localizedMessage ?: "Не удалось открыть экран", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    YandexPracticeTheme {
        SettingsScreen()
    }
}

