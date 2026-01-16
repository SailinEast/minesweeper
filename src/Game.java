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
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
            String[] parts = input.trim().split(" ");

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
    }

    public static void main(String[] args) {
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