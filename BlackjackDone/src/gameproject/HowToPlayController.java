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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chanon
 */
public class HowToPlayController implements Initializable {

    @FXML
    private ImageView back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void moveMouseExited(MouseEvent event) {
        back.setVisible(false);
    }

    @FXML
    private void moveMouseEntered(MouseEvent event) {
        back.setVisible(true);
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        Parent HTPParent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene HTPScene = new Scene(HTPParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(HTPScene);
        window.show();
    }
    
}
