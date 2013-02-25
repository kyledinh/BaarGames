package com.protofarm.baargames;

import java.util.ArrayList;
import java.util.Collections;

import com.protofarm.poker.Card;

public class PokkerUtil {
	private static int INX_STRAIGHTFLUSH = 0;
	private static int INX_QUAD = 1;
	private static int INX_FULLHOUSE = 2;
	private static int INX_FLUSH = 3;
	private static int INX_STRAIGHT = 4;
	private static int INX_TRIP = 5;
	private static int INX_2PAIR = 6;
	private static int INX_PAIR = 7; 
	
	
	static String descWinningHand(char[] winningHand) {
		
		char hand[] = winningHand;
		String handDesc = "High Card " + Card.getHexRank(hand[8]);
		
		if (hand[7] != '0') handDesc = "A Pair of " + Card.getHexRank(hand[7]) +"s";
		if (hand[6] != '0') handDesc = "Two Pairs " + Card.getHexRank(hand[6])+ "s and"
			+ Card.getHexRank(hand[7]) + "s";
		if (hand[5] != '0') handDesc = "Trips of " + Card.getHexRank(hand[5]) +"s";
		if (hand[4] != '0') handDesc = "A " + Card.getHexRank(hand[4])+ " high straight.";
		if (hand[3] != '0') handDesc = "A Flush, " + Card.getHexRank(hand[3])+ " high.";
		if (hand[2] != '0') handDesc = "A Full House";
		if (hand[1] != '0') handDesc = "Quads of " + Card.getHexRank(hand[1]) +"s";
		if (hand[0] != '0') handDesc = "A " + Card.getHexRank(hand[0]) + " high straight flush.";		
		return handDesc;
	}

	static char[] getHandValue(ArrayList<Card> hand) {
		long handValue = 0;
		char handHex[];
		Integer handInt[] = {0,0,0,0,0};

		String tmpStr = "";
		ArrayList<Integer> cards = new ArrayList<Integer>();

		for (Card card : hand) {
			Card.Rank rank = card.rank();
			int ord = rank.ordinal() +1;
			if (ord == 1) { ord = 14; }
			cards.add(ord);
			handValue += ord;
		}

		Collections.sort(cards);
		Collections.reverse(cards);
		int n = 0;
		for (Integer cardInteger : cards){
			handInt[n] = cardInteger;
			tmpStr += Integer.toHexString(cardInteger);
			n++;
		}
		tmpStr = "00000000" + tmpStr;
		handHex = tmpStr.toCharArray();
		handHex = checkPair(handHex, handInt);
		handHex = checkTrip(handHex, handInt);
		handHex = checkStraight(handHex, handInt);			
		handHex = checkFlush(handHex, hand);
		handHex = checkFullHouse(handHex);
		handHex = checkQuad(handHex, handInt);
		handHex = checkStraightFlush(handHex);
		return handHex;
	} 

	static char[] checkPair(char[] handHex, Integer[] handInt) {
			
		if ((handInt[4] == handInt[3]) && (handInt[4] != handInt[2])) {
			handHex[INX_PAIR] = Integer.toHexString(handInt[4]).charAt(0);
			return checkTwoPair(handHex,handInt);
		}
		if ((handInt[3] == handInt[2]) && (handInt[3] != handInt[1])
				&& (handInt[3] != handInt[4])) {
			handHex[INX_PAIR] = Integer.toHexString(handInt[3]).charAt(0);
			return checkTwoPair(handHex,handInt);
		}
		if ((handInt[2] == handInt[1]) && (handInt[2] != handInt[0]) 
				&& (handInt[2] != handInt[3])) {
			handHex[INX_PAIR] = Integer.toHexString(handInt[2]).charAt(0);
			return handHex;
		}
		if ((handInt[1] == handInt[0]) && (handInt[1] != handInt[2])) {
			handHex[INX_PAIR] = Integer.toHexString(handInt[1]).charAt(0);
			return handHex;
		}
		return handHex;
	}

