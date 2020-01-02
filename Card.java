package solitaireGame;

public class Card {
	public int face;
	public int suit;
	public boolean isFaceUp;
	
	public Card() {
		face = 0;
		suit = 0;
		isFaceUp = false;
	}
	
	public Card(int s , int f, boolean faceup) {
		face = f;
		suit = s;
		isFaceUp = faceup;
	}
	
	public void cardFaceUp() {
		isFaceUp = true;
	}
	
	public void cardFaceDown() {
		isFaceUp = false;
	}
	
	public String cardToString() {
		String rv = "";
		if(this.isFaceUp == true) {
			if(this.suit == 1)
				rv += "S";
			else if(this.suit == 2)
				rv += "H";
			else if(this.suit == 3)
				rv += "C";
			else if(this.suit == 4)
				rv += "D";
			else {
				this.isFaceUp = true;
				return "  ";
			}
			if(this.face == 10)
				rv += "X";
			else if(this.face == 11)
				rv += "J";
			else if(this.face == 12)
				rv += "Q";
			else if(this.face == 13)
				rv += "K";
			else
				rv += this.face;
		}
		else if(this.suit != 1 && this.suit != 2 && this.suit != 3 && this.suit != 4) {
			this.isFaceUp = true;
			return "  ";
		}
		else
			return "??";
		return rv;
	}
}