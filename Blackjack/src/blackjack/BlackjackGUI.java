package blackjack;

import cards.Card;
import cards.Shoe;
import java.util.Collection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
    HandPane pHandPane = new HandPane();
    
    Player dealer = new SimplePlayer(0);
    HandPane dealerHandPane = new HandPane();
    
    Shoe deck;
    
    StatTracker st = new StatTracker();
    InputHolder ih = new InputHolder();
    
    int phase = 0;
    int win = 0;
    int loss = 0;
    
    final double MINIMUM_BET = 100.0;
    
    @Override
    public void start(Stage ps) {        
        VBox main = new VBox();
        main.getStyleClass().add("body");
        
        main.getChildren().addAll(st, dealerHandPane, pHandPane, ih);
        
        st.update();
        
        Button startButton = new Button("Start Game");
        startButton.setOnAction((ae) -> {
            startRound();
        });
        
        TextInput vegasDecks = new TextInput("# of Decks: ");
        vegasDecks.setOnAction((ae) -> {
            boolean wrong = false;
            String input = vegasDecks.getInput();
            int iInput = 0;
            
            if (isNumeric(input)) {
                iInput = Integer.parseInt(input);
                if (!(iInput > 0 && iInput <= 10)) {
                    wrong = true;
                }
            } else {
                wrong = true;
            }
            
            
            if (!wrong) {
                deck = new Shoe(Integer.parseInt(vegasDecks.getInput()));
                deck.shuffle();
                ih.hold(startButton);
            } else {
                ih.holdTempMessage("# of Decks must be between 1 and 10.", vegasDecks);
            }
        });
        ih.hold(vegasDecks);
        
        Scene scene = new Scene(main, 600, 350);
        
        //Resizing bits.
        scene.widthProperty().addListener((Observable obv) -> {
            st.setPrefWidth(scene.widthProperty().doubleValue());
        });
        scene.getStylesheets().add("/style/styles.css");
        ps.setScene(scene);
        ps.show();
    }
    
    public static void main(String[] args) {
        Application.launch();
    }
    
    //Blackjack functions
    
    //Desc: Semantic function.
    public void startRound() {
        deck.discardLive();
        advancePhase();
    }
    
    //Desc: Gets bet from user.
    //TODO: - Input validation
    //      - Stop bets that are too high.
    public void getBet() {
        TextInput betInput = new TextInput("What will you bid?: ");
        betInput.setOnAction((ae) -> {
            String input = betInput.getInput();
            boolean wrong = false;
            double dInput = 0.0;
            
            if (isNumeric(input)) {
                dInput = Double.parseDouble(input);
                if (!(dInput <= p.getBalance() && dInput >= MINIMUM_BET)) {
                    wrong = true;
                }
            } else {
                wrong = true;
            }
            
            if (!wrong) {
                p.setBet(dInput);
                st.update();
                advancePhase();
            } else {
                ih.holdTempMessage("Bet must be between " + MINIMUM_BET + " and your bank balance.", betInput);
            }
        });
        ih.hold(betInput);
    }
    
    //Desc: Deals openning cards
    public void openningDeal() {
        p.dealCard(deck.draw());
        p.dealCard(deck.draw());
        pHandPane.displayHand(p.getHand());
        
        dealer.dealCard(deck.draw());
        dealer.dealCard(deck.draw());
//        dealer.dealCard(new Card(0));
//        dealer.dealCard(new Card(3));
        dealerHandPane.displayHandHole(dealer.getHand());
        
        if (p.getPoints() != 21) {
            Platform.runLater(() -> advancePhase());
        } else {
            Platform.runLater(() -> jumpToPhase(5));
        }
    }
    
    //Desc: If the player can get insurance, it sets it up
    public void checkInsurance() {
        if (dealer.getHand().getCard(0).getRank() == 0) {
            Button insure = new Button("Insure");
            insure.setOnAction((ae) -> {
                runInsurance();
            });
            
            Button pass = new Button("Pass");
            pass.setOnAction((ae) -> {
                advancePhase();
            });
            
            //Disables insurance button if the player cannot afford it
            if (p.getBalance() < p.getHand().getBet()) {
                insure.setDisable(true);
            }
            
            ih.hold(insure, pass);
        } else {
            Platform.runLater(() -> advancePhase());
        }
    }
    
    //Desc: Runs insurance rules
    public void runInsurance() {
        p.takeInsurance();
        st.update();
        dealerHandPane.displayHand(dealer.getHand());
        
        ConfirmInput ci;
        if (dealer.getPoints() == 21) {
            p.payInsurance();
            p.resetHand();
            st.update();
            
            ci = new ConfirmInput("Insurance payed out, you broke even!");
            ci.setOnAction((ae) -> {
                jumpToPhase(7);
            });
        } else {
            ci = new ConfirmInput("Insurance didn't pay out.");
            ci.setOnAction((ae) -> {
                jumpToPhase(4);
            });
        }
        
        ih.hold(ci);
    }
    
    //Desc: Queries about side rules
    //TODO: Make surrender better
    public void sideRules() {
        Button continueRound = new Button("Continue Round");
        continueRound.setOnAction((ae) -> {
            advancePhase();
        });
        
        Button doubleDown = new Button("Double Down");
        doubleDown.setOnAction((ae) -> {
            p.doubleDown();
            st.update();
            advancePhase();
        });
        
        Button surrender = new Button("Surrender");
        surrender.setOnAction((ae) -> {
            p.surrender();
            p.resetHand();
            st.update();
            
            ConfirmInput ci = new ConfirmInput(String.format("You surrendered. Your current balance is $%.2f.", p.getBalance()));
            ci.setOnAction((ae2) -> {
                jumpToPhase(7);                
            });
            
            ih.hold(ci);
        });
        
        //Disables doubleDown button if the player can't afford it.
        if (p.getBalance() < p.getHand().getBet()) {
            doubleDown.setDisable(true);
        }
        
        ih.hold(continueRound, doubleDown, surrender);
    }
    
    //Desc: Sets up hit and stay buttons
    //TODO: Setup actual busting
    public void playerHitStay() {
        Button hit = new Button("Hit");
        hit.setOnAction((ae) -> {
            p.dealCard(deck.draw());
            pHandPane.displayHand(p.getHand());
            if (p.checkBust()) {
                advancePhase();
            }
        });
        
        Button stay = new Button("Stay");
        stay.setOnAction((ae) -> {
            advancePhase();
        });
        
        ih.hold(hit, stay);
    }
    
    //Desc: Runs through dealer hit rules
    public void runDealer() {
        //Get rid of input
        ih.clear();
        
        //Flip hole card
        dealerHandPane.displayHand(dealer.getHand());
        
        //Run hit rules
        while (dealer.getPoints() < 17) {
            dealer.dealCard(deck.draw());
            dealerHandPane.displayHand(dealer.getHand());
        }
        
        Platform.runLater(() -> advancePhase());
    }
    
    //Desc: Figures score
    public void runScoring() {
        String outcome;
        
        if (p.checkBust()) {
            outcome = "bust";
            loss++;
        } else if (dealer.checkBlackjack()) {
            if (p.checkBlackjack()) {
                p.push();
                outcome = "pushed";
            } else {
                outcome = "lost";
                loss++;
            }
        } else if (p.checkBlackjack()) {
            p.winHandBlackjack();
            outcome = "got a Blackjack";
            win++;
        } else {
            if (p.getPoints() > dealer.getPoints() || dealer.checkBust()) {
                p.winHand();
                outcome = "won";
                win++;
            } else if (dealer.getPoints() > p.getPoints()) {
                outcome = "lost";
                loss++;
            } else {
                p.push();
                outcome = "pushed";
            }
        }
        
        p.resetHand();
        st.update();
        ConfirmInput ci = new ConfirmInput(String.format("You " + outcome + ". Your current balance is $%.2f.", p.getBalance()));
        ih.hold(ci);
        
        ci.setOnAction((ae) -> {
            advancePhase();
        });
    }
    
    //Desc: Ends round and lets player play again.
    public void endRound() {      
        p.resetHand();
        st.update();
        dealer.resetHand();
        
        pHandPane.reset();
        dealerHandPane.reset();
        
        Button continueButton = new Button("Play again?");
        continueButton.setOnAction((ae) -> jumpToPhase(0));
        ih.hold(continueButton);
        
        //Sets up loss screen, if player is out of money
        if (p.getBalance() < MINIMUM_BET) {
            ih.hold(new Label("You don't have enough money to bet. Game over."));
        }
    }
    
    //Desc: Runs next rule function in sequence
    //For reasons beyond me: advancePhase must be called from within the javafx main work thread.
    //This means you must either put it in an event handler or use Platform.runlater
    public void advancePhase() {
        switch (phase) {
            case 0: getBet(); break;
            case 1: openningDeal(); break;
            case 2: checkInsurance(); break;
            case 3: sideRules(); break;
            case 4: playerHitStay(); break;
            case 5: runDealer(); break;
            case 6: runScoring(); break;
            case 7: endRound(); break;
        }
        phase++;
    }
    
    //Desc: skips to rule function
    public void jumpToPhase(int phase) {
        this.phase = phase;
        advancePhase();
    }
    
    //Gui classes ---
    
    //Desc: Holds labels for stat tracking
    public class StatTracker extends HBox {
        private Label bankLabel = new Label();
        private Label betLabel = new Label();
        private Label insuranceLabel = new Label();
        private Label winLabel = new Label();
        private Label lossLabel = new Label();
        private HBox main = new HBox();
        
        //Desc: Constructor
        public StatTracker() {
            Label bankLabelHolder = new Label("Bank: ", bankLabel);
            bankLabelHolder.getStyleClass().addAll("flavor_text", "ct_right");
            
            Label betLabelHolder = new Label("Bet: ", betLabel);
            betLabelHolder.getStyleClass().addAll("flavor_text", "ct_right");
            
            Label insuranceLabelHolder = new Label("Insurance: ", insuranceLabel);
            insuranceLabelHolder.getStyleClass().addAll("flavor_text", "ct_right");
            
            Label winLabelHolder = new Label("Wins: ", winLabel);
            winLabelHolder.getStyleClass().addAll("flavor_text", "ct_right");
            
            Label lossLabelHolder = new Label("Losses: ", lossLabel);
            lossLabelHolder.getStyleClass().addAll("flavor_text", "ct_right");
            
            this.getChildren().addAll(bankLabelHolder, betLabelHolder, insuranceLabelHolder, winLabelHolder, lossLabelHolder);
            this.getStyleClass().add("stat_tracker");
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(5, 10, 5, 10));
            this.setSpacing(10);

        }
        
        //Desc: Updates bank label
        public void updateBankLabel() {
            bankLabel.setText(String.format("$%.2f", p.getBalance()));
        }
        
        //Desc: Updates bet label
        public void updateBetLabel() {
            betLabel.setText(String.format("$%.2f", p.getHand().getBet()));
        }
        
        //Desc: Updates insurance label
        public void updateInsuranceLabel() {
            insuranceLabel.setText(String.format("$%.2f", p.getHand().getInsurance()));
        }
        
        //Desc: Updates insurance label
        public void updateWinLabel() {
            winLabel.setText(String.valueOf(win));
        }
        
        //Desc: Updates insurance label
        public void updateLossLabel() {
            lossLabel.setText(String.valueOf(loss));
        }
        
        //Desc: Updates all
        public void update() {
            updateBankLabel();
            updateBetLabel();
            updateInsuranceLabel();
            updateWinLabel();
            updateLossLabel();
        }
    }
    
    //Desc: Holds input buttons
    public class InputHolder extends HBox {
        //Desc: Constructor
        public InputHolder() {
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(5, 10, 5, 10));
            this.setSpacing(10);
            this.getStyleClass().add("input_holder");
        }
        
        //Desc: Displays any number of buttons
        public void hold(Node... toHold) {
            System.out.println();
            clear();
            this.getChildren().addAll(toHold);
        }
        
        //Desc: Holds a label with the given string.
        public void holdTempMessage(String message, Node... toHold) {
            ConfirmInput ci = new ConfirmInput(message);
            ci.setOnAction((ae) -> {
                ih.hold(toHold);
            });
            ih.hold(ci);
        }
        
        //Desc: Clears holder of buttons
        public void clear() {
            this.getChildren().clear();
        }
    }
    
    //Desc: Displays a hand
    public class HandPane extends VBox {
        private HBox cardBox = new HBox();
        private Label pointLabel = new Label("?");
        
        //Desc: Constructor
        public HandPane() {
            this.getChildren().addAll(pointLabel, cardBox);
            this.cardBox.getChildren().add(new ImageView(getImageUrl("b1fv")));
            
            this.setPadding(new Insets(10, 10, 10, 10));
            this.pointLabel.getStyleClass().add("flavor_text");
            this.setAlignment(Pos.CENTER);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setSpacing(10);
        }
        
        //Desc: Displays an entire hand of cards.
        public void displayHand(Hand h) {
            clear();
            for (Card c: h.getCards()) {
                cardBox.getChildren().add(new ImageView(getImageUrl(String.valueOf(c.getCardNo()+1))));
            }
            pointLabel.setText(String.valueOf(h.getHandValue()));
        }
        
        //Desc: Display an entire hand, but flips the last card
        public void displayHandHole(Hand h) {
            clear();
            int count = 1;
            for (Card c: h.getCards()) {
                //Break on last card
                if (count == h.getCards().size()) break;
                else count++;
                cardBox.getChildren().add(new ImageView(getImageUrl(String.valueOf(c.getCardNo()+1))));
            }
            cardBox.getChildren().add(new ImageView(getImageUrl("b1fv")));
        }
        
        //Desc: Clears cardBox
        public void clear() {
            cardBox.getChildren().clear();
            pointLabel.setText("?");
        }
        
        //Desc: Resets to one overturned card
        public void reset() {
            clear();
            this.cardBox.getChildren().add(new ImageView(getImageUrl("b1fv")));
        }
    }
    
    //Desc: Provides a labeled text input
    public class TextInput extends Label {
        TextField tf = new TextField();
        
        //Desc: Constructor
        public TextInput(String labelText) {
            this.setText(labelText);
            this.setGraphic(tf);
            this.setContentDisplay(ContentDisplay.RIGHT);
            this.getStyleClass().add("text_input");
        }
        
        //Desc: Redirect setOnAction to tf
        public void setOnAction(EventHandler<ActionEvent> handler) {
            tf.setOnAction(handler);
        }
        
        //Desc: Redirect get text for tf
        public String getInput() {
            return tf.getText();
        }
        
        //Desc: Clears input
        public void clear() {
            tf.setText("");
        }
        
    }
    
    //Desc: Shows a label and an ok button
    public class ConfirmInput extends Label {
        Button okButton;
        
        //Desc: Constructor
        public ConfirmInput(String labelString) {
            this.setText(labelString);
            
            okButton = new Button("Ok");
            this.setGraphic(okButton);
            this.setContentDisplay(ContentDisplay.RIGHT);
        }
        
        //Desc: redirects setOnAction to okButton
        public void setOnAction(EventHandler<ActionEvent> handler) {
            okButton.setOnAction(handler);
        }
    }
    
    //Util ---

    //Desc: Returns valid image url
    public String getImageUrl(String file) {
//        System.out.println(file); //debug
        return String.format("/images/cards/%s.png", file);
    }
    
    //Desc: Returns true if string is numeric
    public boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
