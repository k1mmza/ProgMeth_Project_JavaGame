package test;

import Entity.Player;
import Entity.classes.Knight;
import enums.RoomType;
import gameLogic.CombatManager;
import map.Room;

import java.util.Scanner;

public class RoomBasicTest {

    public static void main(String[] args) {

        Player player = new Knight("BasicTester");
        CombatManager combatManager = new CombatManager();
        Scanner scanner = new Scanner(System.in);

        Room basicRoom = new Room(RoomType.ENEMY);

        // Show spawned enemies
        System.out.println("Spawned Enemies:");
        basicRoom.getEnemies().forEach(enemy ->
                System.out.println("- " + enemy.getName())
        );

        // Enter room
        basicRoom.enter(player, combatManager, scanner);

        // After combat
        System.out.println("\nRoom cleared? " + basicRoom.isCleared());
        System.out.println("Enemies remaining: " + basicRoom.getEnemies().size());
        System.out.println("Player alive? " + player.isAlive());
    }
}