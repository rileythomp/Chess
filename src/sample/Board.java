package sample;

import javafx.util.Pair;

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
        ArrayList<Pair<Integer, Integer>> possibleMoves = startCell.Moves(this);

        // possible moves on the board, need to check for pieces blocking
        for (int i = 0; i < possibleMoves.size(); ++i) {
            Cell(possibleMoves.get(i).getKey(), possibleMoves.get(i).getValue()).setCanBeMovedTo(true);
        }
    }

    public boolean InCheckAt(int x, int y, int offset) {
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).isP1Piece() && !cells.get(y).get(x).isP1Piece()) {

                    ArrayList<Pair<Integer, Integer>> enemyMoves;
                    if (cells.get(i).get(j).PieceName() == Pieces.KING) {
                        enemyMoves = cells.get(i).get(j).ChessPiece().Moves(j, i, this, true);
                    }
                    else {
                        enemyMoves = cells.get(i).get(j).ChessPiece().Moves(j, i, this);
                    }

                    for (Pair<Integer, Integer> enemyMove : enemyMoves) {
                        if (enemyMove.getKey() == x+offset && enemyMove.getValue() == y) {
                            return true;
                        }
                    }
                }
                else if (!cells.get(i).get(j).isP1Piece() && cells.get(y).get(x).isP1Piece()) {

                    ArrayList<Pair<Integer, Integer>> enemyMoves = new ArrayList<>();
                    if (cells.get(i).get(j).PieceName() == Pieces.KING) {
                        enemyMoves = cells.get(i).get(j).ChessPiece().Moves(j, i, this, true);
                    }
                    else if (cells.get(i).get(j).HasPiece()) {
                        enemyMoves = cells.get(i).get(j).ChessPiece().Moves(j, i, this);
                    }

                    for (Pair<Integer, Integer> enemyMove : enemyMoves) {
                        if (enemyMove.getKey() == x+offset && enemyMove.getValue() == y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                if (cells.get(i).get(j).isClicked()) {
                    selectedCell = cells.get(i).get(j);
                    if (selectedCell.PieceName() == Pieces.PAWN && Math.abs(i - y) == 2) {
                        cells.get(i+selectedCell.ChessPiece().direction).get(j).SetEnPassant(1);
                    }
                    cells.get(i).set(j, new BoardCell(j, i));
                }
            }
        }

        selectedCell.SetCoords(x, y);
        selectedCell.SetHasMoved();
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

        for (int i = 0; i < LEN; ++i) {
            for (int j = 0; j < LEN; ++j) {
                cells.get(i).get(j).SetEnPassant(cells.get(i).get(j).IsEnPassant()%2);
            }
        }
    }

    public int Length() {
        return LEN;
    }
}
