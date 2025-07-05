package model.cards.minions;

import model.cards.Card;
import model.heroes.*;
import exceptions.*;
import model.cards.Rarity;

public class Minion extends Card implements Cloneable {
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine;
	private boolean sleeping;
	private boolean attacked;
	private MinionListener listener;

	public void setListener(MinionListener listener) {
		this.listener = listener;
	}

	public Minion(String name, int manaCost, Rarity rarity, int attack, int maxHP, boolean taunt, boolean divine,
			boolean charge) {
		super(name, manaCost, rarity);
		setAttack(attack);
		this.maxHP = maxHP;
		this.currentHP = maxHP;
		this.taunt = taunt;
		this.divine = divine;
		if (!charge)
			this.sleeping = true;
	}

	public void attack(Minion target) {
		if ((target.isDivine() == true) && ((this.isDivine() == true))) {
			if (this.getAttack() != 0)
				target.setDivine(false);
			if (target.getAttack() != 0)
				this.setDivine(false);

		}
		if ((target.isDivine() == false) && ((this.isDivine() == false))) {
			target.setCurrentHP(target.getCurrentHP() - this.attack);
			this.setCurrentHP(this.getCurrentHP() - target.attack);
		}
		if ((target.isDivine() == true) && ((this.isDivine() == false))) {
			this.setCurrentHP(this.getCurrentHP() - target.attack);
			if (this.getAttack() != 0)
				target.setDivine(false);
		}
		if ((target.isDivine() == false) && ((this.isDivine() == true))) {
			target.setCurrentHP(target.getCurrentHP() - this.attack);
			if (target.getAttack() != 0)
				this.setDivine(false);
		}
	}

	public void attack(Hero target) throws InvalidTargetException {
		target.setCurrentHP(target.getCurrentHP() - this.attack);
		if (this.getName() == "Icehowl") {
			throw new InvalidTargetException();
		}
		setAttacked(true);
	}

	public Minion clone() throws CloneNotSupportedException {
		Minion clo = new Minion(this.getName(), this.getManaCost(), this.getRarity(), this.attack, this.maxHP,
				this.taunt, this.divine, !this.isSleeping());
		return (Minion) (clo);
	}

	public boolean isTaunt() {
		return taunt;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHp) {
		this.maxHP = maxHp;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
		if (this.currentHP > maxHP)
			this.currentHP = maxHP;
		else if (this.currentHP <= 0) {
			this.currentHP = 0;

		}
		if (this.currentHP <= 0)
			listener.onMinionDeath(this);

	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
		if (this.attack <= 0)
			this.attack = 0;
	}

	public void setTaunt(boolean isTaunt) {
		this.taunt = isTaunt;
	}

	public void setDivine(boolean divine) {
		this.divine = divine;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isDivine() {
		return divine;
	}

}
