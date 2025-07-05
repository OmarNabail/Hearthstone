package model.cards.spells;

import model.cards.Rarity;
import java.util.*;
import model.cards.minions.*;

public class LevelUp extends Spell implements FieldSpell {

	public LevelUp() {
		super("Level Up!", 6, Rarity.EPIC);

	}

	public void performAction(ArrayList<Minion> field) {
		int i = 0;
		while (i < field.size()) {
			if (field.get(i).getName() == "Silver Hand Recruit") {
				field.get(i).setAttack(field.get(i).getAttack() + 1);
				field.get(i).setMaxHP(field.get(i).getMaxHP() + 1);
				field.get(i).setCurrentHP(field.get(i).getCurrentHP() + 1);

			}
			i++;
		}
	}

}
