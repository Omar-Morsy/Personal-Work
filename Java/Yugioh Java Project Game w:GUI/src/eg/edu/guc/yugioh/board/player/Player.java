package eg.edu.guc.yugioh.board.player;

import java.io.IOException;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.CardDestruction;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class Player implements Duelist{

	private final String name;
	private int lifePoints;
	private Field field;
	private boolean ableToAdd;
	private boolean ableToSwitchMode;
	private Player turnPlayer;
	private MonsterCard saveLastMonster;
	private ArrayList<MonsterCard> ModeSwitchedMonsters;
	private ArrayList<MonsterCard> preformAttackMonsters;

	public Player(String name) throws IOException, MissingFieldException, UnknownCardTypeException, UnknownSpellCardException, NumberFormatException, EmptyFieldException {

		this.name = name;
		this.lifePoints = 8000;
		this.setAbleToAdd(true);
		this.setAbleToSwitchMode(true);
		this.setSaveLastMonster(null);
		setModeSwitchedMonsters(new ArrayList<MonsterCard>());
		this.setPreformAttackMonsters(new ArrayList<MonsterCard>());

		this.field = new Field();

	}

	public boolean summonMonster(MonsterCard monster) {
		if (this.getSaveLastMonster() == monster) {
			this.setAbleToAdd(true);
		}
		if ((this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && this.isAbleToAdd() == true  && Card.getBoard().getActivePlayer() == this && Card.getBoard().GameOver() == false && monster.getLocation() == Location.HAND) {
			if (monster.getLocation() == Location.HAND) {
				this.getField().getHand().remove((MonsterCard)monster);
			}
			if (monster.getLevel() <=4 && this.getField().getMonstersArea().size() < 5) {
			monster.setHidden(false);
			this.getField().addMonsterToField(monster, Mode.ATTACK, null);
			this.setAbleToAdd(false);
			this.setSaveLastMonster(monster);
			return true;
		} else if (this.getField().getMonstersArea().size() >=5) {
			throw new NoMonsterSpaceException();
		} 
	}
		
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		if (this.isAbleToAdd() == false) {
			throw new MultipleMonsterAdditionException();
		}
		return false;
	}
	
	public boolean summonMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices) {
		if (this.getSaveLastMonster() == monster) {
			this.setAbleToAdd(true);
		}
		if ((this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && this.isAbleToAdd() == true  && Card.getBoard().getActivePlayer() == this &&Card.getBoard().GameOver() == false && monster.getLocation() == Location.HAND) {
			if (monster.getLocation() == Location.HAND) {
				this.getField().getHand().remove((MonsterCard)monster);
			}
			if (sacrifices == null && monster.getLevel() <=4) {
			summonMonster(monster);
			this.setSaveLastMonster(monster);
		} else if (monster.getLevel() >= 7 && this.getField().getMonstersArea().size() > 1 && sacrifices.size() == 2) {
				monster.setHidden(false);
				this.getField().addMonsterToField(monster, Mode.ATTACK, sacrifices);
				this.setAbleToAdd(false);
				this.setSaveLastMonster(monster);
				return true;
			} else if (monster.getLevel() < 7 && monster.getLevel() > 4 && this.getField().getMonstersArea().size() > 0 && sacrifices.size() == 1){
					monster.setHidden(false);
					this.getField().addMonsterToField(monster, Mode.ATTACK, sacrifices);
					this.setAbleToAdd(false);
					this.setSaveLastMonster(monster);
				return true;
				
			
		} else if (this.getField().getMonstersArea().size() >=5) {
			throw new NoMonsterSpaceException();
		}
		}
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		if (this.isAbleToAdd() == false) {
			throw new MultipleMonsterAdditionException();
		}
		
		return false;
	}
	
	
	public boolean setMonster(MonsterCard monster) {
		if (this.getSaveLastMonster() == monster) {
			this.setAbleToAdd(true);
		}
		if ((this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && this.isAbleToAdd() == true  && Card.getBoard().getActivePlayer() == this &&Card.getBoard().GameOver() == false && monster.getLocation() == Location.HAND) {
			if (monster.getLevel() <=4 && this.getField().getMonstersArea().size() < 5) {
				if (monster.getLocation() == Location.DECK) {
					this.getField().getDeck().getDeck().remove((MonsterCard)monster);
				}	
				if (monster.getLocation() == Location.HAND) {
					this.getField().getHand().remove((MonsterCard)monster);
				}
				monster.setHidden(false);
				this.getField().addMonsterToField(monster, Mode.DEFENSE, true);
				this.setAbleToAdd(false);
				this.setSaveLastMonster(monster);
				return true;
			} else if (this.getField().getMonstersArea().size() >=5) {
				throw new NoMonsterSpaceException();
			}
			}
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		if (this.isAbleToAdd() == false) {
			throw new MultipleMonsterAdditionException();
		}
			return false;
	}
	
	
	
	public boolean setMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices) {
		if (this.getSaveLastMonster() == monster) {
			this.setAbleToAdd(true);
		}
		if ((this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && this.isAbleToAdd() == true && Card.getBoard().getActivePlayer() == this &&Card.getBoard().GameOver() == false && monster.getLocation() == Location.HAND) {
			if (sacrifices == null && monster.getLevel() <=4) {
				setMonster(monster);
				this.setSaveLastMonster(monster);
			} else if (monster.getLevel() >= 7 && this.getField().getMonstersArea().size() > 1 && sacrifices.size() == 2) {
				if (monster.getLocation() == Location.DECK) {
					this.getField().getDeck().getDeck().remove((MonsterCard)monster);
				}
				if (monster.getLocation() == Location.HAND) {
					this.getField().getHand().remove((MonsterCard)monster);
				}

					monster.setHidden(false);
					this.getField().addMonsterToField(monster, Mode.DEFENSE, sacrifices);
					this.setAbleToAdd(false);
					this.setSaveLastMonster(monster);
					return true;
				} else if (monster.getLevel() < 7 && monster.getLevel() > 4 && this.getField().getMonstersArea().size() > 0 && sacrifices.size() == 1){
					
					if (monster.getLocation() == Location.DECK) {
						this.getField().getDeck().getDeck().remove((MonsterCard)monster);
					}
					if (monster.getLocation() == Location.HAND) {
						this.getField().getHand().remove((MonsterCard)monster);
					}
					
		
						monster.setHidden(false);
						this.getField().addMonsterToField(monster, Mode.DEFENSE, sacrifices);
						this.setAbleToAdd(false);
						this.setSaveLastMonster(monster);
					return true;
					
				
			} else if (this.getField().getMonstersArea().size() >=5) {
				throw new NoMonsterSpaceException();
			}
			}
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		if (this.isAbleToAdd() == false) {
			throw new MultipleMonsterAdditionException();
		}
		
			return false;
	}
	
	
	
	public boolean setSpell(SpellCard spell) {
		
		if ((this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && Card.getBoard().getActivePlayer() == this &&Card.getBoard().GameOver() == false && spell.getLocation() == Location.HAND) {
			if (this.getField().getSpellArea().size() < 5) {
				if (spell.getLocation() == Location.DECK) {
					this.getField().getDeck().getDeck().remove((SpellCard)spell);
				}
				if (spell.getLocation() == Location.HAND) {
					this.getField().getHand().remove((SpellCard)spell);
				}
				this.getField().addSpellToField(spell, null, true);
				return true;
			} else if (this.getField().getSpellArea().size() >=5) {
				throw new NoSpellSpaceException();
			} 
		}
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		return false;
	}
	
	public boolean activateSpell(SpellCard spell, MonsterCard monster) {
		if (spell instanceof CardDestruction && (this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && Card.getBoard().getActivePlayer() == this && (spell.getLocation() == Location.HAND || Card.getBoard().getActivePlayer().getField().getSpellArea().contains(spell))) {
			if (this.getField().getSpellArea().contains(spell)) {
				this.getField().activateSetSpell(spell, monster);
				return true;
			
			} else if(Card.getBoard().getActivePlayer().getField().getSpellArea().size() < 5){
				this.getField().getHand().remove((SpellCard)spell);
				this.getField().addSpellToField(spell, monster, false);
				return true;
			}
		
		}else
		if ((this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) && Card.getBoard().getActivePlayer() == this &&Card.getBoard().GameOver() == false && (spell.getLocation() == Location.HAND || Card.getBoard().getActivePlayer().getField().getSpellArea().contains(spell))) {
			if (this.getField().getSpellArea().contains(spell)) {
				this.getField().activateSetSpell(spell, monster);
				return true;
			
			} else if(Card.getBoard().getActivePlayer().getField().getSpellArea().size() < 5){
				this.getField().getHand().remove((SpellCard)spell);
				this.getField().addSpellToField(spell, monster, false);
				return true;
			} else if (this.getField().getSpellArea().size() >=5) {
				throw new NoSpellSpaceException();
			}
		}
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		return false;
	}
	
	
	public boolean declareAttack(MonsterCard activeMonster, MonsterCard opponentMonster) {
		if (this.getField().getPhase() == Phase.BATTLE && !Card.getBoard().getActivePlayer().getPreformAttackMonsters().contains(activeMonster) && Card.getBoard().getActivePlayer().getField().getMonstersArea().contains(activeMonster) && activeMonster.getMode() == Mode.ATTACK && Card.getBoard().getActivePlayer() == this  &&Card.getBoard().GameOver() == false) {
			activeMonster.action(opponentMonster);
			return true;
		}
		
		if (this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) {
			throw new WrongPhaseException();
		}
		if (this.getPreformAttackMonsters().contains(activeMonster)) {
			throw new MonsterMultipleAttackException();
		}
		if (activeMonster.getMode() == Mode.DEFENSE) {
			throw new DefenseMonsterAttackException();
		}
		
		return false;
	}
	
	public boolean declareAttack(MonsterCard activeMonster) {
		if (this.getField().getPhase() == Phase.BATTLE && !Card.getBoard().getActivePlayer().getPreformAttackMonsters().contains(activeMonster) && Card.getBoard().getActivePlayer().getField().getMonstersArea().contains(activeMonster) && activeMonster.getMode() == Mode.ATTACK && Card.getBoard().getActivePlayer() == this &&Card.getBoard().GameOver() == false) {
			if (Card.getBoard().getOpponentPlayer().getField().getMonstersArea().size() == 0) {
			activeMonster.action();
			return true;
			}	
		}
		if (this.getField().getPhase() == Phase.MAIN1 || this.getField().getPhase() == Phase.MAIN2) {
			throw new WrongPhaseException();
		}
		if (this.getPreformAttackMonsters().contains(activeMonster)) {
			throw new MonsterMultipleAttackException();
		}
		if (activeMonster.getMode() == Mode.DEFENSE) {
			throw new DefenseMonsterAttackException();
		}
		return false;
	}
	
	public void addCardToHand() {
		Card.getBoard().GameOver();
		if (Card.getBoard().GameOver() == false) {
		this.getField().addCardToHand();
		}
		Card.getBoard().GameOver();
	}
	
	public void addNCardsToHand(int n) {
		Card.getBoard().GameOver();
		if (n > this.getField().getDeck().getDeck().size()) {
			this.getField().getDeck().getDeck().clear();
			Card.getBoard().GameOver();
		}else
		if (Card.getBoard().GameOver() == false) {
		this.getField().addNCardsToHand(n);
		}
		Card.getBoard().GameOver();
	}
	
	public void endPhase() {
		Card.getBoard().GameOver();
		if (Card.getBoard().GameOver() == false) {
		if (this.getField().getPhase() == Phase.MAIN1) {
			this.getField().setPhase(Phase.BATTLE);
		} else if (this.getField().getPhase() == Phase.BATTLE) {
			this.getField().setPhase(Phase.MAIN2);
			this.setAbleToSwitchMode(true);
		} else if (this.getField().getPhase() == Phase.MAIN2) {
			endTurn();
		} 
		}
	}
	
	public boolean endTurn() {
		Card.getBoard().GameOver();
		if (this == Card.getBoard().getActivePlayer()  &&Card.getBoard().GameOver() == false) {
		Card.getBoard().nextPlayer();
		this.getField().setPhase(Phase.MAIN1);
		this.setAbleToAdd(true);
		this.setAbleToSwitchMode(true);
		this.setSaveLastMonster(null);
		this.setModeSwitchedMonsters(null);
		this.preformAttackMonsters.clear();
		return true;
		}
		return false;
	}
	
	public boolean switchMonsterMode(MonsterCard monster) {
		Card.getBoard().GameOver();
		
		if ((monster.getLocation() == Location.FIELD) && this.isAbleToSwitchMode() == true  &&Card.getBoard().GameOver() == false && Card.getBoard().getActivePlayer().getField().getPhase() != Phase.BATTLE) {
			if (monster.getMode() == Mode.ATTACK) {
				monster.setMode(Mode.DEFENSE);
			} else {
				monster.setMode(Mode.ATTACK);
			}
			this.setAbleToSwitchMode(false);
			return true;
		}
		if (this.getField().getPhase() == Phase.BATTLE) {
			throw new WrongPhaseException();
		}
		return false;
	}
	
	
	
	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public String getName() {
		return name;
	}

	public Field getField() {
		return field;
	}

	public boolean isAbleToAdd() {
		return ableToAdd;
	}

	public void setAbleToAdd(boolean ableToAdd) {
		this.ableToAdd = ableToAdd;
	}

	public Player getTurnPlayer() {
		return turnPlayer;
	}

	public void setTurnPlayer(Player turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	public boolean isAbleToSwitchMode() {
		return ableToSwitchMode;
	}

	public void setAbleToSwitchMode(boolean ableToSwitchMode) {
		this.ableToSwitchMode = ableToSwitchMode;
	}

	public MonsterCard getSaveLastMonster() {
		return saveLastMonster;
	}

	public void setSaveLastMonster(MonsterCard saveLastMonster) {
		this.saveLastMonster = saveLastMonster;
	}

	public ArrayList<MonsterCard> getModeSwitchedMonsters() {
		return ModeSwitchedMonsters;
	}

	public void setModeSwitchedMonsters(ArrayList<MonsterCard> modeSwitchedMonsters) {
		ModeSwitchedMonsters = modeSwitchedMonsters;
	}

	public ArrayList<MonsterCard> getPreformAttackMonsters() {
		return preformAttackMonsters;
	}

	public void setPreformAttackMonsters(ArrayList<MonsterCard> preformAttackMonsters) {
		this.preformAttackMonsters = preformAttackMonsters;
	}

}
