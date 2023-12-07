package com.example.tictactoegame;

import javafx.application.Application;
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

    }

    private void setGameMode(boolean pvc, boolean cvc, Stage primaryStage) {

    }

    private void choosePvCPlayer(Stage gameStage) {

    }

    private void initializeGame(Stage gameStage) {

    }

    private void handleButtonClick(Button button, Stage gameStage) {

    }

    private void makeComputerMove() {

    }

    private void displayOutcome(String winner, Stage gameStage) {

    }

    private void resetGame() {

    }
}