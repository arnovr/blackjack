package blackjack;
/**
 * De interface die bekend is in de domeinklassen.
 * Door een nieuw Frame te maken die deze interface
 * implementeert, zijn er geen aanpassingen nodig in de domeinklassen.
 *
 */

public interface IBlackJackGui {
	/**
	 * Laat de gegeven speler zijn bod doen.
	 * @param player De speler
	 */
	void	letBet(Player player);
	/**
	 * Er wordt een kaart gedeeld aan gegeven speler en hand.
	 * @param player De speler
	 * @param hand De hand van de speler
	 * @param card De kaart die gedeeld wordt
	 */
	void	dealCard(Player player, Hand hand, Card card);
	/**
	 * Betaal speler uit
	 * @param player De speler die gewonnen heeft
	 * @param hand De hand die gewonnen heeft
	 */
	void	payOut(Player player, Hand hand);
	/**
	 * Laat de gegeven speler een kaart trekken/splitten/standen/double-downen.
	 * @param player De speler
	 */
	void	letPlayHand(Player player);

	/**
	 * Er is een nieuwe ronde gestart.
	 */
	void 	newRoundStarted();
	/**
	 * Laat de spelers een nieuwe ronde starten.
	 */
	void 	letStartNewRound();
	/**
	 * De kaarten zijn verzameld.
	 * @param inShoe Geeft aan of de kaarten naar de shoe of de stack zijn gezet.
	 */
	void	cardsCollected(boolean inShoe);
	/**
	 * De shoe is geschud.
	 */
	void 	shoeShuffled();

}
