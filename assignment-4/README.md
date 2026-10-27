# Assignment 4: Generic ArrayList Subset (`MyArrayList<E>`)

This assignment implements a subset of Java's `ArrayList` to explore object-oriented programming, inheritance, polymorphism, and generic programming.

The implementation progresses from a fixed-capacity list to a dynamically resizable list and finally to a generic list that supports compile-time type checking.

The built-in Java `ArrayList` is not used in the implementation.


## Requirements
- JDK 21 or higher

## Implementation
![Class diagram](/assets/class4.png)
### 1. Fixed-Capacity ArrayList
Implemented `ObjectArrayListLimitedCapacity`, an array-based list with a fixed capacity.

- Stores elements in an `Object[]` array, allowing objects of different types.
- Supports element insertion, retrieval, removal, and size queries.
- Shifts elements when inserting or removing elements at arbitrary indices.
- Rejects additional insertions when the array reaches its capacity.

### 2. Dynamically Resizable ArrayList
Implemented `ObjectArrayList` by extending `ObjectArrayListLimitedCapacityIn`, a package-private base class defined in `ObjectArrayList.java`.

- Reuses the base implementation through inheritance.
- Exposes the backing array and element count to the subclass through protected fields.
- Doubles the backing array's capacity when an insertion exceeds the current capacity.
- Uses `System.arraycopy()` to copy existing elements into the expanded array.

This implementation demonstrates incremental programming by extending an existing implementation rather than rewriting the entire list.

### 3. Generic ArrayList
Implemented `MyArrayList<E>` using an `Object[]` backing array without relying on Java's built-in ArrayList.

- Uses a type parameter `E` to specify the intended element type.
- Provides typed insertion, retrieval, and removal methods.
- Uses casts when retrieving elements from the internal `Object[]` array.
- Doubles the backing array's capacity when additional storage is needed.
- Supports initialization from an existing array through the `MyArrayList(E[] array)` constructor.

For example, `MyArrayList<String>` provides compile-time type checking for operations performed through its parameterized interface, whereas a raw `MyArrayList` can accommodate values of different types.

## Testing

The `Test` class exercises the three list implementations.

- **Fixed-capacity list**: Tests insertion at specific indices, element retrieval, removal, and empty-list detection.
- **Resizable list**: Inserts 100 integers, calculates the sum of the first 50 elements, inserts values of different types, and removes all elements.
- **Raw generic list**: Repeats the heterogeneous-element tests using `MyArrayList` without a type parameter.
- **Parameterized generic list**: Stores strings, concatenates the elements, and tests insertion and removal.
- **Array-based initialization**: Constructs a `MyArrayList<String>` from an existing string array and retrieves its elements.

## Usage
Run the following commands to compile and execute the program:
```bash
$ javac -d out src/*.java
$ java -cp out Test
```
