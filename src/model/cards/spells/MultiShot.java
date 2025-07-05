package model.cards.spells;

import model.cards.Rarity;
import java.util.*;
import model.cards.minions.*;

public class MultiShot extends Spell implements AOESpell {

	public MultiShot() {
		super("Multi-Shot", 4, Rarity.BASIC);

	}

	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {

		Minion m1 = null;
		Minion m2 = null;
		if (oppField.size() > 2) {
			int coin1 = 0;
			int coin2 = 0;
			
			while (coin1 == coin2) {
				coin1 = (int) (Math.random() * oppField.size() - 1);
				coin2 = (int) (Math.random() * oppField.size() - 1);
			}
			m1 = oppField.get(coin1);
			m2 = oppField.get(coin2);
			if (!m1.isDivine())
				m1.setCurrentHP(m1.getCurrentHP() - 3);
			else
				m1.setDivine(false);
			
			if (!m2.isDivine())
				m2.setCurrentHP(m2.getCurrentHP() - 3);
			m2.setDivine(false);
		}

		if (oppField.size() == 2) {
			m1 = oppField.get(0);
			m2 = oppField.get(1);
			if (!m1.isDivine())
				m1.setCurrentHP(m1.getCurrentHP() - 3);
			else
				m1.setDivine(false);

			if (!oppField.get(1).isDivine())
				oppField.get(1).setCurrentHP(oppField.get(1).getCurrentHP() - 3);
			else
				oppField.get(1).setDivine(false);
		}
		if (oppField.size() == 1) {
			m1 = oppField.get(0);
			if (!m1.isDivine())
				m1.setCurrentHP(m1.getCurrentHP() - 3);
			else
				m1.setDivine(false);
		}
		
		if(m1 != null && m1.getCurrentHP() <= 0)
			oppField.remove(m1);
		if(m2 != null && m2.getCurrentHP() <= 0)
			oppField.remove(m2);
			
	}

}
