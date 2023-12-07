package com.example.tictactoegame;

import javafx.scene.control.Button;

import java.util.Random;

public class Minimax {
    public static int[] minimax(Button[][] board, int depth, boolean maximizingPlayer) {
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

    public static int minimaxScore(Button[][] board, int depth, boolean maximizingPlayer) {
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

    public static void makeComputerVsComputerMove(Button[][] buttons, boolean playerXTurn) {
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
}
