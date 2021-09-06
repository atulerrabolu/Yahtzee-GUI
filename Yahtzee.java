import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.collections.ObservableList; 
import java.io.*;
import java.util.*;

public class Yahtzee extends Application
{
    private int[] dice = {1, 2, 3, 4, 5};
    private boolean[] keepIndices = {false, false, false, false, false};
    ObservableList diceBoxList;
    ObservableList upperList;
    ObservableList lowerList;
    
    String[] upperChoices = {"ones", "twos", "threes", "fours", "fives", "sixes", "bonus", "top"};
    String[] lowerChoices = {"3kind", "4kind", "fhouse", "sm str", "lg str", "yahtzee", "chance", "total"};
    boolean selectedChoice = false;
    int rollsLeft = 3;
    
    Logic gameLogic = new Logic();
    HashMap<String, Integer> scorecard = gameLogic.getScorecard();
    HashMap<String, Integer> potential = gameLogic.getPotential(dice);
    
    public static void main(String[] args) {
        launch();
    }   
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Button rollButton = new Button("Reroll");
        Button restartButton = new Button("Restart");
        rollButton.setOnAction(this::reRoll);        
        restartButton.setOnAction(this::restart);  
        
        HBox overall = new HBox();
        ObservableList overallList = overall.getChildren();
        
        //----------------------------------DICE BOX--------------------------------
        VBox left = new VBox();
        ObservableList leftList = left.getChildren();
        
