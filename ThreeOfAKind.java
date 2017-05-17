
public class ThreeOfAKind implements Category {

	private Yahtzee yahtzee;
	
	public ThreeOfAKind(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		if (!yahtzee.howManyDiceMatch(3)) {
			return 0;
		}
		return yahtzee.sumDiceValues();
	}

}
