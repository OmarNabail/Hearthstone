package model.heroes;

import java.io.BufferedReader;
import exceptions.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import engine.ActionValidator;
import engine.GameListener;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.*;
import model.cards.spells.*;

public abstract class Hero implements MinionListener {
	private String name;
	private int currentHP;
	private boolean heroPowerUsed;
	private int totalManaCrystals;
	private int currentManaCrystals;
	private ArrayList<Card> deck;
	private ArrayList<Minion> field;
	private ArrayList<Card> hand;
	private int fatigueDamage = 1;
	@SuppressWarnings("unused")
	private HeroListener listener;
	private ActionValidator validator;

	public Hero(String name) throws IOException, CloneNotSupportedException {
		this.name = name;
		currentHP = 30;
		deck = new ArrayList<Card>();
		field = new ArrayList<Minion>();
		hand = new ArrayList<Card>();
		buildDeck();
		for (Card m : getDeck())
			if (m instanceof Minion)
				((Minion) m).setListener(this);
	}

	public void useHeroPower() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
			FullHandException, FullFieldException, CloneNotSupportedException {
		validator.validateTurn(this);
		validator.validateUsingHeroPower(this);
		setCurrentManaCrystals(getCurrentManaCrystals() - 2);
		setHeroPowerUsed(true);
	}

	public void playMinion(Minion m) throws NotYourTurnException, NotEnoughManaException, FullFieldException {
		validator.validateTurn(this);
		validator.validatePlayingMinion(m);
		validator.validateManaCost(m);
		setCurrentManaCrystals(getCurrentManaCrystals() - m.getManaCost());
		getField().add(m);

	}

	public void attackWithMinion(Minion attacker, Minion target) throws CannotAttackException, NotYourTurnException,
			TauntBypassException, InvalidTargetException, NotSummonedException {
		validator.validateTurn(this);
		validator.validateAttack(attacker, target);
		attacker.attack(target);
	}

	public void attackWithMinion(Minion attacker, Hero target) throws CannotAttackException, NotYourTurnException,
			TauntBypassException, NotSummonedException, InvalidTargetException {
		validator.validateTurn(this);
		validator.validateAttack(attacker, target);
		attacker.attack(target);
	}

	void DeduceMana(Card c) throws NotEnoughManaException {
		validator.validateManaCost(c);
		for (Minion m : getField())
			if (m.getName() == "Kalycgos") {
				c.setManaCost(c.getManaCost() - 4);
			}
		setCurrentManaCrystals(getCurrentManaCrystals() - c.getManaCost());
	}

	public void castSpell(FieldSpell s) throws NotYourTurnException, NotEnoughManaException {
		validator.validateTurn(this);
		DeduceMana((Card) s);
		s.performAction(this.getField());
		this.getHand().remove(s);

	}

	public void castSpell(AOESpell s, ArrayList<Minion> oppField) throws NotYourTurnException, NotEnoughManaException {
		validator.validateTurn(this);
		DeduceMana((Card) s);
		s.performAction(oppField, this.getField());
		this.getHand().remove(s);
	}

	public void castSpell(MinionTargetSpell s, Minion m)
			throws NotYourTurnException, NotEnoughManaException, InvalidTargetException {
		validator.validateTurn(this);
		DeduceMana((Card) s);
		s.performAction(m);
		this.getHand().remove(s);
	}

	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException, NotEnoughManaException {
		validator.validateTurn(this);
		DeduceMana((Card) s);
		s.performAction(h);
		this.getHand().remove(s);
	}

	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException, NotEnoughManaException {
		validator.validateTurn(this);
		DeduceMana((Card) s);
		setCurrentHP(getCurrentHP() + s.performAction(m));
		this.getHand().remove(s);
	}

	public void endTurn() throws FullHandException, CloneNotSupportedException {
		listener.endTurn();
	}

