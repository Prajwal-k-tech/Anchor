package com.anchor.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anchor.domain.profile.UserProfile
import kotlin.math.roundToInt

@Composable
internal fun ProfilePreferencesForm(
    profile: UserProfile,
    onChange: (UserProfile) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "These choices are optional and stay on this device. You can change them at any time.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        PreferenceSwitch(
            title = "Sound is okay",
            detail = "Allow spoken guidance and optional calming audio.",
            checked = profile.audioOk,
            onCheckedChange = { onChange(profile.copy(audioOk = it, voiceOk = profile.voiceOk && it)) },
        )
        PreferenceSwitch(
            title = "Spoken guidance is okay",
            detail = "Used only when sound is also allowed.",
            checked = profile.voiceOk,
            enabled = profile.audioOk,
            onCheckedChange = { onChange(profile.copy(voiceOk = it)) },
        )
        PreferenceSwitch(
            title = "Touch feels sensitive",
            detail = "Recommendations avoid haptic techniques, and the direct session pauses its haptics.",
            checked = profile.touchSensitive,
            onCheckedChange = { onChange(profile.copy(touchSensitive = it)) },
        )
        PreferenceSwitch(
            title = "Prefer a darker screen",
            detail = "Use the dark palette, even when your device uses a light theme.",
            checked = profile.reducedVisual,
            onCheckedChange = { onChange(profile.copy(reducedVisual = it)) },
        )

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Haptic intensity", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(
                    if (profile.hapticIntensity == 0f) "Off" else "${(profile.hapticIntensity * 100).roundToInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Slider(
                    value = profile.hapticIntensity,
                    onValueChange = { onChange(profile.copy(hapticIntensity = it)) },
                    valueRange = 0f..1f,
                    steps = 4,
                )
            }
        }
    }
}

@Composable
private fun PreferenceSwitch(
    title: String,
    detail: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(checked = checked, enabled = enabled, onCheckedChange = onCheckedChange)
        }
    }
}
