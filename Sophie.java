import java.io.*;
import cards.*;

public class Sophie {

	public static Deck main;     //main deck
	public static Deck hand;     //hand
	public static Deck discard;  //discard pile
	public static Boolean out;   //boolean for running out of cards
	public static int separation = 4;

	public static int suitMatches;
	public static int valueMatches;

	public static void main(String[] args) {

		main = new Deck(false);     
		hand = new Deck(false);       //initialise decks
		discard = new Deck(false);
		main.shuffle();

		double averageScore = 0;
		int highScore = 0;
		int lowScore = main.deck.length;            //set counters
		int x = 1;
		int tries = 10000;
		Deck winner = new Deck(false);
		Deck losing = new Deck(false);

		while(x<=tries){              //start game

		suitMatches=0;
		valueMatches=0;

		main.shuffle();               //shuffle main deck
		hand.emptyDeck();             //clear hand
		discard.emptyDeck();          //clear discard pile

		out = false;
		Boolean recentMatch = true;

		int heldCards=hand.deck.length-hand.cardsUsed;
		int first;
		int forth;
	
		//repeat until main deck is empty
		while (main.deck.length-main.cardsUsed>0){

			//if too few cards in deck, draw up to 3
			while(heldCards<separation-1){
				//break if no cards left
				if (main.deck.length==main.cardsUsed){break;}
				//take a card
				hand.addCard(main.dealCard());
				heldCards=hand.deck.length-hand.cardsUsed;
			}
			
			//break if no cards left
			if (main.deck.length==main.cardsUsed){break;}

			//get a card
			hand.addCard(main.dealCard());
			heldCards=hand.deck.length-hand.cardsUsed;
			
			//update card numbers
			forth=heldCards-1;
			first=heldCards-separation;
			recentMatch=true;

			//comparing first and forth cards and discarding
			while(recentMatch==true){
				recentMatch = compare(first,forth);
				heldCards=hand.deck.length-hand.cardsUsed;
				forth=heldCards-1;
				first=heldCards-separation;
			}

		}

	//prints stats for the game

	System.out.println("Game number: " + x +"/" + tries +". Cards left: " + heldCards);
	System.out.println("Suit Matches: " + suitMatches + " Value Matches: " + valueMatches + " Total Matches: " + (valueMatches+suitMatches));
	System.out.println();

	//updates running totals
	if (heldCards==0){winner=main;}
	if (heldCards==hand.deck.length){losing=main;}
	if (heldCards<lowScore){lowScore=heldCards;}
	if (heldCards>highScore){highScore=heldCards;}

	averageScore=(averageScore+heldCards);
	x++;

	}

	//prints totals
	averageScore=averageScore/tries;
	System.out.println("averageScore Score: " + averageScore);
	System.out.println("Lowest score: " + lowScore + ". Highest Score: " + highScore);
	if(lowScore==0){
		winner.fillDeck();
		System.out.println("Winning deck: ");
		winner.printDeck();
	}
	if(highScore==52){
		losing.fillDeck();
		System.out.println("Losing Deck: ");
		losing.printDeck();
	}

	}


	//if match, discards cards and returns true
	public static Boolean compare(int i, int j){

		//check we have the cards to compare
		if (hand.deck.length-hand.cardsUsed<separation){return false;}

		//checks matching value
		if (hand.deck[i].getValue()==hand.deck[j].getValue()){
				//discards 4 cards
				int x = 0;
				while (x<separation){
				discard.addCard(hand.dealCard(x));
				x++;
				}
				valueMatches++;
				return true;
		//checks matching suit
		} else if (hand.deck[i].getSuit()==hand.deck[j].getSuit()){
				//discards middle two cards
				for (int x=j-1;x>i+1;x=x-1){
					discard.addCard(hand.dealCard(x));
					discard.addCard(hand.dealCard(x));
				}
				suitMatches++;
				return true;
		}
		//no matches returns false
		return false;

	}

}
