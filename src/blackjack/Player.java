package blackjack;
import java.util.*;
/**
 * Klasse die een speler vertegenwoordigt.
 *
 */
public class Player
{
	/**
	 * Het saldo van de speler.
	 */
	private double balance;
	/**
	 * Het aantal door de speler gespeelde handen.
	 */
	private String name;
	/**
	 * De geboortedatum van de speler.
	 */
	private Date dateOfBirth;
	/**
	 * ArrayList die de handen van deze speler vertegenwoordigt.
	 */
	private ArrayList<Hand>	hands;
	/**
	 * De huidige hand van de speler
	 */
	private Hand			currentHand;
	/**
	 * Rerefentie naar een deler-object.
	 */
	private Dealer			dealer;
	/**
	 * Maakt een nieuw object van het type speler.
	 */
	public Player()
	{
		currentHand = null;
	}
	/**
	 * Maakt een nieuwe speler aan de hand van de meegegeven waarden.
	 * @param name Een stringwaarde die de naam van de speler vertegenwoordigt.
	 * @param dateOfBirth Een Date die de geboortedatum van de speler vertegenwoordigt.
	 * @param balance Een intwaarde die het startsaldo van de speler vertegenwoordigt.
	 */
	public Player(String name, Date dateOfBirth, double balance)
	{
		hands		= new ArrayList<Hand>();

		this.name		 = name;
		this.dateOfBirth = dateOfBirth;
		this.balance	 = balance;

		/* een speler heeft altijd 1 hand */
		this.newHand();
	}
	/**
	 * Functie om deze speler met gegeven speler te initialiseren.
	 * @param player De speler waarmee deze speler moet worden geinitialiseerd worden.
	 */
	public void init(Player player)
	{
		// bijm copieren ook het originele bod van de hand overzetten
		// hierdoor kan de UI zorgen dat het vorige bod weer kan worden geboden
		if (player.hands.size() >= 1 && hands.size() >= 1)
		{
			// zet het originele bet van zijn vorige hand
			hands.get(0).setOriginalBet(player.hands.get(0).getOriginalBet());
		}
	}
	/**
	 * Geef een kopie van deze speler terug.
	 * @return Player
	 */
	public Player getCopy()
	{
		Player player = new Player(this.name, this.dateOfBirth, this.balance);
		// kopieer de gegevens van mijzelf in de nieuwe player
		player.init(this);
		return player;
	}
	/**
	 * Returnt de naam van de speler.
	 * @return String
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Returnt het saldo van de speler.
	 * @return int
	 */
	public double getBalance()
	{
		return balance;
	}
	/**
	 * Returnt de huidige hand van de speler.
	 * @return Hand
	 */
	public Hand getCurrentHand()
	{
		return currentHand;
	}
	/**
	 * Returnt een ArrayList met alle handen in deze ronde.
	 * @return ArrayList
	 */
	public ArrayList<Hand> getHands()
	{
		return hands;
	}
	/**
	 * Maak een nieuwe hand aan en voeg die toe aan de lijst. Voeg deze ook toe aan de lijst met handen.
	 * @return Hand
	 */
	public Hand newHand()
	{
		/* boac
		 * currentHand ongemoeid laten
		 * omdat we daarmee aan het spelen zijn!!!
		 */
		Hand hand 	= new Hand();
		hands.add(hand);

		/* boac
		 * geef de nieuw aangemaakte hand terug
		 */
		return hand;
	}
	/**
	 * Zet de eerste hand op de current hand. Geef deze ook terug.
	 * @return Hand
	 */
	public Hand firstHand()
	{
		currentHand = null;
		if (hands.size() > 0)
		{
			currentHand = hands.get(0);
		}
		return currentHand;
	}
	/**
	 * Zet volgende hand (na currentHand) uit de lijst met handen op currentHand. Geef deze ook terug.
	 * @return Hand
	 */
	public Hand nextHand()
	{
		int index = hands.indexOf(currentHand) + 1;
		if (index < 0)
		{
			currentHand = null;
		}
		else
		{
			if (index >= hands.size())
			{
				currentHand = null;
			}
			else
			{
				currentHand = hands.get(index);
			}
		}
		return currentHand;
	}
	/**
	 * Testfunctie om de Hand te spelen.
	 */
	public void playHandTest(Dealer dealer)
	{
		this.dealer = dealer;
		System.out.println("-----------------------------------");
		System.out.println("" + name + " takes his turn");
		System.out.println("-----------------------------------");

		firstHand();
		do
		{
			autoPlay();
		} /* boac: net zolang doorspelen als er handen zijn */
		while (nextHand() != null);
	}
	/**
	 * Speelt automatisch, voor testdoeleinden.
	 */
	public void autoPlay()
	{
		while(!currentHand.isBlackjack() && !currentHand.isStanded() && !currentHand.isBust())
		{
			if(currentHand.isSplittable() && !hasSplitted())
			{
				System.out.println("-----------------------------------");
				System.out.println("" + name + " splits!");
				System.out.println("-----------------------------------");
				split();
			}

			/* boac
			 * card vragen
			 */
			Card card = dealer.hit();

			/* boac
			 * en de card toevoegen
			 */
			this.hit(card);

			if(currentHand.getScore() >= 18 && currentHand.getScore() <= 21)
			{
				currentHand.stand();
			}
		}
	}
	/**
	 * Plaatst een bepaald bedrag als inzet op de huidige hand.
	 * @param amount Het in te zetten bedrag in hele euro's
	 */
	public void bet(int amount)
	{
		currentHand.setBet(amount);
		balance -= amount;
	}
	/**
	 * Voegt een kaart toe aan de huidige hand.
	 * @param card De toe te voegen kaart (Card)
	 */
	public void hit(Card card)
	{
		currentHand.addCard(card);
	}
	/**
	 * Be‘indigt de huidige hand.
	 */
	public void stand()
	{
		currentHand.stand();

	}
	/**
	 * Maakt van de huidige hand twee handen door een van de kaarten naar een nieuwe hand te
	 * kopieeren.
	 */
	public void split()
	{
		//create a new hand
		Hand splitHand = newHand();

		//take one card from the current hand and put it in split hand
		splitHand.addCard(currentHand.split());

		//don't forget the bet!
		splitHand.setBet(currentHand.getBet());
		/* boac
		 * niet toevoegen aan het array omdat newHand dat doet
		 */

		/* boac
		 * Het bedrag ook van huidige balance afhalen.
		 */
		balance -= currentHand.getBet();
	}
	/**
	 * Geeft een boolean terug die weergeeft of de speler reeds gesplit heeft.
	 * @return boolean
	 */
	public boolean hasSplitted()
	{
		return hands.size() > 1;
	}

