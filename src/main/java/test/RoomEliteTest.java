package test;

import Entity.Player;
import Entity.classes.Knight;
import enums.RoomType;
import gameLogic.CombatManager;
import map.Room;

import java.util.Scanner;

public class RoomEliteTest {

    public static void main(String[] args) {
        Player player = new Knight("EliteTester");
        CombatManager combatManager = new CombatManager();
        Scanner scanner = new Scanner(System.in);

        Room eliteRoom = new Room(RoomType.ELITE);

        // Print spawned enemies
        System.out.println("Spawned Enemies:");
        eliteRoom.getEnemies().forEach(enemy ->
                System.out.println("- " + enemy.getName())
        );

        // Enter room
        eliteRoom.enter(player, combatManager, scanner);

        // After combat
        System.out.println("\nRoom cleared? " + eliteRoom.isCleared());
        System.out.println("Player alive? " + player.isAlive());
    }
}