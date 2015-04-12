package blackjack;
import java.util.*;
/**
 * Klasse die een deler vertegenwoordigt.
 *
 */
public class Dealer extends Player{
	/**
	 * Referentie naar een kaartstapel, om uit te kunnen delen.
	 */
	private Shoe shoe;
	/**
	 * Een array die als secundaire kaartstapel fungeert, om kaarten
	 * aan de kant te kunnen leggen.
	 */
	private ArrayList<Card> stack;
	/**

	 * Maakt een nieuw deler-object aan.
	 */
	public Dealer()
	{
		super("Dealer", new Date(), 0);
	}
	/**
	 * Maakt een nieuwe deler aan met een referentie naar de kaartstapel.
	 * @param shoe Een kaartstapel (Shoe)
	 */
	public Dealer(Shoe shoe)
	{
		super("Dealer", new Date(), 0);
		this.shoe	= shoe;
		stack		= new ArrayList<Card>();
	}
	/**
	 * Functie om deze deler met gegeven deler te initialiseren.
	 * @param player De deler waarmee deze deler moet worden geinitialiseerd worden.
	 */
	public void init(Player player)
	{
		super.init(player);

		this.shoe = ((Dealer)player).shoe;
		this.stack = ((Dealer)player).stack;
	}
	/**
	 * Geeft een kopie van deze deler terug.
	 * @return Player Een kopie van dit deler-object.
	 */
	public Player getCopy()
	{
		Dealer dealer = new Dealer(this.shoe);
		dealer.init(this);

		return dealer;
	}
	/**
	 * Trekt een kaart uit de kaartstapel en returnt deze.
	 * @return Card
	 */
	public Card hit()
	{
		return shoe.takeCard();
	}
	/**
	 * Stopt de meegegeven kaart terug in de kaartstapel.
	 * @param card De terug te stoppen kaart
	 */
	public void putCardInShoe(Card card)
	{
		shoe.putCard(card);
	}
	/**
	 * Legt de meegegeven kaart op de secundaire kaartstapel.
	 * @param card de aan de kant te leggen kaart
	 */
	public void putCardOnStack(Card card)
	{
		stack.add(card);
	}
	/**
	 * Controleert aan de hand van het meegegeven percentage.
	 * of de kaartstapel moet worden geschud.
	 * @param percentage Een intwaarde die het percentage vertegenwoordigt.
	 * @return boolean
	 */
	public boolean shouldShuffle(int percentage)
	{
		return shoe.shouldBeShuffled(percentage); //need to read this from settings
	}
	/**
	 * Schudt de kaartstapel.
	 */
	public void shuffleShoe()
	{
		System.out.println("-----------------------------------");
		System.out.println("Shuffling shoe");
		System.out.println("-----------------------------------");
		shoe.shuffle();
	}
	/**
	 * Plaatst alle kaarten in de secundaire kaartstapel terug in de kaartstapel
	 * en schudt deze vervolgens.
	 */
	public void shuffleShoeWithStack()
	{
		System.out.println("-----------------------------------");
		System.out.println("Shuffling shoe with stack...");
		System.out.println("-----------------------------------");

		for(Card card : stack)
		{
			shoe.putCard(card);
		}
		stack.clear();
		shoe.shuffle();
	}
}
