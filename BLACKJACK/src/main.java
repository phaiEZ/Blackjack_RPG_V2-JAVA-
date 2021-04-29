import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

import java.security.acl.Group;
import java.util.*;  
public class main extends Application {

    private Text info = new Text();
    private Entity player;
    private List <Entity> enemy = new ArrayList<Entity>();
    private List<Entity> wall = new ArrayList<Entity>();  
    private char move ;
    private int temp;
    private String map = "************   *     **** * ****** * *     ** * ***** ** *   *   ** *** * ****     *   ** ******* **         ************";
    private boolean isFight = false;
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(500, 500);
         
        info.setTranslateX(50);
        info.setTranslateY(50);
        
        mapGen mapgen = new mapGen();
        //mapgen.getGrid();
        map = mapgen.getGrid();
        player = new Entity(30,30,30,30,Color.GREEN);
        //enemy.add(new Entity(300,300,30,30,Color.RED));
        //enemy.add(new Entity(150,150,30,30,Color.RED));
        int tempx = 0 ;
        int tempy = 0 ;
        int temp = 0;
        
        //System.out.println(map);
            for(int j = 0;j <= 10; j++){
                for(int i = 0;i <= 10; i++){
                        if(map.charAt(temp) == '*'){
                           // System.out.print('*');
                        wall.add(new Entity (tempx,tempy,30,30,Color.BLUE));
                    }
                    else{
                        //System.out.print(' ');
                    }
                    temp = temp +1;
                    tempx = tempx + 30 ;
                }
                //System.out.println(' ');
                tempx = 0;
                tempy = tempy + 30;
            }
            

        
        root.getChildren().addAll(info,player);

        for(Entity objecT:wall)  {
            root.getChildren().addAll(objecT);
        }
        for(Entity objecT:enemy)  {
            root.getChildren().addAll(objecT);
        }
        
        
        return root;
    }
    

    private boolean checkCollision(Entity a, Entity b) {
        //
        if(a.getBoundsInParent().intersects(b.getBoundsInParent()) && a.getTranslateX() == b.getTranslateX() && a.getTranslateY() == b.getTranslateY()) {
            return true;
        }
        else return false;
    }

    private static class Entity extends Parent {

        double width;
        double height;

        public Entity(double x, double y, double w, double h, Color color) {
            setTranslateX(x);
            setTranslateY(y);
            width = w;
            height = h;
            Rectangle shape = new Rectangle(w, h);
            shape.setFill(color);
            getChildren().add(shape);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        move = '0';
        
        
        scene.setOnKeyPressed(event -> {
            if (isFight == false){
            if(event.getCode() == KeyCode.W) {
                player.setTranslateY(player.getTranslateY() -  30);
                move = 'w';
            }
            if(event.getCode() == KeyCode.S) {
                player.setTranslateY(player.getTranslateY() +  30);
                move = 's';
            }
            if(event.getCode() == KeyCode.A) {
                player.setTranslateX(player.getTranslateX() -  30);
                move = 'a';
            }
            if(event.getCode() == KeyCode.D) {
                player.setTranslateX(player.getTranslateX() +  30);
                move = 'd';
            }
        }
            for(int i = 0; i < enemy.size();i++)  {
                if(checkCollision(player,enemy.get(i))){
                    
                    info.setText("fight");
                    isFight = true;
                    
                        if(event.getCode() == KeyCode.F) {
                            info.setText("");
                            // if(enemy.isEmpty() == false){
                            //     
                            // }
                            enemy.get(i).setVisible(false);
                            enemy.remove(i);
                            isFight = false;
                    }
                    System.out.println(enemy.size());
                    
                }
                else{
                    
                }
            }
            for(Entity objecT:wall)  {
                if(checkCollision(player,objecT) == true){
                    if(move == 'w'){
                        player.setTranslateY(player.getTranslateY() +  30);
                    }
                    else if(move == 's'){
                        player.setTranslateY(player.getTranslateY() -  30);
                    }
                    else if(move == 'a'){
                        player.setTranslateX(player.getTranslateX() +  30);
                    }
                    else if(move == 'd'){
                        player.setTranslateX(player.getTranslateX() -  30);
                    }
                }
            }
        });
        primaryStage.setTitle("Tutorial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
