# Assignment 5: Picture Search GUI Program

This assignment extends the picture management application developed in [Assignment 3](/assignment-3) by introducing a graphical user interface (GUI) using Java Swing.

The application displays picture records and their associated metadata through a collection of panels. It also provides separate frames for adding and searching pictures, establishing the GUI structure for the subsequent project.

The original array-based data structures were replaced with Java's `ArrayList<E>` to improve flexibility and extensibility.


## Requirements
- JDK 21 or higher
- Java Swing

## Implementation
### 1. GUI Architecture
![Class diagram](/assets/class5.png)  
The GUI is organized into frames and panels, with each component responsible for a specific part of the application.

- **`MainFrame`**: The main application window. Provides buttons for displaying all pictures, adding and deleting pictures, loading and saving data, and searching.
- **`PictureSection`**: Manages the collection of picture panels displayed in the main window.
- **`PicturePanel`**: Displays an individual picture's timestamp, tags, image, associated objects, and comment.
- **`StuffPanel`**: Displays an object's type, name, and tags.
- **`AddFrame`**: Provides a separate window containing input fields for picture information.
- **`SearchFrame`**: Provides a separate window containing time-based and keyword-based search fields.

The GUI classes follow the structure of the data model: `PictureList` corresponds to `PictureSection`, `Picture` to `PicturePanel`, and `Stuff` to `StuffPanel`.

### 2. Picture Display
![Usage](/assets/usage5.png)  
`PictureSection` creates a `PicturePanel` for each picture loaded from the data file.

Each `PicturePanel` organizes its content into several regions:

- **Top:** Timestamp and picture tags.
- **Left:** A scaled preview of the image.
- **Center:** A list of associated objects displayed through `StuffPanel` instances.
- **Bottom:** The picture comment.

Scrollable containers are used to accommodate multiple pictures and pictures containing many associated objects.

### 3. Layout Management

The interface combines several Swing layout managers to organize and group components.

- **`BorderLayout`** divides frames and panels into major regions.
- **`GridLayout`** arranges buttons and fields in regular rows and columns.
- **`FlowLayout`** groups components in a simple horizontal arrangement.
- **`GridBagLayout`** provides more flexible positioning for object information and form components.

Components are grouped into `JPanel` instances according to their roles, allowing the interface to be constructed hierarchically.

### 4. Event Handling

`MainFrame` uses `ActionListener` implementations to handle button clicks.

- **Show All Pictures:** Recreates the picture section to display the available picture records.
- **ADD:** Opens the picture input frame.
- **SEARCH:** Opens the picture search frame.
- **DELETE, LOAD, and SAVE:** Buttons are present, but their actions are not implemented in this assignment.

The ADD and SEARCH frames provide the initial GUI structure for these operations. Their actual functionality is deferred to Assignment 6.

### 5. Data Model Integration

The application reuses the data model from Assignment 3 while adapting its collection-based implementation to use `ArrayList<E>`.

The `PictureList` class loads picture records from a data file, while `PictureSection` and its child panels present the loaded information through the GUI.

This separation between data management and presentation provides a foundation for implementing picture manipulation and search operations in the next assignment.

## Testing

The `Test` class launches the application by creating an instance of `MainFrame`.

The GUI can be inspected to verify the layout of the main window, the display of picture records, the organization of associated object information, and the separate input and search frames.

The ADD and SEARCH buttons can be tested to confirm that their respective frames open. The DELETE, LOAD, and SAVE operations are not implemented in this assignment.

## Usage
Run the following commands to compile and execute the program:
```bash
$ javac -d out src/*.java
$ java -cp out Test
```
The application loads picture records from `picture-normal.data` in the current working directory. Image files must also be accessible at the paths specified in the picture records.
