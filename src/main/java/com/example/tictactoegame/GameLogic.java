package com.example.tictactoegame;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GameLogic {
    public static GridPane createGridPane(Stage gameStage, Button[][] buttons) {
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

    public static HBox createButtonBox(Stage gameStage) {
        HBox buttonBox = new HBox(10);
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> resetGame());
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> gameStage.close());

        buttonBox.getChildren().addAll(restartButton, exitButton);

        return buttonBox;
    }

    public static void makeComputerVsPlayerMove(Button[][] buttons, boolean pvcMode, boolean playerXTurn) {
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

    public static boolean checkForWinner(Button[][] buttons) {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    public static boolean checkRows(Button[][] buttons) {
        for (int i = 0; i < SIZE; i++) {
            if (!buttons[i][0].getText().isEmpty() &&
                    buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkColumns(Button[][] buttons) {
        for (int i = 0; i < SIZE; i++) {
            if (!buttons[0][i].getText().isEmpty() &&
                    buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDiagonals(Button[][] buttons) {
        return (!buttons[0][0].getText().isEmpty() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) ||
                (!buttons[0][2].getText().isEmpty() &&
                        buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                        buttons[0][2].getText().equals(buttons[2][0].getText()));
    }

    public static boolean isBoardFull(Button[][] buttons) {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
