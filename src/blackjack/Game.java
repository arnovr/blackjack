package blackjack;
import java.util.*;
/**
 * Klasse die een blackjack-spel vertegenwoordigt.
 *
 */
public class Game
{
	/**
	 * Geeft weer of de game beeindigd is.
	 */
	private boolean hasEnded = false;
	/**
	 * Geeft weer of er mag worden gedoubledownd.
	 */
	private boolean doubleDownAllowed;
	/**
	 * Geeft weer of de speler rood mag staan.
	 */
	private boolean negativeBalanceAllowed;
	/**
	 * Geeft weer of de speler mag splitten.
	 */
	private boolean splitAllowed;
	/**
	 * Geeft weer of er na iedere ronde geschud moet worden.
	 */
	private boolean shuffleAfterEachRound;
	/**
	 * De maximaal toegestane inzet.
	 */
	private int maxBet;
	/**
	 * De minimale inzet.
	 */
	private int minBet;
	/**
	 * De handwaarde waarop de deler moet standen.
	 */
	private int dealerStandsAt;
	/**
	 * Het startsaldo van de spelers.
	 */
	private int playerStartingBalance;
	/**
	 * Het aantal kaartspellen in de kaartstapel.
	 */
	private int numberOfDecksInShoe;
	/**
	 * Percentage waarbij de kaartstapel moet worden geschud.
	 */
	private int percentageToShuffleShoeAt;
	/**
	 * Een ArrayList van spelers.
	 */
	private ArrayList<Player> players;
	/**
	 * Een ArrayList van ronden.
	 */
	private ArrayList<Round> rounds;
	/**
	 * Referentie naar een deler-object.
	 */
	private Dealer	dealer;
	/**
	 * Referentie naar een kaartstapel-object.
	 */
	private Shoe	shoe;
	/**
	 * De huidige ronde.
	 */
	private Round	currentRound;
	/**
	 * Een referentie naar het spelscherm.
	 */
	private IBlackJackGui gui = null;
	/**
	 * Maakt een nieuw spel aan.
	 */
	public Game()
	{
		makeSettings();
	}
	/**
	 * Initialiseerd zichzelf met gegeven game
	 * @param game Game waaruit gegevens worden gekopieerd
	 */
	public void init(Game game)
	{
		/* alleen de niet runtime spullen kopieren */
		this.doubleDownAllowed = game.doubleDownAllowed;
		this.negativeBalanceAllowed = game.negativeBalanceAllowed;
		this.splitAllowed = game.splitAllowed;
		this.shuffleAfterEachRound = game.shuffleAfterEachRound;
		this.maxBet = game.maxBet;
		this.minBet = game.minBet;
		this.dealerStandsAt = game.dealerStandsAt;
		this.playerStartingBalance = game.playerStartingBalance;
		this.numberOfDecksInShoe = game.numberOfDecksInShoe;
		this.percentageToShuffleShoeAt = game.percentageToShuffleShoeAt;
		this.shuffleAfterEachRound = game.shuffleAfterEachRound;
		this.dealer = game.dealer;
		this.shoe = game.shoe;
		this.gui = game.gui;
	}
	/**
	 * Maakt van zichzelf een copy en geeft deze terug
	 * @return Game
	 */
	public Game getCopy()
	{
		Game game = new Game();

		game.init(this);

		return game;
	}
	/**
	 * Zet referentie naar spelscherm.
	 * @param gui De gebruikersinterface voor de game.
	 */
	public void setGui(IBlackJackGui gui)
	{
		this.gui = gui;
	}
	/**
	 * Voegt de meegegeven speler toe aan het spel. Zorgt ervoor dat de deler het laatste in de lijst blijft.
	 * @param player een Player
	 */
	public void addPlayer(Player player)
	{
		int i = players.indexOf(dealer);
		if (i >= 0)
		{
			/* als gevonden index >=0, dan zit de dealer in de lijst, player op deze plek inserten */
			players.add(i, player);
		}
		else
		{
			/* gewoon aan het einde toevoegen.. */
			players.add(player);
		}
	}
	/**
	 * Testfunctie voor de speel functionaliteit van het spel.
	 */
	public void playTest()
	{
		while(!hasEnded)
		{
			for(int i = 1 ;  i <= 1 ; i++)
			{
				playRoundTest();
				hasEnded = true;
			}
		}
	}
	/**
	 * De huidige ronde bijwerken met de veranderde spelers/handen.
	 */
	public void updateCurrentRound()
	{
		while (currentRound == null || currentRound.updateRound() != false) {
			if (currentRound != null) currentRound.setGui(null);
			newRound();
		}
	}
	/**
	 * De huidige ronde bijwerken en afronden.
	 */
	public void finishCurrentRound()
	{
		currentRound.setGui(null);
		currentRound.updateRound();
		currentRound = null;
	}
	/**
	 * Zet de Game als afgerond.
	 */
	public void end()
	{
		hasEnded = true;
	}
	public boolean isEnded()
	{
		return hasEnded;
	}
	/**
	 * Returnt alle gespeelde ronden.
	 * @return een ArrayList met Rounds
	 */
	public ArrayList<Round> getRounds()
	{
		if ( rounds == null ) rounds = new ArrayList<Round>();
		return rounds;
	}
	/**
	 * Returnt de spelers.
	 * @return een ArrayList van Players
	 */
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	/**
	 * Testfunctie voor de ronde spelen.
	 */
	public void playRoundTest()
	{
		Round round = new Round(this, players, dealer, gui);
		rounds.add(round);
		currentRound = round;
		currentRound.playTest();
	}
	/**
	 * Maak een nieuwe ronde aan. Hierbij worden copies gemaakt van de spelers en deler gemaakt en op de ronde gezet.
	 * Voor dit game worden deze speler en deler gezet als huidige spelers en deler.
	 */
	private void newRound()
	{
		ArrayList<Player> players = new ArrayList<Player>();

		for (Player oldPlayer : this.players) {
			/* wanneer de players wel bestaan
			 * maar niet aan een ronde zijn gekoppeld
			 * zijn we de oude players kwijt..
			 * maar dat moet geen probleem zijn.
			 */
			Player player = oldPlayer.getCopy();

			players.add(player);
			if (player.getClass() == Dealer.class) {
				dealer = (Dealer)player;
			}
		}
		/* eerst de nieuwe lijst met players zetten */
		this.players = players;
		/* daarna de nieuwe ronde aanmaken */
		Round round = new Round(this, players, dealer, gui);

		rounds.add(round);
		currentRound = round;
	}
	/**
	 * Gegeven speler vraagt kaart aan.
	 * De ronde zorgt ervoor dat de speler deze kaart krijgt.
	 * @param player De speler die de kaart trekt
	 */
	public void playerPullCard(Player player)
	{
		currentRound.dealCard(player, false);
	}
	/**
	 * <p>Gegeven speler doet zijn bod.</p>
	 * <p>Als het bod niet voldoet aan de instellingen dan exceptie.</p>
	 * @param player De speler die het bod doet
	 * @param amount Het bod
	 * @throws BlackJackException
	 */
	public void playerBet(Player player, int amount)
		throws BlackJackException
	{
		if (amount != 0 && (amount > maxBet || amount < minBet))
		{
			throw new BlackJackException("Ongeldig bod.", "Game instellingen");
		}
		if (negativeBalanceAllowed == false && player.getBalance() < amount)
		{
			throw new BlackJackException("Speler mag niet rood staan.", "Game instellingen");
		}
		player.bet(amount);
	}
	/**
	 * <p>Gegeven speler split.</p>
	 * <p>Als splitten niet toegestaan is dan exceptie.</p>
	 * @param player De speler die split
	 * @throws BlackJackException
	 */
	public void playerSplit(Player player)
		throws BlackJackException
	{
		if (splitAllowed == false)
		{
			throw new BlackJackException("Speler mag niet splitten.", "Game instellingen");
		}
		else if (negativeBalanceAllowed == false && player.getBalance() < player.getCurrentHand().getBet())
		{
			throw new BlackJackException("Speler mag niet rood staan.", "Game instellingen");
		}
		/* en splitten */
		player.split();
	}
	/**
	 * <p>Kan gegeven speler splitten.</p>
	 * <p>Als splitten niet toegestaan is dan false.</p>
	 * @param player De speler die split
	 * @throws BlackJackException
	 * @return boolean
	 */
	public boolean playerCanSplit(Player player)
	{
		if (splitAllowed == false)
		{
			return false;
		}
		else if (negativeBalanceAllowed == false && player.getBalance() < player.getCurrentHand().getBet())
		{
			return false;
		}
		/* kan splitten */
		return player.getCurrentHand().isSplittable() && player.hasSplitted() == false;
	}
	/**
	 * <p>Gegeven speler doet een doubledown.</p>
	 * <p>Als doubledown niet toegestaan dan exceptie.</p>
	 * @param player
	 * @throws BlackJackException
	 */
	public void playerDoubleDown(Player player)
		throws BlackJackException
	{
		if (doubleDownAllowed == false)
		{
			throw new BlackJackException("Speler mag niet 'doubledownen'.", "Game instellingen");
		}
		else if (negativeBalanceAllowed == false && player.getBalance() < player.getCurrentHand().getBet())
		{
			throw new BlackJackException("Speler mag niet rood staan.", "Game instellingen");
		}
		/* en doubledownen */
		player.doubleDown();
		/* na doubledown ook kaart trekken en standen */
		playerPullCard(player);
		player.getCurrentHand().stand();
	}
	/**
	 * <p>Kan gegeven speler doubledownen.</p>
	 * <p>Als doubledown niet toegestaan dan false.</p>
	 * @param player
	 * @throws BlackJackException
	 */
	public boolean playerCanDoubleDown(Player player)
	{
		if (doubleDownAllowed == false)
		{
			return false;
		}
		else if (negativeBalanceAllowed == false && player.getBalance() < player.getCurrentHand().getBet())
		{
			return false;
		}
		/* kan doubledownen */
		return player.getCurrentHand().isDoubleDownable();
	}
	/**
	 * Returnt of het spel afgelopen is.
	 * @return boolean
	 */
	public boolean hasEnded()
	{
		return hasEnded;
	}
	/**
	 * Returnt of er na iedere ronde geschud moet worden.
	 * @return boolean
	 */
	public boolean shuffleAfterEachRound()
	{
		return shuffleAfterEachRound;
	}
	/**
	 * Returnt het percentage waarbij de kaartstapel geschud moet worden.
	 * @return int
	 */
	public int percentageToShuffleShoeAt()
	{
		return percentageToShuffleShoeAt;
	}
	/**
	 * Zet de spelinstellingen aan de hand van de parameters.
	 */
	public void makeSettings()
	{
		doubleDownAllowed			= true;
		negativeBalanceAllowed		= false;
		splitAllowed				= true;
		shuffleAfterEachRound		= true;

		maxBet						= 100;
		minBet						= 10;
		dealerStandsAt				= 16;
		playerStartingBalance		= 1000;
		numberOfDecksInShoe			= 6;
		percentageToShuffleShoeAt	= 60;
	}
	/**
	 * Returnt of doubledownen is toegestaan.
	 * @return boolean
	 */
	public boolean getDoubleDownAllowed() {
		return doubleDownAllowed;
	}
	/**
	 * Returnt of rood staan is toegestaan.
	 * @return boolean
	 */
	public boolean getNegativeBalanceAllowed() {
		return negativeBalanceAllowed;
	}

