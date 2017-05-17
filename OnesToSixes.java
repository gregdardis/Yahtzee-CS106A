
public class OnesToSixes implements Category {

	private Yahtzee yahtzee;
	private int diceValue;
	
	public OnesToSixes(Yahtzee yahtzee, int diceValue) {
		this.yahtzee = yahtzee;
		this.diceValue = diceValue;
	}
	
	public int findScore() {
		return yahtzee.findScoreOnesToSixes(diceValue);
	}
}
