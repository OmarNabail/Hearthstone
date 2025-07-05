package model.heroes;

import model.cards.spells.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.Flamestrike;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import exceptions.*;

public class Mage extends Hero {

	public Mage() throws IOException, CloneNotSupportedException {
		super("Jaina Proudmoore");
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> neutrals = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13);
		getDeck().addAll(neutrals);
		for (int i = 0; i < 2; i++) {
			getDeck().add(new Polymorph());
			getDeck().add(new Flamestrike());
			getDeck().add(new Pyroblast());
		}
		Minion kalycgos = (new Minion("Kalycgos", 10, Rarity.LEGENDARY, 4, 12, false, false, false));
		;
		getDeck().add(kalycgos);
		Collections.shuffle(getDeck());

	}

	public void useHeroPower(Minion m) throws NotEnoughManaException, HeroPowerAlreadyUsedException,
			NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		if(!m.isDivine())
		m.setCurrentHP(m.getCurrentHP() - 1);
		else
			m.setDivine(false);
	}

	public void useHeroPower(Hero h) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
			FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		h.setCurrentHP(h.getCurrentHP() - 1);

	}

}
