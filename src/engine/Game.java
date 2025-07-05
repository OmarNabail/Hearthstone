package engine;

import engine.GameListener;

import model.heroes.*;
import model.heroes.Hero;

import java.util.ArrayList;

import exceptions.*;
import model.cards.Card;
import model.cards.minions.*;

public class Game implements ActionValidator, HeroListener {
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	private GameListener listener;

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public Game(Hero p1, Hero p2) throws FullHandException, CloneNotSupportedException {
		firstHero = p1;
		secondHero = p2;

		int coin = (int) (Math.random() * 2);
		currentHero = coin == 0 ? firstHero : secondHero;
		opponent = currentHero == firstHero ? secondHero : firstHero;
		currentHero.setCurrentManaCrystals(1);
		currentHero.setTotalManaCrystals(1);

		currentHero.setValidator(this);
		opponent.setValidator(this);

		currentHero.setListener(this);
		opponent.setListener(this);

		for (int i = 0; i < 3; i++)
			currentHero.drawCard();
		for (int i = 0; i < 4; i++)
			opponent.drawCard();

	}

	public void onGameOver() {

	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public Hero getOpponent() {
		return opponent;
	}

	public void validateTurn(Hero user) throws NotYourTurnException {
		if (user == opponent) {
			throw new NotYourTurnException("Please wait for your turn" + user.getName());
		}
	}

	public ArrayList<Minion> helperIsTaunt(Hero ayhaga) {
		int i = 0;
		ArrayList<Minion> tauntMinions = new ArrayList<Minion>();
		while (i < ayhaga.getField().size()) {

			if ((ayhaga.getField().get(i)).isTaunt() == true) {
				tauntMinions.add(ayhaga.getField().get(i));
			}
			i++;
		}
		return tauntMinions;
	}

	public void validateAttack(Minion attacker, Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if ((attacker.isSleeping() == true) || (attacker.isAttacked() == true) || attacker.getAttack() == 0) {
			throw new CannotAttackException(attacker.getName() + " minion cannot attack at the present moment");

		}
		if (currentHero.getField().contains(attacker) == false) {
			throw new NotSummonedException(attacker.getName() + " is not summoned");
		}

		if ((helperIsTaunt(opponent).size() != 0) && (helperIsTaunt(opponent).contains(target) == false)) {
			throw new TauntBypassException("Cannot attack non-taunt minions while taunt minions exist");
		}
		if (currentHero.getField().contains(target)) {
			throw new InvalidTargetException("Cannot attack friendly minons");
		}
		if(!opponent.getField().contains(target))
			throw new NotSummonedException();
	}

	public void validateAttack(Minion attacker, Hero target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if ((attacker.isSleeping() == true) || (attacker.isAttacked() == true)|| attacker.getAttack() == 0) {
			throw new CannotAttackException(attacker.getName() + " minion cannot attack at the present moment");

		}
		if (currentHero.getField().contains(attacker) == false) {
			throw new NotSummonedException(attacker.getName() + " is not summoned");
		}

		for (int j = 0; j < opponent.getField().size(); j++) {
			if (helperIsTaunt(opponent).size() != 0) {
				throw new TauntBypassException("Cannot attack hero due to taunt minions");
			}
		}
		if (currentHero == target) {
			throw new InvalidTargetException("Cannot attack your own hero");
		}
		if (attacker.getName() == "Icehowl") {
			throw new InvalidTargetException();
		}

	}

	public void validateManaCost(Card card) throws NotEnoughManaException {
		if (currentHero.getCurrentManaCrystals() < card.getManaCost()) {
			throw new NotEnoughManaException("Not enough mana");
		}
	}

	public void validatePlayingMinion(Minion minion) throws FullFieldException {
		/*
		 * https://hearthstone.gamepedia.com/Minion maximum number of minions is 7 and
		 * my source is the link above
		 */
		if (currentHero.getField().size() >= 7) {
			throw new FullFieldException("maximum number of minions allowed is 7");
		}
	}

	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException {

		if (hero.isHeroPowerUsed() == true) {
			throw new HeroPowerAlreadyUsedException("Hero power already used this turn");
		}
		/*
		 * https://hearthstone.gamepedia.com/Hero_Power cost of using hero power is 2
		 * and my source is the link above
		 */
		if (hero.getCurrentManaCrystals() < 2) {

			throw new NotEnoughManaException("Not enough mana to use the hero power");
		}
	}

	public void onHeroDeath() {
		if ((currentHero.getCurrentHP() == 0) || (opponent.getCurrentHP() == 0)) {
			listener.onGameOver();
		}
		listener.onGameOver();
	}

	public void damageOpponent(int amount) {
		opponent.setCurrentHP(opponent.getCurrentHP() - amount);
	}

	public void switchHeros() {
		Hero tmp = currentHero;
		currentHero = opponent;
		opponent = tmp;

	}

	public void wakeUp(Hero h) {
		int i = 0;
		while (i < h.getField().size()) {
			h.getField().get(i).setAttacked(false);
			h.getField().get(i).setSleeping(false);
			i++;
		}
	}

	public void endTurn() throws FullHandException, CloneNotSupportedException {
		switchHeros();
		currentHero.setTotalManaCrystals(currentHero.getTotalManaCrystals() + 1);
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		currentHero.setHeroPowerUsed(false);
		wakeUp(currentHero);
		currentHero.drawCard();

	}

}
