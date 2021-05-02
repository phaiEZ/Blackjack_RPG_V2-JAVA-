/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author TUF
 */
public class BlackjackController implements Initializable {

    @FXML
    private HBox playerCard, enemyCard;
    @FXML
    private Label playWin, playLose, playDraw, pointEnemy, pointPlayer, playblackjack;
    @FXML
    private Button drawCardButton, standButton, leaveButton;
    @FXML
    private ImageView standView, leaveView, hitView;

    private boolean checkStandaPlayer = true;
    private boolean checkStandaEnemy = true;
    private boolean checkWinPlayer = true;
    private boolean checkWinEnemy = true;
    private boolean checkLoopStand = true;
    private boolean checkEnd = true;

    private int numCardPalyer = 2;
    private int numCardEnemy = 2;

    private Deck playingDeck = new Deck();
    private Deck playerCards = new Deck();
    private Deck enemyCards = new Deck();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerCards.moveAllToDeck(playingDeck);
        enemyCards.moveAllToDeck(playingDeck);
        playingDeck.createFullDeck();
        playingDeck.shuffle();

        playerCards.draw(playingDeck);
        playerCards.draw(playingDeck);
        System.out.println("1." + playerCards.getCradSuit(0) + " " + playerCards.getCradValue(0));
        System.out.println("2." + playerCards.getCradSuit(1) + " " + playerCards.getCradValue(1));
        for (int i = 0; i < 2; i++) {
            Rectangle temp = (Rectangle) playerCard.getChildren().get(i);
            temp.setVisible(true);
            temp.setFill(new ImagePattern(new Image("image/" + playerCards.getCradSuit(i) + playerCards.getCradValue(i) + ".png")));
        }

        enemyCards.draw(playingDeck);
        enemyCards.draw(playingDeck);
        for (int i = 0; i < 2; i++) {
            Rectangle temp = (Rectangle) enemyCard.getChildren().get(i);
            temp.setVisible(true);
            temp.setFill(new ImagePattern(new Image("image/backCard.png")));
        }

        pointPlayer.setText(playerCards.cardsValue() + "/21");

