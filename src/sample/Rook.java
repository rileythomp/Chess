package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Rook extends ChessPiece {
    private final ArrayList<Pair<Integer, Integer>> shiftPairs = new ArrayList<>();

    public Rook() {
        shiftPairs.add(new Pair(0, -1));
        shiftPairs.add(new Pair(1, -1));
        shiftPairs.add(new Pair(1, 0));
        shiftPairs.add(new Pair(1, 1));
        shiftPairs.add(new Pair(0, 1));
        shiftPairs.add(new Pair(-1, 1));
        shiftPairs.add(new Pair(-1, 0));
        shiftPairs.add(new Pair(-1, -1));

    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        // up
        int curHeight = y;
        while (curHeight-1 >= 0) {
            curHeight--;
            if (!board.Cell(x, curHeight).HasPiece()) {
                moves.add(new Pair(x, curHeight));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(x, curHeight).isP1Piece()) {
                    moves.add(new Pair(x, curHeight));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(x, curHeight).isP1Piece()) {
                    moves.add(new Pair(x, curHeight));
                }
                break;
            }
        }

        // right
        int curWidth = x;
        while (curWidth+1 <= 7) {
            curWidth++;
            if (!board.Cell(curWidth, y).HasPiece()) {
                moves.add(new Pair(curWidth, y));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(curWidth, y).isP1Piece()) {
                    moves.add(new Pair(curWidth, y));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(curWidth, y).isP1Piece()) {
                    moves.add(new Pair(curWidth, y));
                }
                break;
            }
        }
        // down
        curHeight = y;
        while (curHeight+1 <= 7) {
            curHeight++;
            if (!board.Cell(x, curHeight).HasPiece()) {
                moves.add(new Pair(x, curHeight));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(x, curHeight).isP1Piece()) {
                    moves.add(new Pair(x, curHeight));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(x, curHeight).isP1Piece()) {
                    moves.add(new Pair(x, curHeight));
                }
                break;
            }
        }

        // left
        curWidth = x;
        while (curWidth-1 >= 0) {
            curWidth--;
            if (!board.Cell(curWidth, y).HasPiece()) {
                moves.add(new Pair(curWidth, y));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(curWidth, y).isP1Piece()) {
                    moves.add(new Pair(curWidth, y));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(curWidth, y).isP1Piece()) {
                    moves.add(new Pair(curWidth, y));
                }
                break;
            }
        }

        return moves;
    }
}