        VBox diceBox = new VBox();
        diceBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" +
                         "-fx-border-width: 1.5;" + "-fx-border-insets: 3;" +
                         "-fx-border-color: black;");
        diceBoxList = diceBox.getChildren();
        
        //dice 1
        HBox dice1 = new HBox();
        ObservableList d1List = dice1.getChildren();
        Label d1L = new Label("Dice 1");
        Label d1V = new Label(dice[0]+"");
        CheckBox checkBox1 = new CheckBox("Keep?");
        
        FileInputStream inputStream1 = new FileInputStream("dice_"+dice[0]+".png"); 
        Image image1 = new Image(inputStream1);
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(40); 
        imageView1.setFitWidth(40);        
        imageView1.setPreserveRatio(true);
        dice1.setMargin(d1L, new Insets(20, 20, 20, 20)); 
        dice1.setMargin(d1V, new Insets(20, 20, 20, 20));
        dice1.setMargin(imageView1, new Insets(7.5, 20, 20, 20));
        dice1.setMargin(checkBox1, new Insets(20, 20, 20, 20));  
        d1List.addAll(d1L, d1V, imageView1, checkBox1);
        
        //dice 2
        HBox dice2 = new HBox();
        ObservableList d2List = dice2.getChildren();
        Label d2L = new Label("Dice 2");
        Label d2V = new Label(dice[1]+"");
        CheckBox checkBox2 = new CheckBox("Keep?");
        
        FileInputStream inputStream2 = new FileInputStream("dice_"+dice[1]+".png"); 
        Image image2 = new Image(inputStream2);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(40); 
        imageView2.setFitWidth(40);        
        imageView2.setPreserveRatio(true);
        dice2.setMargin(d2L, new Insets(20, 20, 20, 20)); 
        dice2.setMargin(d2V, new Insets(20, 20, 20, 20));
        dice2.setMargin(imageView2, new Insets(7.5, 20, 20, 20));
        dice2.setMargin(checkBox2, new Insets(20, 20, 20, 20));  
        d2List.addAll(d2L, d2V, imageView2, checkBox2);
        
        //dice 3
        HBox dice3 = new HBox();
        ObservableList d3List = dice3.getChildren();
        Label d3L = new Label("Dice 3");
        Label d3V = new Label(dice[2]+"");
        CheckBox checkBox3 = new CheckBox("Keep?");
        
        FileInputStream inputStream3 = new FileInputStream("dice_"+dice[2]+".png"); 
        Image image3 = new Image(inputStream3);
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitHeight(40); 
        imageView3.setFitWidth(40);        
        imageView3.setPreserveRatio(true);
        dice3.setMargin(d3L, new Insets(20, 20, 20, 20)); 
        dice3.setMargin(d3V, new Insets(20, 20, 20, 20));
        dice3.setMargin(imageView3, new Insets(7.5, 20, 20, 20));
        dice3.setMargin(checkBox3, new Insets(20, 20, 20, 20));  
        d3List.addAll(d3L, d3V, imageView3, checkBox3);
        
        //dice 4
        HBox dice4 = new HBox();
        ObservableList d4List = dice4.getChildren();
        Label d4L = new Label("Dice 4");
        Label d4V = new Label(dice[3]+"");
        CheckBox checkBox4 = new CheckBox("Keep?");
        
        FileInputStream inputStream4 = new FileInputStream("dice_"+dice[3]+".png"); 
        Image image4 = new Image(inputStream4);
        ImageView imageView4 = new ImageView(image4);
        imageView4.setFitHeight(40); 
        imageView4.setFitWidth(40);        
        imageView4.setPreserveRatio(true);
        dice4.setMargin(d4L, new Insets(20, 20, 20, 20)); 
        dice4.setMargin(d4V, new Insets(20, 20, 20, 20));
        dice4.setMargin(imageView4, new Insets(7.5, 20, 20, 20));
        dice4.setMargin(checkBox4, new Insets(20, 20, 20, 20));  
        d4List.addAll(d4L, d4V, imageView4, checkBox4);
        
        //dice 5
        HBox dice5 = new HBox();
        ObservableList d5List = dice5.getChildren();
        Label d5L = new Label("Dice 5");
        Label d5V = new Label(dice[4]+"");
        CheckBox checkBox5 = new CheckBox("Keep?");
        
        FileInputStream inputStream5 = new FileInputStream("dice_"+dice[4]+".png"); 
        Image image5 = new Image(inputStream5);
        ImageView imageView5 = new ImageView(image5);
        imageView5.setFitHeight(40); 
        imageView5.setFitWidth(40);        
        imageView5.setPreserveRatio(true);
        dice5.setMargin(d5L, new Insets(20, 20, 20, 20)); 
        dice5.setMargin(d5V, new Insets(20, 20, 20, 20));
        dice5.setMargin(imageView5, new Insets(7.5, 20, 20, 20));
        dice5.setMargin(checkBox5, new Insets(20, 20, 20, 20));  
        d5List.addAll(d5L, d5V, imageView5, checkBox5);
        
        //Options
        HBox options = new HBox();
        ObservableList dObsList = options.getChildren();
        options.setMargin(rollButton, new Insets(20, 50, 20, 20));
        options.setMargin(restartButton, new Insets(20, 50, 20, 20));
        dObsList.addAll(rollButton, restartButton);

        diceBoxList.addAll(dice1, dice2, dice3, dice4, dice5, options);
        
        leftList.addAll(diceBox);
        
        //---------------------------SCORE CARD------------------------
        VBox scorecard = new VBox();
        ObservableList scorecardList = scorecard.getChildren();
        
        //--------------upper scorecard-------------
        VBox upper = new VBox();
        upper.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" +
                         "-fx-border-width: 1.5;" + "-fx-border-insets: 3;" +
                         "-fx-border-color: black;");
        upperList = upper.getChildren();
         
        ArrayList<HBox> upperChoicesBoxes = new ArrayList<HBox>();
        for(String choice : upperChoices)
            upperChoicesBoxes.add(makeScChoice(choice));
        
        upperList.addAll(upperChoicesBoxes);
        
        //-----------lower scorecard------------------
        VBox lower = new VBox();
        lower.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" +
                         "-fx-border-width: 1.5;" + "-fx-border-insets: 3;" +
                         "-fx-border-color: black;");
        lowerList = lower.getChildren();
        
        ArrayList<HBox> lowerChoicesBoxes = new ArrayList<HBox>();
        for(String choice : lowerChoices)
            lowerChoicesBoxes.add(makeScChoice(choice));
                
        lowerList.addAll(lowerChoicesBoxes);
        
        scorecard.setMargin(lower, new Insets(20, 0, 0, 0));
        scorecardList.addAll(upper, lower);
        
        overall.setMargin(scorecard, new Insets(0, 0, 0, 47));
        overall.setMargin(left, new Insets(0, 0, 0, 35));
        overallList.addAll(left, scorecard);
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(overall, 620,800);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        
        // Show the Stage (window)
        stage.show();
    }
    
    private HBox makeScChoice(String choice) {
        scorecard = gameLogic.getScorecard();
        potential = gameLogic.getPotential(dice);
        
        Button boxButton = new Button(potential.get(choice)+"");
        Label boxScore = new Label(scorecard.get(choice)+"");
        HBox box = new HBox();
        ObservableList boxList = box.getChildren();
        Label boxLabel = new Label(choice);
        box.setMargin(boxLabel, new Insets(10, 20, 10, 20));
        
        boolean playable = scorecard.get(choice) == -1;
        if(playable) {
            boxButton.setOnAction((e) -> {
                scorecard.replace(choice, potential.get(choice));
                selectedChoice = true;
                
                //re-allow rolling
                HBox options = (HBox)diceBoxList.get(5);
                Button rollButton = (Button)options.getChildren().get(0);
                rollButton.setDisable(false);
                rollsLeft = 3;
                
                rerenderChoices();
            });
            if(selectedChoice)
                boxButton.setDisable(true);
            box.setMargin(boxButton, new Insets(10, 20, 10, 20));
            boxList.addAll(boxLabel, boxButton);
        } else {
            box.setMargin(boxScore, new Insets(10, 20, 10, 20));
            boxList.addAll(boxLabel, boxScore);
        }
            
        return box;
    }

    private void reRoll(ActionEvent event) {
        try {
            for(int i = 0; i < 5; i++) {
                HBox currDice = (HBox)diceBoxList.get(i);
                CheckBox cB = (CheckBox)(currDice.getChildren().get(3));
                keepIndices[i] = cB.isSelected();
            }
            for(int i = 0; i < dice.length; i++) {
                if(!keepIndices[i]) {
                    randomizeDice(i);
                }
            }
            rollsLeft--;
            
            //Disable button after 3 rolls
            if(rollsLeft == 0) {
                HBox options = (HBox)diceBoxList.get(5);
                Button rollButton = (Button)options.getChildren().get(0);
                rollButton.setDisable(true);
            }
            
            selectedChoice = false;
            rerenderChoices();
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void restart(ActionEvent event)
    {
        try {
            for(int i = 0; i < dice.length; i++) {
                randomizeDice(i);
            }
            gameLogic = new Logic(); //wipe scoreboard clean
            selectedChoice = false;
            
            //re-allow rolling
            HBox options = (HBox)diceBoxList.get(5);
            Button rollButton = (Button)options.getChildren().get(0);
            rollButton.setDisable(false);
            rollsLeft = 3;
            
            rerenderChoices();
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void randomizeDice(int index) throws Exception {
        HBox currDice = (HBox)diceBoxList.get(index);
        dice[index] = (int)(Math.random()*6 + 1);
        FileInputStream inputStream = new FileInputStream("dice_"+dice[index]+".png"); 
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        currDice.setMargin(imageView, new Insets(7.5, 20, 20, 20));
        ObservableList dList = currDice.getChildren();
        dList.set(2, imageView);
    }
    
    private void rerenderChoices() {   
        //upper scorecard
        for(int i = 0; i < upperChoices.length; i++)
            upperList.set(i, makeScChoice(upperChoices[i]));
        
        //lower scorecard
        for(int i = 0; i < lowerChoices.length; i++)
            lowerList.set(i, makeScChoice(lowerChoices[i]));
    }
}