	public Card drawCard() throws FullHandException, CloneNotSupportedException {

		for (Minion m : getField())
			if (this.getDeck().get(0) instanceof Minion)
				if (m.getName() == "Wilfred Fizzlebang")
					this.getDeck().get(0).setManaCost(0);

		if (this.getDeck().size() == 0) {
			this.setCurrentHP(this.getCurrentHP() - fatigueDamage++);
		} else {
			Card c = this.getDeck().remove(0);
			
			if (getHand().size() >= 10)
				System.out.println("FullHandException");
			else {
				this.getHand().add(c);
				if (getHand().size() >= 10)
					throw new FullHandException(c.clone());

				for (Minion m : getField())
					if (m.getName() == "Chromaggus")
						this.getHand().add(c.clone());

			}

		}
		return null;
	}

	public abstract void buildDeck() throws IOException, CloneNotSupportedException;

	public static final ArrayList<Minion> getAllNeutralMinions(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		ArrayList<Minion> minions = new ArrayList<Minion>();
		String current = br.readLine();
		while (current != null) {
			String[] line = current.split(",");
			Minion minion = null;
			String n = line[0];
			int m = Integer.parseInt(line[1]);
			Rarity r = null;
			switch ((line[2])) {
			case "b":
				r = Rarity.BASIC;
				break;
			case "c":
				r = Rarity.COMMON;
				break;
			case "r":
				r = Rarity.RARE;
				break;
			case "e":
				r = Rarity.EPIC;
				break;
			case "l":
				r = Rarity.LEGENDARY;
				break;
			}
			int a = Integer.parseInt(line[3]);
			int p = Integer.parseInt(line[4]);
			boolean t = line[5].equals("TRUE") ? true : false;
			boolean d = line[6].equals("TRUE") ? true : false;
			boolean c = line[7].equals("TRUE") ? true : false;
			if (!n.equals("Icehowl"))
				minion = new Minion(n, m, r, a, p, t, d, c);
			else
				minion = new Icehowl();
			minions.add(minion);
			current = br.readLine();
		}
		br.close();
		return minions;
	}

	public void onMinionDeath(Minion m) {
		if (m != null)
			getField().remove(m);
	}

	public static final ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions, int count)
			throws CloneNotSupportedException {
		ArrayList<Minion> res = new ArrayList<Minion>();
		int i = 0;
		while (i < count) {

			int index = (int) (Math.random() * minions.size());
			Minion minion = minions.get(index).clone();
			int occ = 0;
			for (int j = 0; j < res.size(); j++) {
				if (res.get(j).getName().equals(minion.getName()))
					occ++;
			}
			if (occ == 0) {
				res.add(minion);
				i++;
			} else if (occ == 1 && minion.getRarity() != Rarity.LEGENDARY) {
				res.add(minion);
				i++;
			}
		}
		return res;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int hp) {
		this.currentHP = hp;
		if (this.currentHP > 30)
			this.currentHP = 30;
		else if (this.currentHP <= 0) {
			this.currentHP = 0;
			listener.onHeroDeath();
		}
	}

	public int getTotalManaCrystals() {
		return totalManaCrystals;
	}

	public void setTotalManaCrystals(int totalManaCrystals) {
		this.totalManaCrystals = totalManaCrystals;
		if (this.totalManaCrystals > 10)
			this.totalManaCrystals = 10;
	}

	public int getCurrentManaCrystals() {
		return currentManaCrystals;
	}

	public void setCurrentManaCrystals(int currentManaCrystals) {
		this.currentManaCrystals = currentManaCrystals;
		if (this.currentManaCrystals > 10)
			this.currentManaCrystals = 10;
	}

	public ArrayList<Minion> getField() {
		return field;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setHeroPowerUsed(boolean powerUsed) {
		this.heroPowerUsed = powerUsed;
	}

	public void setListener(HeroListener listener) {
		this.listener = listener;
	}

	public HeroListener getListener() {
		return listener;
	}

	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}

	public String getName() {
		return name;
	}
}
