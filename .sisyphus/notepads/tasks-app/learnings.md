## Project Learnings

## 2026-03-05 Initial Analysis
- Android Java project (not Kotlin), Java 11, compileSdk 36, minSdk 34
- Uses ViewBinding (enabled in build.gradle.kts)
- Uses AndroidX Navigation (fragment + ui)
- Uses Material Components (CoordinatorLayout, AppBarLayout, MaterialToolbar, FloatingActionButton)
- Standard Navigation template: FirstFragment <-> SecondFragment
- Existing model classes: Task.java (has constructor bug), Subject.java
- Task has: id, title, Subject, deadline (long), duration (int), status (boolean)
- Subject has: name (String)
- Task constructor bug: param named "dedline" but assigns "deadline" field (uses wrong variable)
- Task ID generation broken: LocalTime.now() cannot be parsed to int
- FAB already exists in activity_main.xml with email icon
- MainActivity is bare-bones, missing imports for AppCompatActivity
- Package: com.example.tasks
