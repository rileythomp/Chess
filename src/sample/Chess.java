package sample;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.function.BinaryOperator;

public class Chess {
    private Turn turn;

    private Board board;

    private Player p1;
    private Player p2;

    private ChessView view;

    public Chess(Stage stage) throws FileNotFoundException {
        turn = Turn.P2PieceSelect;
        board = new Board();
        view = new ChessView(stage, this);
    }

    public void PlacePiece(int x, int y) {
        UpdateTurn();
        board.UnEnPassantAndPromotion();
        board.MoveSelectedPieceTo(x, y);
        boolean promotion = board.Cell(x, y).IsGettingPromoted();
        if (promotion && turn == Turn.P2PieceSelect) {
            turn = Turn.P1PromotionSelect;
        }
        else if (promotion && turn == Turn.P1PieceSelect) {
            turn = Turn.P2PromotionSelect;
        }
        board.ResetCells();
        // do not consider a move putting the king in check
        board.UpdateKingInCheck();
    }

    public void SelectPiece(BoardCell boardCell, boolean updateTurn) {
        if (updateTurn) {
            UpdateTurn();
        }
        board.ResetCells();
        boardCell.ClickCell();
        // check if moving king into check when doing this
        board.MarkPossibleMoves(boardCell);
    }

    public void PromotePieceTo(Pieces name) {
        board.PromotePieceTo(name);
        if (turn == Turn.P2PromotionSelect) {
            turn = Turn.P1PieceSelect;
        }
        else if (turn == Turn.P1PromotionSelect) {
            turn = Turn.P2PieceSelect;
        }
    }

    public void Play() {
        view.DisplayGame(board, turn);
    }

    public Board Board() {
        return board;
    }

    public Turn Turn() {
        return turn;
    }

    public void UpdateTurn() {
        if (turn == Turn.P1PieceSelect) {
            turn = Turn.P1PlaceSelect;
        }
        else if (turn == Turn.P1PlaceSelect) {
            turn = Turn.P2PieceSelect;
        }
        else if (turn == Turn.P2PieceSelect) {
            turn = Turn.P2PlaceSelect;
        }
        else if (turn == Turn.P2PlaceSelect) {
            turn = Turn.P1PieceSelect;
        }
    }
}
