package eg.edu.guc.yugioh.cards;

import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;

public class MonsterCard extends Card {

	private int level;
	private int defensePoints;
	private int attackPoints;
	private Mode mode;

	public MonsterCard(String n, String desc, int l, int a, int d) {

		super(n, desc);
		this.level = l;
		this.attackPoints = a;
		this.defensePoints = d;
		this.mode = Mode.DEFENSE;

	}
	
	public void action(MonsterCard m) {
		if (Card.getBoard().getActivePlayer().getPreformAttackMonsters().contains(this)) {
			throw new MonsterMultipleAttackException();
		}
		if (this.getMode() == Mode.DEFENSE) {
			throw new DefenseMonsterAttackException();
		}
		if (m == null) {
			this.action();
			return;
		}
		if (Card.getBoard().getOpponentPlayer().getField().getMonstersArea().contains(m) && Card.getBoard().getActivePlayer().getField().getMonstersArea().contains(this) && Card.getBoard().getActivePlayer().getField().getPhase() == Phase.BATTLE && Card.getBoard().GameOver() == false && this.getMode() == Mode.ATTACK) {
		Card.getBoard().getActivePlayer().getPreformAttackMonsters().add(this);
		getBoard().GameOver();
		}
			if (m.getMode() == Mode.ATTACK) {
			if (this.getAttackPoints() > m.getAttackPoints()) {
				getBoard().getOpponentPlayer().setLifePoints(getBoard().getOpponentPlayer().getLifePoints() - (this.getAttackPoints() - m.getAttackPoints()));
				getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(m);
				getBoard().GameOver();
			} else if (this.getAttackPoints() == m.getAttackPoints()) {
				getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(m);
				getBoard().getActivePlayer().getField().removeMonsterToGraveyard(this);
				getBoard().GameOver();
			} else if (this.getAttackPoints() < m.getAttackPoints()){
				getBoard().getActivePlayer().setLifePoints(getBoard().getActivePlayer().getLifePoints() - (m.getAttackPoints() - this.getAttackPoints()));
				getBoard().getActivePlayer().getField().removeMonsterToGraveyard(this);
				getBoard().GameOver();
			}
		} else {
			if (this.getAttackPoints() > m.getDefensePoints()) {
				getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(m);
				getBoard().GameOver();
			} else if (this.getAttackPoints() == m.getDefensePoints()) {
				getBoard().GameOver();
			} else if (this.getAttackPoints() < m.getDefensePoints()){
				getBoard().getActivePlayer().setLifePoints(getBoard().getActivePlayer().getLifePoints() - (m.getDefensePoints() - this.getAttackPoints()));
				getBoard().GameOver();
			}
		
		}
			if (this.getMode() == Mode.DEFENSE) {
				throw new DefenseMonsterAttackException();
			}
			
			getBoard().GameOver();
			getBoard().GameOver();
}
	
	
public void action() {
	if (Card.getBoard().getActivePlayer().getPreformAttackMonsters().contains(this)) {
		throw new MonsterMultipleAttackException();
	}
	if (this.getMode() == Mode.DEFENSE) {
		throw new DefenseMonsterAttackException();
	}
	if (Card.getBoard().GameOver() == false && !Card.getBoard().getActivePlayer().getPreformAttackMonsters().contains(this)) {
		Card.getBoard().getActivePlayer().getPreformAttackMonsters().add(this);
		getBoard().getOpponentPlayer().setLifePoints(getBoard().getOpponentPlayer().getLifePoints() - this.getAttackPoints());
		getBoard().GameOver();
		
		getBoard().GameOver();	
}
	if (this.getMode() == Mode.DEFENSE) {
		throw new DefenseMonsterAttackException();
	}
	getBoard().GameOver();	
}
	

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDefensePoints() {
		return defensePoints;
	}

	public void setDefensePoints(int defensePoints) {
		this.defensePoints = defensePoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}
