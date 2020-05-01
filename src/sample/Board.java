package sample;

import javafx.util.Pair;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;

public class Board {
    private final int LEN = 8;

    private ArrayList<ArrayList<BoardCell>> cells;

    public Board() {
        cells = new ArrayList<>();
        for (int i = 0; i < LEN; ++i) {
            ArrayList<BoardCell> row = new ArrayList<>();
            for (int j = 0; j < LEN; ++j) {
                BoardCell boardCell = new BoardCell(j, i);

                if (i == 0) {
                    if (j == 0 || j == 7) {
                        boardCell = new BoardCell(Pieces.ROOK, true, j, i, new Rook());
                    }
                    else if (j == 1  || j == 6) {
                        boardCell = new BoardCell(Pieces.KNIGHT, true, j, i, new Knight());
                    }
                    else if (j == 2 || j == 5) {
                        boardCell = new BoardCell(Pieces.BISHOP, true, j, i, new Bishop());
                    }
                    else if (j == 4) {
                        boardCell = new BoardCell(Pieces.KING, true, j, i, new King());
                    }
                    else if (j == 3) {
                        boardCell = new BoardCell(Pieces.QUEEN, true, j, i, new Queen());
                    }
                    else {
                        boardCell = new BoardCell(j, i);
                    }
                }
                else if (i == 1) {
                    boardCell = new BoardCell(Pieces.PAWN, true, j, i, new Pawn(1));
                }
                else if (i == 6) {
                    boardCell = new BoardCell(Pieces.PAWN, false, j, i, new Pawn(-1));
                }
                else if (i == 7) {
                    if (j == 0 || j == 7) {
                        boardCell = new BoardCell(Pieces.ROOK, false, j, i, new Rook());
                    }
                    else if (j == 1  || j == 6) {
                        boardCell = new BoardCell(Pieces.KNIGHT, false, j, i, new Knight());
                    }
                    else if (j == 2 || j == 5) {
                        boardCell = new BoardCell(Pieces.BISHOP, false, j, i, new Bishop());
                    }
                    else if (j == 3) {
                        boardCell = new BoardCell(Pieces.QUEEN, false, j, i, new Queen());
                    }
                    else if (j == 4) {
                        boardCell = new BoardCell(Pieces.KING, false, j, i, new King());
                    }
                    else {
                        boardCell = new BoardCell(j, i);
                    }
                }

                row.add(boardCell);
            }
            cells.add(row);
        }
    }

    public void MarkPossibleMoves(BoardCell startCell) {
        // when calcing moves here, check for king in check
        ArrayList<Pair<Integer, Integer>> possibleMoves = startCell.Moves(this);

        // possible moves on the board, need to check for pieces blocking
        for (int i = 0; i < possibleMoves.size(); ++i) {
            Cell(possibleMoves.get(i).getKey(), possibleMoves.get(i).getValue()).setCanBeMovedTo(true);
        }
    }

    public ArrayList<Pair<Integer, Integer>> GetEnemyMoves(int i, int j) {
        if (cells.get(i).get(j).PieceName() == Pieces.KING) {
            return cells.get(i).get(j).ChessPiece().Moves(j, i, this, true, false);
        }
        else {
            return cells.get(i).get(j).ChessPiece().Moves(j, i, this, false);
        }
    }

    public boolean EnemyCellAt(int i, int j, int x, int y) {
        BoardCell curCell = cells.get(i).get(j);
        BoardCell kingCell = cells.get(y).get(x);
        return curCell.HasPiece() && (curCell.isP1Piece() && !kingCell.isP1Piece() || !curCell.isP1Piece() && kingCell.isP1Piece());
    }

    public BoardCell Cell(int x, int y) {
        return cells.get(y).get(x);
    }

    public void ResetCells() {
        for (ArrayList<BoardCell> row : cells) {
            for (BoardCell boardCell : row) {
                boardCell.UnclickCell();
                boardCell.setCanBeMovedTo(false);
            }
        }
    }

