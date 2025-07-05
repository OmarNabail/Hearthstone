package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.*;
import java.util.*;

public class CurseOfWeakness extends Spell implements AOESpell {

	public CurseOfWeakness() {
		super("Curse of Weakness", 2, Rarity.RARE);
		
	}
 public void performAction(ArrayList<Minion> oppField,ArrayList<Minion> curField){
	int i=0;
	 while(i<oppField.size()) {
		 oppField.get(i).setAttack((oppField.get(i).getAttack())-2);
		 i++;
	 }
	 
	 
 }


	
}
