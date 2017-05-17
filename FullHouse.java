
public class FullHouse implements Category {

	private Yahtzee yahtzee;
	
	public FullHouse(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		if (!yahtzee.isFullHouse()) {
			return 0;
		}
		return yahtzee.findScoreFullHouse();
	}
}
