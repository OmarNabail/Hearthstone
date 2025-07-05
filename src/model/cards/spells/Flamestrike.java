package model.cards.spells;

import model.cards.Rarity;
import java.util.*;
import model.cards.minions.*;

public class Flamestrike extends Spell implements AOESpell {

	public Flamestrike() {
		super("Flamestrike", 7, Rarity.BASIC);
	}

	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {

		int i = 0;
		Minion m = null;
		while (i < oppField.size()) {
			m = oppField.get(i);
			if(!oppField.get(i).isDivine())
			oppField.get(i).setCurrentHP((oppField.get(i).getCurrentHP()) - 4);
			else
				oppField.get(i).setDivine(false);
			
			if(m.getCurrentHP() <= 0)
			{
				oppField.remove(m);
				i--;
			}
			
			i++;
		}
	}

}
