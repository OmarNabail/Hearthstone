package engine;

import model.heroes.*;  
import exceptions.FullHandException;
import java.io.IOException;  

public class Main {
    public static void main(String[] args) {
        try {
            Hero player1 = new Hunter();  
            Hero player2 = new Mage();    

            Game game = new Game(player1, player2);

            game.setListener(() -> System.out.println("Game Over!"));

            System.out.println("Game started! Current hero: " + game.getCurrentHero().getName());
            game.endTurn(); 
            System.out.println("Turn ended. New hero: " + game.getCurrentHero().getName());

        } catch (FullHandException | CloneNotSupportedException | IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}