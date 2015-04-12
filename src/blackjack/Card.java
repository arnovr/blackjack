package blackjack;
/**
 * Klasse die een speelkaart vertegenwoordigt.
 *
 */
public final class Card
{
	/**
	 * De waarde die deze kaart in het spel heeft.
	 */
	private int		value;
	/**
	 * De waarde van deze kaart ("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", of "Ace").
	 */
	private String  rank;
	/**
	 * De kleur van deze kaart ("hearts", "diamonds", "clubs" of "spades")
	 */
	private String	suit;
	/**
	 * De afbeelding behorende bij deze kaart.
	 */
	private String imageLocation;
	/**
	 * Maakt een nieuwe speelkaart aan de hand van de onderstaande parameters.
	 * @param rank Een stringwaarde die de kleur van deze kaart vertegenwoordigt.
	 * @param suit Een stringwaarde die de waarde van deze kaart vertegenwoordigt.
	 * @param value Een intwaarde die de spelwaarde van deze kaart vertegenwoordigt.
	 */
	public Card(String rank, String suit, int value)
	{
		this.rank	= rank;
		this.suit	= suit;
		this.value	= value;
		this.imageLocation = "" + rank + "_of_" + suit + ".png";
		System.out.println("Card added to deck: " + this.toString());
	}
	/**
	 * @return String Stringwaarde die de kleur van deze kaart vertegenwoordigt.
	 */
	public String getRank()
	{
		return rank;
	}
	/**
	 * @return String Stringwaarde die de waarde van deze kaart vertegenwoordigt.
	 */
	public String getSuit()
	{
		return suit;
	}
	/**
	 * @return int intwaarde die de spelwaarde van deze kaart vertegenwoordigt.
	 */
	public int getValue()
	{
		return value;
	}
	/**
	 * @return String Stringwaarde die de lokatie van het beeldbestand van deze kaart in het bestandssysteem vertegenwoordigt.
	 */
	public String getImageLocation()
	{
		return imageLocation;
	}
	/**
	 * @return String Stringwaarde die deze kaart vertegenwoordigt.
	 */
	public String toString()
	{
		String returnString = "" + rank + " of " + suit + ", value: " + value + "\n";
		/* liever de echte kaart presentatie */
//		returnString += "Filename:" + this.imageLocation;
		return returnString;
	}
}
