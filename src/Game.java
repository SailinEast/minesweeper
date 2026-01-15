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

    public void start() {
        GameState state = GameState.PLAYING;
        board.initBoard();

        while (state == GameState.PLAYING) {
            System.out.println("Mines: " + board.getMineCount() + " | Flags: " + board.getFlagCount());
            System.out.println(board.toString());
            System.out.print("Row and Column, f if flag: ");
            String input = scanner.nextLine();
            String[] parts = input.trim().split(" ");
            if (parts.length < 2) {
                System.out.println("Invalid input. Format: [row] [col] [f]");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;

                if (parts.length == 3 && parts[2].equalsIgnoreCase("f")) {
                    board.switchFlag(row, col);
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
        Scanner input = new Scanner(System.in);
        System.out.print("Size and mines: ");
        int size = input.nextInt();
        int mines = input.nextInt();

        Game game = new Game(size, mines);
        game.start();
    }
}