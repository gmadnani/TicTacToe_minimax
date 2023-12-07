package com.example.tictactoegame;

import javafx.scene.control.Button;

public class BoardChecks {
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
