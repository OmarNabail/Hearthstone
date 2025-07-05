package model.cards.spells;

import model.cards.Rarity;
import model.heroes.*;
import model.cards.minions.*;
import exceptions.InvalidTargetException;
public class Pyroblast extends Spell implements HeroTargetSpell, MinionTargetSpell {
	public Pyroblast()
	{
		super("Pyroblast", 10, Rarity.EPIC);
	}
	public void performAction(Hero h) {
		h.setCurrentHP(h.getCurrentHP()-10);
	}
	public void performAction(Minion m) throws InvalidTargetException{
		if(!m.isDivine())
		m.setCurrentHP(m.getCurrentHP()-10);
		else
			m.setDivine(false);
	}

	
}
