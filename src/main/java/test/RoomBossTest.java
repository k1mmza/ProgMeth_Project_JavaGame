package test;

import Entity.Player;
import Entity.classes.Knight;
import enums.RoomType;
import gameLogic.CombatManager;
import map.Room;

import java.util.Scanner;

public class RoomBossTest {

    public static void main(String[] args) {

        Player player = new Knight("BossTester");
        CombatManager combatManager = new CombatManager();
        Scanner scanner = new Scanner(System.in);

        Room bossRoom = new Room(RoomType.BOSS);

        // Print spawned enemies
        System.out.println("Spawned Boss:");
        bossRoom.getEnemies().forEach(enemy ->
                System.out.println("- " + enemy.getName())
        );

        // Enter boss room
        bossRoom.enter(player, combatManager, scanner);

        // Results
        System.out.println("\nRoom cleared? " + bossRoom.isCleared());
        System.out.println("Enemies remaining: " + bossRoom.getEnemies().size());
        System.out.println("Player alive? " + player.isAlive());
    }
}