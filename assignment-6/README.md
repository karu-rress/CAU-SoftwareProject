# Assignment 6: Picture Search Final Implementation

![Usage 1](/assets/usage6-1.png)  
This assignment completes the Picture Search application by adding event handling and interactive picture management to the GUI developed in [Assignment 5](/assignment-5/).

The application loads picture records from a data file, displays them in chronological order, and supports adding, deleting, searching, loading, and saving picture information. The GUI is implemented using Java Swing, with event listeners connecting user actions to the underlying data model.

The program also supports cumulative searching, allowing users to refine the results of previous searches without reloading the original data.


## Requirements
- JDK 21 or higher
- Java Swing

## Implementation

### 1. GUI Architecture
![Class diagram](/assets/class6.png)  
The application separates data management from presentation by using model classes and GUI wrapper classes.

- **`MainFrame`**: Provides the main window and coordinates picture management operations, file I/O, and communication with other frames.
- **`PictureSection`**: Displays a collection of picture panels and manages picture selection.
- **`PicturePanel`**: Displays an individual picture's timestamp, tags, image, associated objects, and comment.
- **`StuffPanel`**: Displays or accepts input for an object's type, name, and tags. Its editability is configurable through its constructor.
- **`AddFrame`**: Provides a form for entering picture information and selecting an image file.
- **`SearchFrame`**: Provides time-based and keyword-based search controls.
- **`PictureList`**: Manages picture records, including sorting, filtering, insertion, deletion, and file operations.
- **`Picture`**, **`Image`**, **`Stuff`**, and **`Tag`**: Represent the individual elements of picture records.
- **`StuffList`**: Maintains shared object information.

The GUI classes wrap and present the underlying data model. For example, `PictureSection` manages `PicturePanel` instances, while each `PicturePanel` contains the information associated with a `Picture`.

### 2. Event-Driven Programming

The application uses Java's event-handling mechanism to connect GUI components to application logic.

- **`ActionListener`** handles button clicks in the main, input, and search windows.
- **`MouseAdapter`** handles picture selection in `PictureSection`.
- **`JFileChooser`** allows users to select image files and data files.

When a picture panel is selected, its border changes to red. Selecting another picture updates the selection indicator. The DELETE operation checks whether a picture has been selected before attempting to remove it.

The input and search frames receive a reference to `MainFrame` through their constructors. This allows them to communicate with the main window and update its displayed picture list without relying on static references.

### 3. Adding and Deleting Pictures
![Usage 3](/assets/usage6-3.png)  
`AddFrame` provides input fields for the timestamp, picture tags, object information, and comments.

The **Select Image File** button opens a file chooser with an image-extension filter. The selected image's relative path is displayed to the user. If no timestamp is provided, the current time is used.

The **More Stuff** button adds another editable `StuffPanel`, allowing multiple objects to be associated with one picture. When the user confirms the input, the application creates a `Picture` object and passes it to `MainFrame` for insertion into the picture list and refreshes the GUI.

The DELETE button removes the selected picture from the current picture list. If no picture is selected, a warning dialog is displayed instead.

### 4. Searching Pictures
![Usage 6](/assets/usage6-6.png)  
`SearchFrame` provides time-based and keyword-based search controls.

The supported search criteria include:

- **Time:** Filters pictures by a specified start and end time.
- **Picture tags:** Searches the tags associated with pictures.
- **Comments:** Searches picture comments.
- **Object type:** Searches the types of associated objects.
- **Object name:** Searches the names of associated objects.
- **Object tags:** Searches tags associated with objects.
- **General Search:** Searches across picture tags, comments, object types, object names, and object tags without requiring the user to identify the relevant field.

The AND search implementation narrows the candidate list by applying successive filters. For tag searches, it can require all specified tags to match. The OR search interface is also provided, with filtering operations intended to combine matching results.

General Search takes precedence when its input field is nonempty. It searches the supported fields using the supplied query, regardless of whether the AND SEARCH or OR SEARCH button is clicked.

Search operations use the picture list currently displayed in the main window as their input. Consequently, consecutive searches can narrow the results cumulatively. The **Show All Pictures** button restores the original picture list held by the main window.

If a search produces no results, the application displays an informational dialog and presents an empty picture section.

### 5. File I/O and Data Persistence

The application reuses the file-reading and data-model functionality developed in earlier assignments.

- **LOAD:** Opens a file chooser restricted to `.data` files and rebuilds the picture list from the selected file.
- **SAVE:** Opens a file chooser and writes the current picture records to the selected data file. The `.data` extension is appended when it is missing.
- **Default loading:** The application initially reads `picture-normal.data`.
- **Chronological ordering:** Picture records are sorted by timestamp when loaded or added.

Changes made during a session are stored in memory until they are explicitly saved. Loading another file replaces the current picture list with the records from that file.

### 6. Data Management and Search Design

`PictureList` encapsulates collection operations and filtering logic. It provides methods for filtering pictures by date, picture tags, comments, object types, object names, and object tags.

This design separates the search interface from the underlying filtering operations. `SearchFrame` gathers the user's criteria and coordinates the search, while `PictureList` performs the filtering.

The application uses `ArrayList` to manage collections of pictures, tags, and associated objects. Enumerations distinguish filtering modes, such as matching all or any specified tags.

The separation between data management and presentation makes the application easier to extend and maintain as additional functionality is introduced.

## Testing

The following operations can be tested through the GUI:

- Loading and displaying picture records from a data file.
- Selecting a picture and deleting it.
- Attempting to delete a picture without selecting one.
- Adding a picture with an image file, timestamp, tags, comments, and multiple objects.
- Searching by time and individual keyword fields.
- Performing successive searches and restoring the original displayed list.
- Saving picture records to a data file and loading them again.
- The GUI also provides feedback through dialogs when no picture is selected for deletion, no search results are found, or file operations complete or fail.

## Usage
Run the following commands to compile and execute the program:
```bash
$ javac -d out src/*.java
$ java -cp out Main
```
The application initially loads picture-normal.data from the current working directory. Ensure that the data file and the image files referenced by its records are accessible.

## Known Limitations
- Picture selection is handled by a mouse listener attached to each `PicturePanel`. Clicking certain child components, such as the object information panels or text fields, may not select the containing picture.
