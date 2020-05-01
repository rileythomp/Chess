package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class ChessPiece {

    public boolean hasMoved;
    public int direction;
    public final ArrayList<Pair<Integer, Integer>> shiftPairs;

    public ChessPiece() {
        hasMoved = false;
        shiftPairs = new ArrayList<>();
    }

    public boolean CanMove(int x, int y, Board board) {
        // add true to here
        return this.Moves(x, y, board, true).size() > 0;
    }

    public void SetHasMoved(boolean moved) {
        hasMoved = moved;
    }

    public boolean HasMoved() {
        return hasMoved;
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board, boolean checkKingCheck) {
        return new ArrayList<>();
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board, boolean canCastle, boolean checkKingCheck) {
        return new ArrayList<>();
    }
}
