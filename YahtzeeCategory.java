
public class YahtzeeCategory implements Category {

	private Yahtzee yahtzee;
	
	public YahtzeeCategory(Yahtzee yahtzee) {
		this.yahtzee = yahtzee;
	}
	
	public int findScore() {
		if (!yahtzee.howManyDiceMatch(5)) {
			return 0;
		}
		return yahtzee.findScoreYahtzee();
	}

}
