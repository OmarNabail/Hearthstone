package model.cards.spells;

import model.heroes.*;

import model.cards.Rarity;
import java.util.*;
import model.cards.minions.*;

public class HolyNova extends Spell implements AOESpell {

	public HolyNova() {
		super("Holy Nova", 5, Rarity.BASIC);

	}

	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		int i = 0;
		while (i < oppField.size()) {
			if (!oppField.get(i).isDivine()) {
				Minion m = oppField.get(i);
				m.setCurrentHP((oppField.get(i).getCurrentHP()) - 2);

				if (m.getCurrentHP() <= 0) {
					oppField.remove(m);
					i--;
				}
			} else
				oppField.get(i).setDivine(false);
			i++;
		}
		
		int j = 0;
		while (j < curField.size()) {
			curField.get(j).setCurrentHP((curField.get(j).getCurrentHP()) + 2);
			j++;
		}
	}

}
