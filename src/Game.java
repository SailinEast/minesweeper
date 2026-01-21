import board.Board;
import board.ConsoleColors;
import board.GameState;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private final Board board;
    private boolean firstInput;

    public Game(int size, int mines) {
        board = new Board(size, mines);
        this.firstInput = true;
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
        String art = ConsoleColors.YELLOW + " _______ _________ _        _______  _______           _______  _______  _______  _______  _______ \n" +
                "(       )\\__   __/( (    /|(  ____ \\(  ____ \\|\\     /|(  ____ \\(  ____ \\(  ____ )(  ____ \\(  ____ )\n" +
                "| () () |   ) (   |  \\  ( || (    \\/| (    \\/| )   ( || (    \\/| (    \\/| (    )|| (    \\/| (    )|\n" +
                "| || || |   | |   |   \\ | || (__    | (_____ | | _ | || (__    | (__    | (____)|| (__    | (____)|\n" +
                "| |(_)| |   | |   | (\\ \\) ||  __)   (_____  )| |( )| ||  __)   |  __)   |  _____)|  __)   |     __)\n" +
                "| |   | |   | |   | | \\   || (            ) || || || || (      | (      | (      | (      | (\\ (   \n" +
                "| )   ( |___) (___| )  \\  || (____/\\/\\____) || () () || (____/\\| (____/\\| )      | (____/\\| ) \\ \\__\n" +
                "|/     \\|\\_______/|/    )_)(_______/\\_______)(_______)(_______/(_______/|/       (_______/|/   \\__/\n" +
                "                                                                                                   " + ConsoleColors.RESET;
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

                if (firstInput) {
                    board.placeMines(row, col);
                    board.calculateAdjacent();
                    firstInput = false;
                }

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

    public static int[] get_input() {
        System.out.print("Size and mines: ");
        String input = scanner.nextLine().trim();
        return Arrays.stream(input.trim().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static void main(String[] args) {
        printTitle();
        Game game;

        while (true) {
            try {
                int[] input = get_input();
                int size = input[0];
                int mines = input[1];
                if (size * size <= mines || mines < 0 || size < 0) {
                    System.out.println("Incorrect values. Type again");
                    continue;
                }

                game = new Game(size, mines);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect values!");
            }
        }
        game.start();
    }
}