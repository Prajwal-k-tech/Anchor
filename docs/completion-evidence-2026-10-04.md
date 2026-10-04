# Anchor completion evidence — 2026-10-04

This records the latest available checks for the completion changes based on
`main` at `8d4180f`. Anchor remains a prototype, not a diagnostic tool,
treatment, emergency service, or clinically evaluated product.

## Runtime verification

- On an API 35 emulator, first-run sensory choices were saved and remained
  after relaunch. The saved safety plan was displayed from the emergency
  safety-stop screen.
- Emulator captures were inspected for the home screen and populated safety
  plan dialog. These local captures are not repository assets.

## Build and tests

- JUnit XML reports from the prior successful isolated JDK 17 / Android API
  35 run show `testDebugUnitTest`: 415 tests, 0 failures, 0 errors, 0 skipped.
- The same prior run's XML reports show `testReleaseUnitTest`: 415 tests,
  0 failures, 0 errors, 0 skipped.
- The same run produced `app/build/outputs/apk/debug/app-debug.apk`.
- Separately, a fresh Gradle run could not reach the task phase in this
  sandbox: the default Gradle user home is read-only, and direct offline
  Gradle stopped during machine/IP detection. No fresh task result is claimed;
  the counts above are report-derived from the prior successful run.
- `git diff --check` passes for the completion changes.

The implemented flows store profile preferences, comfort-tool selections,
episodes, outcomes, and the safety plan on-device. The safety plan is
display-only and does not initiate calls or messages. No clinical efficacy
claim is made.
