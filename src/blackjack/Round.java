package blackjack;
import java.util.*;
/**
 * Klasse die een ronde in het spel vertegenwoordigt.
 *
 */
public class Round
{
	public enum RoundState {
		NONE,
		BET,
		DEAL,
		CHECKBLACKJACK,
		PLAYHANDS,
		PAYOUT,
		ROUNDREADY,
		COLLECTCARDS,
		SHUFFLESHOE,
	}

	/**
	 * ArrayList van spelers die meedoen in deze ronde.
	 */
	private ArrayList<Player>	players;
	/**
	 * Referentie naar het deler-object
	 */
	private Dealer				dealer;
	/**
	 * Referentie naar het game-object
	 */
	private Game				game;
	/**
	 * Referentie naar de huidige speler;
	 */
	private Player				currentPlayer;
	/**
	 * De huidige staat van de ronde.
	 */
	private RoundState			state;
	/**
	 * Referentie naar het spelscherm
	 */
	private IBlackJackGui		gui;
	/**
	 * Wanneer deze ronde is gestart
	 */
	private Date roundStarted = null;
	/**
	 * Maakt een nieuwe ronde aan met de meegegeven parameters.
	 * @param game Een object van het type Game.
	 * @param players Een ArrayList met spelers.
	 * @param dealer Een object van het type Dealer.
	 * @param gui Een object van het type IBlackJackGui.
	 */
	public Round(Game game, ArrayList<Player> players, Dealer dealer, IBlackJackGui gui)
	{
		this.game		=	game;
		this.players	=	players;
		this.dealer		=	dealer;

		this.gui		= 	gui;
		this.state		= 	RoundState.BET; /* direct in de initiele state zetten */

		this.roundStarted = new Date();

		/* vertel de gui dat de nieuwe ronde is gestart */
		if (gui != null) this.gui.newRoundStarted();
	}
	/**
	 * Zet de gui op deze Round
	 * @param gui De nieuwe gui
	 */
	void setGui(IBlackJackGui gui)
	{
		if (gui != null) this.gui = gui;
	}
	/**
	 * Testfunctie voor het speelalgoritme.
	 */
	public void playTest()
	{
		takeBetsTest();
		dealTest();
		playerHandsTest();
		/*dealerHand(); deze call is niet meer nodig omdat deze in de player lijst staat */
		payout();
		collectCards();
		shuffleIfNecessary();
	}
	/**
	 * Functie om de ronde status bij te werken.
	 * @return true Wanneer de ronde is afgerond
	 */
	public boolean updateRound()
	{
		System.out.println("-----------------------------------");
		System.out.println("Round " + game.getRounds().size());
		System.out.println("-----------------------------------");

		boolean result = true;

		while (result) {

			switch (this.state) {
			case BET:
				/* takeBet neemt een bet van een player
				 als de player zijn bet heeft gedaan is result true
				 */
				result = takeBet(currentPlayer);
				break;
			case DEAL:
				/* dealCards deelt 1 kaart aan speler
				 * wanneer speler 2 kaarten heeft wordt true terug gegeven
				 *
				 * We doen dit per kaart zodat later animaties eenvoudig worden..
				 */
				result = dealCard(currentPlayer, true);

				break;
			case CHECKBLACKJACK:
				result = checkBlackJack(currentPlayer);
				break;
			case PLAYHANDS:
				result = playHand(currentPlayer);
				break;
			case PAYOUT:
				/* betaal uit */
				payout();

				/* TODO
				 * Ook de userinterface laten weten dat we uitbetalen.
				 * Omdat we dit nu niet geimplementeerd hebben
				 * nu gewoon continue. Anders result op false zetten en break.
				 * De userinterface dan updateRound laten aanroepen.
				 */
				this.state = nextState(state);
				continue;

			case ROUNDREADY:
				/* en laat de volgende ronde starten */
				letStartNewRound();

				this.state = nextState(state);
				result = false;

				break;
			case COLLECTCARDS:
				collectCards();

				this.state = nextState(state);
				result = false;

				break;
			case SHUFFLESHOE:
				if (shuffleIfNecessary() != false)
				{
					/* er is gedeeld */
					result = false;
				}
				this.state = nextState(state);

				break;
			default:
				return result;
			}
			if (result)
			{
				this.currentPlayer = nextPlayer(currentPlayer);
				if (this.currentPlayer == null) {
					this.state = nextState(state);
					// volgende player
					this.currentPlayer = nextPlayer(currentPlayer);
				}
				// we gaan de eerste hand spelen voor een player
				this.currentPlayer.firstHand();
			}
		}

		return result;
	}
	/**
	 * Geeft de speler terug op de plek na de gegeven speler in de spelers lijst. Anders null.
	 * @param player De gaande speler voor de te verkrijgen speler
	 * @return De volgende speler in de lijst met speler na gegeven player. Anders null
	 */
	private Player nextPlayer(Player player)
	{
		int index = players.indexOf(player) + 1;
		if (index < 0) return null;
		if (index >= players.size()) return null;

		return players.get(index);
	}
	/**
	 * Geeft de state terug uit de lijst met RoundStates na de gegeven states. Anders null.
	 * @param state De state voor de terug te geven state
	 * @return De state na de gegeven state in de enum-lijst van Round States
	 */
	private RoundState nextState(RoundState state)
	{
		RoundState prev = RoundState.NONE;
		for (RoundState stateCurrent : RoundState.values()) {
			// als de vorige state gelijk is aan de gegeven state, geef de huidige terug
			if (prev == state) return stateCurrent;
			prev = stateCurrent;
		}
		// geef status None terug
		return RoundState.NONE;
	}
	/**
	 * Geeft aan de gui aan dat een bet wordt verwacht van gegeven player.
	 * <ul>
	 * <li>Als de speler een Dealer is, wordt true teruggegeven.</li>
	 * <li>Als speler zijn bet heeft gedaan wordt true teruggegeven</li>
	 * <li>Als speler is null wordt true teruggegeven</li>
	 * <li>Als bet-verwachting aan gui is doorgegeven wordt false teruggegeven</li>
	 * </ul>
	 * @param player De speler die mag gaan bieden
	 */
	private boolean takeBet(Player player)
	{
		if (player == null) return true;
		/* dealer kan niet bieden */
		if (player.getClass() == Dealer.class) return true;

		/* player heeft geboden */
		if (player.getCurrentHand().hasBet() != false) return true;

		if (gui == null) return true;

		gui.letBet(player);

		return false;
	}
	/**
	 * Testfunctie voor een bod vragen aan een speler.
	 */
	private void takeBetsTest()
	{
		System.out.println("-----------------------------------");
		System.out.println("Taking player's bets");
		System.out.println("-----------------------------------");
		for(Player player : players)
		{
			player.newHand();		//clear the last hand

			/* Dealer kan niet bieden */
			if (player.getClass() == Dealer.class) continue;

			player.bet(10);				//should read this off the screen...
			System.out.println(player.getName() + " bets $" + player.getCurrentHand().getBet());
		}
	}
	/**
	 * <p>Geeft aan de gui aan dat kaart wordt gedeeld aan een player.</p>
	 * <ul>
	 * <li>Als player 2 of meer kaarten heeft afbreken bij dealRound == true</li>
	 * <li>Als player null is wordt true teruggegeven</li>
	 * <li>Als kaart-delen aan gui is doorgegeven wordt false teruggegeven</li>
	 * </ul>
	 * @param player De speler die een kaart gaat krijgen
	 * @param dealRound Indiceert dat het om de kaart delen ronde gaat
	 */
	public boolean dealCard(Player player, boolean dealRound)
	{
		if (player == null || player.getCurrentHand() == null) return true;

		Hand hand = player.getCurrentHand();

		/* wanneer dit niet de deler is en deze player geen bod heeft geplaatst */
		if (player.getClass() != Dealer.class && hand.getBet() == 0) return true;

		/* wanneer het de delen ronde is en de speler heeft 2 of meer kaarten, true terug geven */
		if (dealRound != false && hand.getCards().size() >= 2) return true;

		/* kaart vragen */
		Card card = dealer.hit();
		/* op de players hand zetten */
		hand.addCard(card);

		/* we gegeven ook de kaart mee zodat we daar later wat mee kunnen */
		if (gui != null) gui.dealCard(player, hand, card);

		/* als player 21 heeft dan standen */
		if (player.getCurrentHand().getScore() == 21)
		{
			player.getCurrentHand().stand();
		}

		/* als het niet de kaartdeel ronde is, de hand blackjack heeft en het niet de deler is */
		if (dealRound != false && hand.isBlackjack() && player.getClass() != Dealer.class)
		{
			checkBlackJack(player);
		}

		/* true terug geven als er geen gui (geen interactie) is */
		return gui == null;
	}
	/**
	 * Testfunctie voor het delen van kaarten.
	 */
	private void dealTest()
	{
		System.out.println("-----------------------------------");
		System.out.println("Dealing two cards:");
		System.out.println("-----------------------------------");

		for(Player player : players)
		{
			System.out.println("Dealing " + player.getName() + " two cards:");
			System.out.println("-----------------------------------");
			for (int i = 1 ; i <= 2 ; i++)
			{
				Card card = dealer.hit();
				player.getCurrentHand().addCard(card);
			}
			System.out.println("-----------------------------------");
		}
	}
	/**
	 * <p>Checkt voor gegeven speler of deze blackjack heeft op zijn current hand.</p>
	 * <p>Wanneer de deler geen blackjack kan hebben wordt de speler zijn hand gelijk uitbetaald.</p>
	 * @param player De speler waarvoor blackjack wordt gechecked
	 */
	private boolean checkBlackJack(Player player)
	{
		// als de player null is of de huidige hand van gegeven player null is
		if (player == null || player.getCurrentHand() == null) return true;

		/* als de deler blackjack kan zijn, dan niet uitbetalen */
		if (dealer.firstHand().maybeBlackjack() != false) return true;

		if (player.getCurrentHand().isBlackjack() != false &&
				player.getCurrentHand().getPayedOut() == false) {

			/* deze hand stands */
			player.getCurrentHand().stand();

			/* betaal deze speler uit */
			double winAmount = player.getCurrentHand().getBet()*2.5;
			System.out.println(player.getName() + " has Blackjack!");
			System.out.println(player.getName() + " wins $" + winAmount);
			player.won(winAmount);
			dealer.lost(winAmount);

			/* vertel de gui dat deze speler uitbetaald wordt */
			if (gui != null) gui.payOut(player, player.getCurrentHand());
		}
		return true;
	}
	/**
	 * Geeft aan de gui aan dat kaart wordt gedeeld aan een player.
	 * <ul>
	 * <li>Als player 2 of meer kaarten heeft afbreken</li>
	 * <li>Als player is null wordt true terug gegeven</li>
	 * <li>Als kaart-delen aan gui is doorgegeven wordt false terug gegeven</li>
	 * </ul>
	 * @param player De speler die zijn hand mag spelen
	 */
	private boolean playHand(Player player)
	{
		// als de player null is of de huidige hand van gegeven player null is
		if (player == null || player.getCurrentHand() == null) return true;

		// wanneer de huidige hand bust of standed is
		if (player.getCurrentHand().isBust() != false ||
				player.getCurrentHand().isStanded() != false)
		{
			// volgende hand laten spelen
			player.nextHand();

			// en deze hand laten spelen
			return playHand(player);
		}

		// als het de dealerhand is
		if (player.getClass() == Dealer.class) {
			// de dealer hand laten spelen
			// en dat resultaat terug geven
			return playDealerHand(player);
		} else {

			/* wanneer deze player geen bod heeft geplaatst */
			if (player.getCurrentHand().getBet() == 0) return true;

			/* als er geen gui is.. dan gewoon true returnen */
			if (gui == null) return true;

			/* laat de speler zijn volgende actie doen */
			this.gui.letPlayHand(player);
		}
		return false;
	}
	/**
	 * Speelt de deler hand tot deze de gewenste top waarde heeft of als deze busted is.
	 * @param player De deler (type Dealer)
	 * @return true als de hand volledig is gespeeld anders false
	 */
	private boolean playDealerHand(Player player)
	{
		// als de player niet van het type Dealer is
		if (player.getClass() != Dealer.class) return true;

		System.out.println("-----------------------------------");
		System.out.println("Dealer playing");
		System.out.println("-----------------------------------");

		/* Dealer probeert te blijven tussen 17 en 22 punten (blackjack = 21!!)*/
		if(player.getCurrentHand().getScore() >= game.getDealerStandsAt() && player.getCurrentHand().getScore() <= 21)
		{
			player.getCurrentHand().stand();
			return true;
		}

		/* als blackjack of busted */
		if (player.getCurrentHand().isBlackjack() && player.getCurrentHand().isBust())
		{
			return true;
		}

		// kaart vragen
		Card card = dealer.hit();
		// kaart aan speler geven
		player.hit(card);

		/* als er geen gui is.. dan gewoon true returnen */
		if (gui == null) return true;

		/* we gegeven ook de kaart mee zodat we daar later wat mee kunnen */
		this.gui.dealCard(player, player.getCurrentHand(), card);

		return false;
	}
	/**
	 * Testfunctie om de deler zijn hand te laten spelen.
	 * @param player De deler
	 */
	private void playDealerHandTest(Player player)
	{
		// als de player niet van het type Dealer is
		if (player.getClass() != Dealer.class) return;

		while(!player.getCurrentHand().isBlackjack() && !player.getCurrentHand().isBust())
		{
			Card card = dealer.hit();
			player.hit(card);

			/* Dealer probeert te blijven tussen 17 en 22 punten */
			if(player.getCurrentHand().getScore() >= 18 && player.getCurrentHand().getScore() <= 21)
			{
				player.getCurrentHand().stand();
			}
		}
	}
	/**
	 * Testfunctie om de spelers hun handen te laten spelen.
	 */
	private void playerHandsTest()
	{
		System.out.println("-----------------------------------");
		System.out.println("Players play their hands:");
		System.out.println("-----------------------------------");

		for(Player player : players)
		{
			if (player.getClass() == Dealer.class)
			{
				playDealerHandTest(player);
			}
			else
			{
				player.playHandTest(dealer);
			}
		}
	}
	/**
	 * Betaalt alle spelers uit naar gelang hun handen en de
	 * hand van de deler. Het algoritme gaat uit van vier specifieke
	 * cases die alle mogelijkheden dekken:
	 *
	 * <ul>
	 * <li><i>De hand van de speler is bust, maar van de deler niet:</i> de speler verliest zijn inzet</li>
	 * <li><i>De hand van deler is bust, maar van de speler niet:</i> de speler wint twee keer zijn inzet, tenzij hij blackjack heeft, dan wint hij 1,5 keer zijn inzet.</li>
	 * <li><i>De handen van zowel de speler en de deler zijn bust:</i> de ronde eindigt in gelijkspel; de speler wint niets maar verliest ook niets.</li>
	 * <li><i>De handen van zowel de speler als de deler zijn niet bust:</i> er kan nog niet worden bepaald wie er gewonnen heeft</li>
	 * </ul>
	 *
	 * In het laatste geval moeten we de score van de hand van de speler gaan vergelijken met die van de deler om uit te zoeken wie er
	 * gewonnen heeft. Er zijn dan nog slechts drie denkbare scenario's:
	 *
	 * <ul>
	 * <li><i>De hand van de speler heeft een hogere waarde dan die van de deler:</i> de speler wint 2 keer zijn inzet (tenzij hij blackjack heeft, dan 2,5 keer)</li>
	 * <li><i>De hand van de deler heeft een hogere waarde dan die van de speler:</i> de speler verliest zijn inzet.</li>
	 * <li><i>De hand van de speler heeft een waarde gelijk aan die van de deler:</i> de ronde eindigt in gelijkspel, tenzij ŽŽn van beiden blackjack heeft. Dan wint degene met de blackjack.</li>
	 * </ul>
	 *
	 */
	private void payout()
	{
		System.out.println("-----------------------------------");
		System.out.println("Round finished. Paying winners:");
		System.out.println("-----------------------------------");

		for(Player player : players)
		{
			/* een deler kan niet worden uitbetaald */
			if (player.getClass() == Dealer.class) continue;

			dealer.firstHand();
			Hand hand = player.firstHand();
			do
			{
				/* standaard winbedrag */
				double winAmount	= hand.getBet()*2;

				/* wanneer deze player geen bod heeft geplaatst */
				if (hand.getBet() == 0) continue;

				/* check of deze speler al is uitbetaald */
				if (hand.getPayedOut() != false) continue;

				/* speler bust, maar deler niet: speler verliest */
				if(hand.isBust() && !dealer.getCurrentHand().isBust())
				{
					System.out.println(player.getName() + " lost $" + hand.getBet() + " by busting");
					player.lost(hand.getBet());
					dealer.won(hand.getBet());
				}
				/* deler bust, maar speler niet: speler wint */
				else if (!hand.isBust() && dealer.getCurrentHand().isBust())
				{
					/* heeft speler blackjack? */
					if(hand.isBlackjack())
					{
						winAmount = hand.getBet()*2.5;
						System.out.println(player.getName() + " has Blackjack!");
						System.out.println(player.getName() + " wins $" + winAmount);
						player.won(winAmount);
						dealer.lost(winAmount);
					}
					else
					{
						System.out.println(player.getName() + " wins $" + winAmount);
						player.won(winAmount);
						dealer.lost(winAmount);
					}
				}
				/* speler en deler zijn beiden bust: gelijkspel */
				else if (hand.isBust() && dealer.getCurrentHand().isBust())
				{
					System.out.println(player.getName() + " lost $" + hand.getBet());
					player.lost(hand.getBet());
					dealer.won(hand.getBet());
				}
				/* speler en deler zijn beiden niet bust: verder uitpluizen */
				else if (!hand.isBust() && !dealer.getCurrentHand().isBust())
				{
					/* speler heeft een hogere score dan de deler: speler wint */
					if (hand.getScore() > dealer.getCurrentHand().getScore())
					{
						/* heeft de speler blackjack? */
						if (hand.isBlackjack())
						{
							winAmount = hand.getBet()*2.5;
							System.out.println(player.getName() + " has Blackjack!");
							System.out.println(player.getName() + " wins $" + winAmount);
							player.won(winAmount);
							dealer.lost(winAmount);
						}
						else
						{
							System.out.println(player.getName() + " wins $" + winAmount);
							player.won(winAmount);
							dealer.lost(winAmount);
						}
					}
					/* deler heeft een hogere score dan de speler: deler wint */
					else if (hand.getScore() < dealer.getCurrentHand().getScore())
					{
						System.out.println(player.getName() + " lost $" + hand.getBet());
						player.lost(hand.getBet());
						dealer.won(hand.getBet());
					}
					/* speler en deler hebben een gelijke score: gelijkspel, tenzij... */
					else if (hand.getScore() ==  dealer.getCurrentHand().getScore())
					{
						/* Een van beiden blackjack heeft */
						if(!hand.isBlackjack() && dealer.getCurrentHand().isBlackjack())
						{
							player.lost(hand.getBet());
							dealer.won(hand.getBet());
						}
						else if (hand.isBlackjack() && !dealer.getCurrentHand().isBlackjack())
						{
							winAmount = hand.getBet()*2.5;
							System.out.println(player.getName() + " has Blackjack!");
							System.out.println(player.getName() + " wins $" + winAmount);
							player.won(winAmount);
							dealer.lost(winAmount);
						}
						else if(hand.isBlackjack() && dealer.getCurrentHand().isBlackjack() ||
								!hand.isBlackjack() && !dealer.getCurrentHand().isBlackjack())
						{
							//player.won(hand.getBet());
							System.out.println(player.getName() + " plays even.");
							player.even();
						}
					}
				}
			}
			while ((hand = player.nextHand()) != null);
		}
	}
	/**
	 * Zet de status van de ronde op NONE en geeft de GUI de melding dat de ronde is afgelopen
	 * en er een nieuwe ronde kan worden gestart.
	 */
	private void letStartNewRound()
	{
		/* vertel het aan de gui */
		if (gui != null) this.gui.letStartNewRound();
	}
	/**
	 * Laat de deler alle kaarten ophalen uit de handen van de spelers, en laat de deler
	 * deze kaarten vervolgens naar gelang de instelling aan de kant leggen of terug in
	 * de kaartstapel plaatsen.
	 */
	private void collectCards()
	{
		//dealer collects all the dealt cards
		// NOTE: dealer is also a player
		for(Player player : players)
		{
			for (Hand hand : player.getHands())
			{
				for (Card card : hand.getCards())
				{
					if(game.shuffleAfterEachRound())
					{
						dealer.putCardInShoe(card);
					}
					else
					{
						dealer.putCardOnStack(card);
					}
				}
			}
		}
		/* vertel de gui dat een card wordt geplaatst op de stack of shoe (true)
		 * voor gegeven speler
		 */
		if (gui != null) gui.cardsCollected(game.shuffleAfterEachRound());
	}
	/**
	 * Laat naar gelang de instelling in het instellingenscherm
	 * de deler na iedere ronde de kaartstapel schudden, danwel controleren
	 * of er moet worden geschud, en doet dit indien nodig.
	 * @return boolean
	 */
	private boolean shuffleIfNecessary()
	{
		System.out.println("-----------------------------------");
		System.out.println("Checking if shuffling is necessary");
		System.out.println("-----------------------------------");

		//if the setting is to shuffle after each round, shuffle:
		if(game.shuffleAfterEachRound())
		{
			dealer.shuffleShoe();
			if (gui != null) gui.shoeShuffled();
			return true;
		}
		else
		{
			if(dealer.shouldShuffle(game.percentageToShuffleShoeAt()))
			{
				dealer.shuffleShoeWithStack();
				if (gui != null) gui.shoeShuffled();
				return true;
			}
		}
		// er is niet geshuffled.
		return false;
	}
	/**
	 * Geeft een ArrayList terug met Players (inclusief deler!)
	 * @return ArrayList
	 */
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
	/**
	 * Geeft de huidige state terug van de ronde
	 * @return RoundState
	 */
	public RoundState getState() {
		return this.state;
	}

	/**
	 * Geeft de datum terug wanneer deze ronde is gestart
	 */
	public Date getRoundStarted() {
		return this.roundStarted;
	}
}
