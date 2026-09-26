# College Course & Examination Management System

This is a Java console application we built for managing college academics - students, courses, faculty, exams and results. Everything runs in the terminal.

## How to Run

You need Java 17 or higher installed.

```
javac -d out -sourcepath src src/com/college/management/Main.java
java -cp out com.college.management.Main
```

## What it Does

- **Student Management** - register students, update their info, delete them, search by name/roll number/department
- **Course Management** - add courses with credits and capacity limits, different types (Core, Elective, Lab)
- **Faculty Management** - add faculty, assign them to courses
- **Enrollment** - enroll students in courses (checks if course is full and prevents duplicate enrollment)
- **Exams & Marks** - create exams (midterm/final/internal/practical), enter marks with validation
- **Results** - auto-calculates grades (A/B/C/D/F based on percentage), shows toppers, department wise summary
- **Reports** - export results to CSV, pass/fail stats
- **Search** - search across students, courses and faculty using keywords

## Data Storage

All data gets saved to CSV files in the `data/` folder when you exit. Next time you open the app it loads everything back. No database needed.

## Project Structure

```
src/com/college/management/
├── Main.java                     - entry point
├── model/                        - entity classes (Student, Course, Faculty, etc)
│   └── enums/                    - CourseType, ExamType, EnrollmentStatus
├── interfaces/                   - Searchable, Displayable, Exportable
├── exception/                    - custom exceptions (5 classes)
├── repository/                   - data storage using different collections
├── service/                      - business logic
├── ui/                           - menu classes for CLI
└── util/                         - helper classes (file I/O, table formatting, pair, validation)
```

50 source files total.

## Java Concepts Used

Here's what Java concepts we covered and where:

**OOP & Inheritance**
- `Person` is an abstract class, `Student` and `Faculty` extend it
- All fields are private with getters/setters (encapsulation)
- `equals()` and `hashCode()` overridden in entity classes

**Interfaces**
- `Searchable<T>` - for keyword search across entities
- `Displayable` - for formatting output
- `Exportable` - for CSV export

**Abstract Classes**
- `Person` - shared fields for Student and Faculty
- `BaseRepository<T extends Displayable>` - generic base for repositories

**Generics & Bounded Types**
- `BaseRepository<T extends Displayable>` - bounded type parameter
- `Pair<K, V>` - generic pair for holding two values
- `Searchable<T>` - generic interface

**Collections**
- `ArrayList` - for StudentRepository, FacultyRepository (general purpose)
- `HashMap` - for CourseRepository (fast lookup by course code)
- `LinkedList` - for EnrollmentRepository
- `TreeSet` - for ResultRepository (auto-sorts results by percentage)
- `TreeMap` - for department wise reports (sorted keys)

**Comparable**
- `Student`, `Course`, `Result` implement `Comparable` for natural ordering

**Exception Handling**
- 3 checked exceptions: `CourseFullException`, `DuplicateEnrollmentException`, `InvalidMarksException`
- 2 unchecked exceptions: `StudentNotFoundException`, `ExamNotFoundException`
- Services throw exceptions, menus catch them and show error messages

**Other**
- Arrays: `double[] marksheet` in Exam class
- Enums: CourseType, ExamType, EnrollmentStatus
- Control flow: switch-case menus, if-else for grades, while loops
- File I/O with try-with-resources in FileStorageUtil
- `LocalDate` for dates

## Architecture

3 layers:
1. **UI Layer** (menus) - handles user input, displays output
2. **Service Layer** - business logic, validation, throws exceptions
3. **Data Layer** (repositories) - stores data in collections, handles file persistence

Services get their repositories through constructor parameters. One shared Scanner used across all menus.

### Architecture Diagrams

- [Complete System Architecture](https://miro.com/app/board/uXjVHiGs0cI=/?share_link_id=480792921128)
- [Database Schema Architecture](https://dbdiagram.io/d/College-Course-andamp;-Examination-Management-System-6ab65e1558694256129453d0)

## Team

| Member | What they built |
|--------|----------------|
| Member 1 | Foundation stuff - Person, Student, interfaces, BaseRepository, Main.java |
| Member 2 | Course and Enrollment - Course entity, HashMap repo, enrollment with checks |
| Member 3 | Faculty and Course Assignment - Faculty entity, CourseAssignment, FacultyService |
| Member 4 | Exams and Marks - Exam (with arrays), Mark, validation |
| Member 5 | Results and Reports - Result with TreeSet, grade calculation, CSV export, reports |