	/**
	 * Returnt of splitten is toegestaan.
	 * @return boolean
	 */
	public boolean getSplitAllowed() {
		return splitAllowed;
	}
	/**
	 * Returnt de maximaal toegestane inzet.
	 * @return int
	 */
	public int getMaxBetAllowed() {
		return maxBet;
	}
	/**
	 * Returnt de minimaal toegestane inzet.
	 * @return int
	 */
	public int getMinBetAllowed() {
		return minBet;
	}
	/**
	 * Returnt de handwaarde waarbij de dealer moet standen.
	 * @return int
	 */
	public int getDealerStandsAt() {
		return dealerStandsAt;
	}
	/**
	 * Returnt het startsaldo.
	 * @return int
	 */
	public int getStartingBalance() {
		return playerStartingBalance;
	}
	/**
	 * Returnt hoeveel kaartspellen er in de kaartstapel moeten.
	 * @return int
	 */
	public int getNumberOfDecksInShoe() {
		return numberOfDecksInShoe;
	}
	/**
	 * Zet alle spelinstellingen aan de hand van de parameters.
	 * @param doubleDownAllowed boolean
	 * @param negativeBalanceAllowed boolean
	 * @param splitAllowed boolean
	 * @param shuffleAfterEachRound boolean
	 * @param maxBet int
	 * @param minBet int
	 * @param dealerStandsAt int
	 * @param playerStartingBalance int
	 * @param numberOfDecksInShoe int
	 * @param percentageToShuffleShoeAt int
	 */
	public void setSettings(boolean doubleDownAllowed,
							boolean negativeBalanceAllowed,
							boolean splitAllowed,
							boolean shuffleAfterEachRound,
							int maxBet,
							int minBet,
							int dealerStandsAt,
							int playerStartingBalance,
							int numberOfDecksInShoe,
							int percentageToShuffleShoeAt)
	{
		this.doubleDownAllowed			=	doubleDownAllowed;
		this.negativeBalanceAllowed		=	negativeBalanceAllowed;
		this.splitAllowed				=	splitAllowed;
		this.shuffleAfterEachRound		=	shuffleAfterEachRound;
		this.maxBet						=	maxBet;
		this.minBet						=	minBet;
		this.dealerStandsAt				=	dealerStandsAt;
		this.playerStartingBalance		=	playerStartingBalance;
		this.numberOfDecksInShoe		=	numberOfDecksInShoe;
		this.percentageToShuffleShoeAt	=	percentageToShuffleShoeAt;
	}

