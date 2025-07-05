package model.cards.spells;
import model.cards.minions.*;
import java.util.*;

public interface AOESpell {

	public void performAction(ArrayList<Minion> oppField,ArrayList<Minion> curField);

}
