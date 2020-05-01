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

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board, boolean checkKingCheck) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();

        moves.addAll(GetMovesInDir(board, x, y, 1, true, checkKingCheck)); // right
        moves.addAll(GetMovesInDir(board, x, y, -1, true, checkKingCheck)); // left
        moves.addAll(GetMovesInDir(board, x, y, 1, false, checkKingCheck)); // down
        moves.addAll(GetMovesInDir(board, x, y, -1, false, checkKingCheck)); // up

        return moves;
    }

    public ArrayList<Pair<Integer, Integer>> GetMovesInDir(Board board, int x, int y, int dir, boolean horizontal, boolean checkKingCheck) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        int curWidth = x;
        int curHeight = y;
        while ((curWidth+dir >= 0 && curWidth+dir <= 7 && horizontal) || (curHeight+dir >= 0 && curHeight+dir <= 7 && !horizontal)) {
            if (horizontal) {
                curWidth += dir;
            }
            else {
                curHeight += dir;
            }

            // if moving the piece to curWidth, curHeight puts king in check, then it cant move and continue
            //
            if (checkKingCheck && board.MovePutsOwnKingInCheck(x, y, curWidth, curHeight)) {
                continue;
            }

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
