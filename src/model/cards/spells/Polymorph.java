package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.*;
import exceptions.InvalidTargetException;
public class Polymorph extends Spell implements MinionTargetSpell {

	public Polymorph() {
		super("Polymorph", 4, Rarity.BASIC);
	}
	public void performAction(Minion m) throws InvalidTargetException{
		m.setAttack(1);
		m.setMaxHP(1);
		m.setCurrentHP(1);
		m.setName("Sheep");
		m.setDivine(false);
		m.setTaunt(false);
		m.setSleeping(true);
		m.setManaCost(1);
		
	}

}
