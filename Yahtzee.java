/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
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
			/* Make this make it a random persons turn. Currently player 0 goes first always */
			return 0;
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
	
	/* Takes an index for a category and checks to see if that category is met.
	 * Returns a score to be added to the correct place in the scoreboard by a later method*/
	private int findScoreForCategory(int category) {
		int score = 0;
		
		if (category <= SIXES) {
			score = findScoreOnesToSixes(category);
			return score;
		}
		
		if (YahtzeeMagicStub.checkCategory(dice, THREE_OF_A_KIND) && category == THREE_OF_A_KIND) {
			score = findScoreThreeOfAKind();
			return score;
		}
		
		if (YahtzeeMagicStub.checkCategory(dice, FOUR_OF_A_KIND) && category == FOUR_OF_A_KIND) {
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
	
	/* Update both the scoreboard, and the nearly identical categories
	 * array. The categories array will have a 0 in a spot if the category
	 * hasn't been chosen, and a 1 if it has been chosen before */
	private void updateScoreboardAndArrays(int category, int zeroIndexedPlayerTurn, int score) {
		scoreboard[category][zeroIndexedPlayerTurn] = score;
		categories[category][zeroIndexedPlayerTurn] = 1;
		
		int totalScore = 0;
		display.updateScorecard(category, zeroIndexedPlayerTurn + 1, score);
		for (int i = 0; i < scoreboard.length - 1; i++) {
			System.out.println("Scoreboard at i = " + scoreboard[i][zeroIndexedPlayerTurn]);
			totalScore += scoreboard[i][zeroIndexedPlayerTurn];
		}
		scoreboard[TOTAL][zeroIndexedPlayerTurn] = totalScore;
		display.updateScorecard(TOTAL, zeroIndexedPlayerTurn + 1, totalScore);
	}
	
//	private void updateScoreboard(int category, int zeroIndexedPlayerTurn, int score) {
//		int totalScore = 0;
//		display.updateScorecard(category, zeroIndexedPlayerTurn + 1, score);
//		for (int i = 0; i < scoreboard.length; i++) {
//			totalScore += scoreboard[i][zeroIndexedPlayerTurn];
//		}
//		display.updateScorecard(TOTAL, zeroIndexedPlayerTurn + 1, totalScore);
//	}
	
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
	
	
	private void playGame() {
		/* Scoreboard keeps track of the score, categories keeps track of if
		 * a category has been chosen yet */
		scoreboard = new int[N_CATEGORIES + 1][nPlayers];
		categories = new int[N_CATEGORIES + 1][nPlayers];

		for (int i = 0; i < (nPlayers * 13); i++) {
			zeroIndexedPlayerTurn = selectPlayerTurn(zeroIndexedPlayerTurn);
			takeTurn(zeroIndexedPlayerTurn);
		}
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
// TODO: figure out how to keep track of total score to display it at the end of the game. I guess it's just in the scoreboard array actually