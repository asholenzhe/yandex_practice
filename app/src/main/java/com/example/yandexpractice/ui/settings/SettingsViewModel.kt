package com.example.yandexpractice.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.yandexpractice.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow<SettingsState?>(null)
    val state: StateFlow<SettingsState?> = _state.asStateFlow()

    fun initState(
        shareMessage: String,
        developerEmail: String,
        emailSubject: String,
        emailBody: String,
        termsUrl: String
    ) {
        _state.value = SettingsState(
            shareMessage = shareMessage,
            developerEmail = developerEmail,
            emailSubject = emailSubject,
            emailBody = emailBody,
            termsUrl = termsUrl
        )
    }

    fun shareApp(context: Context) {
        val state = _state.value ?: return
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, state.shareMessage)
        }
        safeStartActivity(context, Intent.createChooser(shareIntent, null))
    }

    fun contactDevelopers(context: Context) {
        val state = _state.value ?: return
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(state.developerEmail))
            putExtra(Intent.EXTRA_SUBJECT, state.emailSubject)
            putExtra(Intent.EXTRA_TEXT, state.emailBody)
        }
        safeStartActivity(context, Intent.createChooser(intent, null))
    }

    fun openTerms(context: Context) {
        val state = _state.value ?: return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.termsUrl))
        safeStartActivity(context, intent)
    }

    private fun safeStartActivity(context: Context, intent: Intent) {
        runCatching {
            context.startActivity(intent)
        }.onFailure {
            Toast.makeText(
                context,
                context.getString(R.string.settings_open_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
