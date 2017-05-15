package blackjack;

import cards.Shoe;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/15/2017
 * File:        BlackjackGUI.java
 * Purpose:     Simple GUI to orchestrate a Blackjack game.
 */
public class BlackjackGUI extends Application {
    Player p = new SimplePlayer(5000);
    Shoe deck = new Shoe(4);
    InputHolder ih = new InputHolder();
    
    @Override
    public void start(Stage ps) {
        VBox main = new VBox();
        
        main.getChildren().add(ih);
        Button startButton = new Button();
        startButton.setOnAction((ae) -> {
            startRound();
        });
        
        
        
    }
    
    public static void main(String[] args) {
        Application.launch();
    }
    
    //Blackjack functions
    
    public void startRound() {
        
    }
    
    //Gui classes ---
    
    //Desc: Holds input buttons
    public class InputHolder extends HBox {
        
        //Desc: Displays any number of buttons
        public void hold(Button... toHold) {
            this.getChildren().addAll(toHold);
        }
        
        //Desc: Clears holder of buttons
        public void clear() {
            this.getChildren().clear();
        }
    }
}
