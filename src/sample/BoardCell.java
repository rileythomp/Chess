package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardCell {
    private boolean hasPiece;
    private Pieces pieceName;
    private boolean p1Piece;
    private boolean cellClicked;
    private ChessPiece chessPiece;
    private boolean canBeMovedTo;
    public int xcoord;
    public int ycoord;

    public BoardCell(int x, int y) {
        hasPiece = false;
        xcoord = x;
        ycoord = y;
    }

    public BoardCell(Pieces name, boolean p1, int x, int y, ChessPiece piece) {
        hasPiece = true;
        pieceName = name;
        chessPiece = piece;
        p1Piece = p1;
        cellClicked = false;
        xcoord = x;
        ycoord = y;
    }

    public void SetCoords(int x, int y) {
        xcoord = x;
        ycoord = y;
    }

    public ArrayList<Pair<Integer, Integer>> Moves(Board board) {
        if (!hasPiece) {
            return new ArrayList<>();
        }
        return chessPiece.Moves(xcoord, ycoord, board);
    }

    public ChessPiece ChessPiece() {
        return chessPiece;
    }

    public void setCanBeMovedTo(boolean possible) {
        canBeMovedTo = possible;
    }

    public boolean CanBeMovedTo() {
        return canBeMovedTo;
    }

    public void ClickCell() {
        cellClicked = true;
    }

    public void UnclickCell() {
        cellClicked = false;
    }

    public boolean isClicked() {
        return cellClicked;
    }

    public boolean HasPiece() {
        return hasPiece;
    }

    public Pieces PieceName() {
        return pieceName;
    }

    public boolean isP1Piece() {
        return p1Piece;
    }

    public void SetHasMoved() {
        chessPiece.SetHasMoved();
    }
}
