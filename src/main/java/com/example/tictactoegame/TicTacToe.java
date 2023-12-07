package com.example.tictactoegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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