package com.clarusone.poker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PokerHandSecond implements Comparable<PokerHandSecond> {

	private String[] arrayOfCards;
	private static char[] indices = new char[2]; 
	private char[] aux = new char[] {'2','3','4','5','6','7','8','9','T','J','Q','K','A'};
	private char temp_char_1,temp_char_2;

	//Holds all symbols in ascending rank -> ["2", 2], ..... , ["Q", 12],["K", 13],["A", 14]
	private static HashMap<String, Integer> rankedSymbols  = new HashMap<String, Integer>();

	public PokerHandSecond(String fiveCards) {
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
    public int compareTo(PokerHandSecond that) {
    	Arrays.sort(this.arrayOfCards);
    	Arrays.sort(that.arrayOfCards); 
    	//METHOD 2 WHERE WE HAVE TO SHOW WHAT TYPE OF WIN IS WE CAN USE THE ALGORITHM BELOW AND THE ADDITIONAL METHODS
		//REMOVE THE /* TO TEST THE 2ND METHOD AND PUT IN COMMENTS THE 1ST METHOD
    	int end = arrayOfCards.length - 1;
    	String s1 = String.valueOf(indices[0]);
    	String s2 = String.valueOf(indices[1]);

    	if(this.isEqualToOther(that,5)) return 0;
    	else if(this.isFlushRoyale()) return 1;
    	else if(that.isFlushRoyale()) return -1;	
    	else if(this.isFlushAndStraight()) {   		
    	    if(that.isFlushAndStraight()) {     	    	
    	    	if(rankedSymbols.get(getStringOf(this,end))>rankedSymbols.get(getStringOf(that,end))) return 1;
    			else if (rankedSymbols.get(getStringOf(this,end))<rankedSymbols.get(getStringOf(that,end))) return -1;
    			else return 0;
    	    }
    	    else return 1;
    	}
    	else if(that.isFlushAndStraight()) return -1;  	
    	else if(this.isFourOfAKind()) {
    		if(that.isFourOfAKind()) {
    			if(rankedSymbols.get(getStringOf(this,end-2))>rankedSymbols.get(getStringOf(that,end-2))) return 1;
    			else if (rankedSymbols.get(getStringOf(this,end-2))<rankedSymbols.get(getStringOf(that,end-2))) return -1;
    			else return 0;  			
    		}
    		else return 1;
    	}
    	else if(that.isFourOfAKind()) return -1;
    	else if(this.isFullHouse(0)) {
    		if(that.isFullHouse(1)) {
    			if(rankedSymbols.get(s1)>rankedSymbols.get(s2)) return 1;
    			else if (rankedSymbols.get(s1)<rankedSymbols.get(s2)) return -1;
    			else return 0;  
    		}
    		else return 1;
    	}
    	else if(that.isFullHouse(1)) return -1;
    	else if(this.isFlush()) {
    		if(that.isFlush()) {
    			findCharsWhereDontMatch(that);
    			if(rankedSymbols.get(String.valueOf(temp_char_1))>rankedSymbols.get(String.valueOf(temp_char_2))) return -1;
    			else if (rankedSymbols.get(String.valueOf(temp_char_1))<rankedSymbols.get(String.valueOf(temp_char_2))) return 1;
    			else return 0; 			
    		}
    		else return 1;
    	}
    	else if(that.isFlush()) return -1;
    	else if(this.isStraight()) {
    		if(that.isStraight()) {
    			if(rankedSymbols.get(getStringOf(this,end))>rankedSymbols.get(getStringOf(that,end))) return 1;
    			else if (rankedSymbols.get(getStringOf(this,end))<rankedSymbols.get(getStringOf(that,end))) return -1;
    			else return 0;		
    		}
    		else return 1;
    	}
    	else if(that.isStraight()) return -1;  	    	
    	else if(this.isThreeOfAKind()) {
    		if(that.isThreeOfAKind()) {
    			//The index (end-2) refers to indices 1,2,3 because we know that in these indices
    	    	//the symbols are the same rank so the end-2 it could be alse end-1,end-3
    			if(rankedSymbols.get(getStringOf(this,end-2))>rankedSymbols.get(getStringOf(that,end-2))) return 1;
    			else if (rankedSymbols.get(getStringOf(this,end-2))<rankedSymbols.get(getStringOf(that,end-2))) return -1;
    			else return 0;  			
    		}
    		else return 1;
    	}
    	else if(that.isThreeOfAKind()) return -1;      	
    	else if(this.isPair(0)) {
    		if(that.isPair(1)) {
    			if(rankedSymbols.get(s1)>rankedSymbols.get(s2)) return 1;
    			else if (rankedSymbols.get(s1)<rankedSymbols.get(s2)) return -1;
    			else return 0;  			
    		}
    		else return 1;
    	}
    	else if(that.isPair(1)) return -1;	
    	else if(isEqualToOther(that,0)) {
    		if(rankedSymbols.get(getStringOf(this,0))>rankedSymbols.get(getStringOf(that,0))) return 1;
			else if (rankedSymbols.get(getStringOf(this,end))<rankedSymbols.get(getStringOf(that,end))) return -1;
			else return 0;  
    	}
    	else return 0;
    }

	
    private boolean isEqualToOther(PokerHandSecond that,int matches) {
		int counter = 0,counter2 = 0;
		for(int i=0; i<arrayOfCards.length-1; i++) {
			if(arrayOfCards[i].charAt(0)==arrayOfCards[i+1].charAt(0)) {
				counter++;
			}
			if(arrayOfCards[i].charAt(0)==arrayOfCards[i+1].charAt(0)) {
				counter2++;
			}
		}
		if(counter==matches && counter2==matches) return true;
		else return false;
	}

	private boolean isFlushRoyale() {
		if(isFlush()) {
            if(isRoyale()) return true;            
            else return false;           
    	}
    	else return false;  	
    }		
	
    private boolean isFlushAndStraight() {
    	if(isFlush() && isStraight()) return true;
    	else return false;
    }
    
    private boolean isFourOfAKind() {
    	if(isKindOf(4)) return true;
    	else return false;
    }
    
    private boolean isFullHouse(int player) {
    	int[] counters = new int[] {1,1};
    	int index=0;
    	boolean flag = false;
    	for(int i=0; i<arrayOfCards.length-1; i++) {
    		if(arrayOfCards[i].charAt(0) == arrayOfCards[i+1].charAt(0)) {
    			counters[index]++;   
    			flag=true;
    		}
    		else if(flag) {
    			index = 1;
    		}
    		else break;    		  		
    	}
    	if((counters[0]==2 && counters[1]==3) || (counters[0]==3 && counters[1]==2)) { 
    		indices[player] = arrayOfCards[2].charAt(0);
    		return true;
    	}
    	else return false;
    }
          
    private boolean isThreeOfAKind() {
    	if(isKindOf(3)) return true;
    	else return false;
    }
 
    private boolean isPair(int player) {
    	HashMap<Character,Integer> aux_map = new HashMap<Character,Integer>();
    	for(int i=0; i<arrayOfCards.length; i++) 
    		aux_map.put(arrayOfCards[i].charAt(0), 0);
    	for(int i=0; i<aux.length; i++) {
    		for(int j=0; j<arrayOfCards.length; j++) {
        		if(aux[i] == arrayOfCards[j].charAt(0)) {
        			int inc_value = aux_map.get(arrayOfCards[j].charAt(0)) + 1;
        			aux_map.put(arrayOfCards[j].charAt(0), inc_value);
        		}
        	}  	
    	}	   	
		if(aux_map.containsValue(2)) {
			Character ch = getKey(aux_map,2);	
			indices[player] = ch;
			return true;
		}
		else return false;		
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
   
    private boolean isRoyale() {
        if(arrayOfCards[0].charAt(0) == 'A' && arrayOfCards[1].charAt(0)== 'J' && arrayOfCards[2].charAt(0)=='K' && arrayOfCards[3].charAt(0)=='Q' && arrayOfCards[4].charAt(0)=='T')
        	return true;
        else return false;     
	}

    private boolean isKindOf(int matches) {
    	int counter=1;
    	for(int i=0; i<arrayOfCards.length-1; i++) {
    		if(arrayOfCards[i].charAt(0) == arrayOfCards[i+1].charAt(0)) counter++;
    		else counter = 1;
    	}
    	if(counter==matches) return true;   	
    	else return false;
    }

    private void findCharsWhereDontMatch(PokerHandSecond that) {
    	outerloop:
    	for(int i=0; i<arrayOfCards.length; i++) {
        	for(int j=0; j<arrayOfCards.length; j++) {
        		if(this.arrayOfCards[i].charAt(0) != that.arrayOfCards[j].charAt(0)) {
        			temp_char_1 = this.arrayOfCards[i].charAt(0);
        			temp_char_2 = that.arrayOfCards[j].charAt(0);
        			break outerloop;
        		}
        	}
    	}
	}

    private String getStringOf(PokerHandSecond other,int index) {
    	return String.valueOf(other.arrayOfCards[index].charAt(0));
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

