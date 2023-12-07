package com.example.tictactoegame;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class OutcomeDisplay {
    public static void displayOutcome(String winner, Stage gameStage) {
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
}