    public void PromotePieceTo(Pieces name) {
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).IsGettingPromoted()) {
                    cells.get(i).get(j).PromotePieceTo(name);
                }
            }
        }
    }

    public void UnEnPassantAndPromotion() {
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).IsEnPassant() == 1) {
                    cells.get(i).get(j).SetEnPassant(2);
                }
                cells.get(i).get(j).SetGettingPromoted(false);

            }
        }
    }

    public void MoveSelectedPieceTo(int x, int y) {
        BoardCell selectedCell = new BoardCell(0, 0);
        int xShift = 0;
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).isClicked()) {
                    xShift = Math.abs(x - j);
                    selectedCell = cells.get(i).get(j);
                    if (selectedCell.PieceName() == Pieces.PAWN && Math.abs(i - y) == 2) {
                        cells.get(i+selectedCell.ChessPiece().direction).get(j).SetEnPassant(1);
                    }
                    cells.get(i).set(j, new BoardCell(j, i));
                }
            }
        }

        selectedCell.SetCoords(x, y);
        selectedCell.SetHasMoved(true);
        selectedCell.SetEnPassant(cells.get(y).get(x).IsEnPassant());
        cells.get(y).set(x, selectedCell);
        // IF THE CELL MOVED TO BY THE PAWN IS ENPASSANT2, THEN REMOVE THE PIECE BELOW IT
        if (cells.get(y).get(x).IsEnPassant() == 2) {
            cells.get(y - cells.get(y).get(x).ChessPiece().direction).set(x, new BoardCell(x, y - cells.get(y).get(x).ChessPiece().direction));
        }

        if (selectedCell.PieceName() == Pieces.PAWN && selectedCell.isP1Piece() && y == 7) {
            cells.get(y).get(x).SetGettingPromoted(true);
        }
        else if (selectedCell.PieceName() == Pieces.PAWN && !selectedCell.isP1Piece() && y == 0) {
            cells.get(y).get(x).SetGettingPromoted(true);
        }

        if (cells.get(y).get(x).PieceName() == Pieces.KING && xShift == 2) {
            // castled
            // kings good move the rook over 2 to the X (dir)
            if (x == 2) {
                BoardCell rookCell = cells.get(y).get(x-2);
                cells.get(y).set(x-2, new BoardCell(x-2, y));
                rookCell.SetCoords(x+1, y);
                rookCell.SetHasMoved(true);
                cells.get(y).set(x+1, rookCell);
            }
            else if (x == 6) {
                BoardCell rookCell = cells.get(y).get(x+1);
                cells.get(y).set(x+1, new BoardCell(x+1, y));
                rookCell.SetCoords(x-1, y);
                rookCell.SetHasMoved(true);
                cells.get(y).set(x-1, rookCell);
            }
            else {
                System.exit(1);
            }
        }

        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                cells.get(i).get(j).SetEnPassant(cells.get(i).get(j).IsEnPassant()%2);
            }
        }
    }

    public boolean InCheckAt(int x, int y, int xOffset, int yOffset) {
        // x and y are the start coords of the king
        // move the king temporarily
        boolean inCheck = false;

        BoardCell kingCell = cells.get(y).get(x);
        boolean kingMoved = kingCell.HasMoved();
        BoardCell newCellOriginal = cells.get(y+yOffset).get(x+xOffset);
        cells.get(y).set(x, new BoardCell(x, y)); // empty out the current cell

        kingCell.SetCoords(x+xOffset, y+yOffset);
        kingCell.SetHasMoved(true);
        cells.get(y+yOffset).set(x+xOffset, kingCell); // put king on the new cell

        // see if the enemypiece can hit the king in its new spot
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (EnemyCellAt(i, j, x+xOffset, y+yOffset)) {
                    ArrayList<Pair<Integer, Integer>> enemyMoves = GetEnemyMoves(i, j);
                    for (Pair<Integer, Integer> enemyMove : enemyMoves) {
                        if (enemyMove.getKey() == x+xOffset && enemyMove.getValue() == y+yOffset) {
                            inCheck = true;
                        }
                    }
                }
            }
        }
        // move the king back
        kingCell = cells.get(y+yOffset).get(x+xOffset);
        cells.get(y+yOffset).set(x+xOffset, newCellOriginal); // empty out the new cell, this should be the original cell, not a new board cell

        kingCell.SetCoords(x, y);
        kingCell.SetHasMoved(kingMoved);
        cells.get(y).set(x, kingCell); // put the king back on original cell

        return inCheck;
    }

    public boolean MovePutsOwnKingInCheck(int x, int y, int curWidth, int curHeight) {
        // if moving the piece to curWidth, curHeight puts OWN king in check, then it cant move and continue
        boolean putsOwnKingInCheck = false;
        boolean isP1 = cells.get(y).get(x).isP1Piece();

        // move piece from x, y to curWidth, curHeight
        // move the king temporarily

        BoardCell startCell = cells.get(y).get(x);
        boolean startMoved = startCell.HasMoved();
        BoardCell newCellOriginal = cells.get(curHeight).get(curWidth);
        cells.get(y).set(x, new BoardCell(x, y)); // empty out the current cell

        startCell.SetCoords(curWidth, curHeight);
        startCell.SetHasMoved(true);
        cells.get(curHeight).set(curWidth, startCell); // put king on the new cell

        // find my king
        // is he in check?
        // if he is, return true
        // if he is not, continue on
        // dont check ofr king in check here
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).PieceName() == Pieces.KING && cells.get(i).get(j).isP1Piece() == isP1) {
                    if (InCheckAt(j, i, 0, 0)) {
                        return true;
                    }
                }
            }
        }

        // move the king back
        startCell = cells.get(curHeight).get(curWidth);
        cells.get(curHeight).set(curWidth, newCellOriginal); // empty out the new cell, this should be the original cell, not a new board cell

        startCell.SetCoords(x, y);
        startCell.SetHasMoved(startMoved);
        cells.get(y).set(x, startCell); // put the king back on original cell

        return putsOwnKingInCheck;
    }

    public void UpdateKingInCheck() {
        // dont worry about putting king in check here
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).PieceName() == Pieces.KING) {
//                    ArrayList<Pair<Integer, Integer>> kingShiftPairs = cells.get(i).get(j).ChessPiece().GetShiftPairs();
//                    for (Pair<Integer, Integer> kingShiftPair : kingShiftPairs) {
//                        int kingShiftX = kingShiftPair.getKey();
//                        int kingShiftY = kingShiftPair.getValue();
//                        if (InCheckAt(j, i, kingShiftX, kingShiftY)) {
//                            cells.get(i).get(j).SetInCheck(true);
//                        }
//                    }
                    cells.get(i).get(j).SetInCheck(InCheckAt(j, i, 0, 0));
                }
            }
        }
    }

    public int Length() {
        return LEN;
    }
}
