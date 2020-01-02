package solitaireGame;

import java.util.Scanner;

public class Play {
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static void rules() {
		System.out.println("Welcome to Solitaire!\n---------------------");
		System.out.println("The objective in this game is to order the cards by suit from least to greatest,"
				+ "\nstarting from Ace and ending at King in the Columns 2, 3, 4, and 5");
		System.out.println("\nThe way that you will play this game is by typing in the position you want to start at"
				+ "\nA prompt will then appear asking you for your next move"
				+ "\nThis can be moving the card to another position or leaving it where it is");
		System.out.println("\nTo draw a card type 0"
				+ "\nIf a there are no cards to draw from type 1 and then 100");
		System.out.println("\nColumn 2 will be the spot for Spades\nColumn 3 will be the spot for Hearts"
				+ "\nColumn 4 will be the spot for Clubs\nColumn 5 will be the spot for Diamonds");
	}
	public static void play() {
		Card[][] deck = Board.makeBoard(Board.randomDeck());
		Scanner in = new Scanner(System.in);
        int fColumn = 0;
        int fRow = 0;
        int sColumn = 0;
        int sRow = 0;
        int colTwo = 0;
        boolean isFirstMove = true;
        boolean isSecondMove = false;
		rules();
		System.out.println("\n\nPRESS ENTER TO START THE GAME");
		in.nextLine();
		System.out.println();
		//while(isPlaying == true) {
		while(deck[2][12] == null||deck[3][12] == null||deck[4][12] == null||deck[5][12] == null) {
//if the foundation column isn't filled out the game will continue
			while(isFirstMove == true) {
//asking and checking the first move
				Board.printBoard(deck);
				System.out.println("Type in the Column for your move:");
				fColumn = in.nextInt();
				//System.out.println("Move just entered: " + fColumn);
				if(fColumn <=12 && fColumn>=0) {
					if(fColumn != 0 && fColumn != 1) {
//checks if the card being moves is from the tableau column
						System.out.println("Type in the row of your move:");
						fRow = in.nextInt();
						if(deck[fColumn][fRow]!= null&&deck[fColumn][fRow].isFaceUp) {
//checks if the spot contains a card object
							isFirstMove = false;
							isSecondMove = true;
						}
					}
					else if(fColumn == 0) {
						Board.flipStack(deck);//Opens the next card
						//Board.printBoard(deck);
					}
					else if(fColumn == 1) {
						System.out.println("Type 100 if you want to restart the stack, otherwise"
								+ "type in the column you want to move the card to");
						colTwo = in.nextInt();
						if(colTwo == 100)
							Board.resetStack(deck);
						else {
							Board.moveWaste(deck, fColumn, colTwo);
						}
					}
				}
			}
			while(isSecondMove == true) {
//asks where the selected card will move
				System.out.println("Type in the Column where you want to place the card:");
				sColumn = in.nextInt();
				//System.out.println("Move just entered: " + fColumn);
				if(sColumn <=12 && sColumn>=0 && sColumn!= 0 && sColumn !=1) {
					Board.move(deck,fColumn, fRow, sColumn);
					isSecondMove = false;
					isFirstMove = true;
//this processes repeats until all the foundation columns are completed
				}
			}
		}
		
	}
	public static void main(String[] args) {
		play();//playing the game
	}

}
