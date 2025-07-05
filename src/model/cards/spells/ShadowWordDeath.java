package model.cards.spells;
import model.heroes.*;
import model.cards.Rarity;
import model.cards.minions.*;
import exceptions.InvalidTargetException;
public class ShadowWordDeath extends Spell implements MinionTargetSpell {

	public ShadowWordDeath() {
		super("Shadow Word: Death", 3, Rarity.BASIC);
		
	}
	public void performAction(Minion m) throws InvalidTargetException{
		if(m.getAttack()>=5) {
			m.setCurrentHP(0);
		}
	}
}
