package application;

import Entity.Player;
import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import gameLogic.GameLogic;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        printTitle();

        while (true) {

            printMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    startGame();
                    break;
                case "2":
                    System.out.println("Goodbye, warrior.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ==========================
    // Menu Logic
    // ==========================

    private static void printTitle() {
        System.out.println("=================================");
        System.out.println("           SLAY THE GPA          ");
        System.out.println("=================================");
    }

    private static void printMenu() {
        System.out.println("\n1. Start Game");
        System.out.println("2. Exit");
        System.out.print("Choose option: ");
    }

    private static void startGame() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        Player player = chooseClass(name);

        GameLogic game = new GameLogic(player);
        game.startGame();
    }

    private static Player chooseClass(String name) {

        while (true) {

            System.out.println("\nChoose your class:");
            System.out.println("1. Knight (Tank)");
            System.out.println("2. Mage (High Damage)");
            System.out.println("3. Rogue (Critical Assassin)");
            System.out.print("Choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1": return new Knight(name);
                case "2": return new Mage(name);
                case "3": return new Rogue(name);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}