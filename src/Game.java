import board.Board;
import board.Cell;
import board.GameState;

import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private Board board;

    public Game(int size, int mines) {
        board = new Board(size, mines);
        scanner = new Scanner(System.in);
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.println("\\033[H\\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public static void printTitle() {
        String art = "        _   _          _          _             _           _            _             _            _            _          _            _      \n" +
                "       /\\_\\/\\_\\ _     /\\ \\       /\\ \\     _    /\\ \\        / /\\         / /\\      _   /\\ \\         /\\ \\         /\\ \\       /\\ \\         /\\ \\    \n" +
                "      / / / / //\\_\\   \\ \\ \\     /  \\ \\   /\\_\\ /  \\ \\      / /  \\       / / /    / /\\ /  \\ \\       /  \\ \\       /  \\ \\     /  \\ \\       /  \\ \\   \n" +
                "     /\\ \\/ \\ \\/ / /   /\\ \\_\\   / /\\ \\ \\_/ / // /\\ \\ \\    / / /\\ \\__   / / /    / / // /\\ \\ \\     / /\\ \\ \\     / /\\ \\ \\   / /\\ \\ \\     / /\\ \\ \\  \n" +
                "    /  \\____\\__/ /   / /\\/_/  / / /\\ \\___/ // / /\\ \\_\\  / / /\\ \\___\\ / / /_   / / // / /\\ \\_\\   / / /\\ \\_\\   / / /\\ \\_\\ / / /\\ \\_\\   / / /\\ \\_\\ \n" +
                "   / /\\/________/   / / /    / / /  \\/____// /_/_ \\/_/  \\ \\ \\ \\/___// /_//_/\\/ / // /_/_ \\/_/  / /_/_ \\/_/  / / /_/ / // /_/_ \\/_/  / / /_/ / / \n" +
                "  / / /\\/_// / /   / / /    / / /    / / // /____/\\      \\ \\ \\     / _______/\\/ // /____/\\    / /____/\\    / / /__\\/ // /____/\\    / / /__\\/ /  \n" +
                " / / /    / / /   / / /    / / /    / / // /\\____\\/  _    \\ \\ \\   / /  \\____\\  // /\\____\\/   / /\\____\\/   / / /_____// /\\____\\/   / / /_____/   \n" +
                "/ / /    / / /___/ / /__  / / /    / / // / /______ /_/\\__/ / /  /_/ /\\ \\ /\\ \\// / /______  / / /______  / / /      / / /______  / / /\\ \\ \\     \n" +
                "\\/_/    / / //\\__\\/_/___\\/ / /    / / // / /_______\\\\ \\/___/ /   \\_\\//_/ /_/ // / /_______\\/ / /_______\\/ / /      / / /_______\\/ / /  \\ \\ \\    \n" +
                "        \\/_/ \\/_________/\\/_/     \\/_/ \\/__________/ \\_____\\/        \\_\\/\\_\\/ \\/__________/\\/__________/\\/_/       \\/__________/\\/_/    \\_\\/    \n" +
                "                                                                                                                                                \n";
        System.out.println(art);
    }

    public void start() {
        GameState state = GameState.PLAYING;
        board.initBoard();

        while (state == GameState.PLAYING) {
            clearScreen();
            System.out.println("Mines: " + board.getMineCount() + " | Flags: " + board.getFlagCount());
            System.out.println(board.toString());
            System.out.print("Row and Column, 'f' for flag / 'q' to quit: ");
            String input = scanner.nextLine();
            String[] parts = input.trim().split("\\s+");

            if (parts[0].equalsIgnoreCase("q")) {
                break;
            }

            if (parts.length < 2) {
                System.out.println("Invalid input. Format: [row] [col] [f]");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;

                if (parts.length == 3 && parts[2].equalsIgnoreCase("f")) {
                    board.switchFlag(row, col);
                } else if (parts.length == 3 && !parts[2].equalsIgnoreCase("f")) {
                    System.out.println("incorrect 3rd value. Did you mean 'f'?");
                } else {
                    state = board.revealCell(row, col);

                    if (state == GameState.LOST) {
                        System.out.println("Sadly you lost :(");
                        System.out.println(board.toString());
                    } else if (state == GameState.WON) {
                        System.out.println("Yahoo you won!");
                        System.out.println(board.toString());
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Error. Input correct numbers!");
            }
        }

        System.out.println("Press 'enter' to exit...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        printTitle();
        Scanner s = new Scanner(System.in);
        System.out.print("Size and mines: ");
        String input = s.nextLine();
        String[] input_parts = input.trim().split(" ");

        try {
            int size = Integer.parseInt((input_parts[0]));
            int mines = Integer.parseInt(input_parts[1]);

            Game game = new Game(size, mines);
            game.start();
        } catch (NumberFormatException nfe) {
            System.out.println("Incorrect values!");
        }
    }
}