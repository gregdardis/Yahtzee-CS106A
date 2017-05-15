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
	
	/* Takes an index for a category and checks to see if that category is met.
	 * Returns a score to be added to the correct place in the scoreboard by a later method*/
	private int findScoreForCategory(int category) {
		int score = 0;
		if (category == ONES) {
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 1) {
					score += dice[i];
				}
			}
			return score;
		}
		return score;
//		if (YahtzeeMagicStub.checkCategory(dice, ONES)) {}
	}
	
	/* Go through a turn based on who's turn it is */
	private void takeTurn(int zeroIndexedPlayerTurn, int[][] scoreboard) {
		/* Waits for the player to click the Roll Dice button to start the first dice roll
		 * Returns when the Roll Dice button is pressed. */
		display.waitForPlayerToClickRoll(zeroIndexedPlayerTurn + 1);
		
		rollDiceFirst();
		rollDiceAgain();
		rollDiceAgain();
		
		/* Returns which category the player selected for their dice */
		int category = display.waitForPlayerToSelectCategory();
		int score = findScoreForCategory(category);
		display.updateScorecard(category, zeroIndexedPlayerTurn + 1, score);
	}
	
	
	private void playGame() {
		int[][] scoreboard = new int[N_CATEGORIES][nPlayers];
		/* zeroIndexedPlayerTurn == -1 because nobody has played yet */
//		zeroIndexedPlayerTurn = selectPlayerTurn(zeroIndexedPlayerTurn);
		for (int i = 0; i < (nPlayers * 13); i++) {
			zeroIndexedPlayerTurn = selectPlayerTurn(zeroIndexedPlayerTurn);
			takeTurn(zeroIndexedPlayerTurn, scoreboard);
		}
		
	}
		
/* Private instance variables */
	int[] dice = new int[N_DICE]; 
	private int zeroIndexedPlayerTurn = -1;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
