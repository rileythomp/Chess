package sample;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardCell {
    private Pieces pieceName;
    private ChessPiece chessPiece;

    private boolean hasPiece;
    private boolean p1Piece;
    private boolean cellClicked;
    private boolean canBeMovedTo;
    private int enPassant;
    private boolean gettingPromoted;
    private boolean pieceInCheck;

    public int xcoord;
    public int ycoord;

    public BoardCell(int x, int y) {
        hasPiece = false;
        xcoord = x;
        ycoord = y;
    }

    public void PromotePieceTo(Pieces name) {
        pieceName = name;
        if (name == Pieces.QUEEN) {
            chessPiece = new Queen();
        }
        else if (name == Pieces.ROOK) {
            chessPiece = new Rook();

        }
        else if (name == Pieces.BISHOP) {
            chessPiece = new Bishop();

        }
        else if (name == Pieces.KNIGHT) {
            chessPiece = new Knight();
        }
        gettingPromoted = false;
    }

    public boolean IsInCheck() {
        return pieceInCheck;
    }

    public void SetInCheck(boolean inCheck) {
        pieceInCheck = inCheck;
    }

    public boolean IsGettingPromoted() {
        return gettingPromoted;
    }

    public void SetGettingPromoted(boolean promoted) {
        gettingPromoted = promoted;
    }

    public int IsEnPassant() {
        return enPassant;
    }

    public void SetEnPassant(int bool) {
        enPassant = bool;
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
        return chessPiece.Moves(xcoord, ycoord, board, true);
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

    public boolean HasMoved() {
        return chessPiece.HasMoved();
    }

    public void SetHasMoved(boolean moved) {
        chessPiece.SetHasMoved(moved);
    }
}
