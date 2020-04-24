package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class ChessPiece {

    public boolean hasMoved;
    public int direction;

    public ChessPiece() {
        hasMoved = false;
    }

    public boolean CanMove(int x, int y, Board board) {
        return this.Moves(x, y, board).size() > 0;
    }

    public void SetHasMoved() {
        hasMoved = true;
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board) {
        return new ArrayList<>();
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board, boolean canCastle) {
        return new ArrayList<>();
    }

}