	/**
	 * Geeft een boolean terug die weergeeft of de speler doubledown heeft gedaan.
	 * Bij double down is je hand altijd 0, toch?
	 */
	public boolean hasDoubleDowned() {
		if ( hands.size() > 0 ) {
			return hands.get(0).getDoubleDown();
		}
		return false;
	}

	/**
	 * Verdubbelt de inzet op de huidige hand.
	 */
	public void doubleDown()
	{
		currentHand.setBet(currentHand.getBet()*2);
		currentHand.doubleDown();

		/* boac
		 * Het bedrag ook van huidige balance afhalen.
		 */
		balance -= currentHand.getBet() / 2;
	}
	/**
	 * Dient te aangeroepen wanneer de speler gewonnen heeft zodat
	 * alle relevante variabelen kunnen worden bijgewerkt.
	 * @param amount (double) Het gewonnen bedrag
	 */
	public void won(double amount)
	{
		currentHand.payedOut();
		currentHand.won();
		balance	+= amount;
	}
	/**
	 * Dient te worden aangeroepen wanneer de speler verloren heeft zodat
	 * alle relevante variabelen kunnen worden bijgewerkt.
	 * @param amount (double) Het verloren bedrag
	 */
	public void lost(double amount)
	{
		currentHand.payedOut();
		currentHand.lost();
	}
	/**
	 * Dient te worden aangeroepen wanneer de speler gelijkgespeeld heeft zodat
	 * alle relevante variabelen kunnen worden bijgewerkt.
	 */
	public void even()
	{
		currentHand.payedOut();
		currentHand.even();
	}
    /*
     * Get the date of birth
     */
    public Date getDateOfBirth() {
    	return dateOfBirth;
    }
}
