
public class LargeStraight implements Category {

private Yahtzee yahtzee;
	
	public LargeStraight(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		if (!yahtzee.isStraightLength(5)) {
			return 0;
		}
		return yahtzee.findScoreLargeStraight();
	}

}
