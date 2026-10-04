package com.anchor.ui.safetyplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anchor.data.SafetyPlanStore
import com.anchor.domain.safetyplan.SafetyPlan
import com.anchor.domain.safetyplan.SafetyPlanValidator
import com.anchor.domain.safetyplan.ValidationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyPlanScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val store = remember { SafetyPlanStore(context) }
    val savedPlan = remember { store.get() }
    var warningSigns by remember { mutableStateOf(savedPlan?.warningSigns.orEmpty().joinToString("\n")) }
    var copingStrategies by remember { mutableStateOf(savedPlan?.copingStrategies.orEmpty().joinToString("\n")) }
    var socialDistraction by remember { mutableStateOf(savedPlan?.socialDistraction.orEmpty().joinToString("\n")) }
    var helpContacts by remember { mutableStateOf(savedPlan?.helpContacts.orEmpty().joinToString("\n")) }
    var professionals by remember { mutableStateOf(savedPlan?.professionals.orEmpty().joinToString("\n")) }
    var meansRestriction by remember { mutableStateOf(savedPlan?.meansRestriction.orEmpty().joinToString("\n")) }
    var validationError by remember { mutableStateOf<String?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    fun entries(text: String) = text.lines().map(String::trim).filter(String::isNotEmpty)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal safety plan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    "Entries stay on this device. Add one item per line, up to 10 per section. The first five sections need at least one item to save. This page only saves and displays notes; it never calls or texts anyone.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                PlanField("Warning signs", warningSigns, { warningSigns = it; validationError = null }, "One item per line")
                PlanField("Things I can do on my own", copingStrategies, { copingStrategies = it; validationError = null }, "One item per line")
                PlanField("People or places for distraction", socialDistraction, { socialDistraction = it; validationError = null }, "One item per line")
                PlanField("People I can ask for help", helpContacts, { helpContacts = it; validationError = null }, "One item per line")
                PlanField("Professionals or agencies", professionals, { professionals = it; validationError = null }, "Use contact details you have checked")
                PlanField("Ways to make my environment safer (optional)", meansRestriction, { meansRestriction = it; validationError = null }, "One item per line")
                validationError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
            Button(
                onClick = {
                    val plan = SafetyPlan(
                        warningSigns = entries(warningSigns),
                        copingStrategies = entries(copingStrategies),
                        socialDistraction = entries(socialDistraction),
                        helpContacts = entries(helpContacts),
                        professionals = entries(professionals),
                        meansRestriction = entries(meansRestriction),
                    )
                    when (val result = SafetyPlanValidator.validate(plan)) {
                        ValidationResult.Valid -> {
                            store.save(plan)
                            onBack()
                        }
                        is ValidationResult.Invalid -> validationError = result.reasons.joinToString("\n")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Save plan")
            }
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
            if (savedPlan != null) {
                TextButton(onClick = { showDeleteConfirmation = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("Delete saved plan", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete saved plan?") },
            text = { Text("This removes the safety-plan notes stored by Anchor on this device.") },
            confirmButton = {
                TextButton(onClick = {
                    store.clear()
                    showDeleteConfirmation = false
                    onBack()
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) { Text("Cancel") }
            },
        )
    }
}

@Composable
private fun PlanField(label: String, value: String, onValueChange: (String) -> Unit, hint: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(hint) },
        modifier = Modifier.fillMaxWidth(),
        minLines = 2,
        maxLines = 5,
    )
}
