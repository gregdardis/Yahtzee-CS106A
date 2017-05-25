
public class SmallStraight implements Category {

	private Yahtzee yahtzee;
	
	public SmallStraight(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		if (!yahtzee.isStraightLength(4)) {
			return 0;
		}
		return yahtzee.findScoreSmallStraight();
	}
	
}
