import java.util.*;

public class Logic {
    private HashMap<String, Integer> sc;
    
    public Logic() {
        sc = new HashMap<String, Integer>();
        sc.put("ones", -1);
        sc.put("twos", -1);
        sc.put("threes", -1);
        sc.put("fours", -1);
        sc.put("fives", -1);
        sc.put("sixes", -1);
        sc.put("top", 0);
        sc.put("bonus", 0);
        sc.put("3kind", -1);
        sc.put("4kind", -1);
        sc.put("fhouse", -1);
        sc.put("sm str", -1);
        sc.put("lg str", -1);
        sc.put("yahtzee", -1);
        sc.put("chance", -1);
        sc.put("total", 0);
    }
    
    public HashMap<String, Integer> getPotential(int[] realDices) {
        int[] tempDices = new int[realDices.length];
        for(int i=0; i < realDices.length; i++)
            tempDices[i] = realDices[i];
            
        Arrays.sort(tempDices); //don't alter real dice
        HashMap<String, Integer> potential = new HashMap<String, Integer>();
        
        //ones
        int count = getCount(tempDices, 1);
        potential.put("ones", count*1);
        
        //twos
        count = getCount(tempDices, 2);
        potential.put("twos", count*2);
        
        //threes
        count = getCount(tempDices, 3);
        potential.put("threes", count*3);
        
        //fours
        count = getCount(tempDices, 4);
        potential.put("fours", count*4);
        
        //fives
        count = getCount(tempDices, 5);
        potential.put("fives", count*5);
        
        //sixes
        count = getCount(tempDices, 6);
        potential.put("sixes", count*6);
        
        //3kind
        potential.put("3kind", 0);
        if(hasXKind(tempDices, 3))
            potential.replace("3kind", getSum(tempDices));
            
        //4kind
        potential.put("4kind", 0);
        if(hasXKind(tempDices, 4))
            potential.replace("4kind", getSum(tempDices));
            
        //fhouse
        potential.put("fhouse", 0);  
        boolean hasPair = false;
        boolean hasTriple = false;
        for(Map.Entry<Integer, Integer> entry : getFrequencies(tempDices).entrySet()) {
            Integer frequency = entry.getValue();
            if(frequency == 2)
                hasPair = true;
            else if(frequency == 3)
                hasTriple = true;
        }
        if(hasPair && hasTriple)
            potential.replace("fhouse", 25);
            
        //sm str
        potential.put("sm str", 0);
        boolean hasStraight = false;
        if(tempDices[0] == tempDices[1]-1 && tempDices[1] == tempDices[2]-1 &&
           tempDices[2] == tempDices[3]-1)
           hasStraight = true;
        else if(tempDices[1] == tempDices[2]-1 && tempDices[2] == tempDices[3]-1 &&
           tempDices[3] == tempDices[4]-1)
                hasStraight = true;
        if(hasStraight)
            potential.replace("sm str", 30);
            
        //lg str
        potential.put("lg str", 0);
        hasStraight = false;
        if(tempDices[0] == tempDices[1]-1 && tempDices[1] == tempDices[2]-1 &&
           tempDices[2] == tempDices[3]-1 && tempDices[3] == tempDices[4]-1)
                hasStraight = true;
        if(hasStraight)
            potential.replace("lg str", 40);
        
        //chance
        potential.put("chance", getSum(tempDices));
        
        //yahtzee
        potential.put("yahtzee", 0);
        if(hasXKind(tempDices, 5))
            potential.replace("yahtzee", 50);
        
        //top and bonus
        int topSum = getTopTotal();
        sc.replace("top", topSum);
        if(hasFullTop()) {
            if(topSum >= 63)
                sc.replace("bonus", 35);
            else
                sc.replace("bonus", 0);
        }
        
        //update bottom of the card
        int bottomSum = getBottomTotal();
        int totalSum = topSum + bottomSum;
        sc.replace("total", bottomSum);    
        
        return potential;
    }
    
    public boolean hasFullTop() {
        return sc.get("ones") != -1 && sc.get("twos") != -1 &&
               sc.get("threes") != -1 && sc.get("fours") != -1 &&
               sc.get("fives") != -1 && sc.get("sixes") != -1;
    }
    
    public int getTopTotal() {
        ArrayList<String> options = new ArrayList<String>(Arrays.asList("ones",
                                                          "twos", "threes",
                                                          "fours", "fives", "sixes"));
        return getOptionsSum(options);
    }
    
    public boolean hasFullBottom() {
        return sc.get("3kind") != -1 && sc.get("4kind") != -1 &&
               sc.get("fhouse") != -1 && sc.get("sm str") != -1 &&
               sc.get("lg str") != -1 && sc.get("yahtzee") != -1 &&
               sc.get("chance") != -1;
    }
    
    public int getBottomTotal() {
        ArrayList<String> options = new ArrayList<String>(Arrays.asList("3kind",
                                                          "4kind", "fhouse",
                                                          "sm str", "lg str",
                                                          "yahtzee", "chance"));
        return getOptionsSum(options);
    }
    
    public int getOptionsSum(ArrayList<String> options) {
        int sum = 0;
        for(String x : options) {
            int value = sc.get(x);
            if(value == -1)
                sum += 0;
            else
                sum += value;
        }
        return sum;
    }
    
    public boolean hasFullCard() {
        return hasFullTop() && hasFullBottom();
    }
    
    public int getCount(int[] tempDices, int value) {
        int count = 0;
        for(int i = 0; i < tempDices.length; i++) 
            if(tempDices[i] == value)
                count++;
        return count;
    }
    
    public HashMap<Integer, Integer> getFrequencies(int tempDices[]) {
        HashMap<Integer, Integer> freqs = new HashMap<Integer, Integer>();
        for(int i = 0; i < tempDices.length; i++) {
            if(freqs.containsKey(tempDices[i]))
                freqs.replace(tempDices[i], freqs.get(tempDices[i])+1);
            else 
                freqs.put(tempDices[i], 1);
        }
        return freqs;
    }
    
    public boolean hasXKind(int[] tempDices, int x) {
        for(Map.Entry<Integer, Integer> entry : getFrequencies(tempDices).entrySet()) {
            Integer frequency = entry.getValue();
            if(frequency >= x)
                return true;
        }
        return false;
    }
    
    public int getSum(int tempDices[]) {
        int sum = 0;
        for(int i = 0; i < tempDices.length; i++) 
            sum += tempDices[i];
        return sum;
    }
    
    public HashMap<String, Integer> getScorecard() {
        return sc;
    }
}