import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import entity.Entity;
import entity.character.Human;
import entity.opponent.Opponent;
import enums.AttackType;
import tools.Weapon;

public class TBGame {
    // Turn order queue to keep track of the order of turns
    public Deque<Entity> turnOrderDeque;
    // List of characters and opponents
    public List<Human<Weapon>> characters;
    public List<Opponent> opponents;

    // Initialize the characters list
    private void initializeCharacters() {
        this.characters = new ArrayList<>();
    }

    // Initialize the opponents list randomly
    private void initializeOpponents() {
        this.opponents = new ArrayList<>();
        int numberOfOpponents = new Random().nextInt(4) + 1;

        for (int i = 0; i < numberOfOpponents; i++)
            opponents.add(new Opponent(i + 1));
        
    }

    // Initialize the turn order queue by sorting the characters and opponents by speed
    private void initializeTurnOrder() {
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(characters);
        allEntities.addAll(opponents);
        allEntities.sort(Comparator.comparing(Entity::getSpeed));

        this.turnOrderDeque = new ArrayDeque<>(allEntities);
    }

    @SuppressWarnings("unchecked") // Suppress the warning for casting to Human<Weapon>
    private void printTurnOrder() {
        System.out.print("*** Turn Order: ");
        for (Entity entity : turnOrderDeque) {
            if (entity instanceof Human) System.out.print(" " + ((Human<Weapon>)entity).name + ", ");
            if (entity instanceof Opponent) System.out.print(" Opponent " + ((Opponent)entity).opponentID + ",");
        } System.out.print("***" + "\n");
    }

    @SuppressWarnings({ "unchecked", "unused" }) // Suppress the warning for casting to Human<Weapon>
    public void startBattle() {
        System.out.println("The battle starts!" + "\n");
        printTurnOrder();

        int currentMoveCounter = 1;

        while (!characters.isEmpty() || !opponents.isEmpty()) {
            Entity currentEntity = turnOrderDeque.poll();

            // If character has no turns left, skip it
            if (currentEntity.numberOfTurns <= 0) {
                currentEntity.numberOfTurns += 1;
                turnOrderDeque.add(currentEntity);
                continue;
            }else if(currentEntity == null) {
            	currentEntity.numberOfTurns=0;
            }

            if (currentEntity instanceof Opponent) {
                Opponent currentOpponent = (Opponent)currentEntity;
                Human<Weapon> attackedHuman = characters.get(new Random().nextInt(characters.size()));
                int choice = 1 + new Random().nextInt(3);
                int damage = 0;
                switch (choice) {
                    case 1:
                        damage = attackedHuman.points;
                        currentOpponent.attack(attackedHuman);
                        damage -= attackedHuman.points;
                        System.out.println("Move " + currentMoveCounter + " - " + "Opponent " + currentOpponent.opponentID + " attacks " + attackedHuman.name + ". " + "Deals " + damage + " damage.");
                        System.out.println(attackedHuman);
                        break;

                    case 2:
                        currentOpponent.guard();
                        System.out.println("Move " + currentMoveCounter + " - " + "Opponent " + currentOpponent.opponentID + " starts guarding.");
                        break;
                    case 3:
                        damage = attackedHuman.points;
                        currentOpponent.special(attackedHuman, opponents, turnOrderDeque);
                        damage -= attackedHuman.points;
                        System.out.println("Move " + currentMoveCounter + " - " + "Opponent " + currentOpponent.opponentID + " uses its special ability on " + attackedHuman.name + ". " + "Deals " + damage + " damage.");
                        System.out.println(attackedHuman);
                        System.out.println(currentOpponent);
                        break;
                }

                if (currentOpponent.points <= 0) {
                    System.out.println("Opponent " + currentOpponent.opponentID + " is dead.");
                    opponents.remove(currentOpponent);
                }

                if (attackedHuman.points <= 0) {
                    System.out.println(attackedHuman.name + " is dead.");
                    characters.remove(attackedHuman);
                }
            }

            if (currentEntity instanceof Human) {
                Human<Weapon> currentCharacter = (Human<Weapon>)currentEntity;
                Menu menu = this.new Menu();
                menu.showMenu(currentMoveCounter, currentCharacter);
                menu.getMenuInput();
                Opponent attackedOpponent;
                if(menu.selectedOption != 4||menu.selectedOption !=5) {
                	attackedOpponent = opponents.stream().filter(opponent -> opponent.opponentID == menu.opponentID).findFirst().get();
                }else {
                	attackedOpponent = null;
                }
                

                switch (menu.selectedOption) {
                    case 1:
                        int damage = currentCharacter.points;
                        currentCharacter.punch(attackedOpponent);
                        damage -= attackedOpponent.points;
                        System.out.println("Move " + currentMoveCounter + " Result: " + currentCharacter.name + " attacks Opponent " + attackedOpponent.opponentID + ". " + "Deals " + damage + " damage.");
                        System.out.println(currentCharacter);
                        System.out.println(attackedOpponent);
                        break;

                    case 2:
                        int damage2 = currentCharacter.points;
                        currentCharacter.attackWithWeapon(attackedOpponent, AttackType.values()[menu.attackType]);
                        damage2 -= attackedOpponent.points;
                        System.out.println("Move " + currentMoveCounter + " - " + currentCharacter.name + " attacks Opponent " + attackedOpponent.opponentID + ". " + "Deals " + damage2 + " damage.");
                        System.out.println(currentCharacter);
                        System.out.println(attackedOpponent);
                        break;

                    case 3:
                        currentCharacter.special(attackedOpponent);
                        System.out.println("Move " + currentMoveCounter + " - " + currentCharacter.name + " uses its special ability.");
                        System.out.println(attackedOpponent);
                        System.out.println(currentCharacter);
                        break;

                    case 4:
                        currentCharacter.guard();
                        System.out.println("Move " + currentMoveCounter + " - " + currentCharacter.name + " starts guarding.");
                        break;

                    case 5:
                        currentCharacter.run();
                        System.out.println("Your character(s) started running away. The battle ends!\n" + "Thanks for playing!");
                        break;
                }
                if (menu.selectedOption == 5) break;

                if (currentCharacter.points <= 0) {
                    System.out.println(currentCharacter.name + " is dead.");
                    characters.remove(currentCharacter);
                }

                if (attackedOpponent.points <= 0) {
                    System.out.println("Opponent " + attackedOpponent.opponentID + " is dead.");
                    opponents.remove(attackedOpponent);
                }
            }

            if (currentEntity.points > 0) {
                currentEntity.numberOfTurns -= 1;
            }

            currentMoveCounter++;
        } // end while

        
    }
    // Show the welcome message
    public void showWelcomer() {
        System.out.println("Welcome to TBGame!" + "\n");
        System.out.println("These opponents appeared in front of you:");
        for (Opponent opponent : opponents) System.out.println(opponent);
    }

