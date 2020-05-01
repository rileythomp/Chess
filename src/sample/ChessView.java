package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class ChessView {
    private Stage gameStage;
    private Pane gameRoot;
    private Scene gameScene;

    private final Image WHITE_PAWN = new Image(new FileInputStream("pieces/whitepawn.png"));
    private final Image BLACK_PAWN = new Image(new FileInputStream("pieces/blackpawn.png"));
    private final Image WHITE_ROOK = new Image(new FileInputStream("pieces/whiterook.png"));
    private final Image BLACK_ROOK = new Image(new FileInputStream("pieces/blackrook.png"));
    private final Image WHITE_KNIGHT = new Image(new FileInputStream("pieces/whiteknight.png"));
    private final Image BLACK_KNIGHT = new Image(new FileInputStream("pieces/blackknight.png"));
    private final Image WHITE_BISHOP = new Image(new FileInputStream("pieces/whitebishop.png"));
    private final Image BLACK_BISHOP = new Image(new FileInputStream("pieces/blackbishop.png"));
    private final Image WHITE_KING = new Image(new FileInputStream("pieces/whiteking.png"));
    private final Image BLACK_KING = new Image(new FileInputStream("pieces/blackking.png"));
    private final Image WHITE_QUEEN = new Image(new FileInputStream("pieces/whitequeen.png"));
    private final Image BLACK_QUEEN = new Image(new FileInputStream("pieces/blackqueen.png"));

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 800;
    private final int FONT_SIZE = 30;
    private final String FONT_NAME = "verdana";
    private Color BACKGROUND_COLOR = Color.BURLYWOOD;

    private final int CELL_LEN = 50;
    private final int BOARD_LEN = 8;

    Chess chessGame;

    public ChessView(Stage stage, Chess chess) throws FileNotFoundException {
        chessGame = chess;
        gameStage = stage;
    }

    public void DisplayGame(Board board, Turn turn) {
        gameRoot = new Pane();

        DisplayBoard(board);

        for (int i = BOARD_LEN; i > 0; --i) {
            Text rowNum = Util.CreateTextNode(
                    String.valueOf(i),
                    FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                    ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) - 25, 31 + 50*(8-i+1)
            );
            gameRoot.getChildren().add(rowNum);
        }

        for (int i = BOARD_LEN; i > 0; --i) {
            Text colChar = Util.CreateTextNode(
                    String.valueOf((char)('a' + (8-i))),
                    FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                    ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN/2 - 4 + 50*(8-i), 50 + CELL_LEN*BOARD_LEN + 25
            );
            gameRoot.getChildren().add(colChar);
        }

        String turnText = GetTurnText(chessGame.Turn());
        Text turnMessage = Util.CreateTextNode(
                turnText,
                FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                50, 50 + CELL_LEN * board.Length() + 33 + 33
        );
        gameRoot.getChildren().add(turnMessage);

        gameScene = new Scene(gameRoot, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);

        if (chessGame.Turn() == Turn.P1PromotionSelect || chessGame.Turn() == Turn.P2PromotionSelect) {
            String promotionText = "Please enter what piece you'd like to promote your pawn to: \n\n" +
                    "q - Queen \n\n" +
                    "r - Rook \n\n" +
                    "b - Bishop \n\n" +
                    "k - Knight \n\n";

            Text promotionMessage = Util.CreateTextNode(
                    promotionText,
                    FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                    50, 50 + CELL_LEN * board.Length() + 33 + 33 + 33
            );

            gameRoot.getChildren().add(promotionMessage);
            gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode pressed = keyEvent.getCode();
                    if (pressed == KeyCode.Q) {
                        chessGame.PromotePieceTo(Pieces.QUEEN);
                        gameRoot.getChildren().remove(promotionMessage);
                        DisplayGame(chessGame.Board(), chessGame.Turn());
                    }
                    else if (pressed == KeyCode.R) {
                        chessGame.PromotePieceTo(Pieces.ROOK);
                        gameRoot.getChildren().remove(promotionMessage);
                        DisplayGame(chessGame.Board(), chessGame.Turn());
                    }
                    else if (pressed == KeyCode.B) {
                        chessGame.PromotePieceTo(Pieces.BISHOP);
                        gameRoot.getChildren().remove(promotionMessage);
                        DisplayGame(chessGame.Board(), chessGame.Turn());
                    }
                    else if (pressed == KeyCode.K) {
                        chessGame.PromotePieceTo(Pieces.KNIGHT);
                        gameRoot.getChildren().remove(promotionMessage);
                        DisplayGame(chessGame.Board(), chessGame.Turn());
                    }
                }
            });
        }
        gameStage.setScene(gameScene);
    }

    private String GetTurnText(Turn turn) {
        String msg = "";
        if (turn == Turn.P1PieceSelect) {
            msg = "Player 1 select piece";
        }
        else if (turn == Turn.P1PlaceSelect) {
            msg = "Player 1 place piece";
        }
        else if (turn == Turn.P2PieceSelect) {
            msg = "Player 2 select piece";
        }
        else if (turn == Turn.P2PlaceSelect){
            msg = "Player 2 place piece";
        }
        else if (turn == Turn.P1PromotionSelect) {
            msg = "Player 1 choose promotion piece";
        }
        else if (turn == Turn.P2PromotionSelect){
            msg = "Player 2 choose promotion piece";
        }
        return msg;
    }

    private void DisplayBoard(Board board) {
        for (int i = 0; i < board.Length(); ++i) {
            for (int j = 0; j < board.Length(); ++j) {
                BoardCell boardCell = board.Cell(j, i);

                DisplayCells(i, j, board, boardCell);

                if (boardCell.HasPiece()) {
                    DisplayPiece(boardCell, i, j);
                }
            }
        }

        DisplayLines();
    }

    private void DisplayLines() {
        for (int i = 0; i <= BOARD_LEN; ++i) {
            Line line = new Line(((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2), CELL_LEN*i + 50, ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN*BOARD_LEN, 50 + CELL_LEN*i);
            line.setStroke(Color.BLACK);
            gameRoot.getChildren().add(line);
        }

        for (int i = 0; i <= BOARD_LEN; ++i) {
            Line line = new Line(((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN*i, 50, ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + CELL_LEN*i, 50 + CELL_LEN*BOARD_LEN);
            line.setStroke(Color.BLACK);
            gameRoot.getChildren().add(line);
        }
    }

    private void DisplayCells(int i, int j, Board board, BoardCell boardCell) {
        Rectangle cellView = new Rectangle(CELL_LEN*j + ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2), CELL_LEN*i + 50, CELL_LEN, CELL_LEN);
        if ((i+j)%2 == 0) {
            cellView.setFill(Color.WHITESMOKE);
        }
        else {
            cellView.setFill(Color.BROWN);
        }
        cellView.opacityProperty().set(1);

        final int x = j;
        final int y = i;
        EventHandler<MouseEvent> handleCellClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Selecting Pieces
                if (boardCell.HasPiece() && boardCell.ChessPiece().CanMove(x, y, board) && boardCell.isP1Piece() && chessGame.Turn() == Turn.P1PieceSelect) {
                    chessGame.SelectPiece(boardCell, true);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
                else if (boardCell.HasPiece() && boardCell.ChessPiece().CanMove(x, y, board) && !boardCell.isP1Piece() && chessGame.Turn() == Turn.P2PieceSelect) {
                    chessGame.SelectPiece(boardCell, true);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
                else if (boardCell.HasPiece() && boardCell.ChessPiece().CanMove(x, y, board) && boardCell.isP1Piece() && chessGame.Turn() == Turn.P1PlaceSelect) {
                    chessGame.SelectPiece(boardCell, false);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
                else if (boardCell.HasPiece() && boardCell.ChessPiece().CanMove(x, y, board) && !boardCell.isP1Piece() && chessGame.Turn() == Turn.P2PlaceSelect) {
                    chessGame.SelectPiece(boardCell, false);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }

                // Placing Pieces
                else if (chessGame.Turn() == Turn.P1PlaceSelect && boardCell.CanBeMovedTo() && !boardCell.HasPiece()) {
                    // can move here
                    chessGame.PlacePiece(x, y);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
                else if (chessGame.Turn() == Turn.P1PlaceSelect && boardCell.CanBeMovedTo()&& !boardCell.isP1Piece()) {
                    // can move here
                    chessGame.PlacePiece(x, y);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
                else if (chessGame.Turn() == Turn.P2PlaceSelect && boardCell.CanBeMovedTo() && !boardCell.HasPiece()) {
                    // can move here
                    chessGame.PlacePiece(x, y);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
                else if (chessGame.Turn() == Turn.P2PlaceSelect && boardCell.CanBeMovedTo() && boardCell.isP1Piece()) {
                    // can move here
                    chessGame.PlacePiece(x, y);
                    DisplayGame(chessGame.Board(), chessGame.Turn());
                }
            }
        };

        if (boardCell.isClicked()) {
            cellView.opacityProperty().set(0.5);
        }
        if (boardCell.CanBeMovedTo()) {
            cellView.setFill(Color.LIGHTGREEN);
            cellView.opacityProperty().set(0.45);
        }
//        if (boardCell.IsEnPassant() > 0) {
//            cellView.setFill(Color.RED);
//            cellView.opacityProperty().set(0.5);
//        }
        if (boardCell.IsGettingPromoted()) {
            cellView.setFill(Color.BLUE);
            cellView.opacityProperty().set(0.5);
        }

        if (boardCell.IsInCheck()) {
            cellView.setFill(Color.RED);
            cellView.opacityProperty().set(0.5);
        }
        cellView.addEventFilter(MouseEvent.MOUSE_CLICKED, handleCellClick);
        gameRoot.getChildren().add(cellView);
    }

    private void DisplayPiece(BoardCell boardCell, int i, int j) {
        ImageView pieceView;

        if (Pieces.PAWN == boardCell.PieceName()) {
            if (boardCell.isP1Piece()) {
                pieceView = new ImageView(BLACK_PAWN);
            }
            else {
                pieceView = new ImageView(WHITE_PAWN);
            }
        }
        else if (Pieces.BISHOP == boardCell.PieceName()) {
            if (boardCell.isP1Piece()) {
                pieceView = new ImageView(BLACK_BISHOP);
            }
            else {
                pieceView = new ImageView(WHITE_BISHOP);
            }
        }
        else if (Pieces.ROOK == boardCell.PieceName()) {
            if (boardCell.isP1Piece()) {
                pieceView = new ImageView(BLACK_ROOK);
            }
            else {
                pieceView = new ImageView(WHITE_ROOK);
            }
        }
        else if (Pieces.KNIGHT == boardCell.PieceName()) {
            if (boardCell.isP1Piece()) {
                pieceView = new ImageView(BLACK_KNIGHT);
            }
            else {
                pieceView = new ImageView(WHITE_KNIGHT);
            }
        }
        else if (Pieces.QUEEN == boardCell.PieceName()) {
            if (boardCell.isP1Piece()) {
                pieceView = new ImageView(BLACK_QUEEN);
            }
            else {
                pieceView = new ImageView(WHITE_QUEEN);
            }
        }
        else if (Pieces.KING == boardCell.PieceName()) {
            if (boardCell.isP1Piece()) {
                pieceView = new ImageView(BLACK_KING);
            }
            else {
                pieceView = new ImageView(WHITE_KING);
            }
        }
        else {
            pieceView = new ImageView();
        }

        pieceView.setFitHeight(40);
        pieceView.setFitWidth(40);
        pieceView.setX(CELL_LEN*j + ((SCREEN_WIDTH - BOARD_LEN*CELL_LEN)/2) + 5);
        pieceView.setY(CELL_LEN*i + 50 + 5);
        gameRoot.getChildren().add(pieceView);
    }
}
