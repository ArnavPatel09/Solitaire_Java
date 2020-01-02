package solitaireGame;

import java.util.Random;

import myfirstpackage.Lab13_Cards;

public class Board {
	private static int lastStockCard = 0;
	
	public static void moveWaste(Card[][]deck, int fColumn, int sColumn) {
		if(deck[1][lastStockCard-1] != null)
//checks if the card being moved is the one just flipped
			move(deck, fColumn, lastStockCard-1, sColumn);
		else {
//otherwise we check the next closest card that is facing up
			for(int i = 0; i<lastStockCard; i++)
				if(deck[1][i]!= null && deck[1][i].isFaceUp)
					move(deck, fColumn, i, sColumn);
		}
		
	}
	
	public static void foundations(Card[][] deck, int fColumn, int fRow, int sColumn) {
		if(deck[fColumn][fRow+1] == null) {
//if a card gets moved to the foundation, we have to make sure that it is the only card
			int lastCard = 0;
			if(sColumn-1 == deck[fColumn][fRow].suit)
//checking if the suit matches the appropriate column
					if(deck[fColumn][fRow].face == 1) {
//checking if the card is an ace
						deck[sColumn][0] = deck[fColumn][fRow];
						deck[fColumn][fRow] = null;
						if(fRow !=0) {
//checking if there is another card in the column
							if(fColumn == 1)
//checking if it's in the waste column (where the cards are flipped
								wasteFaceUp(deck, fRow);
							else
								deck[fColumn][fRow-1].cardFaceUp();
						}
							
					}
					else {
						if(deck[sColumn][0] != null && deck[sColumn][12] == null) {
//makes sure the card is not an ace and the card can go into the column
							for(int i = 0; i<deck[sColumn].length; i++)
								if(deck[sColumn][i] != null)
									lastCard = i;
//finding where the last card in the column is
							if(deck[sColumn][lastCard].face-1 == deck[fColumn][fRow].face) {
//checking if the card selected can move to the new column
								deck[sColumn][lastCard+1] = deck[fColumn][fRow];
								deck[fColumn][fRow] = null;
								if(fRow != 0)
									deck[fColumn][fRow-1].cardFaceUp();
							}
						}
					}
		}
		else {
			System.out.println("MOVE IS NOT VALID\n\n");
		}
	}
	
	public static void move(Card[][] deck, int fColumn, int fRow, int sColumn){
		if(sColumn == 2||sColumn==3||sColumn==4||sColumn==5) {
//checking if the move is with the foundation columns
			foundations(deck,fColumn,fRow, sColumn);
		}
		else {
			int lastCard = 0;
			Card[] moving = new Card[13];
			int z = 0;
			for(int i = 0; i<deck[sColumn].length; i++) {
				if(deck[sColumn][i] != null)
					if(deck[sColumn][i].isFaceUp)
						lastCard = i;
//find the last card in the column
			}
			if(deck[fColumn][fRow].face==13) {
//if the card is a King it can only be moved to a column with no other cards
				if(deck[sColumn][0] == null) {
					for(int i = fRow; i<deck[fColumn].length; i++) {
						if(deck[fColumn][i]!= null && deck[fColumn][i].isFaceUp) {
							moving[z] = deck[fColumn][i];
							deck[fColumn][i] = null;
							z++;
						}
					}
					for(int i = 0; i<moving.length; i++) {
						deck[sColumn][i] = moving[i];
					}
					if(fRow !=0) {
//making sure the card behind the selected card is face up
						if(fColumn == 1)
							wasteFaceUp(deck, fRow);
						else
							deck[fColumn][fRow-1].cardFaceUp();
					}
				}
				
			}
			else if(deck[sColumn][lastCard].face-1 == deck[fColumn][fRow].face) {
				if((deck[sColumn][lastCard].suit%2 ==0 && deck[fColumn][fRow].suit%2 ==1)||(deck[sColumn][lastCard].suit%2==1 && deck[fColumn][fRow].suit%2==0)) {
//checks if the card can be moved to the column
					for(int i = fRow; i<deck[fColumn].length; i++) {
						if(deck[fColumn][i]!= null && deck[fColumn][i].isFaceUp) {
//moving the cards after the selected card too
							moving[z] = deck[fColumn][i];
							deck[fColumn][i] = null;
							z++;
						}
					}
					z = lastCard+1;
					for(int i = 0; i<moving.length && z<deck[sColumn].length; i++) {
						deck[sColumn][z] = moving[i];
						z++;
					}
					deck[fColumn][fRow] = null;
					if(fRow !=0) {
//making sure the card before the selected card is facing up
						if(fColumn == 1)
							wasteFaceUp(deck, fRow);
						else
							deck[fColumn][fRow-1].cardFaceUp();
					}
				}
			}
			else {
				System.out.println("Not a valid move");
			}
		}
		
	}
	
