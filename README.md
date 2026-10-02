# Anchor

Anchor is an Android grounding and stabilization prototype built with Kotlin and Jetpack Compose. It explores a direct haptic grounding flow, on-device visual grounding, and optional ways to reach a trusted contact.

The project was developed by **Team Hogorithm** for [IEEE MACE's >.hack(); '26](https://hack26.ieeemace.org/), where the team received the **Best Software** award. This repository is Prajwal Kumar K's contribution fork of the [shared team repository](https://github.com/NiranjanRSoorej06/Anchor).

Anchor is a prototype, not a diagnostic or treatment tool, a clinically evaluated product, or an emergency service.

## What is implemented

| Area | Current state |
| --- | --- |
| ANCHOR NOW | A direct session flow with tactile grounding and a Better/Same/Worse check-in. |
| Camera-assisted grounding | Captures one frame in memory and labels it on-device with the bundled ML Kit model. The frame is discarded after labeling; capture or permission failures use a text fallback. |
| Support tools | Bundled support information and map intents. Opening a map or web resource leaves the app and may require connectivity. |
| Companion Mode | Optional direct SMS to configured trusted contacts when the emergency flow is triggered. It can include last-known coordinates if location sharing is enabled. SMS and location permissions are requested for these features. |
| Home-screen widget and volume trigger | Prototype entry points. The volume trigger requires the user to enable the accessibility service and is scoped to screen-on or locked-but-awake use; deep-sleep reliability is not claimed. |
| Domain modules | Tested Kotlin models include safety filtering, intervention routing, routines, onboarding, profile and episode data, triage, safety plans, follow-up, and personalization. Some are not connected to complete screens or persistent storage. |

The core grounding flow and bundled image-labeling model do not require an internet connection. External map destinations and SMS use Android's other apps and services. Anchor has no backend or cloud database. Optional spoken delivery uses Android TextToSpeech; its availability and offline behavior depend on the device's installed speech engine.

### Still in progress

The onboarding, profile, safety-plan, and routine-builder screens are incomplete. Profile, routine, and episode persistence is not fully wired. Scheduled follow-up notifications and the full support-directory flow also remain in progress. The repository's [technical feature inventory](docs/technical-features.md) distinguishes implemented runtime behavior from domain foundations and developer tools.

## Prajwal's contributions

My contributions in this team repository include:

- Implementing and testing the Kotlin safety filter, intervention router, content catalog, and routing inspection screen.
- Building domain models and tests for routines, onboarding, profiles, episode history, safety plans, support lookup, follow-up, triage, medication reminders, and goals.
- Contributing Android screens, navigation, theme work, and research-informed product and safety documentation.

The [repository history](https://github.com/Prajwal-k-tech/Anchor/commits/main/?author=prajwal-k-tech) shows these contributions in context; they do not represent sole authorship of Anchor. The Best Software award belongs to the team.

## Screenshots

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/a250685b-75a8-40a0-9003-601dd074ca4e" alt="Anchor home screen" width="180"></td>
    <td><img src="https://github.com/user-attachments/assets/e09f6e06-d389-4053-ab0f-ac9bd16357e7" alt="Anchor grounding session" width="180"></td>
    <td><img src="https://github.com/user-attachments/assets/54755aaa-3951-45e5-8dc6-2caa2a983fb1" alt="Anchor support screen" width="180"></td>
    <td><img src="https://github.com/user-attachments/assets/c4127141-c8aa-434d-ba06-7a3c3fbeeb90" alt="Anchor app screen" width="180"></td>
  </tr>
</table>

## Build and run

### Requirements

- Android Studio
- JDK 17
- Android SDK 35
- Android device or emulator running API 26 or later

```sh
git clone https://github.com/Prajwal-k-tech/Anchor.git
cd Anchor
./gradlew assembleDebug
```

Run the JVM test suite with:

```sh
./gradlew test
```

The domain tests do not establish clinical effectiveness or validate behavior on every Android device. The accessibility volume trigger needs separate real-device checks, especially for screen-off and deep-sleep behavior.

## Permissions and privacy boundaries

The manifest declares vibration, camera, SMS, location, audio-recording, notification, wake-lock, and lock-screen permissions for optional app features. The app requests sensitive permissions in their feature flows. Companion Mode is distinct from the default grounding flow: when enabled, an emergency trigger can dispatch a background SMS without a second confirmation. Location is optional. The default grounding session does not automatically call emergency services.

Anchor does not collect a trauma narrative or upload camera frames. This README describes the current code, not a privacy certification or clinical assessment. See [the product vision](docs/vision.md), [technical feature inventory](docs/technical-features.md), and [evidence notes](evidence.md) for scope and source references.

## License

See [LICENSE](LICENSE). The repository currently uses an all-rights-reserved license.
