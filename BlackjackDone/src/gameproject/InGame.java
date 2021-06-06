package gameproject;

import static gameproject.MenuController.window;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class InGame {

    Image playerImage = new Image("image/2-Recovered.png");
    Image wallImage = new Image("image/wall.png");
    Image monsterImage = new Image("image/spider_monster.png");
    Image stairImage = new Image("image/stair.png");

    BlackjackController gameFight = new BlackjackController();
    GameOverController gameOver = new GameOverController();
    private Text info = new Text();
    private Text infoHpPlayer = new Text();
    private Text infoScore = new Text();
    private Text infoPause = new Text();
    private Text infoE = new Text();

    public Entity player;
    private Entity stair;
    public List<Entity> enemy = new ArrayList<Entity>();
    private List<Entity> wall = new ArrayList<Entity>();

    private char move;
    private int temp;
    private String map = "************   *     **** * ****** * *     ** * ***** ** *   *   ** *** * ****     *   ** ******* **         ************";
    private boolean isFight = false;
    private boolean whileCheck = false;
    private static boolean windowCloes = false;
    private int numI = 0;
    private boolean checkW = true;
    private int hpPlayer = 3;
    public static int score = 0;
    private int numEnemy = 0;
    private int enemySpawn = 1;

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(830, 550);
        Rectangle InGameBG = new Rectangle(830, 550);
        InGameBG.setFill(Color.BLACK);
        Font font = new Font("8BIT WONDER Nominal", 20);
        //Font font = new Font(Font.("font/font1.TTF"));

        info.setFont(font);
        info.setTranslateX(600);
        info.setTranslateY(200);
        info.setFill(Color.WHITE);

        infoHpPlayer.setFont(font);
        infoHpPlayer.setTranslateX(600);
        infoHpPlayer.setTranslateY(250);
        infoHpPlayer.setFill(Color.WHITE);

        infoScore.setFont(font);
        infoScore.setTranslateX(600);
        infoScore.setTranslateY(300);
        infoScore.setFill(Color.WHITE);

        infoPause.setFont(font);
        infoPause.setTranslateX(580);
        infoPause.setTranslateY(400);
        infoPause.setFill(Color.WHITE);
        infoPause.setText("PRESS ENTER");
        infoPause.setVisible(false);

        infoE.setFont(font);
        infoE.setTranslateX(610);
        infoE.setTranslateY(400);
        infoE.setFill(Color.WHITE);
        infoE.setText("PRESS E");
        infoE.setVisible(false);

        infoHpPlayer.setText("HP : " + hpPlayer);
        info.setText("LEVEL : " + (numI + 1));
        infoScore.setText("SCORE : " + score);

        mapGen mapgen = new mapGen();

        map = mapgen.getGrid();
        System.out.println(map);
        player = new Entity(50, 50, 50, 50, playerImage);

        int tempx = 0;
        int tempy = 0;
        int temp = 0;

        for (int j = 0; j <= 10; j++) {
            for (int i = 0; i <= 10; i++) {
                if (map.charAt(temp) == '*') {
                    wall.add(new Entity(tempx, tempy, 50, 50, wallImage));
                }
                temp = temp + 1;
                tempx = tempx + 50;
            }
            tempx = 0;
            tempy = tempy + 50;
        }

        int check = 0;
        int xPositionE = 0, yPositionE = 0;
        int xPositionS = 0, yPositionS = 0;
        for (int i = 0; i < enemySpawn; i++) {
            while (true) {

                int randEnemyX = (int) (2 + Math.random() * 5);
                int randEnemyY = (int) (2 + Math.random() * 5);
                if (check == 0) {
                    if (map.charAt(randEnemyX + (randEnemyY * 11)) == ' ') {
                        xPositionE = randEnemyX;
                        yPositionE = randEnemyY;
                        check = 1;
                    }
                }
                if (check == 1) {
                    randEnemyX = (int) (7 + Math.random() * 4);
                    randEnemyY = (int) (7 + Math.random() * 4);
                    if (map.charAt(randEnemyX + (randEnemyY * 11)) == ' ') {
                        if (randEnemyX != xPositionE || randEnemyY != yPositionE) {
                            xPositionS = randEnemyX;
                            yPositionS = randEnemyY;
                            check = 0;
                            break;
                        }
                    }
                }
            }
            enemy.add(new Entity(xPositionE * 50, yPositionE * 50, 50, 50, monsterImage));
        }

        stair = new Entity(xPositionS * 50, yPositionS * 50, 50, 50, stairImage);

        root.getChildren().addAll(InGameBG, stair, player);

        for (Entity objecT : wall) {
            root.getChildren().addAll(objecT);
        }
        for (Entity objecT : enemy) {
            root.getChildren().addAll(objecT);
        }
        root.getChildren().addAll(info, infoHpPlayer, infoScore, infoPause, infoE);
        return root;
    }

    public boolean checkCollision(Entity a, Entity b) {

        if (a.getBoundsInParent().intersects(b.getBoundsInParent()) && a.getTranslateX() == b.getTranslateX() && a.getTranslateY() == b.getTranslateY()) {
            return true;
        } else {
            return false;
        }
    }

    private static class Entity extends Parent {

        double width;
        double height;

        public Entity(double x, double y, double w, double h, Image image) {
            setTranslateX(x);
            setTranslateY(y);
            width = w;
            height = h;
            Rectangle shape = new Rectangle(w, h);
            shape.setFill(new ImagePattern(image));
            getChildren().add(shape);
        }
    }

    public void upDate(Scene scene) {
        move = '0';
        scene.setOnKeyPressed(event -> {
            if (gameFight.isCheckWindow() == false) {
                if (event.getCode() == KeyCode.W) {
                    player.setTranslateY(player.getTranslateY() - 50);
                    move = 'w';
                }
                if (event.getCode() == KeyCode.S) {
                    player.setTranslateY(player.getTranslateY() + 50);
                    move = 's';
                }
                if (event.getCode() == KeyCode.A) {
                    player.setTranslateX(player.getTranslateX() - 50);
                    move = 'a';
                }
                if (event.getCode() == KeyCode.D) {
                    player.setTranslateX(player.getTranslateX() + 50);
                    move = 'd';
                }
            }

            for (int i = 0; i < enemy.size(); i++) {
                if (checkCollision(player, enemy.get(i))) {
                    try {
                        gameFight.showWindow();
                    } catch (IOException ex) {
                        Logger.getLogger(InGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    numEnemy = i;
                }
                if (gameFight.isCheckWindow()) {
                    infoPause.setVisible(true);
                } else if (gameFight.isCheckWindow() == false) {
                    infoPause.setVisible(false);
                }

                if (event.getCode() == KeyCode.ENTER) {
                    gameFight.setCheckWindow(false);
                }
                /*if (event.getCode() == KeyCode.F) {
                    info.setText("");
                    enemy.get(i).setVisible(false);
                    enemy.remove(i);
                    gameFight.setCheckWindow(false);
                    
                }*/

                if (BlackjackController.isPlayerDraw() == false && BlackjackController.isCheckGame() && BlackjackController.isPlayerWin() == true && event.getCode() == KeyCode.ENTER && checkW) {
                    BlackjackController.setPlayerWin(false);
                    BlackjackController.setCheckGame(false);
                    enemy.get(numEnemy).setVisible(false);
                    enemy.remove(numEnemy);
                    score += 100;
                    if (BlackjackController.isPlayerBlackjack()) {
                        score += 100;
                        BlackjackController.setPlayerBlackjack(false);
                        hpPlayer++;
                    }
                    BlackjackController.setCheckLeave(true);

                } else if (BlackjackController.isPlayerDraw() == false && BlackjackController.isCheckGame() && BlackjackController.isPlayerWin() == false && event.getCode() == KeyCode.ENTER && checkW) {
                    hpPlayer--;
                    BlackjackController.setPlayerBlackjack(false);
                    checkW = false;
                    BlackjackController.setCheckLeave(true);
                }

                if (hpPlayer <= 0) {
                    try {
                        endGame();
                    } catch (IOException ex) {
                        Logger.getLogger(InGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //System.out.println("gameFight : " + gameFight.isCheckWindow());
            }
            if (checkCollision(player, stair)) {
                infoE.setVisible(true);
            } else {
                infoE.setVisible(false);
            }
            if (checkCollision(player, stair) && checkW && event.getCode() == KeyCode.E) {

                while (!wall.isEmpty()) {
                    wall.remove(0);
                }
                while (!enemy.isEmpty()) {
                    enemy.remove(0);
                }
                map();
                if (numI > 0) {
                    closeMap();

                }
                numI++;
                score += 50;
                hpPlayer++;
                if (numI % 2 == 1 && numI != 0) {
                    enemySpawn++;
                    System.out.println("11");
                }
                checkW = false;

            }
            infoHpPlayer.setText("HP : " + hpPlayer);
            info.setText("LEVEL : " + (numI + 1));
            infoScore.setText("SCORE : " + score);

            if (checkW == false) {
                checkW = true;
            }
            for (Entity objecT : wall) {
                if (checkCollision(player, objecT) == true) {
                    if (move == 'w') {
                        player.setTranslateY(player.getTranslateY() + 50);
                    } else if (move == 's') {
                        player.setTranslateY(player.getTranslateY() - 50);
                    } else if (move == 'a') {
                        player.setTranslateX(player.getTranslateX() + 50);
                    } else if (move == 'd') {
                        player.setTranslateX(player.getTranslateX() - 50);
                    }
                }
            }
            for (int i = 0; i < enemy.size(); i++) {
                if (checkCollision(player, enemy.get(i)) == true) {
                    if (move == 'w') {
                        player.setTranslateY(player.getTranslateY() + 50);
                    } else if (move == 's') {
                        player.setTranslateY(player.getTranslateY() - 50);
                    } else if (move == 'a') {
                        player.setTranslateX(player.getTranslateX() + 50);
                    } else if (move == 'd') {
                        player.setTranslateX(player.getTranslateX() - 50);
                    }
                }
            }
        });
    }

    public void map() {
        MenuController.window.close();
        //((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        Scene inGameScene = new Scene(createContent());
        window = new Stage();
        window.setTitle("BlackJackRPG");
        window.setScene(inGameScene);
        window.show();
        upDate(inGameScene);
    }

    public void closeMap() {
        while (!wall.isEmpty()) {
            wall.remove(0);
        }
        while (!enemy.isEmpty()) {
            enemy.remove(0);
        }
        window.close();
        Scene inGameScene = new Scene(createContent());
        window = new Stage();
        window.setTitle("BlackJackRPG");
        window.setScene(inGameScene);
        window.show();
        upDate(inGameScene);
    }

    public void endGame() throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("gameOver.fxml"));
        Scene scene = new Scene(menu);
        window.setTitle("BlackJackRPG");
        window.setScene(scene);
        window.show();
    }
}
