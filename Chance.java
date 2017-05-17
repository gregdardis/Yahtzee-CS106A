
public class Chance implements Category {
	
	private Yahtzee yahtzee;
	
	public Chance(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		return yahtzee.sumDiceValues();
	}

}
