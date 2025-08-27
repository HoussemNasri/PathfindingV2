# PathfindingV2 Architecture Documentation

## Table of Contents
1. [Overview](#overview)
2. [Architecture Pattern](#architecture-pattern)
3. [System Architecture](#system-architecture)
4. [Module Structure](#module-structure)
5. [Class Relationships](#class-relationships)
6. [Core Components Deep Dive](#core-components-deep-dive)
7. [Implementation Details](#implementation-details)
8. [Data Flow](#data-flow)
9. [Design Patterns Used](#design-patterns-used)
10. [Technology Stack](#technology-stack)
11. [Key Architectural Benefits](#key-architectural-benefits)
12. [Configuration and Customization](#configuration-and-customization)
13. [Troubleshooting Guide](#troubleshooting-guide)
14. [Future Enhancement Opportunities](#future-enhancement-opportunities)

## Overview

PathfindingV2 is a JavaFX-based graphical application designed for visualizing pathfinding algorithms. The application demonstrates various algorithms like A*, Dijkstra, and Depth-First Search (DFS) on a customizable grid, allowing users to interactively explore how these algorithms work.

## Architecture Pattern

The application follows the **Model-View-Presenter (MVP)** architectural pattern, which provides clear separation of concerns and improved testability:

- **Model**: Contains the business logic and data (Grid, Nodes, Algorithms)
- **View**: Handles the user interface and user interactions
- **Presenter**: Acts as an intermediary between Model and View, containing presentation logic

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     PathfindingV2 Application                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │  Pathfinding    │  │  Pathfinding    │  │  Pathfinding    │ │
│  │    Launcher     │  │      API        │  │   Implementation │ │
│  │                 │  │                 │  │                 │ │
│  │ • Application   │  │ • Interfaces    │  │ • Concrete      │ │
│  │   Entry Point   │  │ • Contracts     │  │   Classes       │ │
│  │ • Main Setup    │  │ • MVP Framework │  │ • Business      │ │
│  │                 │  │                 │  │   Logic         │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
│           │                      │                      │       │
│           └──────────────────────┼──────────────────────┘       │
│                                  │                              │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                 Pathfinding Common                          │ │
│  │                                                             │ │
│  │ • Shared Resources (Themes, CSS)                           │ │
│  │ • Enums (AlgorithmDescriptor, ThemeDescriptor)             │ │
│  │ • Common Utilities                                         │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Module Structure

### 1. pathfinding-api
**Purpose**: Defines contracts and interfaces for the entire application.

**Key Components**:
- **MVP Framework** (`tech.houssemnasri.mvp`):
  - `View<V>`: Base interface for all views
  - `PresentableView<P, V>`: Views that require a presenter
  - `Presenter<V>`: Base interface for presenters

- **Core Interfaces**:
  - `IGrid`: Grid model interface
  - `IGridView`: Grid view interface  
  - `IGridPresenter`: Grid presenter interface
  - `IToolbox`: Toolbox model interface
  - `IToolboxView`: Toolbox view interface
  - `IToolboxPresenter`: Toolbox presenter interface
  - `IMainView`: Main application view interface
  - `IMainViewPresenter`: Main presenter interface

- **Algorithm Framework**:
  - `BaseAlgorithm`: Abstract base for pathfinding algorithms
  - `AlgorithmFactory`: Factory for creating algorithm instances
  - `Visualizer`: Abstract visualizer for algorithm animation
  - `ICommand`: Command pattern interface for algorithm operations

### 2. pathfinding-impl
**Purpose**: Provides concrete implementations of all interfaces defined in the API.

**Key Components**:
- **MVP Implementations**:
  - `MainView`: Main application window implementation
  - `MainViewPresenter`: Main presenter logic
  - `PGridView`: Grid visualization implementation
  - `PGridPresenter`: Grid interaction logic
  - `PGrid`: Grid model implementation
  - `ToolboxView`: Control panel implementation
  - `ToolboxPresenter`: Toolbox logic

- **Algorithm Implementations**:
  - Concrete algorithm classes (A*, Dijkstra, DFS)
  - `SimpleVisualizer`: Animation controller
  - `AlgorithmFactoryImpl`: Algorithm factory implementation

- **Node System**:
  - `PNode`: Node model implementation
  - `PNodeView`: Node visualization
  - Node skins for different visual representations

- **Command Implementations**:
  - `OpenNodeCommand`: Mark node as open
  - `CloseNodeCommand`: Mark node as closed
  - `SetCurrentNodeCommand`: Set algorithm's current node
  - `UpdateCostCommand`: Update pathfinding costs
  - `TracePathCommand`: Highlight final path

### 3. pathfinding-launcher
**Purpose**: Application entry point and dependency wiring.

**Key Components**:
- `Launcher`: JavaFX Application class
  - Sets up the scene and window
  - Creates and wires all MVP components
  - Initializes algorithms and visualizer
  - Configures the main application layout

### 4. pathfinding-common
**Purpose**: Shared resources and utilities.

**Key Components**:
- `AlgorithmDescriptor`: Enum defining available algorithms
- `ThemeDescriptor`: Enum defining available UI themes
- CSS theme files and assets
- Common utility classes

## Core Components Deep Dive

### Grid System
The grid is the central component where pathfinding visualization occurs:

```
IGrid (Model) ←→ IGridPresenter ←→ IGridView
```

- **IGrid**: Manages grid state, node positions, source/destination nodes
- **IGridPresenter**: Handles user interactions (clicks, drags), node editing
- **IGridView**: Renders the grid and individual nodes

### Toolbox System
The toolbox provides controls for algorithm selection and playback:

```
IToolbox (Model) ←→ IToolboxPresenter ←→ IToolboxView
```

- **IToolbox**: Manages algorithm selection, playback state, drawing modes
- **IToolboxPresenter**: Handles control interactions, algorithm switching
- **IToolboxView**: Renders buttons, sliders, dropdown menus

### Algorithm Framework
Algorithms follow a consistent pattern:

```
BaseAlgorithm → Specific Algorithm (A*, Dijkstra, DFS)
     ↓
AlgorithmStep → Commands → Grid State Changes
     ↓
Visualizer → Animation → View Updates
```

### Command Pattern
Algorithm operations use the Command pattern for undo/redo functionality:

- Each algorithm step contains multiple commands
- Commands are reversible for backtracking
- History is maintained for step-by-step navigation

## Class Relationships

### MVP Component Relationships
```
                    ┌─────────────────────────────────────────┐
                    │              Main View                  │
                    │                                         │
                    │  MainView ←→ MainViewPresenter          │
                    │      │                                  │
                    │      ├── ToolboxView ←→ ToolboxPresenter│
                    │      │       │               │          │
                    │      │       │               ├→ Toolbox │
                    │      │       │               │          │
                    │      │       │               ├→ Visualizer │
                    │      │       │               │          │
                    │      │       │               └→ AlgorithmFactory │
                    │      │                                  │
                    │      └── GridView ←→ GridPresenter      │
                    │              │           │              │
                    │              │           ├→ Grid        │
                    │              │           │              │
                    │              │           └→ Toolbox     │
                    │              │                          │
                    │              └── NodeViews              │
                    └─────────────────────────────────────────┘
```

### Algorithm Class Hierarchy
```
BaseAlgorithm (Abstract)
    │
    ├── AStarAlgorithm
    ├── DijkstraAlgorithm
    └── DFSAlgorithm

Each algorithm uses:
    ├── AlgorithmStep (contains Commands)
    ├── AlgorithmHistory (for undo/redo)
    └── Command implementations:
        ├── OpenNodeCommand
        ├── CloseNodeCommand
        ├── SetCurrentNodeCommand
        ├── UpdateCostCommand
        └── TracePathCommand
```

### Grid and Node System
```
IGrid ←→ INode
  │       │
  │       ├── Position
  │       ├── NodeState (BASIC, WALL, OPEN, CLOSED, PATH, etc.)
  │       └── Cost information (for A*, Dijkstra)
  │
  ├── Source Node Management
  ├── Destination Node Management
  ├── Wall/Obstacle Management
  └── Grid Traversal and Pathfinding Support
```

## Data Flow

### User Interaction Flow
1. **User Action** → View receives input
2. **View** → Presenter handles the interaction
3. **Presenter** → Updates Model state
4. **Model** → Notifies observers of changes
5. **View** → Updates display based on model changes

### Algorithm Execution Flow
1. **User starts algorithm** → ToolboxPresenter
2. **Presenter** → Configures and starts Visualizer
3. **Visualizer** → Calls algorithm.forward() repeatedly
4. **Algorithm** → Creates AlgorithmStep with Commands
5. **Commands** → Modify grid and node states
6. **Grid/Node changes** → Trigger view updates
7. **Views** → Re-render with new states

### Visualization Flow
```
User Input → Presenter → Visualizer → Algorithm → Commands → Model → View
    ↑                                                                  ↓
    └──────────────────── Visual Feedback ←──────────────────────────┘
```

## Design Patterns Used

### 1. Model-View-Presenter (MVP)
- **Separation of Concerns**: UI logic separated from business logic
- **Testability**: Presenters can be unit tested independently
- **Modularity**: Components can be developed and maintained separately

### 2. Command Pattern
- **Undo/Redo**: Algorithm steps are reversible
- **Logging**: All operations are recorded
- **Macro Operations**: Complex operations built from simple commands

### 3. Factory Pattern
- **Algorithm Creation**: `AlgorithmFactory` creates algorithm instances
- **Flexibility**: Easy to add new algorithms without modifying existing code

### 4. Observer Pattern
- **Property Binding**: JavaFX properties for reactive updates
- **Event Handling**: Visualizer listeners for state changes

### 5. Strategy Pattern
- **Algorithm Selection**: Different algorithms with same interface
- **Theme System**: Different visual themes

## Technology Stack

### Core Technologies
- **Java 17**: Primary programming language
- **JavaFX 15**: GUI framework for desktop application
- **FXML**: Declarative UI layout

### Build System
- **Gradle 6.7**: Build automation and dependency management
- **Multi-module project**: Modular architecture with clear boundaries

### Key Dependencies
- **AnimateFX**: Animation library for smooth transitions
- **Ikonli**: Icon library for modern UI elements
- **JUnit 5**: Testing framework

## Implementation Details

### Application Startup Sequence
1. **Launcher.start()** creates the JavaFX scene and primary stage
2. **Components are instantiated in order**:
   ```java
   IToolbox toolbox = new Toolbox();
   IGrid grid = new PGrid(35, 65);  // 35 rows, 65 columns
   AlgorithmFactoryImpl algorithmFactory = new AlgorithmFactoryImpl(grid);
   
   IToolboxView toolboxView = new ToolboxView();
   IToolboxPresenter toolboxPresenter = new ToolboxPresenter(scene, toolbox, toolboxView, algorithmFactory);
   
   IGridView gridView = new PGridView();
   IGridPresenter gridPresenter = new PGridPresenter(grid, gridView, toolbox);
   ```
3. **Dependencies are wired**: Presenters are set on views, models are connected
4. **UI is assembled**: Views are added to the main scene
5. **Visualizer is configured**: Algorithm animations are set up

### JavaFX and FXML Integration
The application leverages JavaFX's declarative UI capabilities:

#### FXML-based UI Definition
- **mainview.fxml**: Defines the main application layout with containers:
  ```xml
  <VBox>
      <StackPane fx:id="toolboxContainer" />    <!-- Control panel -->
      <StackPane fx:id="gridContainer" />       <!-- Pathfinding grid -->
      <StackPane fx:id="infoBarContainer" />    <!-- Status information -->
  </VBox>
  ```
- **toolbox.fxml**: Defines the control panel with interactive elements:
  - Algorithm selection dropdown
  - Play/Pause/Reset buttons
  - Speed slider
  - Wall drawing mode toggles

#### View-Controller Binding
Views implement both the MVP View interface and act as FXML controllers:
```java
public class ToolboxView implements IToolboxView, Initializable {
    @FXML private ComboBox<String> algorithmComboBox;
    @FXML private Button playPauseButton;
    
    @Override
    public void refresh() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/toolbox.fxml"));
        loader.setController(this);  // View acts as FXML controller
        loader.load();
    }
}
```

#### Property Binding System
JavaFX properties enable reactive UI updates without manual event handling:
```java
// Grid changes automatically trigger view updates
gridModel.rowsProperty().bindBidirectional(gridView.rowsProperty());

// Algorithm selection updates visualizer
toolbox.selectedAlgorithmProperty().addListener((obs, old, newVal) -> 
    visualizer.setAlgorithm(algorithmFactory.getAlgorithm(newVal)));
```

#### Event Handling Architecture
FXML controllers handle UI events and delegate to presenters:
```java
@FXML
public void initialize(URL location, ResourceBundle resources) {
    playPauseButton.setOnMouseClicked(e -> presenter.onPlayClicked());
    algorithmComboBox.getSelectionModel().selectedIndexProperty()
        .addListener((obs, old, newVal) -> presenter.onAlgorithmSelected(newVal.intValue()));
}
```

### Key Interactions

#### Algorithm Selection and Execution
```
User selects algorithm → ToolboxView → ToolboxPresenter → Toolbox model
                                    ↓
AlgorithmFactory creates algorithm instance → Visualizer is updated
                                    ↓
User clicks Play → ToolboxPresenter → Visualizer.visualize()
                                    ↓
Visualizer starts animation timer → Repeatedly calls algorithm.forward()
                                    ↓
Algorithm creates AlgorithmStep → Commands modify grid state
                                    ↓
Grid state changes → Property bindings → Views update automatically
```

#### Grid Interaction
```
User clicks on grid → GridView captures mouse event → GridPresenter
                                                      ↓
GridPresenter determines action (based on toolbox state):
  • Wall drawing/erasing
  • Source node dragging
  • Destination node dragging
                                                      ↓
GridPresenter modifies Grid model → Property changes → View updates
```

#### Node State Management
Each node in the grid can be in different states:
- **BASIC**: Default traversable state
- **WALL**: Obstacle that blocks pathfinding
- **OPEN**: Node discovered but not yet evaluated (frontier)
- **CLOSED**: Node that has been fully evaluated
- **PATH**: Node that is part of the final solution path
- **SOURCE**: Starting point for pathfinding
- **DESTINATION**: Target point for pathfinding

### Visual Feedback System
- **Real-time updates**: JavaFX property bindings ensure automatic UI updates
- **Animation**: Smooth transitions between node states during algorithm execution
- **Color coding**: Different node states have distinct visual representations
- **Theme support**: CSS-based theming system for different visual styles

### Command Pattern Implementation
```java
public class AlgorithmStep {
    private final Stack<ICommand> commands = new Stack<>();
    
    public void pushAndExecute(ICommand command) {
        commands.push(command);
        command.execute();
    }
    
    public void cancel() {
        while (!commands.isEmpty()) {
            commands.pop().cancel();
        }
    }
}
```

This allows for:
- **Step-by-step execution**: Each algorithm step can be individually executed
- **Complete rollback**: Any step can be completely undone
- **Complex operations**: Multiple commands can be grouped into a single step

## Key Architectural Benefits

### 1. Modularity
- Clear separation between API contracts and implementations
- Easy to add new algorithms or modify existing ones
- Independent development of UI and business logic

### 2. Testability
- MVP pattern enables comprehensive unit testing
- Interfaces allow for easy mocking
- Separation of concerns simplifies test scenarios

### 3. Extensibility
- Plugin-like architecture for algorithms
- Theme system for easy UI customization
- Command pattern enables new operations

### 4. Maintainability
- Well-defined interfaces reduce coupling
- Consistent patterns across components
- Clear responsibility boundaries

### 5. User Experience
- Smooth animations via JavaFX
- Responsive UI with proper event handling
- Interactive visualization with real-time feedback

## Configuration and Customization

### Adding New Algorithms
1. **Create algorithm class** extending `BaseAlgorithm`:
   ```java
   public class NewAlgorithm extends BaseAlgorithm {
       public NewAlgorithm(IGrid grid) { super(grid); }
       
       @Override
       protected AlgorithmStep advance() {
           // Implement algorithm logic
           AlgorithmStep step = new AlgorithmStep();
           // Add commands to step
           return step;
       }
   }
   ```
2. **Add to descriptor enum** in `AlgorithmDescriptor`
3. **Update factory** in `AlgorithmFactoryImpl`
4. Algorithm automatically appears in UI dropdown

### Adding New Themes
1. **Create CSS file** in `pathfinding-common/src/main/resources/theme/`
2. **Add entry** to `ThemeDescriptor` enum with CSS path
3. **Define color schemes** for different node states:
   ```css
   .node-basic { -fx-background-color: #ffffff; }
   .node-wall { -fx-background-color: #2c3e50; }
   .node-open { -fx-background-color: #3498db; }
   .node-closed { -fx-background-color: #e74c3c; }
   ```
4. Theme automatically appears in UI dropdown

### Modifying UI Layout
1. **Update FXML files** for structural changes
2. **Modify View classes** for new component behavior
3. **Update Presenter classes** for new interaction logic
4. **Add CSS classes** for styling new components

### Performance Tuning
- **Grid size**: Larger grids require more memory and processing
- **Animation speed**: Controlled via `Visualizer.setSpeed()`
- **Command history**: Affects memory usage for undo/redo functionality

## Troubleshooting Guide

### Common Issues

#### Build Problems
- **Java version compatibility**: Ensure Java 17+ is used
- **JavaFX module issues**: Verify JavaFX modules are properly configured
- **Gradle issues**: Check Gradle wrapper version compatibility

#### Runtime Issues
- **FXML loading failures**: Verify FXML paths in resource loading
- **Property binding errors**: Check for null references in property chains
- **Animation performance**: Reduce grid size or animation speed

#### Memory Issues
- **Large grids**: Consider implementing lazy loading for very large grids
- **Command history**: Implement history size limits for long-running algorithms
- **Node view caching**: Reuse node views instead of creating new ones

### Debugging Tips
1. **Enable JavaFX debugging**: Use Scene Builder for FXML inspection
2. **Property binding**: Use `InvalidationListener` to track property changes
3. **Algorithm steps**: Add logging to command execution for step-by-step debugging
4. **Performance**: Use JavaFX profiling tools for animation performance

## Future Enhancement Opportunities

### Potential Extensions
1. **Additional algorithms**: BFS, IDA*, Fringe search
2. **Maze generation**: Recursive backtracking, Prim's algorithm
3. **Graph visualization**: Support for weighted edges and different graph types
4. **Save/Load functionality**: Grid configurations and algorithm recordings
5. **Plugin system**: Dynamic algorithm loading
6. **Multi-threading**: Parallel algorithm execution for comparison
7. **3D visualization**: Extended to 3D pathfinding scenarios

### Architecture Improvements
1. **Event bus**: Centralized event handling system
2. **Dependency injection**: Framework-based dependency management
3. **Configuration system**: External configuration files
4. **Localization**: Multi-language support
5. **Accessibility**: Enhanced keyboard navigation and screen reader support

This architecture provides a solid foundation for a complex interactive application while maintaining clean separation of concerns and enabling easy extension and maintenance.