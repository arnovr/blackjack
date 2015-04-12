package blackjack;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import javax.swing.Timer;
import javax.swing.WindowConstants;

import java.util.*;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * Het spelscherm.
 *
 */
public class GraphicalGameFrame extends javax.swing.JFrame implements IBlackJackGui, ActionListener {

	private static final long serialVersionUID = 2157925777026685930L;
	private Game game;
	private ArrayList<Game> games;
	/**
	 * Array van spelerpanelen op dit scherm.
	 */
	private ArrayList<GraphicalPlayerControl> playerControls;
	/**
	 *
	 * Referentie naar het spelerpaneel van de huidige speler.
	 */
	private GraphicalPlayerControl currentPlayerControl;
	/**
	 * Het delerpaneel.
	 */
	private GraphicalDealerControl dealerControl;
	/**
	 * Timer om automatische functies af te handelen, zoals het delen van kaarten.
	 */
	private Timer tmrPlay = new Timer(200, this);
	/**
	 * Timer om kaarten verzamelen te animeren.
	 */
	private Timer tmrCollectCards = new Timer(100, this);
	/**
	 * Timer om shoe shuffle te animeren.
	 */
	private Timer tmrShoeShuffle = new Timer(100, this);
	/**
	 * Het knoppenpaneel.
	 */
	private JPanel pnlButton;
	/**
	 * Knop waarmee de speler om een kaart kan vragen.
	 */
	private JButton btnHit;
	/**
	 * Knop waarmee de speler kan 'standen'.
	 */
	private JButton btnStand;
	/**
	 * Knop waarmee de speler kan 'splitten'.
	 */
	private JButton btnSplit;
	/**
	 * Knop waarmee de speler kan 'doubledownen'.
	 */
	private JButton btnDouble;
	/**
	 * Knop waarmee een nieuwe ronde kan worden gestart.
	 */
	private JButton btnNewRound;
	/**
	 * Containerpaneel voor de spelerpanelen.
	 */
	private JPanel pnlPlayers;
	/**
	 * Spelerpaneel van speler 1.
	 */
	private JPanel pnlPlayer1;
	/**
	 * Spelerpaneel van speler 2.
	 */
	private JPanel pnlPlayer2;
	/**
	 * Spelerpaneel van speler 3.
	 */
	private JPanel pnlPlayer3;
	/**
	 * Spelerpaneel van speler 4.
	 */
	private JPanel pnlPlayer4;
	/**
	 * Containerpaneel voor het delerpaneel.
	 */
	private JPanel pnlDealer;
	/**
	 * Help knop.
	 */
	private JButton btnHelp;
	/**
	 * Statistieken knop.
	 */
	private JButton btnStatistics;
	/**
	 * Veld om het bod op te voeren.
	 */
	private JTextField txtBet;
	/**
	 * Informatie veld voor het bieden.
	 */
	private JLabel lblBetInfo;
	/**
	 * Knop waarmee de speler zijn inzet kan bevestigeen.
	 */
	private JButton btnPlaceBet;
	/**
	 * Sluit knop.
	 */
	private JButton btnClose;
	/**
	 * Referentie naar het startscherm
	 */
	private StartFrame startFrame;

	public GraphicalGameFrame(StartFrame startFrame, Game game, ArrayList<Game> games) {
		super();

		playerControls = new ArrayList<GraphicalPlayerControl>();

		this.game = game;
		this.games = games;
		this.startFrame = startFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
		if (this.game != null) this.game.setGui(this);
		initGUI();
		postInitGUI();
		if (this.game != null) this.game.updateCurrentRound();
	}

