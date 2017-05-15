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
	
	/* Takes an index for a category and checks to see if that category is met.
	 * Returns a score to be added to the correct place in the scoreboard by a later method*/
	private int findScoreForCategory(int category) {
		int score = 0;
		score = findScoreOnesToSixes(category);
		
		return score;
//		if (YahtzeeMagicStub.checkCategory(dice, ONES)) {}
	}
	
	/* Go through a turn based on who's turn it is */
	private void takeTurn(int zeroIndexedPlayerTurn) {
		/* Waits for the player to click the Roll Dice button to start the first dice roll
		 * Returns when the Roll Dice button is pressed. */
		display.waitForPlayerToClickRoll(zeroIndexedPlayerTurn + 1);
		
		rollDiceFirst();
		rollDiceAgain();
		rollDiceAgain();
		
		/* Returns which category the player selected for their dice */
		int category = display.waitForPlayerToSelectCategory();
		
		/* If the category has already been selected, prompt the player to choose
		 * another category */
		while (categories[category][zeroIndexedPlayerTurn] == 1) {
			display.printMessage("That category has already been selected!");
			category = display.waitForPlayerToSelectCategory();
		}
		
		int score = findScoreForCategory(category);
		
		/* Update both the scoreboard, and the nearly identical categories
		 * array. The categories array will have a 0 in a spot if the category
		 * hasn't been chosen, and a 1 if it has been chosen before */
		scoreboard[category][zeroIndexedPlayerTurn] = score;
		categories[category][zeroIndexedPlayerTurn] = 1;
		display.updateScorecard(category, zeroIndexedPlayerTurn + 1, score);
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
