package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.*;
import exceptions.InvalidTargetException;

public class DivineSpirit extends Spell implements MinionTargetSpell {

	public DivineSpirit() {
		super("Divine Spirit", 3, Rarity.BASIC);
		
	}
	public void performAction(Minion m) throws InvalidTargetException{
	    m.setMaxHP(m.getMaxHP()*2);
		m.setCurrentHP(m.getCurrentHP()*2);
	}
	

}
