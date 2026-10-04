package com.anchor.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anchor.data.UserProfileStore
import com.anchor.domain.profile.UserProfile
import com.anchor.ui.profile.ProfilePreferencesForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val context = LocalContext.current
    val store = remember { UserProfileStore(context) }
    var profile by remember { mutableStateOf(store.get() ?: UserProfile()) }

    fun finish(savedProfile: UserProfile) {
        store.save(savedProfile.copy(onboardingComplete = true))
        onComplete()
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Welcome to Anchor", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    "Anchor is a grounding and support prototype. It is not a diagnostic tool, treatment, or emergency service.",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text("Set optional preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                ProfilePreferencesForm(profile = profile, onChange = { profile = it })
            }
            Button(onClick = { finish(profile) }, modifier = Modifier.fillMaxWidth()) {
                Text("Save and continue")
            }
            TextButton(onClick = { finish(store.get() ?: UserProfile()) }, modifier = Modifier.fillMaxWidth()) {
                Text("Skip setup and use defaults")
            }
        }
    }
}
