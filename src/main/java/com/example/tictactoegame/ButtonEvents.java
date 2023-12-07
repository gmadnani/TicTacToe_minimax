package com.example.tictactoegame;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Random;

public class ButtonEvents {
    public static void handleButtonClick(Button button, Stage gameStage, boolean playerXTurn, boolean pvcMode, boolean cvcMode) {
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

//    public static void makeComputerMove(Button[][] buttons, boolean pvcMode, boolean cvcMode, boolean playerXTurn) {
//        if (pvcMode) {
//            makeComputerVsPlayerMove();
//        } else if (cvcMode) {
//            makeComputerVsComputerMove();
//        }
//    }
//
//    public static void makeComputerVsPlayerMove(Button[][] buttons, boolean pvcMode, boolean playerXTurn) {
//        int[] bestMove = minimax(buttons, 0, true);
//        int row = bestMove[0];
//        int col = bestMove[1];
//
//        buttons[row][col].setText("O");
//
//        if (checkForWinner()) {
//            displayOutcome("O", null);
//        } else if (isBoardFull()) {
//            displayOutcome("Draw", null);
//        } else {
//            playerXTurn = true;
//        }
//    }
//
//    public static void makeComputerVsComputerMove(Button[][] buttons, boolean playerXTurn) {
//        Random random = new Random();
//
//        String symbol = playerXTurn ? "X" : "O";
//
//        int row, col;
//        do {
//            row = random.nextInt(SIZE);
//            col = random.nextInt(SIZE);
//        } while (!buttons[row][col].getText().isEmpty());
//
//        buttons[row][col].setText(symbol);
//
//        if (checkForWinner()) {
//            displayOutcome(symbol, null);
//        } else if (isBoardFull()) {
//            displayOutcome("Draw", null);
//        } else {
//            playerXTurn = !playerXTurn;
//            makeComputerMove();
//        }
//    }
}
