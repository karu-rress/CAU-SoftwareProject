# Picture Search — Refactored Version

This project is a refactored version of the Picture Search application developed in [Assignment 6](/assignment-6) for the Software Project course.

Rather than introducing new user-facing features, this version focuses on simplifying the existing implementation and refining its code structure through several targeted changes.

## Key Changes

- **Code Cleanup:** Removed unnecessary methods to reduce redundant code and simplify the implementation.
- **Record-Based Tag Model:** Refactored the `Tag` class into a Java record to represent tag data more concisely.
- **Interface-Based Printing:** Introduced a printing interface to define a common contract for printing operations.
- **Serialization Support:** Introduced the `Serializable` interface to support Java object serialization where applicable.

## Requirements

- JDK 21
- Java Swing (included in the JDK)

## Usage

Run the following commands from the `final` directory, assuming the Java source files are located in `src/`.

### Compile

```bash
javac -d out src/*.java
```

### Run

```bash
java -cp out Test
```

The application uses the data files and image paths referenced by its picture records. Ensure that the required files are accessible from the working directory.
