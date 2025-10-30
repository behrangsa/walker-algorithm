# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java library implementing the Walker Node-Positioning Algorithm for general trees, as described in John Q. Walker II's paper. The algorithm aesthetically positions tree nodes in 2D space using a two-pass approach.

## Build Commands

This project uses Gradle with Java 17:

- **Build the project**: `./gradlew build`
- **Run tests**: `./gradlew test`
- **Generate test coverage report**: `./gradlew jacocoTestReport`
  - Coverage reports are generated in `build/reports/jacoco/test/html/`
- **Clean build artifacts**: `./gradlew clean`
- **Run the demo**: `./gradlew run` or directly run `DemoFrame.main()`
- **Publish to GitHub Packages**: `./gradlew publish`

### Running Individual Tests

Use JUnit 5 test filtering:
```bash
./gradlew test --tests WalkerAlgorithmTest
./gradlew test --tests WalkerAlgorithmTest.testPosition
```

## Code Architecture

### Core Algorithm Components

The Walker algorithm positions tree nodes through two traversal passes:

1. **WalkerAlgorithm** (`src/main/java/org/behrang/algorithm/tree/WalkerAlgorithm.java`)
   - Main algorithm class implementing the two-pass layout
   - **First pass** (`firstWalk`): Performs post-order traversal, calculating preliminary x-coordinates (`prelim`) and modifiers for each node
   - **Second pass** (`secondWalk`): Performs pre-order traversal, computing final x,y coordinates by accumulating modifiers down the tree
   - **Apportionment** (`apportion`): Resolves conflicts between subtrees by shifting them apart when they would overlap
   - Configurable spacing parameters: `siblingSeparation`, `subtreeSeparation`, `levelSeparation`
   - Maintains `previousNodeAtLevel` map to track left neighbors during traversal

2. **Node** (`src/main/java/org/behrang/algorithm/tree/Node.java`)
   - Generic tree node structure holding user data and layout information
   - **Layout fields**: `prelim` (preliminary x-position), `modifier` (x-adjustment), `x`, `y` (final coordinates), `width`, `height`
   - **Tree navigation**: Methods for parent/child access, sibling traversal (`getLeftSibling`, `getRightSibling`), and finding leftmost descendants
   - **Left neighbor**: Tracks the previous node at the same level for conflict detection

3. **TreeTraversal** (`src/main/java/org/behrang/algorithm/tree/TreeTraversal.java`)
   - Utility for tree traversal operations (currently preorder only)

4. **DimensionCalculator** (`src/main/java/org/behrang/algorithm/tree/DimensionCalculator.java`)
   - Calculates bounding box dimensions after layout
   - `calculateTreeDimension`: Returns total width/height of positioned tree
   - `calculateMaxNodeDimension`: Returns maximum node dimensions in tree

### Demo Package

Located in `src/main/java/org/behrang/algorithm/tree/demo/`:
- **DemoFrame**: Swing JFrame showing visual tree layout
- **TreePanel**: Custom panel that renders positioned tree nodes
- **SampleTree**: Generates test tree structures

### Algorithm Key Concepts

- **Prelim**: Preliminary x-coordinate calculated during first walk (relative to parent)
- **Modifier**: Offset to be added to all descendant nodes during second walk
- **Apportion**: Process of shifting subtrees to prevent overlap while maintaining aesthetic centering
- **Left Neighbor**: The rightmost node at the same level in the previously processed subtree, used for detecting potential overlaps

### Testing Structure

Tests are in `src/test/java/org/behrang/algorithm/tree/`:
- **WalkerAlgorithmTest**: Tests the complete positioning algorithm with a complex tree structure
- **DimensionCalculatorTest**: Tests dimension calculation utilities
- Uses JUnit 5 with `@BeforeEach` setup
- Helper methods: `node()` creates test nodes, `link()` establishes parent-child relationships
- Tests verify exact x,y coordinates after positioning

## Important Notes

- The algorithm is stateful per positioning operation (uses `previousNodeAtLevel` map), so call `position()` with the root for each layout
- Node dimensions (`width`, `height`) must be set before calling `position()`
- Coordinates are in abstract units; scale as needed for rendering
- The implementation is heavily inspired by https://github.com/soerenreichardt/graph-drawing