	static char[] checkTwoPair(char[] handHex, Integer[] handInt) {
			
		if ((handInt[1] == handInt[2]) && (handInt[1] != handInt[3])
				&& (handInt[1] != handInt[0])) {
			handHex[INX_2PAIR] = Integer.toHexString(handInt[1]).charAt(0);
			return handHex;
		}
		if ((handInt[0] == handInt[1]) && (handInt[0] != handInt[2])) {
			handHex[INX_2PAIR] = Integer.toHexString(handInt[0]).charAt(0);
			return handHex;
		}
		return handHex;
	}	

	static char[] checkTrip(char[] handHex, Integer[] handInt) {
		if ((handInt[0] == handInt[1]) &&
			(handInt[0] == handInt[2])) {
			handHex[INX_TRIP] = Integer.toHexString(handInt[0]).charAt(0);
		}
		if ((handInt[1] == handInt[2]) &&
			(handInt[1] == handInt[3])) {
			handHex[INX_TRIP] = Integer.toHexString(handInt[1]).charAt(0);
		}
		if ((handInt[2] == handInt[3]) &&
			(handInt[2] == handInt[4])) {
			handHex[INX_TRIP] = Integer.toHexString(handInt[2]).charAt(0);
		}		
		return handHex;
	}	

	static char[] checkStraight(char[] handHex, Integer[] handInt) {
		if ((handInt[0] - handInt[1] == 1) &&
				(handInt[1] - handInt[2] == 1) &&
				(handInt[2] - handInt[3] == 1) &&
				(handInt[3] - handInt[4] == 1)) {
			handHex[INX_STRAIGHT] = handHex[8];			
		}
		if ((handInt[0] - handInt[1] == 9) &&
				(handInt[1] - handInt[2] == 1) &&
				(handInt[2] - handInt[3] == 1) &&
				(handInt[3] - handInt[4] == 1)) {
			handHex[INX_STRAIGHT] = handHex[10];	// 5 high straight		
		}
		return handHex;
	}	
	
	static char[] checkFlush(char[] handHex, ArrayList<Card> hand) {
		if ((hand.get(0).suit().equals(hand.get(1).suit())) &&  
				(hand.get(0).suit().equals(hand.get(2).suit())) &&
				(hand.get(0).suit().equals(hand.get(3).suit())) &&
				(hand.get(0).suit().equals(hand.get(4).suit())))
		{ handHex[INX_FLUSH] = '1'; } // flush
		return handHex;
	}

	static char[] checkFullHouse(char[] handHex) {
		if ((handHex[INX_TRIP] != '0') && (handHex[INX_PAIR] != '0')) {
			handHex[INX_FULLHOUSE] = handHex[INX_TRIP];
		}		
		return handHex;
	}

	static char[] checkQuad(char[] handHex, Integer[] handInt) {
		if ((handInt[0] == handInt[1]) &&
			(handInt[0] == handInt[2]) &&
			(handInt[0] == handInt[3])) {
			handHex[INX_QUAD] = Integer.toHexString(handInt[0]).charAt(0);
			handHex[INX_TRIP] = 0;
			handHex[INX_2PAIR] = 0;
			handHex[INX_PAIR] = 0;
		}
		if ((handInt[1] == handInt[2]) &&
			(handInt[1] == handInt[3]) &&
			(handInt[1] == handInt[4])) {
			handHex[INX_QUAD] = Integer.toHexString(handInt[1]).charAt(0);	//	
			handHex[INX_TRIP] = 0;
			handHex[INX_2PAIR] = 0;
			handHex[INX_PAIR] = 0;
		}
		return handHex;
	}

	static char[] checkStraightFlush(char[] handHex) {
		if ((handHex[INX_FLUSH] != '0') && (handHex[INX_STRAIGHT] != '0')) {
		    handHex[INX_STRAIGHTFLUSH] = handHex[INX_STRAIGHT];
		}
		return handHex;
	}
}