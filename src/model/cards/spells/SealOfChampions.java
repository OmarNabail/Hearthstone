package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.*;
import exceptions.InvalidTargetException;

public class SealOfChampions extends Spell implements MinionTargetSpell {

	public SealOfChampions() {
		super("Seal of Champions", 3, Rarity.COMMON);
		
	}
	public void performAction(Minion m) throws InvalidTargetException{
	m.setAttack(m.getAttack()+3);
	m.setDivine(true);
	}

	
}
