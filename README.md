# Java-Based RPG Game

## Current Progress

### ✅ Core Systems Implemented

- Character base class with:
    - HP, Attack, Defense
    - Shield (Block) system
    - Energy system
    - Gold system
    - Status effects (Poison, Vulnerable, Evade)

- Player abstract class with:
    - 3 skill methods (skill1, skill2, skill3)

- Enemy system with:
    - performAction(Character target)

- Combat System:
    - Menu-based actions:
        - Attack
        - Block
        - Use Item
        - Skill (select skill number)
        - Focus (gain energy)
    - One action per turn
    - End turn status resolution

- Potion System:
    - Abstract Potion class
    - Implemented potion types:
        - Healing
        - Energy
        - Strength
        - Defense
    - Integrated PotionType enum

- Shop System:
    - Fixed stock items
    - Buy/Sell system
    - Gold deduction and validation

- Enums Implemented:
    - GameState
    - PlayerClass
    - PotionType
    - RoomType
    - StatusEffect

---
## Next Steps

- Implement GameState manager loop
- Add room progression system
