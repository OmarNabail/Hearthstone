package model.cards.spells;

import model.heroes.*;
import model.cards.Rarity;
import java.util.*;
import model.cards.minions.*;

public class TwistingNether extends Spell implements AOESpell {

	public TwistingNether() {
		super("Twisting Nether", 8, Rarity.EPIC);

	}

	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		int i = 0;
		while (i < oppField.size()) {
			Minion m = oppField.get(i);
			oppField.get(i).setCurrentHP(0);

			if (m.getCurrentHP() <= 0) {
				oppField.remove(m);
				i--;
			}

			i++;
		}

		int j = 0;
		while (j < curField.size()) {
			Minion m = null;
			
			if (!curField.isEmpty())
			{
				m = curField.get(j);

			curField.get(j).setCurrentHP(0);

			if (m.getCurrentHP() <= 0) {
				curField.remove(m);
				j--;
			}

			j++;

			}
		}
	}

}
