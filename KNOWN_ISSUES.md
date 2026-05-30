# Known Issues & Observations

## AppFunctions Reliability

### Intent Parsing
- **Ambiguous commands** — Gemini may misroute "finish my review PR" (is "finish" a task action or conversational?) vs. "mark 'review PR' as done". Clearer KDoc helps but doesn't eliminate ambiguity.
- **Partial title matching** — `completeTask` requires an exact title match. If the user says "complete reviewing PR" but the task was added as "review PR", it won't match. Fuzzy matching would need a custom search layer.
- **Multiple tasks with similar names** — Only the first matching incomplete task gets completed. No disambiguation prompt is provided.

### Parameter Extraction
- **Extra words get included** — Gemini sometimes includes filler words in the `title` parameter (e.g., "a task called review PR" → title becomes "a task called review PR" instead of "review PR").
- **Punctuation handling** — Voice-to-text may add periods or commas to task titles.

## Platform Requirements

| Requirement | Detail |
|---|---|
| **compileSdk** | 36 (Android 16) |
| **Device/Emulator** | API 36+ required for AppFunctions indexing |
| **Gemini integration** | Private preview — requires supported device (Pixel 10, Galaxy S26+) |
| **EXECUTE_APP_FUNCTIONS** permission | Only granted to system-level agents like Gemini |

## Edge Cases

- **Empty title** — Handled gracefully, returns error string
- **Duplicate task names** — Both get stored; `completeTask` marks the first incomplete match
- **No tasks for today** — Returns friendly "no tasks" message
- **Database on first cold launch** — Room builds DB asynchronously; first AppFunction call may have slight delay

## What Works Well

- Simple, imperative commands: *"Add buy groceries to my tasks"*
- Direct completions: *"Complete buy groceries"*
- Summary requests: *"Summarize my tasks for today"*
- All execution is **on-device** — no network latency, data stays local
