
public class FourOfAKind implements Category {

	private Yahtzee yahtzee;
	
	public FourOfAKind(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		if (!yahtzee.howManyDiceMatch(4)) {
			return 0;
		}
		return yahtzee.sumDiceValues();
	}

}
