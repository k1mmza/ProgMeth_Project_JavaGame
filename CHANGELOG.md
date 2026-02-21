## v.0.1 - Initial
- Basic game structure
- Player and enemy classes
- Potion Class
- Inventory Class

## v0.2 - Optimization 
- Refactored code for better readability and performance 
  - Separated Character to Player and Enemy classes
- Added Enums for better type safety
- Implemented basic combat system
  - added attack and defend mechanics
  - added skill for player and enemy
- Added Status Effects (e.g., Poison)

## v0.3 - Combat System & Shop
- Refactored Character class to Entity class
  - Player and Enemy now inherit from Entity
- Added Combat System
  - Implemented turn-based combat mechanics
  - Added attack, defend, focus, item and skill options for player
- Shop Class

## v0.3.1 - Combat System Optimization
- Added Combat test class
- Added method to get skill names for better combat menu display

## v0.3.2 - Bosses and Boss Mechanics
- Added Boss class that inherits from Enemy
- Implemented unique mechanics for bosses (e.g., special attacks, phases)
- Added Boss-specific skills and status effects

## v0.4 - Map Generation & Visuals
### Added
- **Implemented Map Generation** : Created the new node-based map system from scratch.
- **Custom Icons** : Replaced basic nodes with image icons (Enemy, Elite, Boss, etc.).
- **Path Progression** : Added rules to start from the bottom and only select connected paths. 
- **Visual Effects** : Added glow effects for selectable nodes and dimmed unavailable paths.
- **UI Optimization** : Scaled the map to a fullscreen layout with automatic centering.

### Changed
- **bulid.gradle** : Changed path application main class to application.Main