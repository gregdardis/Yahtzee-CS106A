/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	private void initScoreboard() {
		for (int j = 0; j < nPlayers; j++) {
			for (int i = 0; i < scoreboard.length; i++) {
				scoreboard[i][j] = 0;
			}
		}
	}
	
	public void init() {
		initScoreboard();
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/* If playerTurn == 0, it's player one's turn. If playerTurn == 1, it's player two's turn, etc.
	 * Takes the current players turn and returns who's turn it should be next. If game is just starting,
	 * player 0 will always go first. TODO: Will change that so a random player goes first  */
	private int selectPlayerTurn(int zeroIndexedPlayerTurn) {
		if (zeroIndexedPlayerTurn == -1) {
			/* If game is just starting, make it a random players turn */
			return rgen.nextInt(0, nPlayers - 1);
		}
		else {
			return (zeroIndexedPlayerTurn + 1) % nPlayers;
		}
	}
	
	/* Rolls the dice. If it's the first roll, roll all 5 dice. If it's not, only
	 * reroll the dice that the user selected */
	private void rollDice(Boolean firstRoll) {
		if (firstRoll) {
			for (int i = 0; i < dice.length; i++) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}
		else {
			for (int i = 0; i < dice.length; i++) {
				if (display.isDieSelected(i)) {
					dice[i] = rgen.nextInt(1, 6);
				}
			}
		}
		
	}
	
	/* Rolls the dice for the first time in a turn */
	private void rollDiceFirst() {
		rollDice(true);
		display.displayDice(dice);
		display.printMessage("Select the dice to reroll. You must roll the dice three times per turn.");
	}
	
	/* Rolls the dice for the second or third time in a turn*/
	private void rollDiceAgain() {
		display.waitForPlayerToSelectDice();
		rollDice(false);
		display.displayDice(dice);
	}
	
	/* Finds the score for categories ONES to SIXES, and returns it */
	private int findScoreOnesToSixes(int category) {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == category) {
				score += dice[i];
			}
		}
		return score;
	}
	
	/* Finds the score for categories THREE_OF_A_KIND, and returns it. TODO: find out if it's actually a three of a kind, then return it */
	private int findScoreThreeOfAKind() {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			score += dice[i];
		}
		return score;
	}
	
	/* Finds the score for categories FOUR_OF_A_KIND, and returns it. TODO: find out if it's actually a four of a kind, then return it */
	private int findScoreFourOfAKind() {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			score += dice[i];
		}
		return score;
	}
	
	/* Finds the score for a full house. TODO: find out if it's actually a full house, then return it */
	private int findScoreFullHouse() {
		int score = 0;
		score = 25;
		return score;
	}
	
	/* Finds the score for a small straight. TODO: find out if it's really a small straight, then return it */
	private int findScoreSmallStraight() {
		int score = 0;
		score = 30;
		return score;
	}
	
	/* Finds the score for a large straight. TODO: find out if it's really a large straight, then return it */
	private int findScoreLargeStraight() {
		int score = 0;
		score = 40;
		return score;
	}
	
	/* Finds the score for a yahtzee. TODO: find out if it's actually a yahtzee, then return it */
	private int findScoreYahtzee() {
		int score = 0;
		score = 50;
		return score;
	}
	
	/* Finds the score for "chance" category, as in sums all the dice values and returns it */
	private int findScoreChance() {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			score += dice[i];
		}
		return score;
	}
	
	private int compareWithDieForMatches(int diceNumber) {
		int counter = 0;
		
		for (int i = diceNumber; i < dice.length; i++) {
			if (dice[diceNumber - 1] == dice[i]) {
				counter++;
			}
		}
		
		return counter;
	}
	
	/* Returns true if the dice are showing three of a kind, false otherwise */
	private boolean isThreeOfAKind() {
		int threeOfAKindCounter = compareWithDieForMatches(1);
		if (threeOfAKindCounter >= 2) {
			return true;
		} 
		threeOfAKindCounter = compareWithDieForMatches(2);
		if (threeOfAKindCounter >= 2) {
			return true;
		}
		threeOfAKindCounter = compareWithDieForMatches(3);
		if (threeOfAKindCounter >= 2) {
			return true;
		}
	return false;
	}
	
	/* Returns true if the dice are showing four of a kind, false otherwise */
	private boolean isFourOfAKind() {
		int fourOfAKindCounter = compareWithDieForMatches(1);
		if (fourOfAKindCounter >= 3) {
			return true;
		} 
		fourOfAKindCounter = compareWithDieForMatches(2);
		if (fourOfAKindCounter >= 3) {
			return true;
		}
		
	return false;
	}
	
	/* Returns true if the dice are showing full house, false otherwise */
	private boolean isFullHouse() {
		if (isThreeOfAKind()) {
			
			return true;
		}
		
		return false;
	}
	
	/* Checks to see if the category has actually been met. Returns true if 
	 * it has been, false otherwise */
	private boolean isCategory(int category) {
		if (category == THREE_OF_A_KIND) {
			return isThreeOfAKind();
		}
		if (category == FOUR_OF_A_KIND) {
			return isFourOfAKind();
		}
		if (category == FULL_HOUSE) {
			return isFullHouse();
		}
		
		return false;
		
	}
	
	/* Takes an index for a category and checks to see if that category is met.
	 * Returns a score to be added to the correct place in the scoreboard by a later method*/
	private int findScoreForCategory(int category) {
		int score = 0;
		
		if (category <= SIXES) {
			score = findScoreOnesToSixes(category);
			return score;
		}
		
		if (isCategory(THREE_OF_A_KIND) && category == THREE_OF_A_KIND) {
			score = findScoreThreeOfAKind();
			return score;
		}
		
		if (isCategory(FOUR_OF_A_KIND) && category == FOUR_OF_A_KIND) {
			score = findScoreFourOfAKind();
			return score;
		}
		
		if (YahtzeeMagicStub.checkCategory(dice, FULL_HOUSE) && category == FULL_HOUSE) {
			score = findScoreFullHouse();
			return score;
		}
		
		if (YahtzeeMagicStub.checkCategory(dice, YAHTZEE) && category == YAHTZEE) {
			score = findScoreYahtzee();
			return score;
		}
		
		if (YahtzeeMagicStub.checkCategory(dice, SMALL_STRAIGHT) && category == SMALL_STRAIGHT) {
			score = findScoreSmallStraight();
			return score;
		}
		
		if (YahtzeeMagicStub.checkCategory(dice, LARGE_STRAIGHT) && category == LARGE_STRAIGHT) {
			score = findScoreLargeStraight();
			return score;
		}
		
		if (category == CHANCE) {
			score = findScoreChance();
			return score;
		}
		return score;
	}
	
	private void displayPickCategoryMessage() {
		display.printMessage("Pick a category to apply these dice to.");
	}
	
	private void displayStartTurnMessage() {
		display.printMessage(playerNames[zeroIndexedPlayerTurn] + "'s turn. Click \"Roll Dice\" to roll the dice.");
	}
	
	private void displayBeforeThirdRollMessage() {
		display.printMessage("Select the dice to reroll. You must roll the dice one more time.");
	}
	
	/* Has the player roll the dice to obtain a final dice configuration for that turn */
	private void rollDiceThreeTimes() {
		rollDiceFirst();
		rollDiceAgain();
		displayBeforeThirdRollMessage();
		rollDiceAgain();
	}
	
	/* If the category has already been selected, prompt the player to choose
	 * another category */
	private int checkIfUnchosenCategory(int category) {
		while (categories[category][zeroIndexedPlayerTurn] == 1) {
			display.printMessage("That category has already been selected!");
			category = display.waitForPlayerToSelectCategory();
		}
		return category;
	}
	
	/* Totals up the score in a column and displays it in TOTAL for a certain player */
	private void calculateAndAddTotalScore(int zeroIndexedPlayerNumber) {
		
			int totalScore = 0;
			for (int i = ONES; i <= SIXES; i++) {
				totalScore += scoreboard[i][zeroIndexedPlayerNumber];
			}
			for (int i = UPPER_BONUS; i <= CHANCE; i++) {
				totalScore += scoreboard[i][zeroIndexedPlayerNumber];
			}
			scoreboard[TOTAL][zeroIndexedPlayerNumber] = totalScore;
			display.updateScorecard(TOTAL, zeroIndexedPlayerNumber + 1, totalScore);
	}
	
	/* Update both the scoreboard, and the nearly identical categories
	 * array. The categories array will have a 0 in a spot if the category
	 * hasn't been chosen, and a 1 if it has been chosen before */
	private void updateScoreboardAndArrays(int category, int zeroIndexedPlayerTurn, int score) {
		scoreboard[category][zeroIndexedPlayerTurn] = score;
		categories[category][zeroIndexedPlayerTurn] = 1;
		display.updateScorecard(category, zeroIndexedPlayerTurn + 1, score);
		
		calculateAndAddTotalScore(zeroIndexedPlayerTurn);
	}
	
	/* Go through a turn */
	private void takeTurn(int zeroIndexedPlayerTurn) {
		displayStartTurnMessage();
		
		/* Waits for the player to click the Roll Dice button to start the first dice roll
		 * Returns when the Roll Dice button is pressed. */
		display.waitForPlayerToClickRoll(zeroIndexedPlayerTurn + 1);
		
		rollDiceThreeTimes();
		displayPickCategoryMessage();
		
		/* Returns which category the player selected for their dice */
		int category = display.waitForPlayerToSelectCategory();
		
		category = checkIfUnchosenCategory(category);
		int score = findScoreForCategory(category);
		updateScoreboardAndArrays(category, zeroIndexedPlayerTurn, score);
	}
	
	/* Calculates the upper score and bonus, updates the scoreboard and puts them in the
	 * scoreboard array */
	private void fillInUpperScoreAndBonus() {
		for (int j = 0; j < nPlayers; j++) {
			int upperScore = 0;
			for (int i = ONES; i <= SIXES; i++) {
				upperScore += scoreboard[i][j];
			}
			scoreboard[UPPER_SCORE][j] = upperScore;
			display.updateScorecard(UPPER_SCORE, j + 1, upperScore);
			if (upperScore >= 63) {
				scoreboard[UPPER_BONUS][j] = 35;
				display.updateScorecard(UPPER_BONUS, j + 1, 35);
			} else {
				display.updateScorecard(UPPER_BONUS, j + 1, 0);
			}
		}	
	}
	
	private void fillInLowerScore() {
		for (int j = 0; j < nPlayers; j++) {
			int lowerScore = 0;
			for (int i = THREE_OF_A_KIND; i <= CHANCE; i++) {
				lowerScore += scoreboard[i][j];
			}
			scoreboard[LOWER_SCORE][j] = lowerScore;
			display.updateScorecard(LOWER_SCORE, j + 1, lowerScore);
		}
	}
	
	
	private void fillInScoreboard() {
		fillInUpperScoreAndBonus();
		fillInLowerScore();
		for (int i = 0; i < nPlayers; i++) {
			calculateAndAddTotalScore(i);
		}
		
	}
	
	/* First iterate through the scoreboard to find the value of the highest score. 
	 * Then iterate through the scores again and add every person with that score to an ArrayList (to deal with ties) */
	private void chooseWinner() {
		int highestScore = 0;
		ArrayList<Integer> winners = new ArrayList<Integer>();
		for (int i = 0; i < nPlayers; i++) {
			if (scoreboard[TOTAL][i] > highestScore) {
				highestScore = scoreboard[TOTAL][i];
			}
		}
		
		for (int i = 0; i < nPlayers; i++) {
			if (scoreboard[TOTAL][i] == highestScore) {
				winners.add(i);
			}
		}
		printWinners(winners);
	}
	
	private void printWinners(ArrayList<Integer> winners) {
		if (winners.size() == 1) {
			display.printMessage("The winner is: " + playerNames[winners.get(0)]);
		} else {
			String listOfNames = joinNames(winners);
			display.printMessage("The winners are: " + listOfNames + ".");
		}
		
	}
	
	private String joinNames(ArrayList<Integer> winners) {
		String listOfNames = playerNames[winners.get(0)];
		for (int i = 1; i < winners.size(); i++) {
			listOfNames = listOfNames + ", " + playerNames[winners.get(i)];
		}
		return listOfNames;
	}
	
	private void playGame() {
		/* Scoreboard keeps track of the score, categories keeps track of if
		 * a category has been chosen yet. Both are from 1 index to N_CATEGORIES + 1 */
		scoreboard = new int[N_CATEGORIES + 1][nPlayers];
		categories = new int[N_CATEGORIES + 1][nPlayers];

		for (int i = 0; i < (nPlayers * N_SCORING_CATEGORIES); i++) {
			zeroIndexedPlayerTurn = selectPlayerTurn(zeroIndexedPlayerTurn);
			takeTurn(zeroIndexedPlayerTurn);
		}
		fillInScoreboard();
		chooseWinner();
	}
		
/* Private instance variables */
	int[][] scoreboard;
	int[][] categories;
	int[] dice = new int[N_DICE]; 
	private int zeroIndexedPlayerTurn = -1;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}