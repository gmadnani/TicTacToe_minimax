package com.example.tictactoegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Random;

public class TicTacToe extends Application {
    private static final int SIZE = 3;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private boolean playerXTurn = true; // Player X starts first
    private boolean pvcMode = false;
    private boolean cvcMode = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic-Tac-Toe Mode Selection");

        HBox modeSelection = new HBox(10);
        Button pvpButton = new Button("PvP");
        Button pvcButton = new Button("PvC");
        Button cvcButton = new Button("CvC");

        pvpButton.setOnAction(e -> setGameMode(false, false, primaryStage));
        pvcButton.setOnAction(e -> setGameMode(true, false, primaryStage));
        cvcButton.setOnAction(e -> setGameMode(false, true, primaryStage));

        modeSelection.getChildren().addAll(pvpButton, pvcButton, cvcButton);

        Scene scene = new Scene(modeSelection, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setGameMode(boolean pvc, boolean cvc, Stage primaryStage) {
        pvcMode = pvc;
        cvcMode = cvc;

        primaryStage.close();

        Stage gameStage = new Stage();
        gameStage.setTitle("Tic-Tac-Toe Game");

        if (pvcMode) {
            choosePvCPlayer(gameStage);
        } else {
            initializeGame(gameStage);
        }
    }

    private void choosePvCPlayer(Stage gameStage) {
        Stage playerSelectionStage = new Stage();
        playerSelectionStage.setTitle("Choose First Player");

        HBox playerSelectionBox = new HBox(10);
        Button playerXButton = new Button("Player X");
        Button playerOButton = new Button("Player O");

        playerXButton.setOnAction(e -> {
            playerXTurn = true;
            pvcMode = true;
            playerSelectionStage.close();
            initializeGame(gameStage);
        });

        playerOButton.setOnAction(e -> {
            playerXTurn = false;
            pvcMode = true;
            playerSelectionStage.close();
            initializeGame(gameStage);
        });

        playerSelectionBox.getChildren().addAll(playerXButton, playerOButton);
        playerSelectionBox.setAlignment(Pos.CENTER);

        Scene playerSelectionScene = new Scene(playerSelectionBox, 300, 100);
        playerSelectionStage.setScene(playerSelectionScene);
        playerSelectionStage.showAndWait();
    }

    private void initializeGame(Stage gameStage) {
        GridPane gridPane = createGridPane(gameStage);
        HBox buttonBox = createButtonBox(gameStage);

        Scene gameScene = new Scene(new HBox(gridPane, buttonBox), 500, 300);
        gameStage.setScene(gameScene);
        gameStage.show();

        if (cvcMode) {
            makeComputerMove();
        } else if (!playerXTurn && pvcMode) {
            makeComputerMove();
        }
        else {
            playerXTurn = true;
        }
    }


    private GridPane createGridPane(Stage gameStage) {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.setOnAction(e -> handleButtonClick(button, gameStage));
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        return gridPane;
    }

    private HBox createButtonBox(Stage gameStage) {
        HBox buttonBox = new HBox(10);
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> resetGame());
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> gameStage.close());

        buttonBox.getChildren().addAll(restartButton, exitButton);

        return buttonBox;
    }

    private void handleButtonClick(Button button, Stage gameStage) {
        if (button.getText().isEmpty()) {
            button.setText(playerXTurn ? "X" : "O");

            if (checkForWinner()) {
                displayOutcome(playerXTurn ? "X" : "O", gameStage);
            } else if (isBoardFull()) {
                displayOutcome("Draw", gameStage);
            } else {
                playerXTurn = !playerXTurn;

                if (!playerXTurn && (pvcMode || cvcMode)) {
                    makeComputerMove();
                }
            }
        }
    }

    private void makeComputerMove() {
        if (pvcMode) {
            makeComputerVsPlayerMove();
        } else if (cvcMode) {
            makeComputerVsComputerMove();
        }
    }

    private void makeComputerVsPlayerMove() {
        int[] bestMove = minimax(buttons, 0, true);
        int row = bestMove[0];
        int col = bestMove[1];

        buttons[row][col].setText("O");

        if (checkForWinner()) {
            displayOutcome("O", null);
        } else if (isBoardFull()) {
            displayOutcome("Draw", null);
        } else {
            playerXTurn = true;
        }
    }

    private int[] minimax(Button[][] board, int depth, boolean maximizingPlayer) {
        int[] bestMove = {-1, -1};
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].getText().isEmpty()) {
                    board[i][j].setText(maximizingPlayer ? "O" : "X");

                    int score = minimaxScore(board, depth - 1, !maximizingPlayer);

                    board[i][j].setText(""); // Undo the move

                    if ((maximizingPlayer && score > bestScore) || (!maximizingPlayer && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimaxScore(Button[][] board, int depth, boolean maximizingPlayer) {
        if (checkForWinner()) {
            return maximizingPlayer ? -1 : 1;
        } else if (isBoardFull() || depth == 0) {
            return 0;
        }

        int score = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].getText().isEmpty()) {
                    board[i][j].setText(maximizingPlayer ? "O" : "X");

                    int currentScore = minimaxScore(board, depth - 1, !maximizingPlayer);

                    board[i][j].setText(""); // Undo the move

                    score = maximizingPlayer ? Math.max(score, currentScore) : Math.min(score, currentScore);
                }
            }
        }

        return score;
    }


    private void makeComputerVsComputerMove() {
        Random random = new Random();

        String symbol = playerXTurn ? "X" : "O";

        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (!buttons[row][col].getText().isEmpty());

        buttons[row][col].setText(symbol);

        if (checkForWinner()) {
            displayOutcome(symbol, null);
        } else if (isBoardFull()) {
            displayOutcome("Draw", null);
        } else {
            playerXTurn = !playerXTurn;
            makeComputerMove();
        }
    }

    private boolean checkForWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < SIZE; i++) {
            if (!buttons[i][0].getText().isEmpty() &&
                    buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < SIZE; i++) {
            if (!buttons[0][i].getText().isEmpty() &&
                    buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return (!buttons[0][0].getText().isEmpty() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) ||
                (!buttons[0][2].getText().isEmpty() &&
                        buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                        buttons[0][2].getText().equals(buttons[2][0].getText()));
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayOutcome(String winner, Stage gameStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);

        if (winner.equals("X")) {
            alert.setContentText("Player X wins!");
        } else if (winner.equals("O")) {
            alert.setContentText("Player O wins!");
        } else {
            alert.setContentText("It's a draw!");
        }

        alert.showAndWait();

        resetGame();
    }

    private void resetGame() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
            }
        }

        Stage currentStage = (Stage) buttons[0][0].getScene().getWindow();
        currentStage.close();

        start(new Stage());
    }
}