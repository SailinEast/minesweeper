package board;

public class Cell {
    private boolean isFlagged;
    private boolean isMine;
    private boolean isRevealed;
    private int adjacentMines;

    public Cell() {
        isMine = false;
        isRevealed = false;
        isFlagged = false;
        adjacentMines = 0;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    @Override
    public String toString() {
        if (!isRevealed) {
            if (isFlagged) {
                return "[" + ConsoleColors.YELLOW + "F" + ConsoleColors.RESET + "]";
            }
            return "[_]";
        } else {
            if (isMine) {
                return "[" + ConsoleColors.RED_BOLD + "X" + ConsoleColors.RESET +  "]";
            }
            switch (adjacentMines) {
                case 1: return "[" + ConsoleColors.BLUE + adjacentMines + ConsoleColors.RESET + "]";
                case 2: return "[" + ConsoleColors.GREEN + adjacentMines + ConsoleColors.RESET + "]";
                case 3: return "[" + ConsoleColors.RED + adjacentMines + ConsoleColors.RESET + "]";
                case 4: return "[" + ConsoleColors.PURPLE + adjacentMines + ConsoleColors.RESET + "]";
                case 0: return ConsoleColors.GRAY + "[ ]" + ConsoleColors.RESET;
                default: return ConsoleColors.CYAN + "[" + adjacentMines + "]" + ConsoleColors.RESET;
            }
        }
    }
}