	public static void wasteFaceUp(Card[][] deck, int row) {
		int lastCard = -1;
		for(int i = 0; i<row; i++) {
			if(deck[1][i] != null)
				lastCard = i;
//finding the closest card to the last flipped card
		}
		if(lastCard>-1)
//if there is a card we will make that face up
			deck[1][lastCard].cardFaceUp();
	}
	
	public static void flipStack(Card[][] deck) {
//flips the next card from the stock
		if(lastStockCard>0)
			for(int i = 0; i<lastStockCard; i++)
				if(deck[1][i] != null)
					deck[1][i].cardFaceDown();
		deck[1][lastStockCard] = deck[0][lastStockCard];
		deck[1][lastStockCard].cardFaceUp();
		deck[0][lastStockCard] = null;
		lastStockCard++;
	}
	
	public static void resetStack(Card[][] deck) {
//moves all the remaining cards from the waste pile to the stock pile
		int x = 0;
		for(int i = 0; i<deck[0].length; i++) {
			if(deck[1][i] != null) {
				deck[0][x] = deck[1][i];
				deck[0][x].cardFaceDown();
				deck[1][i] = null;
				x++;
			}
		}
//reseting the lastStockCard
		lastStockCard = 0;
		printBoard(deck);
	}
	
	public static Card[][] makeBoard(Card[] mixedDeck){
		//making the board setup
			int p = 6;
			Card[][] deck = new Card[13][];
				deck[0] = new Card [24];//pile
				deck[1] = new Card [24];//waste
				deck[2] = new Card [20];//Spade foundation
				deck[3] = new Card[20];//Heart foundation
				deck[4] = new Card[20];//Diamond foundation
				deck[5] = new Card[20];//Club foundation
				int y = 0;//used to transverse mixedDeck
				int z = 0;
				for(int i = 6; i<deck.length; i++) {
					deck[i] = new Card[19-z];
					z++;
					for(int x = 0; x<deck[i].length-1; x++) {
						deck[i][x] = mixedDeck[y];
						++y;
						if(y==7||y==13||y==18||y==22||y==25||y==27||y==28) {
							x = 100;
						}
					}
				}
				for(int i = 0; i<deck[0].length; i++) {
					deck[0][i] = mixedDeck[y];
					y++;
				}
				for(int i = 6; i<deck.length; i++) {
					//makes the last cards in the tableau facing up
					deck[i][p].cardFaceUp();
					p--;
				}
				return deck;		
	}
	public static Card[] randomDeck(){
		Card[] cards = new Card[52];
		Random r = new Random();//using random class to randomize the cards
		int suit = 1;
		int face = 1;
		int index = 0;
		Card temp = new Card();
		for(int i = 0; i<cards.length; i++) {//creates the deck
			cards[i] = new Card(suit, face, false);
			face++;
			if(face>13) {
				face = 1;
				suit++;
			}
		}
		for(int i = 0; i<cards.length; i++) {
			index = r.nextInt(52);//gives a random number from 0-52
//mixes the deck 52 times
			temp = cards[i];
			cards[i] = cards[index];
			cards[index] = temp;
		}
		return cards;
	}//end createRandomDeck
	
	public static void printBoard(Card[][] deck) {
		int a = 0;
		System.out.println("\t 0 \t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8 \t9 \t10\t11\t12\n");
//prints out the column numbers
		for(int r = 0; r<deck.length; r++) {
			System.out.print(r + "\t");
//prints out the row numbers
			if(r ==0) {
				for(int c = 0; c<deck[r].length-1 && c<13; c++) {
					if(deck[c][r] != null )
//if there is a card, it will be printed
						System.out.print(deck[c][r].cardToString() + "\t");
					else
//otherwise it will be a blank space
						System.out.print("  \t");
				}
			}
			else if(r>=7) {
//repeating this for tableau columns
				for(int c = 0; c<13; c++) {
					if(deck[c][r] != null )
						System.out.print(deck[c][r].cardToString() + "\t");
					else
						System.out.print("  \t");
				}
			}
			else {
				for(int c = 0; c<deck[r].length-1-a && c<13; c++) {
//the columns will be in descending order
					if(deck[c][r] != null )
						System.out.print(deck[c][r].cardToString() + "\t");
					else {
						System.out.print("  \t");
					}
				}
			}
			if(r>=2)
				a++;
			System.out.println();
		}
		int y = 0;
		for(int i = 13; i<20; i++) {
			System.out.print(i + "\t");
			for(int x = 0; x<12+y; x++) {
				if(deck[x][i] != null)
					System.out.print(deck[x][i].cardToString() + "\t");
				else
					System.out.print("  \t");
			}
			if(y<20)
				y--;
			System.out.println();
		}
		
		for(int i = 20; i<24; i++) {
//printing the rest of the cards in the stock and waste piles
			System.out.print(i + "\t");
			for(int x = 0; x<2; x++) {
				if(deck[x][i] != null)
					System.out.print(deck[x][i].cardToString() + "\t");
				else
					System.out.print("  \t");
			}
			System.out.println();
		}
	}
		
}
