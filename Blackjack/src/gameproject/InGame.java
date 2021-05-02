package gameproject;

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
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class InGame {

    Image playerImage = new Image("image/player.png");
    Image wallImage = new Image("image/wall.png");
    Image monsterImage = new Image("image/spider_monster.png");
    Image stairImage = new Image("image/stair.png");

    Parent fightParent;

    private Text info = new Text();
    public Entity player;
    private Entity stair;
    public List<Entity> enemy = new ArrayList<Entity>();
    private List<Entity> wall = new ArrayList<Entity>();
    private char move;
    private int temp;
    public String map = "************   *     **** * ****** * *     ** * ***** ** *   *   ** *** * ****     *   ** ******* **         ************";
    private boolean isFight = false;
    private boolean whileCheck = false;
    private Stage window;

    // Scene inGameScene = ;
        
    // window.setScene(new Scene(inGame.createWall()));
    
    // window.show();

    public Parent createWall(){
        Pane root = new Pane();

        mapGen mapgen = new mapGen();
        map = mapgen.getGrid();
//System.out.println(map);
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
        for (Entity objecT : wall) {
            root.getChildren().addAll(objecT);
        }
        return root;
    }






    public Parent createContent(Stage window) {
        this.window = window;
        Pane root = new Pane();
        root.setPrefSize(830, 550);
        Rectangle InGameBG = new Rectangle(830, 550);
        InGameBG.setFill(Color.BLACK);
        info.setTranslateX(50);
        info.setTranslateY(50);

        mapGen mapgen = new mapGen();
        player = new Entity(50, 50, 50, 50, playerImage);

        map = mapgen.getGrid();
        //System.out.println(map);
        
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
        int xPositionS, yPositionS;

        while (true) {
            int randEnemyX = (int) (3 + Math.random() * 4);
            int randEnemyY = (int) (3 + Math.random() * 4);
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
        stair = new Entity(xPositionS * 50, yPositionS * 50, 50, 50, stairImage);

        root.getChildren().addAll(InGameBG, info, stair, player);
        for (Entity objecT : wall) {
            root.getChildren().addAll(objecT);
        }
        for (Entity objecT : enemy) {
            root.getChildren().addAll(objecT);
        }

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

    public void upDate(Scene scene) throws IOException {
        move = '0';
        //Parent fightParent = FXMLLoader.load(getClass().getResource("BlackJack.fxml"));
        scene.setOnKeyPressed(event -> {
            if (isFight == false) {
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
                if (event.getCode() == KeyCode.R) {
                    
                    // nextlevel
                    while(enemy.size() != 0){
                        enemy.get(0).setVisible(false);
                        enemy.remove(0);
                    }
                    while(wall.size() != 0){
                        wall.get(0).setVisible(false);
                        wall.remove(0);
                        
                    }
                    player.setTranslateX(50);
                    player.setTranslateY(50);
                    }
            }
            
           /* for (int i = 0; i < enemy.size(); i++) {
                if (checkCollision(player, enemy.get(i))) {
                    if (event.getCode() == KeyCode.ENTER) {
                        
                        Scene fightScene = new Scene(fightParent);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(fightScene);
                        window.show();
                        isFight = true;
                    }
                }
            
                if (event.getCode() == KeyCode.F) {
                    info.setText("");
                    enemy.get(i).setVisible(false);
                    enemy.remove(i);
                    isFight = false;
                }
            }*/

            if (checkCollision(player, stair)) {
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
        });
    }

    /* private void fight(KeyCode keycode) throws IOException {
        Parent fightParent = FXMLLoader.load(getClass().getResource("BlackJack.fxml"));
        Scene fightScene = new Scene(fightParent);
        Stage window = (Stage) ((Node) .event.getSource()).getScene().getWindow();
        window.setScene(fightScene);
        window.show();
    }*/
}
     