	private void initGUI() {
		try {
			this.setUndecorated(true);
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				pnlButton = new JPanel();
				getContentPane().add(pnlButton, BorderLayout.SOUTH);
				pnlButton.setPreferredSize(new java.awt.Dimension(281, 37));
				{
					btnHit = new JButton();
					pnlButton.add(btnHit);
					btnHit.setText("Hit");
					btnHit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnHitActionPerformed(evt);
						}
					});
				}
				{
					btnStand = new JButton();
					pnlButton.add(btnStand);
					btnStand.setText("Stand");
					btnStand.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnStandActionPerformed(evt);
						}
					});
				}
				{
					btnSplit = new JButton();
					pnlButton.add(btnSplit);
					btnSplit.setText("Split");
					btnSplit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnSplitActionPerformed(evt);
						}
					});
				}
				{
					btnDouble = new JButton();
					pnlButton.add(btnDouble);
					btnDouble.setText("Double");
					btnDouble.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnDoubleActionPerformed(evt);
						}
					});
				}
				{
					txtBet = new JTextField();
					pnlButton.add(txtBet);
					txtBet.setPreferredSize(new java.awt.Dimension(55, 20));
				}
				{
					btnPlaceBet = new JButton();
					pnlButton.add(btnPlaceBet);
					btnPlaceBet.setText("Bied");
					btnPlaceBet.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnPlaceBetActionPerformed(evt);
						}
					});
				}
				{
					btnNewRound = new JButton();
					pnlButton.add(btnNewRound);
					btnNewRound.setText("Nieuwe ronde");
					btnNewRound.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnNewRoundActionPerformed(evt);
						}
					});
				}
				{
					btnClose = new JButton();
					pnlButton.add(btnClose);
					btnClose.setEnabled(false);
					btnClose.setText("Sluiten");
					btnClose.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnCloseActionPerformed(evt);
						}
					});
				}
			}
			{
				pnlPlayers = new JPanel();
				pnlPlayers.setLayout(null);
				getContentPane().add(pnlPlayers, BorderLayout.CENTER);
				pnlPlayers.setPreferredSize(new java.awt.Dimension(584, 350));
				pnlPlayers.setSize(600, 360);
				pnlPlayers.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent evt) {
						pnlPlayersComponentResized(evt);
					}
				});
				{
					pnlPlayer1 = new JPanel();
					BorderLayout pnlPlayer1Layout = new BorderLayout();
					pnlPlayer1.setLayout(pnlPlayer1Layout);
					pnlPlayers.add(pnlPlayer1);
					pnlPlayer1.setBounds(481, 75, 130, 157);
				}
				{
					pnlPlayer2 = new JPanel();
					BorderLayout pnlPlayer2Layout = new BorderLayout();
					pnlPlayer2.setLayout(pnlPlayer2Layout);
					pnlPlayers.add(pnlPlayer2);
					pnlPlayer2.setBounds(339, 177, 130, 157);
				}
				{
					pnlPlayer3 = new JPanel();
					BorderLayout pnlPlayer3Layout = new BorderLayout();
					pnlPlayer3.setLayout(pnlPlayer3Layout);
					pnlPlayers.add(pnlPlayer3);
					pnlPlayer3.setBounds(158, 177, 130, 157);
				}
				{
					pnlPlayer4 = new JPanel();
					BorderLayout pnlPlayer4Layout = new BorderLayout();
					pnlPlayer4.setLayout(pnlPlayer4Layout);
					pnlPlayers.add(pnlPlayer4);
					pnlPlayer4.setBounds(14, 75, 132, 157);
				}
				{
					pnlDealer = new JPanel();
					BorderLayout pnlDealerLayout = new BorderLayout();
					pnlDealer.setLayout(pnlDealerLayout);
					pnlPlayers.add(pnlDealer);
					pnlDealer.setBounds(257, 8, 114, 157);
					{
						dealerControl = new GraphicalDealerControl();
						pnlDealer.add(dealerControl, BorderLayout.CENTER);
					}
				}
				{
					lblBetInfo = new JLabel();
					pnlPlayers.add(lblBetInfo);
					lblBetInfo.setText("<html>Bieden kan tussen % en %<br> of 0 (om ronde over te slaan)</html>");
					lblBetInfo.setBounds(619, 461, 165, 52);
				}
				{
					btnStatistics = new JButton();
					pnlPlayers.add(btnStatistics);
					btnStatistics.setText("Stats");
					btnStatistics.setBounds(12, 12, 71, 23);
					btnStatistics.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnStatisticsActionPerformed(evt);
						}
					});
				}
				{
					btnHelp = new JButton();
					pnlPlayers.add(btnHelp);
					btnHelp.setText("Help");
					btnHelp.setBounds(12, 47, 71, 23);
					btnHelp.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnHelpActionPerformed(evt);
						}
					});
				}
			}
			pack();
			this.setMinimumSize(new Dimension(800, 600));
			this.setSize(800, 600);
			this.setLocationRelativeTo(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					thisWindowClosed(evt);
				}
			});
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	/**
	 * Maak de controls aan voor dealer en spelers.
	 */
	private void postInitGUI() {

		pnlPlayers.setBackground(new Color(0,220,0));
		pnlButton.setBackground(new Color(0,220,0));
		btnDouble.setBackground(new Color(0,220,0));
		btnSplit.setBackground(new Color(0,220,0));
		btnHit.setBackground(new Color(0,220,0));
		btnStand.setBackground(new Color(0,220,0));
		btnPlaceBet.setBackground(new Color(0,220,0));
		btnNewRound.setBackground(new Color(0,220,0));
		btnClose.setBackground(new Color(0,220,0));
		btnHelp.setBackground(new Color(0,220,0));
		btnStatistics.setBackground(new Color(0,220,0));

		GraphicalPlayerControl control;
		control = new GraphicalPlayerControl();
		playerControls.add(control);
		pnlPlayer1.add(control, BorderLayout.CENTER);
		pnlPlayer1.validate();
		control = new GraphicalPlayerControl();
		playerControls.add(control);
		pnlPlayer2.add(control, BorderLayout.CENTER);
		control = new GraphicalPlayerControl();
		playerControls.add(control);
		pnlPlayer3.add(control, BorderLayout.CENTER);
		control = new GraphicalPlayerControl();
		playerControls.add(control);
		pnlPlayer4.add(control, BorderLayout.CENTER);

		int i = 0;
		for (Player player : this.game.getPlayers()) {
			if ((player.getClass() != Dealer.class))
			{
				if (playerControls.size() > i) playerControls.get(i).setPlayer(player);
			}
			else
			{
				dealerControl.setPlayer(player);
			}
			i++;
		}

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		btnSplit.setEnabled(false);
		btnSplit.setVisible(game.getSplitAllowed());
		btnDouble.setEnabled(false);
		btnDouble.setVisible(game.getDoubleDownAllowed());
		btnPlaceBet.setEnabled(false);
		txtBet.setEnabled(false);
		lblBetInfo.setVisible(false);
		btnNewRound.setEnabled(false);
		btnClose.setEnabled(false);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>Laat de gegeven speler zijn bod doen.</p>
	 * @param player De speler
	 */
	public void letBet(Player player)
	{
		/* Maak de bet controls zichtbaar voor de gebruiker.
		 * De betreffende player control wordt gemarkeerd.
		 * Bij een actie zal de bet worden gezet op de player
		 * Play voor Game moet dan weer worden aangeroepen om
		 * in de volgende state te komen (of de volgende player
		 * te laten bieden)
		 */
		for (GraphicalPlayerControl control : playerControls) {
			if (control.getPlayer() == player) {
				currentPlayerControl = control;
				control.letBet();
				btnPlaceBet.setEnabled(true);
				txtBet.setEnabled(true);

				lblBetInfo.setText("<html>Bieden kan tussen " + game.getMinBetAllowed() + " en " + game.getMaxBetAllowed() + "<br> of 0 (om ronde over te slaan)</html>");
				lblBetInfo.setVisible(true);

				txtBet.requestFocusInWindow();

				// zorg dat de speler zijn originele bod weer te zien krijgt.
				txtBet.setText("" + player.getCurrentHand().getBet());
			}
		}
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>Laat de gegeven speler een kaart trekken/splitten/standen/double-downen.</p>
	 * @param player De speler
	 */
	public void letPlayHand(Player player)
	{
		/* Maak de play controls zichtbaar voor de gebruiker.
		 * De betreffende player control wordt gemarkeerd.
		 * Bij een actie zal de hit/stand/split/doubledown
		 * worden gezet op de player
		 * Play voor Game moet dan weer worden aangeroepen om
		 * in de volgende state te komen
		 * * player is busted
		 * * player kan nog een kaart trekken
		 * * player heeft stand gekozen
		 *
		 */
		/* Alle handen disablen */
		for (GraphicalPlayerControl control : playerControls) {
			control.enableHands(false);
		}
		for (GraphicalPlayerControl control : playerControls) {
			if (control.getPlayer() == player) {
				currentPlayerControl = control;
				control.letPlay();
				btnPlaceBet.setEnabled(false);
				txtBet.setEnabled(false);
				lblBetInfo.setVisible(false);
				btnHit.setEnabled(true);
				btnStand.setEnabled(true);
				btnNewRound.setEnabled(false);
				btnClose.setEnabled(false);
				btnSplit.setEnabled(game.playerCanSplit(player));
				btnDouble.setEnabled(game.playerCanDoubleDown(player));

				btnHit.requestFocusInWindow();
			}
		}
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>Er wordt een kaart gedeeld aan gegeven speler en hand.</p>
	 * @param player De speler
	 * @param hand De hand van de speler
	 * @param card De kaart die gedeeld wordt
	 */
	public void	dealCard(Player player, Hand hand, Card card)
	{
		/* Start een animatie eventueel
		 * De player heeft een kaart gekregen
		 * Geen gui actie starten..
		 * DAT MAG PAS NA RETURN VAN PLAY CALL OP GAME!!!
		 */

		if (dealerControl.getPlayer() == player) {
			dealerControl.updateCardsList(false);
			currentPlayerControl = null;
		} else {
			for (GraphicalPlayerControl control : playerControls) {
				if (control.getPlayer() == player) {
					currentPlayerControl = control;
				}
			}
			currentPlayerControl.updateInfo();
			currentPlayerControl.updateCardsList();
		}

		dealerControl.dealCard();

		btnPlaceBet.setEnabled(false);
		txtBet.setEnabled(false);
		lblBetInfo.setVisible(false);
		btnNewRound.setEnabled(false);
		btnClose.setEnabled(false);

		/* timer starten om door te gaan */
		tmrPlay.start();
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>De gegeven hand van de gegeven speler wordt uitbetaald.</p>
	 * @param player De speler die gewonnen heeft
	 * @param hand De hand die gewonnen heeft
	 */
	public void payOut(Player player, Hand hand)
	{
		for (GraphicalPlayerControl control : playerControls) {
			if (control.getPlayer() == player) {
				control.updateCardsList();
			}
		}
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>De kaarten zijn verzameld.</p>
	 * @param inShoe Geeft aan of de kaarten naar de shoe of de stack zijn gezet.
	 */	public void cardsCollected(boolean inShoe)
	{
		/* timer starten om door te gaan en de kaarten te laten verzamelen*/
		dealerControl.setCollectToShoe(inShoe);

		tmrCollectCards.start();
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>De shoe is geschud.</p>
	 */
	public void shoeShuffled()
	{
		/* timer starten om door te gaan en de shoe te laten shufflen*/
		tmrShoeShuffle.start();
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>Er is een nieuwe ronde gestart.</p>
	 */
	public void newRoundStarted()
	{
		/* zet nu de players op de gewenste controls */
		int index = 0;
		for (Player player : this.game.getPlayers()) {
			if (player.getClass() == Dealer.class) {
				dealerControl.setPlayer(player);
				dealerControl.updateCardsList(false);
				dealerControl.enableHands(true);
			}
			else
			{
				if (index < 4) {
					playerControls.get(index).setPlayer(player);
					playerControls.get(index).updateCardsList();
					playerControls.get(index).enableHands(true);
				}
				index++;
			}
		}
		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		btnSplit.setEnabled(false);
		btnDouble.setEnabled(false);
		btnPlaceBet.setEnabled(false);
		txtBet.setEnabled(false);
		lblBetInfo.setVisible(false);
		btnNewRound.setEnabled(false);
		btnClose.setEnabled(false);

		tmrPlay.stop();
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>Laat de spelers een nieuwe ronde starten.</p>
	 */
	public void letStartNewRound()
	{
		/* we zetten de controls op enabled */
		/* na de return van play call op Game
		 * kunnen we eventueel weer wat animeren..
		 * * uitbetalen
		 * * en kaarten terug nemen..
		 */
		for (GraphicalPlayerControl control : playerControls) {
			control.updateCardsList();
			control.updateInfo();
			control.enableHands(true);
		}
		dealerControl.updateCardsList(true);
		dealerControl.enableHands(true);

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		btnSplit.setEnabled(false);
		btnDouble.setEnabled(false);
		btnNewRound.setEnabled(true);
		btnClose.setEnabled(true);

		btnNewRound.requestFocusInWindow();
	}
	/**
	 * Een gebruiker heeft op de bied-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnPlaceBetActionPerformed(ActionEvent evt) {
		System.out.println("btnPlaceBet.actionPerformed, event="+evt);

		try {
			game.playerBet(currentPlayerControl.getPlayer(), Integer.parseInt(txtBet.getText().toString()));
			// zet bod op huidige player control
			currentPlayerControl.updateBet();
			currentPlayerControl.updateInfo();
			// game engine weer laten spelen
			this.game.updateCurrentRound();
		}
		catch (BlackJackException bjex)
		{
			JOptionPane.showMessageDialog(this, bjex.getMessage());
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
	/**
	 * Er is een actie gestart (meestal is dit van de timer).
	 * @param e Event informatie
	 */
	public void actionPerformed(ActionEvent e) {
		// is dit de timer?
		if (e.getSource() == tmrPlay) {
			tmrPlay.stop();
			System.out.println("***********************************");
			System.out.println("Game timer fired");
			System.out.println("***********************************");

			// de game-engine weer laten spelen
			game.updateCurrentRound();
		}
		// is dit de kaart verzamel timer?
		else if (e.getSource() == tmrCollectCards) {
			System.out.println("***********************************");
			System.out.println("CollectCards timer fired");
			System.out.println("***********************************");
			if (dealerControl.collectCards() != false)
			{
				tmrCollectCards.stop();

				// de game-engine weer laten spelen
				game.updateCurrentRound();
			}
		}
		// is dit de shoe shuffle timer?
		else if (e.getSource() == tmrShoeShuffle)
		{
			System.out.println("***********************************");
			System.out.println("Shoe timer fired");
			System.out.println("***********************************");
			if (dealerControl.shuffleShoe() != false)
			{
				tmrShoeShuffle.stop();

				// de game-engine weer laten spelen
				game.updateCurrentRound();
			}
		}
	}
	/**
	 * Een gebruiker heeft op de hit-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnHitActionPerformed(ActionEvent evt) {
		System.out.println("btnHit.actionPerformed, event="+evt);

		// laat de speler een kaart krijgen uit de kaarten collectie
		game.playerPullCard(currentPlayerControl.getPlayer());
	}
	/**
	 * Een gebruiker heeft op de split-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnSplitActionPerformed(ActionEvent evt) {
		System.out.println("btnSplit.actionPerformed, event="+evt);

		try {
			// de player laten splitten
			game.playerSplit(currentPlayerControl.getPlayer());
			// de kaartenlijsten voor het huidige control bijwerken
			currentPlayerControl.updateCardsList();

			// de game-engine laten spelen
			game.updateCurrentRound();
		}
		catch (BlackJackException ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
	/**
	 * Een gebruiker heeft op de double-down-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnDoubleActionPerformed(ActionEvent evt) {
		System.out.println("btnDouble.actionPerformed, event="+evt);

		try {
			// de player laten 'doubledownen'
			game.playerDoubleDown(currentPlayerControl.getPlayer());
			// de kaartenlijsten voor het huidige control bijwerken
			currentPlayerControl.updateInfo();

			// de game-engine laten spelen
			game.updateCurrentRound();
		}
		catch (BlackJackException ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
	/**
	 * Een gebruiker heeft op de stand-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnStandActionPerformed(ActionEvent evt) {
		System.out.println("btnStand.actionPerformed, event="+evt);

		// de player gaat standen
		currentPlayerControl.getPlayer().stand();
		// de kaartenlijst van het huidige control bijwerken
		currentPlayerControl.updateCardsList();

		// control in de stand stand zetten
		currentPlayerControl.stand();

		// de game-engine laten spelen
		game.updateCurrentRound();
	}

	/**
	 * Een gebruiker heeft op de nieuwe-ronde-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnNewRoundActionPerformed(ActionEvent evt) {
		System.out.println("btnNewRound.actionPerformed, event="+evt);

		/* eerst de knop disablen zodat er niet opnieuw kan geklikt worden */
		btnNewRound.setEnabled(false);
		btnClose.setEnabled(false);
		/*
		 * Omdat de ronde klaar is zal er nu automatisch
		 * een nieuwe ronde worden aangemaakt.
		 */
		game.updateCurrentRound();
	}

	/**
	 * Een gebruiker heeft op de sluiten-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnCloseActionPerformed(ActionEvent evt) {
		System.out.println("btnClose.actionPerformed, event="+evt);

		/* eerst de knop disablen zodat er niet opnieuw kan geklikt worden */
		btnNewRound.setEnabled(false);
		btnClose.setEnabled(false);

		closeGame();

		startFrame.setVisible(true);
		setVisible(false);

		/**
		 * TODO: Michael Hier het startscherm tonen en een pointer naar de game doorgeven.
		 *
		 */
	}
	/**
	 * Sluit het huidige game af.
	 */
	private void closeGame()
	{
		/*
		 * Deze ronde netjes afronden.
		 */
		game.finishCurrentRound();
		/*
		 * En de game vertellen dat dit afgelopen is.
		 */
		game.end();

		/* stop alle timers */
		tmrCollectCards.stop();
		tmrPlay.stop();
		tmrShoeShuffle.stop();
	}
	/**
	 * Het scherm wordt geresized. Alle controls worden netjes op hun plaats gehouden.
	 * @param evt Event informatie.
	 */
	private void pnlPlayersComponentResized(ComponentEvent evt) {
		System.out.println("pnlPlayers.componentResized, event="+evt);

		int pnlWidth = pnlPlayers.getWidth() / 4 - 4;
		int pnlHeight = pnlPlayers.getHeight() / 2 - 4;
		int pnlLeft = 2;
		int pnlTopDealer = 2;
		int pnlTop1 = pnlHeight / 3 * 2 + 4;
		int pnlTop2 = pnlHeight + 4;

		pnlPlayer4.setBounds(pnlLeft, pnlTop1, pnlWidth, pnlHeight);
		pnlLeft += pnlWidth + 2;
		pnlPlayer3.setBounds(pnlLeft, pnlTop2, pnlWidth, pnlHeight);

		/* dealer is aligned met player 3 */
		pnlDealer.setBounds(pnlLeft / 2, pnlTopDealer, pnlWidth * 3 + 2, pnlHeight / 3 * 2);

		pnlLeft += pnlWidth + 2;
		pnlPlayer2.setBounds(pnlLeft, pnlTop2, pnlWidth, pnlHeight);
		pnlLeft += pnlWidth + 2;
		pnlPlayer1.setBounds(pnlLeft, pnlTop1, pnlWidth, pnlHeight);

		pnlPlayers.revalidate();

		lblBetInfo.setBounds(pnlPlayers.getWidth() - lblBetInfo.getWidth() - 10,
							 pnlTop1 + pnlHeight + 10,
							 lblBetInfo.getWidth(),
							 lblBetInfo.getHeight());
	}
	/**
	 * Een gebruiker heeft op de help-knop gedrukt.
	 * @param evt Event informatie.
	 */
	private void btnHelpActionPerformed(ActionEvent evt) {
		System.out.println("btnHelp.actionPerformed, event="+evt);
		HelpFrame helpFrame = new HelpFrame(this.startFrame);
		helpFrame.setVisible(true);
	}
	/**
	 * Een gebruiker heeft op de statistieken-knop gedrukt.
	 * @param evt Event informatie.
	 */
	private void btnStatisticsActionPerformed(ActionEvent evt) {
		System.out.println("btnStatistics.actionPerformed, event="+evt);
		StatisticsFrame statisticsFrame = new StatisticsFrame(games);
		statisticsFrame.setVisible(true);
	}
	/**
	 * Het venster is afgesloten met Alt-F4 of kruisje.
	 * Het huidige game wordt afgesloten.
	 * @param evt Event informatie.
	 */
	private void thisWindowClosed(WindowEvent evt) {
		System.out.println("this.windowClosed, event="+evt);

		closeGame();

		startFrame.setVisible(true);
	}
}
