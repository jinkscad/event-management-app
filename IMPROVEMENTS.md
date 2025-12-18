# Event Management App - Improvement Recommendations

This document outlines areas for improvement identified during code review.

## 🔴 Critical Issues

### 1. **Bug in Event.java Setters (Lines 225, 229)**
**Location:** `code/app/src/main/java/com/example/chicksevent/misc/Event.java`

**Issue:** The setters for `setEventStartTime()` and `setEventEndTime()` have incorrect parameter usage:
```java
// Current (WRONG):
public void setEventStartTime(String eventDate) { this.eventStartTime = eventStartTime; }
public void setEventEndTime(String eventDate) { this.eventEndTime = eventEndTime; }

// Should be:
public void setEventStartTime(String eventStartTime) { this.eventStartTime = eventStartTime; }
public void setEventEndTime(String eventEndTime) { this.eventEndTime = eventEndTime; }
```

**Impact:** These setters don't actually set the values, causing data loss.

### 2. **Memory Leaks - Firebase Listeners Not Removed**
**Location:** Multiple fragments using `addValueEventListener()`

**Issue:** Several fragments add Firebase listeners but never remove them:
- `WaitingListFragment.java` (line 177)
- `PoolingFragment.java` (line 234)
- `FinalListFragment.java` (line 177)
- `ChosenListFragment.java` (line 199)
- `CancelledListFragment.java` (line 177)
- `Organizer.java` (line 111)

**Impact:** Listeners continue to receive updates after fragments are destroyed, causing memory leaks and potential crashes.

**Fix:** Store listener references and remove them in `onDestroyView()`:
```java
private ValueEventListener listener;

// In onViewCreated:
listener = new ValueEventListener() { ... };
ref.addValueEventListener(listener);

// In onDestroyView:
if (listener != null) {
    ref.removeEventListener(listener);
}
```

### 3. **Hardcoded Firebase URL**
**Location:** `code/app/src/main/java/com/example/chicksevent/misc/FirebaseService.java:47`

**Issue:** Firebase database URL is hardcoded:
```java
database = FirebaseDatabase.getInstance("https://listycity-friedchicken-default-rtdb.firebaseio.com/");
```

**Recommendation:** Use `FirebaseDatabase.getInstance()` without URL (uses default from google-services.json) or move to build config.

## 🟡 High Priority Issues

### 4. **Error Handling - printStackTrace() Usage**
**Location:** Multiple files

**Issue:** 12 instances of `printStackTrace()` found instead of proper logging:
- `QRCodeGenerator.java` (lines 61, 91)
- `UpdateEventFragment.java` (lines 308, 525)
- `EventDetailOrgFragment.java` (lines 339, 391)
- `EventDetailFragment.java` (lines 486, 905)
- `CreateEventFragment.java` (line 462)
- `NotificationAdapter.java` (line 132)
- `HostedEventAdapter.java` (line 167)
- `EventAdapter.java` (line 164)

**Fix:** Replace with proper logging:
```java
// Instead of: e.printStackTrace();
Log.e(TAG, "Error description", e);
```

### 5. **Excessive Debug Logging**
**Issue:** 262 log statements across 36 files. Many are debug logs that should be removed or conditionally compiled.

**Recommendation:**
- Remove debug logs in production code
- Use a logging utility that can be disabled in release builds
- Keep only error and important info logs

### 6. **Code Duplication - Event Validation Logic**
**Location:** `CreateEventFragment.java` and `UpdateEventFragment.java`

**Issue:** Nearly identical validation logic (~200 lines) duplicated between these two fragments.

**Recommendation:** Extract to a shared utility class:
```java
public class EventValidationHelper {
    public static ValidationResult validateEventForm(...) { ... }
}
```

### 7. **Missing ProGuard Rules**
**Location:** `code/app/app/proguard-rules.pro`

**Issue:** ProGuard rules are minimal. Release build has `isMinifyEnabled = false`.

**Recommendation:**
- Enable minification for release builds
- Add ProGuard rules for Firebase, OSMDroid, ZXing, Glide
- Keep line numbers for crash reports

