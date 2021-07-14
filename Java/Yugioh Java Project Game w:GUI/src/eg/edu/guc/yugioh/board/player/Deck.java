package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.Card;



import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.*;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;



public class Deck {

	private static ArrayList<Card> monsters;
	private static ArrayList<Card> spells;
	private final ArrayList<Card> deck;
	private static String monstersPath = "Database-Monsters.csv";
	private static String spellsPath = "Database-Spells.csv";
    private int x;
	private Scanner sc;
	

	public Deck() throws IOException, MissingFieldException, UnknownCardTypeException, UnknownSpellCardException, NumberFormatException, EmptyFieldException {
		
		
		sc = new Scanner(System.in);
		
		
		
	


		
		
		
		
		

			monsters = load(getMonstersPath());
			spells = load(getSpellsPath());

		

		deck = new ArrayList<Card>();
		buildDeck(monsters, spells);
		shuffleDeck();

	}


	@SuppressWarnings("resource")
	public ArrayList<Card> loadCardsFromFile(String path)
			throws NumberFormatException, IOException, MissingFieldException, UnknownCardTypeException, UnknownSpellCardException, EmptyFieldException {

		ArrayList<Card> temp = new ArrayList<Card>();

		String line;

		FileReader fr = new FileReader(path);

		BufferedReader br = new BufferedReader(fr);
		
		int s = 1;

		while ((line = br.readLine()) != null) {

			String[] cardInfo = line.split(",");
			
			
			if (cardInfo.length == 0) {
				if (x >= 3) {
				throw new MissingFieldException(path, s);
				}
				return anotherChance(path); 
			}
			if (cardInfo[0] == null || cardInfo[0].trim().isEmpty()) {
				if (x >= 3 ) {
				throw new EmptyFieldException(path, s, 1);
			}
				return anotherChance(path); 
			}

			if (cardInfo[0].equalsIgnoreCase("Monster")) {
				if (cardInfo.length != 6 ) {
					if (x >= 3) {
					throw new MissingFieldException(path, s);
				}
					return anotherChance(path); 
				}
				
				if (cardInfo[1] == null || cardInfo[1].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 2);
				}
					return anotherChance(path); 
				}
				if (cardInfo[2] == null || cardInfo[2].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 3);
				}
					return anotherChance(path); 
				}
				if (cardInfo[3] == null || cardInfo[3].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 4);
				}
					return anotherChance(path); 
				}
				if (cardInfo[4] == null || cardInfo[4].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 5);
				}
					return anotherChance(path); 
				}
				if (cardInfo[5] == null || cardInfo[5].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 6);
				}
					return anotherChance(path); 
				}

				temp.add(new MonsterCard(cardInfo[1], cardInfo[2], Integer
						.parseInt(cardInfo[5]), Integer.parseInt(cardInfo[3]),
						Integer.parseInt(cardInfo[4])));

			} else if (cardInfo[0].equalsIgnoreCase("Spell")) {
				if (cardInfo.length != 3) {
					if (x >= 3) {
					throw new MissingFieldException(path, s);
				}
					return anotherChance(path); 
				}
				
				if (cardInfo[1] == null || cardInfo[1].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 2);
				}
					return anotherChance(path); 
				}
				
				if (cardInfo[2] == null || cardInfo[2].trim().isEmpty()) {
					if (x >= 3) {
					throw new EmptyFieldException(path, s, 3);
				}
					return anotherChance(path);  
				}

				switch (cardInfo[1]) {

				case "Card Destruction":
					temp.add(new CardDestruction(cardInfo[1], cardInfo[2]));
					break;
				case "Change Of Heart":
					temp.add(new ChangeOfHeart(cardInfo[1], cardInfo[2]));
					break;
				case "Dark Hole":
					temp.add(new DarkHole(cardInfo[1], cardInfo[2]));
					break;
				case "Graceful Dice":
					temp.add(new GracefulDice(cardInfo[1], cardInfo[2]));
					break;
				case "Harpie's Feather Duster":
					temp.add(new HarpieFeatherDuster(cardInfo[1], cardInfo[2]));
					break;
				case "Heavy Storm":
					temp.add(new HeavyStorm(cardInfo[1], cardInfo[2]));
					break;
				case "Mage Power":
					temp.add(new MagePower(cardInfo[1], cardInfo[2]));
					break;
				case "Monster Reborn":
					temp.add(new MonsterReborn(cardInfo[1], cardInfo[2]));
					break;
				case "Pot of Greed":
					temp.add(new PotOfGreed(cardInfo[1], cardInfo[2]));
					break;
				case "Raigeki":
					temp.add(new Raigeki(cardInfo[1], cardInfo[2]));
					break;
					
				default:
					if (x >= 3) {
					throw new UnknownSpellCardException(path, s, cardInfo[1]);
					}
					return anotherChance(path);  
				}

			} else {
				if (x >= 3 && cardInfo[0] != null) {
				throw new UnknownCardTypeException(path, s, cardInfo[0]);
			}
				return anotherChance(path); 
			}
