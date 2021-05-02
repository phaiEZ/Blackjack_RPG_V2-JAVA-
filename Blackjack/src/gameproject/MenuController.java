/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chanon
 */
public class MenuController implements Initializable {
    
    InGame inGame = new InGame();
    @FXML
    private ImageView play;
    @FXML
    private ImageView howToPlay;
    @FXML
    private ImageView credits;
    @FXML
    private ImageView exit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onPlay(ActionEvent event) throws IOException {     
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene inGameScene = new Scene(inGame.createContent(window));
        
        window.setScene(inGameScene);
        
        window.show();
        inGame.upDate(inGameScene);
        
    }


    @FXML
    private void onHowtoplay(ActionEvent event) throws IOException {
        Parent howtoplayParent = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));
        Scene howtoplayScene = new Scene(howtoplayParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(howtoplayScene);
        window.show();
    }
    
    @FXML
    private void onCredit(ActionEvent event) throws IOException {
        Parent creditParent = FXMLLoader.load(getClass().getResource("Credit.fxml"));
        Scene creditScene = new Scene(creditParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creditScene);
        window.show();
    }

    @FXML
    private void onExit(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void playExit(MouseEvent event) {
        play.setVisible(false);
    }

    @FXML
    private void playEntre(MouseEvent event) {
        play.setVisible(true);
    }

    @FXML
    private void howExit(MouseEvent event) {
        howToPlay.setVisible(false);
    }

    @FXML
    private void howEnter(MouseEvent event) {
        howToPlay.setVisible(true);
    }

    @FXML
    private void creditExit(MouseEvent event) {
        credits.setVisible(false);
    }

    @FXML
    private void creditEnter(MouseEvent event) {
        credits.setVisible(true);
    }

    @FXML
    private void exitExit(MouseEvent event) {
        exit.setVisible(false);
    }

    @FXML
    private void exitEnter(MouseEvent event) {
        exit.setVisible(true);
    }
    
}
