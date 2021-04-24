import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Controller{

    @FXML
    private Button startButton;

    @FXML
    void buttonPressed(ActionEvent event) {
        System.out.println("Hello");
    }

}