s++;
		}

		br.close();

		return (temp);

	}

	private void buildDeck(ArrayList<Card> Monsters, ArrayList<Card> Spells) {

		int monstersQouta = 15;
		int spellsQouta = 5;

		Random r = new Random();
		while(r.nextInt() < 0) {
			r = new Random();
		}

		for (; monstersQouta > 0; monstersQouta--) {

			MonsterCard monster = (MonsterCard) monsters.get(r.nextInt(monsters
					.size()));

			MonsterCard clone = new MonsterCard(monster.getName(),
					monster.getDescription(), monster.getLevel(),
					monster.getAttackPoints(), monster.getDefensePoints());
			clone.setMode(monster.getMode());
			clone.setHidden(monster.isHidden());
			clone.setLocation(Location.DECK);

			deck.add(clone);

		}

		for (; spellsQouta > 0; spellsQouta--) {

			Card spell = spells.get(r.nextInt(spells.size()));

			SpellCard clone;

			if (spell instanceof CardDestruction) {

				clone = new CardDestruction(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof ChangeOfHeart) {

				clone = new ChangeOfHeart(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof DarkHole) {

				clone = new DarkHole(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof GracefulDice) {

				clone = new GracefulDice(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof HarpieFeatherDuster) {

				clone = new HarpieFeatherDuster(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof HeavyStorm) {

				clone = new HeavyStorm(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof MagePower) {

				clone = new MagePower(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof MonsterReborn) {

				clone = new MonsterReborn(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof PotOfGreed) {

				clone = new PotOfGreed(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof Raigeki) {

				clone = new Raigeki(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

		}

	}


	public ArrayList<Card> load(String path) throws NumberFormatException, UnknownCardTypeException, UnknownSpellCardException, MissingFieldException, EmptyFieldException, IOException {
		File f = new File(path);

		if (!f.exists() && x >= 4) {
			throw new FileNotFoundException();
		} else if (!f.exists()) {
			System.out.println("Please enter a correct path: " + (String)path);
			System.out.println("The file was not found");
			x++;
			if (sc.hasNextLine()) {
			return load(sc.nextLine());
			
			} 
				
			
		} 
		return loadCardsFromFile(path);
	}
	
	public ArrayList<Card> anotherChance(String path) throws NumberFormatException, UnknownCardTypeException, UnknownSpellCardException, MissingFieldException, EmptyFieldException, IOException{
		System.out.println("Please enter a correct path: " + (String)path);
		System.out.println("The file has a missing Field");
		x++;
		if (sc.hasNextLine()) {
		return load(sc.nextLine());
	   } 
		throw new FileNotFoundException();
	}


	private void shuffleDeck() {

		Collections.shuffle(deck);

	}

	public ArrayList<Card> drawNCards(int n) {
		if (n > this.getDeck().size() && Card.getBoard().GameOver() == false) {
			this.getDeck().clear();
			Card.getBoard().GameOver();
		}

		ArrayList<Card> cards = new ArrayList<Card>(n);

		for (int i = 0; i < n; i++) {
			deck.get(i).setLocation(Location.HAND);
			cards.add(deck.remove(i));
			
		}
		return (cards);

	}

	public Card drawOneCard() {
		deck.get(0).setLocation(Location.HAND);

		return (deck.remove(0));

	}

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static void setMonsters(ArrayList<Card> monsters) {
		Deck.monsters = monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}

	public static void setSpells(ArrayList<Card> spells) {
		Deck.spells = spells;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String m) {
		monstersPath = m;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String s) {
		spellsPath = s;
	}

}
