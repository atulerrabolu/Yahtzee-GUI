import java.util.*;

public class Scorecard {
    private HashMap<String, Integer> sc;
    
    public Scorecard() {
        sc = new HashMap<String, Integer>();
        sc.put("ones", -1);
        sc.put("twos", -1);
        sc.put("threes", -1);
        sc.put("fours", -1);
        sc.put("fives", -1);
        sc.put("sixes", -1);
        sc.put("sum", 0);
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
    
    public boolean isValidOption(String option) {
        if(option.equals("1") && sc.get("ones") != -1)
            return true;
        else if(option.equals("2") && sc.get("twos") != -1)
            return true;
        else if(option.equals("3") && sc.get("threes") != -1)
            return true;
        else if(option.equals("4") && sc.get("fours") != -1)
            return true;
        else if(option.equals("5") && sc.get("fives") != -1)
            return true;
        else if(option.equals("6") && sc.get("sixes") != -1)
            return true;
        else if(option.equals("3k") && sc.get("3kind") != -1)
            return true;
        else if(option.equals("4k") && sc.get("4kind") != -1)
            return true;
        else if(option.equals("f") && sc.get("fhouse") != -1)
           return true;
        else if(option.equals("s") && sc.get("sm str") != -1)
           return true;
        else if(option.equals("l") && sc.get("lg str") != -1)
           return true;
        else if(option.equals("c") && sc.get("chance") != -1)
           return true;
        else if(option.equals("y") && sc.get("yahtzee") != -1)
            return true;
        
        return false;
    }
    
    public void addItem(int[] dices, String option) {
        //validate patterns
        if(option.equals("1")) {
            int count = getCount(dices, 1);
            sc.replace("ones", count*1);       
        } else if(option.equals("2")) {
            int count = getCount(dices, 2);
            sc.replace("twos", count*2);
        } else if(option.equals("3")) {
            int count = getCount(dices, 3);
            sc.replace("threes", count*3);
        } else if(option.equals("4")) {
            int count = getCount(dices, 4);
            sc.replace("fours", count*4);
        } else if(option.equals("5")) {
            int count = getCount(dices, 5);
            sc.replace("fives", count*5);
        } else if(option.equals("6")) {
            int count = getCount(dices, 6);
            sc.replace("sixes", count*6);
        } else if(option.equals("3k")) {
            sc.replace("3kind", 0);
            if(hasXKind(dices, 3))
                sc.replace("3kind", getSum(dices));
        } else if(option.equals("4k")) {
            sc.replace("4kind", 0);
            if(hasXKind(dices, 4))
                sc.replace("4kind", getSum(dices));
        } else if(option.equals("f")) {
            sc.replace("fhouse", 0);
            
            boolean hasPair = false;
            boolean hasTriple = false;
            for(Map.Entry<Integer, Integer> entry : getFrequencies(dices).entrySet()) {
                Integer frequency = entry.getValue();
                if(frequency == 2)
                    hasPair = true;
                else if(frequency == 3)
                    hasTriple = true;
            }
            
            if(hasPair && hasTriple)
                sc.replace("fhouse", 25);
        } else if(option.equals("s")) {
            sc.replace("sm str", 0);
            boolean hasStraight = false;
            for(int i = 0; i < dices.length-3; i++)
                if(dices[i] < dices[i+1] && dices[i+1] < dices[i+2] &&
                   dices[i+2] < dices[i+3])
                    hasStraight = true;
            
            if(hasStraight)
                sc.replace("sm str", 30);
        } else if(option.equals("l")) {
            sc.replace("lg str", 0);
            boolean hasStraight = false;
            for(int i = 0; i < dices.length-4; i++)
                if(dices[i] < dices[i+1] && dices[i+1] < dices[i+2] &&
                   dices[i+2] < dices[i+3] && dices[i+3] < dices[i+4])
                    hasStraight = true;
            
            if(hasStraight)
                sc.replace("lg str", 40);
        } else if(option.equals("c")) {
            sc.replace("chance", getSum(dices));
        } else if(option.equals("y")) {
            sc.replace("yahtzee", 0);
            if(hasXKind(dices, 5))
                sc.replace("yahtzee", 50);
        }
        
        //update top of the card
        int topSum = getTopTotal();
        sc.replace("sum", topSum);
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
    
    public int getCount(int[] dices, int value) {
        int count = 0;
        for(int i = 0; i < dices.length; i++) 
            if(dices[i] == value)
                count++;
        return count;
    }
    
    public HashMap<Integer, Integer> getFrequencies(int dices[]) {
        HashMap<Integer, Integer> freqs = new HashMap<Integer, Integer>();
        for(int i = 0; i < dices.length; i++) {
            if(freqs.containsKey(dices[i]))
                freqs.replace(dices[i], freqs.get(dices[i])+1);
            else 
                freqs.put(dices[i], 1);
        }
        return freqs;
    }
    
    public boolean hasXKind(int[] dices, int x) {
        for(Map.Entry<Integer, Integer> entry : getFrequencies(dices).entrySet()) {
            Integer frequency = entry.getValue();
            if(frequency >= x)
                return true;
        }
        return false;
    }
    
    public int getSum(int dices[]) {
        int sum = 0;
        for(int i = 0; i < dices.length; i++) 
            sum += dices[i];
        return sum;
    }

    public String toString() {
        String output = "\n";
        output += "ones\t" + sc.get("ones") + "\n" + 
                  "twos\t" + sc.get("twos") + "\n" +
                  "threes\t" + sc.get("threes") + "\n" +
                  "fours\t" + sc.get("fours") + "\n" +
                  "fives\t" + sc.get("fives") + "\n" +
                  "sixes\t" + sc.get("sixes") + "\n" +
                  "-----" + "\n" +
                  "sum\t" + sc.get("sum") + "\n" +
                  "bonus\t" + sc.get("bonus") + "\n" +
                  "-----" + "\n" +
                  "3kind\t" + sc.get("3kind") + "\n" +
                  "4kind\t" + sc.get("4kind") + "\n" +
                  "fhouse\t" + sc.get("fhouse") + "\n" +
                  "sm str\t" + sc.get("sm str") + "\n" +
                  "lg str\t" + sc.get("lg str") + "\n" +
                  "yahtzee\t" + sc.get("yahtzee") + "\n" +
                  "chance\t" + sc.get("chance") + "\n" +
                  "-----" + "\n" +
                  "total\t" + sc.get("total") + "\n";
        return output + "\n";
    }
}