package model.cards.spells;
import model.cards.minions.*;
import model.cards.Rarity;

public class SiphonSoul extends Spell implements LeechingSpell {

	public SiphonSoul() {
		super("Siphon Soul", 6, Rarity.RARE);
		
	}
	public int performAction(Minion m) {
		m.setCurrentHP(0);
		return 3;
	}
}
