import java.util.*;

public class Game {
    public static void main(String[] args) {
        Scorecard sc = new Scorecard();
        int[] dices = new int[5];
        Scanner kb = new Scanner(System.in);
        while(!sc.hasFullCard()) {
            System.out.println(sc);
            generateRoll(dices);
            System.out.println("you rolled " + Arrays.toString(dices));            
            for(int i = 0; i < 2; i++) {
                System.out.print("do you want to roll<r> or score<s>? ");
                String answer = kb.nextLine();
                if(answer.equals("r")) {
                    System.out.print("which dice do you want to re-roll (enter 0 through 4 seperated by spaces): ");
                    Scanner line = new Scanner(kb.nextLine());
                    while(line.hasNext()) {
                        int index = line.nextInt();
                        reroll(dices, index);
                    }
                    System.out.println("roll dice " + Arrays.toString(dices));
                } else if(answer.equals("s")) {
                    break;
                }
            }
            
            String options = "enter a score category -> " +
                             "ones<1>, twos<2>, threes<3>, fours<4>, fives<5> " +
                             "sixes<6>, 3kind<3k>, 4kind<4k>, fhouse<f>, sm str<s> " +
                             "lg str<l>, chance<c>, yahtzee<y>: ";
            System.out.print(options);
            String answer = kb.nextLine();
            
            //must choose a valid pattern to play (space open)
            while(sc.isValidOption(answer)) {
                System.out.print(options);
                answer = kb.nextLine();
            }
            
            sc.addItem(dices, answer);
        }
        
        System.out.println("\nThank you for playing Yahtzee!");
    }
    
    public static void generateRoll(int[] dices) {
        for(int i = 0; i < dices.length; i++)
            dices[i] = (int)(Math.random()*5 + 1);
    }
    
    public static void reroll(int[] dices, int index) {
        dices[index] = (int)(Math.random()*5 + 1);
    }
}