package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bishop extends ChessPiece {
    private final ArrayList<Pair<Integer, Integer>> shiftPairs = new ArrayList<>();

    public Bishop() {
        shiftPairs.add(new Pair(0, -1));
        shiftPairs.add(new Pair(1, -1));
        shiftPairs.add(new Pair(1, 0));
        shiftPairs.add(new Pair(1, 1));
        shiftPairs.add(new Pair(0, 1));
        shiftPairs.add(new Pair(-1, 1));
        shiftPairs.add(new Pair(-1, 0));
        shiftPairs.add(new Pair(-1, -1));

    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board, boolean checkKingCheck) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        // up and right
        int curHeight = y;
        int curWidth = x;
        while (curHeight-1 >= 0 && curWidth+1 <= 7) {
            curHeight--;
            curWidth++;
            if (!board.Cell(curWidth, curHeight).HasPiece()) {
                moves.add(new Pair(curWidth, curHeight));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                break;
            }
        }
        // right and down
        curHeight = y;
        curWidth = x;
        while (curHeight+1 <= 7 && curWidth+1 <= 7) {
            curHeight++;
            curWidth++;
            if (!board.Cell(curWidth, curHeight).HasPiece()) {
                moves.add(new Pair(curWidth, curHeight));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                break;
            }
        }
        // down and left
        curHeight = y;
        curWidth = x;
        while (curHeight+1 <= 7 && curWidth-1 >= 0) {
            curHeight++;
            curWidth--;
            if (!board.Cell(curWidth, curHeight).HasPiece()) {
                moves.add(new Pair(curWidth, curHeight));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                break;
            }
        }

        // left and up
        curHeight = y;
        curWidth = x;
        while (curHeight-1 >= 0 && curWidth-1 >= 0) {
            curHeight--;
            curWidth--;
            if (!board.Cell(curWidth, curHeight).HasPiece()) {
                moves.add(new Pair(curWidth, curHeight));
            }
            else {
                if (board.Cell(x, y).isP1Piece() && !board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                if (!board.Cell(x, y).isP1Piece() && board.Cell(curWidth, curHeight).isP1Piece()) {
                    moves.add(new Pair(curWidth, curHeight));
                }
                break;
            }
        }
        return moves;
    }
}
