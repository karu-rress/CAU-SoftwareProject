# Assignment 3: Picture/Stuff Search Program (Picture Search)

This assignment implements the foundation of a picture management application that associates images with information about the objects appearing in them.

The program reads picture and object information from a data file, stores the information in memory using object-oriented classes, and displays the records in the console. It also supports saving the managed picture records to a file.

This implementation serves as the starting point for the subsequent GUI-based picture search projects.

## Requirements
- JDK 21 or higher

## Implementation

### 1. Object-Oriented Data Model
![Class design](/assets/class3.png)  
The program separates responsibilities into several classes.

- **`Picture`**: Represents a picture, including its ID, timestamp, image information, associated objects, tags, and comment.
- **`PictureList`**: Manages a collection of pictures using a fixed-size array. Supports retrieving, adding, and sorting picture records.
- **`Image`**: Stores image-related information, including its ID, file name, image name, and tags.
- **`Stuff`**: Represents an object appearing in a picture, including its ID, kind, name, and tags.
- **`StuffList`**: Maintains a shared collection of object records using static fields and methods.
- **`Tag`**: Represents an individual tag and provides methods for parsing and formatting tag collections.

### 2. File I/O and Parsing

- Reads picture records from a text file, with each line representing one picture.
- Parses the input into structured fields and creates the corresponding objects.
- Ignores comment lines beginning with `//`.
- Reports file access errors and malformed records to the console.
- Writes the managed picture records to a file through `PictureList.writeFile()`.

### 3. Picture and Object Management

- Assigns a timestamp and a generated ID when creating a picture from an image file name.
- Checks for duplicate picture IDs when adding records to `PictureList`.
- Supports sorting pictures by timestamp using a comparator.
- Maintains shared object records through `StuffList`, allowing object information to be managed independently of individual pictures.

### 4. Tags and Metadata
The `Tag` class encapsulates tag names and provides methods to parse tag strings and convert tag collections back into text.

Picture, image, and object records can include tags, while picture records can also contain comments.

## Testing

The `Test` class loads picture records from `picture-normal.data` and prints each successfully loaded picture to the console.

This test exercises the file-reading, parsing, object-construction, and console-output paths.

## Usage
Run the following commands to compile and execute the program:
```bash
$ javac -d out src/*.java
$ java -cp out Test
```
Note: The program expects the `picture-normal.data` file to be present in the working directory for testing. The output file `picture-test.output` will be created in the working directory after execution.