        System.out.println("player point = " + playerCards.cardsValue());
        System.out.println("enemy point = " + enemyCards.cardsValue());
    }

    @FXML
    private void drawCardButton(ActionEvent event) {
        if (numCardPalyer < 5 && checkStandaPlayer) {
            playerCards.draw(playingDeck);
            System.out.println((numCardPalyer + 1) + "." + playerCards.getCradSuit(numCardPalyer) + " " + playerCards.getCradValue(numCardPalyer));
            Rectangle temp = (Rectangle) playerCard.getChildren().get(numCardPalyer);
            temp.setFill(new ImagePattern(new Image("image/" + playerCards.getCradSuit(numCardPalyer) + playerCards.getCradValue(numCardPalyer) + ".png")));
            playerCard.getChildren().get(numCardPalyer++).setVisible(true);
        }
        upDate();
    }

    @FXML
    private void standButton(ActionEvent event) {
        checkStandaPlayer = false;

        while (checkStandaEnemy || checkLoopStand == false) {
            upDate();
        }
        if (checkStandaEnemy == false && checkStandaPlayer == false) {
            upDate();
        }
    }

    private void upDate() {

        if (enemyCards.cardsValue() > 21) {
            checkWinEnemy = false;
            checkStandaEnemy = false;
        }
        if (playerCards.cardsValue() > 21) {
            checkWinPlayer = false;
            checkStandaPlayer = false;
        }
        if (checkStandaEnemy == false && checkStandaPlayer == false) {
            if (playerCards.cardsValue() == enemyCards.cardsValue() && checkWinPlayer && checkWinEnemy) {

                playDraw.setVisible(true);
                checkLoopStand = true;
                checkEnd = false;
                endGame();

            } else if (checkWinPlayer == false && checkWinEnemy == false) {
                playDraw.setVisible(true);
                checkLoopStand = true;
                checkEnd = false;
                endGame();
            } else if (playerCards.cardsValue() > enemyCards.cardsValue() && checkWinPlayer) {
                playWin.setVisible(true);
                checkLoopStand = true;
                checkEnd = false;
                endGame();
            } else if (playerCards.cardsValue() < enemyCards.cardsValue() && checkWinEnemy) {
                playLose.setVisible(true);
                checkLoopStand = true;
                checkEnd = false;
                endGame();
            }

        }
        if (checkWinPlayer == false && checkEnd) {
            playLose.setVisible(true);
            checkLoopStand = true;
            endGame();
        } else if (checkWinEnemy == false && checkEnd) {
            playWin.setVisible(true);
            checkLoopStand = true;
            endGame();
        }

        if (enemyCards.cardsValue() <= 10 && checkStandaEnemy && checkWinPlayer) {
            checkStandaEnemy = true;
        } else if (enemyCards.cardsValue() < 19 && checkStandaEnemy && checkWinPlayer) {
            int i = (int) (Math.random() * 2);
            if (i == 0) {
                checkStandaEnemy = false;
                checkLoopStand = false;
            } else {
                checkStandaEnemy = true;
            }
        } else {
            checkStandaEnemy = false;
        }
        if (numCardEnemy < 5 && checkStandaEnemy) {
            Rectangle temp = (Rectangle) enemyCard.getChildren().get(numCardEnemy);
            temp.setFill(new ImagePattern(new Image("image/backCard.png")));
            enemyCard.getChildren().get(numCardEnemy++).setVisible(true);
            enemyCards.draw(playingDeck);

        }
        System.out.println("player point = " + playerCards.cardsValue());
        System.out.println("enemy point = " + enemyCards.cardsValue());
        pointPlayer.setText(playerCards.cardsValue() + "/21");
        if (enemyCards.cardsValue() > 21) {
            checkWinEnemy = false;
            checkStandaEnemy = false;
        }
        if (playerCards.cardsValue() > 21) {
            checkWinPlayer = false;
            checkStandaPlayer = false;
        }

        if (checkWinPlayer == false && checkEnd) {
            playLose.setVisible(true);
            checkLoopStand = true;
            endGame();
        } else if (checkWinEnemy == false && checkEnd) {
            playWin.setVisible(true);
            checkLoopStand = true;
            endGame();
        }

    }

    private void endGame() {
        pointEnemy.setText(enemyCards.cardsValue() + "/21");
        for (int i = 0; i < numCardEnemy; i++) {
            Rectangle temp = (Rectangle) enemyCard.getChildren().get(i);
            temp.setFill(new ImagePattern(new Image("image/" + enemyCards.getCradSuit(i) + enemyCards.getCradValue(i) + ".png")));
        }
        if (playerCards.deckSize() == 2 && playerCards.cardsValue() == 21 || enemyCards.deckSize() == 2 && enemyCards.cardsValue() == 21) {
            playblackjack.setVisible(true);
        }
    }

    @FXML
    private void leaveButton(ActionEvent event) {

    }

    @FXML
    private void drawCardButtonEntered(MouseEvent event) {
        //Image temp = new Image("image/hit2.png");
        //hitView.setImage(temp);
        hitView.setVisible(true);

    }

    @FXML
    private void drawCardButtonExited(MouseEvent event) {
        //Image temp = new Image("image/test.png");
        //hitView.setImage(temp);
        hitView.setVisible(false);
    }

    @FXML
    private void standButtonEntered(MouseEvent event) {
        standView.setVisible(true);
    }

    @FXML
    private void standButtonExited(MouseEvent event) {
        standView.setVisible(false);
    }

    @FXML
    private void leaveButtonEntered(MouseEvent event) {
        leaveView.setVisible(true);
    }

    @FXML
    private void leaveButtonExited(MouseEvent event) {
        leaveView.setVisible(false);
    }
}
