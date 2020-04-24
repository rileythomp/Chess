package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pawn extends ChessPiece {
    private int direction;

    public Pawn(int dir) {
        direction = dir;
        hasMoved = false;
    }

    public ArrayList<Pair<Integer, Integer>> Moves(int x, int y, Board board) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        if (y + direction >= 0 && y + direction <= 7) {
            if (!board.Cell(x, y + direction).HasPiece()) {
                moves.add(new Pair(x, y + direction));
            }
            else {
//                if (board.Cell(x, y).isP1Piece() && !board.Cell(x, y + direction).isP1Piece()) {
//                    moves.add(new Pair(x, y + direction));
//                }
//                if (!board.Cell(x, y).isP1Piece() && board.Cell(x, y + direction).isP1Piece()) {
//                    moves.add(new Pair(x, y + direction));
//                }
                return moves;
            }
        }
        if (y + direction >= 0 && y + direction <= 7 && x + 1 <= 7) {
            BoardCell startCell = board.Cell(x, y);
            BoardCell endCell = board.Cell(x+1, y+direction);
            if (endCell.HasPiece() && !endCell.isP1Piece() && startCell.isP1Piece()) {
                moves.add(new Pair(x+1, y + direction));
            }
            else if (endCell.HasPiece() && endCell.isP1Piece() && !startCell.isP1Piece()) {
                moves.add(new Pair(x+1, y + direction));
            }
        }
        if (y + direction >= 0 && y + direction <= 7 && x - 1 >= 0) {
            BoardCell startCell = board.Cell(x, y);
            BoardCell endCell = board.Cell(x-1, y+direction);
            if (endCell.HasPiece() && !endCell.isP1Piece() && startCell.isP1Piece()) {
                moves.add(new Pair(x-1, y + direction));
            }
            else if (endCell.HasPiece() && endCell.isP1Piece() && !startCell.isP1Piece()) {
                moves.add(new Pair(x-1, y + direction));
            }
        }
        if (!hasMoved && y + 2*direction >= 0 && y + 2*direction <= 7) {
            if (!board.Cell(x, y + 2*direction).HasPiece()) {
                moves.add(new Pair(x, y + 2*direction));
            }
//            else {
//                if (board.Cell(x, y).isP1Piece() && !board.Cell(x, y + 2*direction).isP1Piece()) {
//                    moves.add(new Pair(x, y + 2*direction));
//                }
//                if (!board.Cell(x, y).isP1Piece() && board.Cell(x, y + 2*direction).isP1Piece()) {
//                    moves.add(new Pair(x, y + 2*direction));
//                }
//            }
        }
        return moves;
    }
}
