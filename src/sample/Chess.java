package sample;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Chess {
    private Turn turn;

    private Board board;

    private Player p1;
    private Player p2;

    private ChessView view;

    public Chess(Stage stage) throws FileNotFoundException {
        turn = Turn.P1PieceSelect;
        board = new Board();
        view = new ChessView(stage, this);
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
