package model.cards.spells;

import model.cards.Rarity;
import model.heroes.*;
import model.cards.minions.*;
import exceptions.InvalidTargetException;
public class KillCommand extends Spell implements MinionTargetSpell, HeroTargetSpell {

	public KillCommand() {
		super("Kill Command", 3, Rarity.COMMON);
		
	}
	public void performAction(Hero h) {
		h.setCurrentHP(h.getCurrentHP()-3);
	}
	public void performAction(Minion m) throws InvalidTargetException{
		m.setCurrentHP(m.getCurrentHP()-5);
		
	}

}
