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
                return "[F]";
            }
            return "[_]";
        } else {
            if (isMine) {
                return "[X]";
            }
            if (adjacentMines == 0) {
                return "[ ]";
            }
            return "[" + adjacentMines + "]";
        }
    }
}