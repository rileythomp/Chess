package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class King extends ChessPiece {
    private final ArrayList<Pair<Integer, Integer>> shiftPairs = new ArrayList<>();


    public King() {
        shiftPairs.add(new Pair(0, -1));
        shiftPairs.add(new Pair(1, -1));
        shiftPairs.add(new Pair(1, 0));
        shiftPairs.add(new Pair(1, 1));
        shiftPairs.add(new Pair(0, 1));
        shiftPairs.add(new Pair(-1, 1));
        shiftPairs.add(new Pair(-1, 0));
        shiftPairs.add(new Pair(-1, -1));

    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board, boolean canCastle) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        for (Pair<Integer, Integer> shiftPair : shiftPairs) {
            int xNew = x + shiftPair.getKey();
            int yNew = y + shiftPair.getValue();
            if (xNew >= 0 && xNew <= 7 && yNew >= 0 && yNew <= 7) {
                if (!board.Cell(xNew, yNew).HasPiece()) {
                    moves.add(new Pair(xNew, yNew));
                }
                else {
                    if (CellHasEnemy(x , y, xNew, yNew, board)) {
                        if (!board.InCheckAt(x, y, shiftPair.getKey(), shiftPair.getValue())) {
                            moves.add(new Pair(xNew, yNew));
                        }
                    }
                }
            }
        }

        return moves;
    }

    private boolean CellHasEnemy(int x, int y, int xNew, int yNew, Board board) {
        BoardCell curCell = board.Cell(x, y);
        BoardCell newCell = board.Cell(xNew, yNew);
        return (curCell.isP1Piece() && !newCell.isP1Piece()) || (!curCell.isP1Piece() && newCell.isP1Piece());
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        for (Pair<Integer, Integer> shiftPair : shiftPairs) {
            int xNew = x + shiftPair.getKey();
            int yNew = y + shiftPair.getValue();
            if (xNew >= 0 && xNew <= 7 && yNew >= 0 && yNew <= 7) {
                if (!board.Cell(xNew, yNew).HasPiece()) {
                    if (!board.InCheckAt(x, y, shiftPair.getKey(), shiftPair.getValue())) {
                        moves.add(new Pair(xNew, yNew));
                    }
                }
                else {
                    if (CellHasEnemy(x , y, xNew, yNew, board)) {
                        if (!board.InCheckAt(x, y, shiftPair.getKey(), shiftPair.getValue())) {
                            moves.add(new Pair(xNew, yNew));
                        }
                    }
                }
            }
        }

        // castling
        if (!hasMoved && !board.Cell(x+3, y).ChessPiece().hasMoved && !board.Cell(x+1, y).HasPiece() && !board.Cell(x+2, y).HasPiece()) {
            if (!board.InCheckAt(x, y, 0, 0) && !board.InCheckAt(x, y, 1, 0) && !board.InCheckAt(x, y, 2, 0)) {
                moves.add(new Pair(x+2, y));
            }
        }
        if (!hasMoved && !board.Cell(x-4, y).ChessPiece().hasMoved && !board.Cell(x-1, y).HasPiece() && !board.Cell(x-2, y).HasPiece() && !board.Cell(x-3, y).HasPiece()) {
            if (!board.InCheckAt(x, y, 0, 0) && !board.InCheckAt(x, y, -1, 0) && !board.InCheckAt(x, y, -2, 0)) {
                moves.add(new Pair(x-2, y));
            }
        }

        return moves;
    }
}
