package eg.edu.guc.yugioh.board;

import java.util.Random;

import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;

public class Board {

	private Player activePlayer;
	private Player opponentPlayer;
	private Player winner;

	public Board() {

		Card.setBoard(this);

	}

	public void whoStarts(Player p1, Player p2) {

		Random r = new Random();

		if (r.nextInt(2) == 0) {
			setActivePlayer(p1);
			setOpponentPlayer(p2);
		} else {
			setActivePlayer(p2);
			setOpponentPlayer(p1);
		}

	}

	public void startGame(Player p1, Player p2) {

		p1.addNCardsToHand(5);
		p2.addNCardsToHand(5);

		whoStarts(p1, p2);

		activePlayer.addCardToHand();

	}

	public void nextPlayer() {
		if (GameOver()) {
			return;
		}

		Player temp = activePlayer;
		setActivePlayer(opponentPlayer);
		setOpponentPlayer(temp);
		activePlayer.addCardToHand();

	}

	
	public boolean GameOver() {
		if (this.getOpponentPlayer().getLifePoints() <= 0 || this.getOpponentPlayer().getField().getDeck().getDeck().size() <= 0) {
			
			this.setWinner(activePlayer);
			return true;
		} 
		if (this.getActivePlayer().getLifePoints() <= 0 || this.getActivePlayer().getField().getDeck().getDeck().size() <= 0) {
			
			this.setWinner(opponentPlayer);
			return true;
		}
		if (this.getWinner() == this.getActivePlayer() || this.getWinner() == this.getOpponentPlayer()) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Player getOpponentPlayer() {
		return opponentPlayer;
	}

	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
