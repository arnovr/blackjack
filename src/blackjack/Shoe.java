package blackjack;
/**
 * Klasse die een kaartstapel vertegenwoordigt.
 *
 */
public class Shoe extends Deck
{
	/**
	 * Maakt een nieuwe object van het type Shoe met het gegeven aantal kaartspellen.
	 * @param numberOfDecks een intwaarde die het aantal kaartspellen in de Shoe vertegenwoordigt.
	 */
	public Shoe(int numberOfDecks)
	{
		super();

		for(int i = 1 ; i < numberOfDecks ; i++){
			generateCards();
		}

	}
}
