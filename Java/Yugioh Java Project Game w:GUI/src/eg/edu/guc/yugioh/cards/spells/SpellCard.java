package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public abstract class SpellCard extends Card {

	public SpellCard(String name, String desc) {
		
		super(name, desc);
		
	}
	
	public  abstract void action(MonsterCard m);

}