	/**
	 * geeft het aantal handen gespeeld van deze speler
	 * @param player Player
	 * @return int
	 */
	public int getHandsPlayed(Player player) {
		int handsPlayed = 0;
		for(Round r : this.getRounds()) {
			for(Player p : r.getPlayers() ) {
				if (player.getName().equals(p.getName()) ) {
					handsPlayed += p.getHands().size();
				}
			}
		}
		return handsPlayed;
	}
	/**
	 * Geeft het aantal handen gewonnen van de speler
	 * @param player Player
	 * @return int
	 */
	public int getHandsWon(Player player) {
		int handsWon = 0;
		for(Round r : this.getRounds()) {
			for(Player p : r.getPlayers() ) {
				if (player.getName().equals(p.getName()) ) {
					for(Hand h : p.getHands()) {
						if ( h.getWon() ) {
							handsWon++;
						}
					}
				}
			}
		}
		return handsWon;
	}

	/**
	 * Geeft het aantal handen verloren van de speler
	 * @param player
	 * @return int
	 */
	public int getHandsLost(Player player) {
		int handsLost = 0;
		for(Round r : this.getRounds()) {
			for(Player p : r.getPlayers() ) {
				if (player.getName().equals(p.getName()) ) {
					for(Hand h : p.getHands()) {
						if ( h.getLost() ) {
							handsLost++;
						}
					}
				}
			}
		}
		return handsLost;
	}
	/**
	 * Geeft het aantal even hands van de speler
	 * @param player
	 * @return int
	 */
	public int getHandsEven(Player player) {
		int handsEven = 0;
		for(Round r : this.getRounds()) {
			for(Player p : r.getPlayers() ) {
				if (player.getName().equals(p.getName()) ) {
					for(Hand h : p.getHands()) {
						if ( h.getEven() ) {
							handsEven++;
						}
					}
				}
			}
		}
		return handsEven;
	}
	/**
	 * Geeft het bedrag gewonnen van de speler.
	 * @param player een object van het type Player
	 * @return int
	 */
	public double getAmountWon(Player player) {
		double amountWon = 0;
		for(Round round : this.getRounds()) {
			for(Player p : round.getPlayers() ) {
				if (player.getName().equals(p.getName()) ) {
					for(Hand h : p.getHands()) {
						if ( h.getWon() ) {
							if (h.isBlackjack())
							{
								amountWon += h.getBet() * 1.25;
							}
							else
							{
								amountWon += h.getBet();
							}
						}
					}
				}
			}
		}
		return amountWon;
	}

	/**
	 * Geeft het door de speler verloren bedrag
	 * @param player een object van het type Player
	 * @return int
	 */
	public int getAmountLost(Player player) {
		int amountLost = 0;
		for(Round r : this.getRounds()) {
			for(Player p : r.getPlayers() ) {
				if (player.getName().equals(p.getName()) ) {
					//M: zoiets?
					for(Hand h : p.getHands()) {
						if ( h.getLost() ) {
							amountLost += h.getBet();
						}
					}
				}
			}
		}
		return amountLost;
	}

	/**
	 *Maakt de componenten players, rounds, shoe en dealer aan.
	 */
	public void createComponents(){
		players = new ArrayList<Player>();
		rounds	= new ArrayList<Round>();
		shoe	= new Shoe(numberOfDecksInShoe);
		dealer  = new Dealer(shoe);

		/* ook de dealer is een player */
		addPlayer(dealer);

		shoe.shuffle();
	}
}
