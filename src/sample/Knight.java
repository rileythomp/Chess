package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Knight extends ChessPiece {
    private final ArrayList<Integer> xShifts = new ArrayList<Integer>();

    public Knight() {
        xShifts.add(-2);
        xShifts.add(-1);
        xShifts.add(1);
        xShifts.add(2);
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        for (int xShift : xShifts) {
            int xNew = x+xShift;
            if (xNew >= 0 && xNew <= 7) {
                int yShift = 3 - Math.abs(xShift);
                int yNew = y - yShift;
                if (yNew >= 0 && yNew <= 7) {
                    if (!board.Cell(xNew, yNew).HasPiece()) {
                        moves.add(new Pair(xNew, yNew));
                    }
                    else {
                        if (board.Cell(x, y).isP1Piece() && !board.Cell(xNew, yNew).isP1Piece()) {
                            moves.add(new Pair(xNew, yNew));
                        }
                        if (!board.Cell(x, y).isP1Piece() && board.Cell(xNew, yNew).isP1Piece()) {
                            moves.add(new Pair(xNew, yNew));
                        }
                    }
                }
                yNew = y + yShift;
                if (yNew >= 0 && yNew <= 7) {
                    if (!board.Cell(xNew, yNew).HasPiece()) {
                        moves.add(new Pair(xNew, yNew));
                    }
                    else {
                        if (board.Cell(x, y).isP1Piece() && !board.Cell(xNew, yNew).isP1Piece()) {
                            moves.add(new Pair(xNew, yNew));
                        }
                        if (!board.Cell(x, y).isP1Piece() && board.Cell(xNew, yNew).isP1Piece()) {
                            moves.add(new Pair(xNew, yNew));
                        }
                    }
                }
            }
        }
        return moves;
    }
}
