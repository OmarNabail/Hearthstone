package model.heroes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.KillCommand;
import model.cards.spells.MultiShot;
import exceptions.*
;public class Hunter extends Hero {

	public Hunter() throws IOException, CloneNotSupportedException {
		super("Rexxar");
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> neutrals= getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"),15);
		getDeck().addAll(neutrals);
		for(int i = 0 ; i < 2; i++)
		{
			getDeck().add(new KillCommand());
			getDeck().add(new MultiShot());
			
		}
		Minion krush=(new Minion("King Krush", 9, Rarity.LEGENDARY, 8, 8, false, false, true));
		
		getDeck().add(krush);
		Collections.shuffle(getDeck());
	}
	public void useHeroPower(Hero opponent) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		opponent.setCurrentHP(opponent.getCurrentHP()-2);
	}
	
}
