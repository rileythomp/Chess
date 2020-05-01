package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Queen extends ChessPiece {
    private final ArrayList<Pair<Integer, Integer>> shiftPairs = new ArrayList<>();

    public Queen() {
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

        // up and right
        curHeight = y;
        curWidth = x;
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











        // up
//        int curHeight = y-1;
//        while (curHeight >= 0) {
//            moves.add(new Pair(x, curHeight));
//            curHeight--;
//        }
//        // up and right
//        curHeight = y-1;
//        int curWidth = x+1;
//        while (curHeight >= 0 && curWidth <= 7) {
//            moves.add(new Pair(curWidth, curHeight));
//            curHeight--;
//            curWidth++;
//        }
//        // right
//        curWidth = x+1;
//        while (curWidth <= 7) {
//            moves.add(new Pair(curWidth, y));
//            curWidth++;
//        }
//        // right and down
//        curHeight = y+1;
//        curWidth = x+1;
//        while (curHeight <= 7 && curWidth <= 7) {
//            moves.add(new Pair(curWidth, curHeight));
//            curHeight++;
//            curWidth++;
//        }
//        // down
//        curHeight = y+1;
//        while (curHeight <= 7) {
//            moves.add(new Pair(x, curHeight));
//            curHeight++;
//        }
//        // down and left
//        curHeight = y+1;
//        curWidth = x-1;
//        while (curHeight <= 7 && curWidth >= 0) {
//            moves.add(new Pair(curWidth, curHeight));
//            curHeight++;
//            curWidth--;
//        }
//        // left
//        curWidth = x-1;
//        while (curWidth >= 0) {
//            moves.add(new Pair(curWidth, y));
//            curWidth--;
//        }
//        // left and up
//        curHeight = y-1;
//        curWidth = x-1;
//        while (curHeight >= 0 && curWidth >= 0) {
//            moves.add(new Pair(curWidth, curHeight));
//            curHeight--;
//            curWidth--;
//        }
        return moves;
    }
}