    // Create characters
    public void createCharacters() {
        int key = 0;
    	Scanner scanner = new Scanner(System.in);
        int numberOfCharacters = 0;
        while (key ==0) {
            /*System.out.print("Please enter the number of characters to create: ");
            if (scanner.hasNextInt()) {
                numberOfCharacters = scanner.nextInt();
                break;
            } else {
                System.out.println("That's not a valid number. Please try again.");
                scanner.next(); // discard the invalid input
            }*/
        	System.out.println("Please enter the number of characters to create: ");
        	numberOfCharacters = scanner.nextInt();
        	break;
        	
        }

        for (int i = 1; i < numberOfCharacters+1; i++) {
            System.out.print("Enter name for character " + (i) + ": ");
            
            Scanner scanner1 = new Scanner(System.in);
            String characterName = scanner1.next();
            
            /*for(Human<Weapon> character : characters) {
                if(character.name.equals(characterName)) {
                    System.out.print("This name is already taken. ");
                    i--; break;
                }else {
                	Human<Weapon> newCharacter = new Human<Weapon>(characterName);
                    System.out.println("The stats of the character " + (i) + ": " + newCharacter);
                    characters.add(newCharacter);
                	
                }
            }*/
            Human<Weapon> newCharacter = new Human<Weapon>(characterName);
            System.out.println("The stats of the character " + (i) + ": " + newCharacter);
            characters.add(newCharacter);
        }

        
    }

    public class Menu {
        public int selectedOption;
        public int attackType;
        public int opponentID;

        public Menu() {
            this.selectedOption = 0;
            this.attackType = 0;
            this.opponentID = 0;
        }


        public void showMenu(int currentMoveCounter, Human<Weapon> currentCharacter) {
            System.out.println("Move " + currentMoveCounter + " - " + "It is the turn of " + currentCharacter.name + ".");
            System.out.println("[1] Punch");
            System.out.println("[2] Attack with weapon");
            System.out.println("[3] Special Action");
            System.out.println("[4] Guard");
            System.out.println("[5] Run");
        }

        public int getSelectedOption() {
            Scanner scanner = new Scanner(System.in);
           
            // Get the selected option
            while (true) {
                System.out.print("Please select an option: ");

                if (scanner.hasNextInt()) {
                    selectedOption = scanner.nextInt();
                    break;
                } else {
                    System.out.println("That's not a valid selection. Please try again.");
                    if (scanner.hasNext()) {
                        scanner.next(); // discard the invalid input
                    }
                }
                break;
            }
            

            return selectedOption;
        }

        public void getMenuInput() {
            Scanner scanner = new Scanner(System.in);
            
            selectedOption = getSelectedOption();

            // Get the attack type
            if (selectedOption == 2) {
                while (true) {
                    System.out.print("Please select an weapon attack type ([1] or [2]): ");
                    if (scanner.hasNextInt()) {
                        attackType = scanner.nextInt();
                        if (attackType < 1 || attackType > 2) {
                            System.out.println("That's not a valid selection. Please try again.");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("That's not a valid selection. Please try again.");
                        scanner.next(); // discard the invalid input
                    }
                }
            }

            // Get the opponent ID
            if (selectedOption == 1 || selectedOption == 2 || selectedOption == 4) {
                while (true) {
                    System.out.print("Please enter an opponent ID: ");
                    if (scanner.hasNextInt()) {
                        opponentID = scanner.nextInt();
                        if (opponentID < 1 || opponentID > opponents.size()) {
                            System.out.println("That's not a valid selection. Please try again.");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("That's not a valid selection. Please try again.");
                        scanner.next(); // discard the invalid input
                    }
                }
            }
            
        }
    }

    public static void main(String[] args) {
        // Create a new game
        TBGame game = new TBGame();
        // Create a new menu for it
        // Initialize the opponents and show the welcome message
        game.initializeOpponents();
        game.showWelcomer();
        // Create characters and initialize the turn order
        game.initializeCharacters();
        System.out.println();
        game.createCharacters();
        game.initializeTurnOrder();
        game.printTurnOrder();
        // Start the game
        game.startBattle();

    }
}
