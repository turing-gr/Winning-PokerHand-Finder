package com.clarusone.poker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PokerHand implements Comparable<PokerHand> {

	private String[] arrayOfCards;
	private char[] aux = new char[] {'2','3','4','5','6','7','8','9','T','J','Q','K','A'};
	private static int[] pairs = new int[] {0,0};

	//Holds all symbols in ascending rank -> ["2", 2], ..... , ["Q", 12],["K", 13],["A", 14]
	private static HashMap<String, Integer> rankedSymbols  = new HashMap<String, Integer>();

	public PokerHand(String fiveCards) {
		//Insert cards of player in array by splitting with spaces
		arrayOfCards = fiveCards.split(" ");
    }
	
	public static void initializeRankedMap() {	
		for(int i=2; i<=9; i++) rankedSymbols.put(String.valueOf(i), i);
		rankedSymbols.put("T", 10);
		rankedSymbols.put("J", 11);
		rankedSymbols.put("Q", 12);
		rankedSymbols.put("K", 13);
		rankedSymbols.put("A", 14);		
	}

    @Override
    public int compareTo(PokerHand that) {
    	Arrays.sort(this.arrayOfCards);
    	Arrays.sort(that.arrayOfCards); 
    	
    	//METHOD 1 WHERE WE DONT HAVE TO SHOW WHAT TYPE OF WIN IS  
    	calcPairs(this,0);
    	calcPairs(that,1);
    	int sum=0,sum2=0;
    	for(int i=0; i<this.arrayOfCards.length; i++) {
    		sum += rankedSymbols.get(String.valueOf(this.arrayOfCards[i].charAt(0)));
    	}
    	for(int i=0; i<that.arrayOfCards.length; i++) {
    		sum2 += rankedSymbols.get(String.valueOf(that.arrayOfCards[i].charAt(0)));
    	}
		if(sum < sum2 && pairs[0]>pairs[1] && pairs[0]==2 && (pairs[1]==1 || pairs[1]==0)) {
			return 1;
		}
		else if(sum > sum2 && pairs[0]<pairs[1]  && pairs[1]==2 && (pairs[0]==1 || pairs[0]==0)) return -1;
		else if(sum>sum2) {
    		if(that.isStraight()) {
    			if(this.isFlush()) return 1;   			
    			else return -1;   			
    		}
    		else return 1;
    	}
    	else if(sum<sum2) {
    		if(this.isStraight()) {
    			if(that.isFlush()) return -1;    			
    			else return 1;  			
    		}
    		else return -1;
    	}
    	else return 0;
    }
    private void calcPairs(PokerHand other,int player) {
    	//Check for pairs because sum doesnt win when we have 2 pairs VS 1 pair
    	for(int i=0; i<aux.length; i++) {
    		int counter=0;
	    	for(int j=0; j<other.arrayOfCards.length; j++) {
	    		if(aux[i] == other.arrayOfCards[j].charAt(0)) counter++;
	    	}
	    	if(counter==2) pairs[player]++;	    	
    	}
    }

    private boolean isFlush() {
    	//Count how many symbols are equal e.g. if the hand is TS-JS-QS-KS-AS then counter = 5;
    	int counter = 1;
    	boolean flag = false;
    	for(int i=0; i<arrayOfCards.length-1; i++) {
        	if(arrayOfCards[i].charAt(1)==arrayOfCards[i+1].charAt(1)) counter++;          	
        	else break;
    	}
    	if(counter==5) flag = true;
    	return flag;
    }  
  
    private boolean isStraight() {
    	int counter = 0,start = 0;
    	outerloop:
    	for(int i=0; i<arrayOfCards.length; i++) {
        	for(int j=0; j<aux.length; j++) {	
	    		if(arrayOfCards[i].charAt(0) == aux[j]) {
	    			start = j;
	    			break outerloop;
	    		}
        	}
    	}   	
    	for(int i=0; i<arrayOfCards.length; i++) {
    		if(arrayOfCards.length+start<13) {
    			if(arrayOfCards[i].charAt(0) == aux[i+start]) {
    				counter++;   			
    			}
    		}
    	}   	
    	if(counter==5) return true; 	
    	else return false;
    }   
    
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