### 8. **Hardcoded URLs**
**Location:** `code/app/src/main/java/com/example/chicksevent/fragment_org/CsvExportHelper.java:4`

**Issue:** Cloud function URL is hardcoded:
```java
private static final String BASE_URL = "https://us-central1-listycity-friedchicken.cloudfunctions.net/exportFinalEntrants";
```

**Recommendation:** Move to `strings.xml` or build config.

## 🟢 Medium Priority Issues

### 9. **Incomplete Features (TODOs)**
**Location:** Multiple files

**Issues:**
- `UpdateEventFragment.java` (lines 478-479): `eventStartDate` and `eventEndDate` are null with TODO comment
- `CreateEventFragment.java` (lines 415-416): Same issue
- `Admin.java` (line 173): Admin profile browsing not implemented
- `fragment_event_detail_org.xml` (line 277): "TODO" text in layout

**Recommendation:** Either implement these features or remove the incomplete code.

### 10. **Inconsistent Error Messages**
**Issue:** Error messages are hardcoded strings instead of using string resources.

**Recommendation:** Move all user-facing strings to `res/values/strings.xml` for:
- Internationalization support
- Easier maintenance
- Consistency

### 11. **Null Safety Issues**
**Location:** Multiple fragments

**Issue:** Some places use `getContext()` which can return null, while others use `requireContext()`.

**Recommendation:** Consistently use `requireContext()` when context is required, or add null checks.

### 12. **Date Formatting - Thread Safety**
**Location:** `EventDetailFragment.java:897`

**Issue:** `SimpleDateFormat` is not thread-safe and is created locally each time.

**Recommendation:** Use `DateTimeFormatter` (API 26+) or make `SimpleDateFormat` static/thread-local:
```java
private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = 
    ThreadLocal.withInitial(() -> new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH));
```

### 13. **Missing Input Validation**
**Issue:** Some user inputs may not be validated (e.g., event name length, description length).

**Recommendation:** Add validation for:
- Maximum string lengths
- Special character restrictions
- SQL injection prevention (if applicable)

### 14. **Log Tag Inconsistency**
**Issue:** Log tags vary: `"FirestoreTest"`, `"RTD8"`, `"EventDetail"`, `TAG`, etc.

**Recommendation:** Use consistent naming: `ClassName.class.getSimpleName()` or `private static final String TAG = "ClassName"`.

## 🔵 Low Priority / Nice to Have

### 15. **Build Configuration**
- Consider using version catalogs more consistently
- Some dependencies are duplicated (e.g., Firebase Database appears twice in build.gradle.kts)

### 16. **Documentation**
- README.md is minimal (only 2 lines)
- Consider adding:
  - Setup instructions
  - Architecture overview
  - Contributing guidelines
  - Known issues

### 17. **Testing**
- Good test coverage exists, but could add:
  - Integration tests for Firebase operations
  - UI tests for critical user flows
  - Performance tests

### 18. **Code Organization**
- Consider extracting common utility methods (e.g., `s()` helper method appears in multiple fragments)
- Create a base fragment class for common functionality

### 19. **Resource Management**
- Some image loading could benefit from better caching strategies
- Consider using `Coil` instead of Glide for smaller footprint (optional)

### 20. **Security**
- Review Firebase security rules
- Consider adding certificate pinning for network requests
- Review permission usage (location, camera)

## 📊 Summary Statistics

- **Critical Issues:** 3
- **High Priority:** 5
- **Medium Priority:** 6
- **Low Priority:** 6
- **Total Issues Identified:** 20

## 🎯 Recommended Action Plan

1. **Immediate (This Week):**
   - Fix Event.java setter bugs (#1)
   - Fix Firebase listener memory leaks (#2)
   - Replace printStackTrace() with proper logging (#4)

2. **Short Term (This Month):**
   - Extract duplicate validation logic (#6)
   - Enable ProGuard and add rules (#7)
   - Move hardcoded strings to resources (#10)

3. **Long Term (Next Sprint):**
   - Complete TODO items or remove them (#9)
   - Improve documentation (#16)
   - Add missing tests (#17)

---

*Generated from code review on: $(date)*

