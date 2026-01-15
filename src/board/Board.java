package board;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Cell[][] grid;
    private int size;
    private int mineCount;
    private int revealedCells;
    private int totalSafeCells;
    private int flagCount;
    Random random = new Random();

    public Board(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        revealedCells = 0;
        totalSafeCells = size * size - mineCount;
        grid = new Cell[size][size];
        flagCount = 0;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void initBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
        placeMines();
        calculateAdjacent();
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void switchFlag(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) return;
        Cell cell = grid[row][col];
        if (cell.isRevealed()) return;

        if (!cell.isFlagged()) {
            cell.setFlagged(true);
            flagCount++;
        } else {
            cell.setFlagged(false);
            flagCount--;
        }
    }

    public void placeMines() {
        int placedMines = 0;

        while (placedMines < mineCount) {
            int randX = random.nextInt(0, size);
            int randY = random.nextInt(0, size);
            Cell cell = grid[randX][randY];

            if (!cell.isMine()) {
                cell.setMine(true);
                placedMines++;
            }
        }
    }

    private int countMines(int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < size && j >= 0 && j < size) {
                    if (grid[i][j].isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void calculateAdjacent() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = grid[i][j];
                cell.setAdjacentMines(countMines(i, j));
            }
        }
    }

    public GameState revealCell(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            return GameState.PLAYING;
        }

        Cell cell = grid[row][col];

        if (cell.isFlagged()) {
            return GameState.PLAYING;
        }

        if (cell.isMine()) {
            revealGrid();
            return GameState.LOST;
        } else {
            floodFill(row, col);
            if (totalSafeCells == revealedCells) {
                return GameState.WON;
            }
            return GameState.PLAYING;
        }
    }

    private void revealGrid() {
        Arrays.stream(grid).
                flatMap(Arrays::stream).
                forEach(cell -> cell.setRevealed(true));
    }

    private void floodFill(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) return;
        Cell cell = grid[row][col];
        if (cell.isRevealed()) return;


        cell.setRevealed(true);
        revealedCells++;

        if (cell.getAdjacentMines() > 0) return;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                floodFill(i, j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   | ");
        for (int k = 1; k <= size; k++) {
            String kStr = String.valueOf(k);
            sb.append(k).append(" ".repeat(3 - kStr.length()));
        }
        sb.append("\n");
        for (int k = 0; k < size * 3 + 5; k++) {
            sb.append("-");
        }
        sb.append("\n");

        for (int i = 0; i < size; i++) {
            if (i < 9) {
                sb.append(" ").append(i + 1).append(" | ");
            } else {
                sb.append(i + 1).append(" | ");
            }
            for (int j = 0; j < size; j++) {
                Cell cell = grid[i][j];
                sb.append(cell.toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}