package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;

import java.io.IOException;
import java.util.ArrayList;

public class Field {

	private final Deck deck;
	private ArrayList<MonsterCard> monstersArea;
	private ArrayList<SpellCard> spellArea;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;
	private Phase phase;

	public Field() throws IOException, MissingFieldException, UnknownCardTypeException, UnknownSpellCardException, NumberFormatException, EmptyFieldException {

		monstersArea = new ArrayList<MonsterCard>();
		spellArea = new ArrayList<SpellCard>();
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new Deck();
		phase = Phase.MAIN1;

	}

	public void addMonsterToField(MonsterCard monster, Mode m, boolean isHidden) {	
		if (monstersArea.size() >= 5 ) {
			throw new NoMonsterSpaceException();
		}

		if (monstersArea.size() < 5 && Card.getBoard().GameOver() == false) {

			monster.setHidden(isHidden);
			monster.setMode(m);
			monster.setLocation(Location.FIELD);
			monstersArea.add(monster);

		}

	}

	public void addMonsterToField(MonsterCard monster, Mode mode, ArrayList<MonsterCard> sacrifices) {
		if (monstersArea.size() >= 5 ) {
			throw new NoMonsterSpaceException();
		}
			if ((sacrifices == null  || sacrifices.size() == 0) && this.getMonstersArea().size() < 5 && Card.getBoard().GameOver() == false) {
			monster.setMode(mode);
			monster.setLocation(Location.FIELD);
			this.getMonstersArea().add(monster);
		} else if (sacrifices.size() == 1  && this.getMonstersArea().size() <= 5 && Card.getBoard().GameOver() == false) {
			this.removeMonsterToGraveyard(sacrifices);
			monster.setMode(mode);
			monster.setLocation(Location.FIELD);
			this.getMonstersArea().add(monster);
		} else if (this.getMonstersArea().size() <= 6 && Card.getBoard().GameOver() == false){
			this.removeMonsterToGraveyard(sacrifices);
			monster.setMode(mode);
			monster.setLocation(Location.FIELD);
			this.getMonstersArea().add(monster);
		}
		
	}

	public void removeMonsterToGraveyard(MonsterCard monster) {
        if (this.getMonstersArea().contains(monster) && Card.getBoard().GameOver() == false) {
		monstersArea.remove(monster);
		graveyard.add(monster);
		monster.setLocation(Location.GRAVEYARD);
        }
	}

	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters) {
if (Card.getBoard().GameOver() == false) {
		for (int i = 0; i < monsters.size(); i++)
			removeMonsterToGraveyard(monsters.get(i));
}
	}

	public void addSpellToField(SpellCard spell, MonsterCard monster, boolean hidden) {
if (spellArea.size() >=5) {
	throw new NoSpellSpaceException();
}
		spellArea.add(spell);
		spell.setLocation(Location.FIELD);
		if (!hidden)
			activateSetSpell(spell, monster);

	}

	public void activateSetSpell(SpellCard spell, MonsterCard monster) {

		if (getSpellArea().contains(spell) && Card.getBoard().GameOver() == false) {

			spell.action(monster);
			removeSpellToGraveyard(spell);

		}

	}

	public void removeSpellToGraveyard(SpellCard spell) {
		if (spellArea.contains(spell) && Card.getBoard().GameOver() == false) {
		spellArea.remove(spell);
		graveyard.add(spell);
		spell.setLocation(Location.GRAVEYARD);
		}
	}

	public void removeSpellToGraveyard(ArrayList<SpellCard> spells) {
if (Card.getBoard().GameOver() == false) {
		for (int i = 0; i < spells.size(); i++)
			removeSpellToGraveyard(spells.get(i));
}
	}

	public void addCardToHand() {

		Card temp = deck.drawOneCard();
		temp.setLocation(Location.HAND);
		hand.add(temp);

	}

	public void addNCardsToHand(int n) {
		if (n > this.getDeck().getDeck().size()) {
			this.getDeck().getDeck().clear();
			Card.getBoard().GameOver();
		}

		hand.addAll(deck.drawNCards(n));

	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Deck getDeck() {
		return deck;
	}

	public ArrayList<MonsterCard> getMonstersArea() {
		return monstersArea;
	}

	public ArrayList<SpellCard> getSpellArea() {
		return spellArea;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public int discardHand() {

		int discardedCards = hand.size();
		for (int i = 0; i < hand.size();) {
			graveyard.add(hand.get(i));
			hand.remove(i).setLocation(Location.GRAVEYARD);
		}
		return (discardedCards);

	}

	public MonsterCard strongestMonsterInGraveyard() {

		MonsterCard strongest = new MonsterCard("", "", 0, 0, 0);
		int strongestValue = 0;
		for (int i = 0; i < graveyard.size(); i++) {

			Card currentCard = graveyard.get(i);
			if (currentCard instanceof MonsterCard) {

				if (((MonsterCard) currentCard).getAttackPoints() > strongestValue) {

					strongest = (MonsterCard) currentCard;
					strongestValue = ((MonsterCard) currentCard)
							.getAttackPoints();

				}

			}

		}

		return (strongest);

	}

}
