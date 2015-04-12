package blackjack;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JOptionPane;

import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.SwingUtilities;

import java.util.*;
/**
 * Het spelscherm.
 *
 */
public class SimpleGameFrame extends javax.swing.JFrame implements IBlackJackGui, ActionListener {

	private static final long serialVersionUID = 2157925777026685930L;
	private Game game;
	/**
	 * Array van spelerpanelen op dit scherm.
	 */
	private ArrayList<SimplePlayerControl> playerControls;
	/**
	 *
	 * Referentie naar het spelerpaneel van de huidige speler.
	 */
	private SimplePlayerControl currentPlayerControl;
	/**
	 * Het delerpaneel.
	 */
	private SimpleDealerControl dealerControl;
	/**
	 * Timer om automatische kaart functies af te handelen, zoals het delen van kaarten.
	 */
	private Timer tmrPlay = new Timer(100, this);
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
	 * Control waarmee de speler zijn inzet kan bepalen.
	 */
	private JSpinner spBet;
	/**
	 * Knop waarmee de speler zijn inzet kan bevestigeen.
	 */
	private JButton btnPlaceBet;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimpleGameFrame inst = new SimpleGameFrame(null);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public SimpleGameFrame(Game game) {
		super();

		playerControls = new ArrayList<SimplePlayerControl>();

		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
		if (this.game != null) this.game.setGui(this);
		initGUI();
		postInitGUI();
		if (this.game != null) this.game.updateCurrentRound();
	}

	private void initGUI() {
		try {
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
					SpinnerListModel spBetModel =
						new SpinnerListModel(
								new String[] { "10", "20" , "30" , "40" , "50" , "60" , "70" });
					spBet = new JSpinner();
					pnlButton.add(spBet);
					spBet.setModel(spBetModel);
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
					pnlPlayer1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlPlayer2 = new JPanel();
					BorderLayout pnlPlayer2Layout = new BorderLayout();
					pnlPlayer2.setLayout(pnlPlayer2Layout);
					pnlPlayers.add(pnlPlayer2);
					pnlPlayer2.setBounds(339, 177, 130, 157);
					pnlPlayer2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlPlayer3 = new JPanel();
					BorderLayout pnlPlayer3Layout = new BorderLayout();
					pnlPlayer3.setLayout(pnlPlayer3Layout);
					pnlPlayers.add(pnlPlayer3);
					pnlPlayer3.setBounds(158, 177, 130, 157);
					pnlPlayer3.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlPlayer4 = new JPanel();
					BorderLayout pnlPlayer4Layout = new BorderLayout();
					pnlPlayer4.setLayout(pnlPlayer4Layout);
					pnlPlayers.add(pnlPlayer4);
					pnlPlayer4.setBounds(14, 75, 132, 157);
					pnlPlayer4.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlDealer = new JPanel();
					BorderLayout pnlDealerLayout = new BorderLayout();
					pnlDealer.setLayout(pnlDealerLayout);
					pnlPlayers.add(pnlDealer);
					pnlDealer.setBounds(257, 8, 114, 157);
					pnlDealer.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					{
						dealerControl = new SimpleDealerControl();
						pnlDealer.add(dealerControl, BorderLayout.CENTER);
					}
				}
			}
			pack();
			this.setLocationRelativeTo(null);
			this.setSize(640, 480);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	/**
	 * Maak de controls aan voor dealer en spelers.
	 */
	private void postInitGUI() {

		SimplePlayerControl control;
		control = new SimplePlayerControl();
		playerControls.add(control);
		pnlPlayer1.add(control, BorderLayout.CENTER);
		pnlPlayer1.validate();
		control = new SimplePlayerControl();
		playerControls.add(control);
		pnlPlayer2.add(control, BorderLayout.CENTER);
		control = new SimplePlayerControl();
		playerControls.add(control);
		pnlPlayer3.add(control, BorderLayout.CENTER);
		control = new SimplePlayerControl();
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
		spBet.setEnabled(false);
		btnNewRound.setEnabled(false);
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
		for (SimplePlayerControl control : playerControls) {
			if (control.getPlayer() == player) {
				currentPlayerControl = control;
				control.letBet();
				btnPlaceBet.setEnabled(true);
				spBet.setEnabled(true);
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
		if (currentPlayerControl != null) {

		}
		for (SimplePlayerControl control : playerControls) {
			if (control.getPlayer() == player) {
				currentPlayerControl = control;
				control.letPlay();
				btnPlaceBet.setEnabled(false);
				spBet.setEnabled(false);
				btnHit.setEnabled(true);
				btnStand.setEnabled(true);
				btnNewRound.setEnabled(false);
				btnSplit.setEnabled(player.getCurrentHand().isSplittable() && player.hasSplitted() == false);
				btnDouble.setEnabled(player.getCurrentHand().isDoubleDownable());
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
			dealerControl.updateCardsList();
			currentPlayerControl = null;
		} else {
			for (SimplePlayerControl control : playerControls) {
				if (control.getPlayer() == player) {
					currentPlayerControl = control;
				}
			}
			currentPlayerControl.updateInfo();
			currentPlayerControl.updateCardsList();
		}

		btnPlaceBet.setEnabled(false);
		spBet.setEnabled(false);
		btnNewRound.setEnabled(false);

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
		for (SimplePlayerControl control : playerControls) {
			if (control.getPlayer() == player) {
				control.updateCardsList();
			}
		}
	}
	/**
	 * <p>Wordt aangeroepen vanuit de game-domein-klassen.</p>
	 * <p>De kaarten zijn verzameld.</p>
	 */
	public void cardsCollected(boolean inShoea)
	{
		/* timer starten om door te gaan en de kaarten te laten verzamelen*/
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
				dealerControl.updateCardsList();
			}
			else
			{
				if (index < 4) {
					playerControls.get(index).setPlayer(player);
					playerControls.get(index).updateCardsList();
				}
				index++;
			}
		}
		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		btnSplit.setEnabled(false);
		btnDouble.setEnabled(false);
		btnPlaceBet.setEnabled(false);
		spBet.setEnabled(false);
		btnNewRound.setEnabled(false);

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
		for (SimplePlayerControl control : playerControls) {
			control.updateCardsList();
			control.updateInfo();
		}
		dealerControl.updateCardsList();

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		btnSplit.setEnabled(false);
		btnDouble.setEnabled(false);
		btnNewRound.setEnabled(true);
	}
	/**
	 * Een gebruiker heeft op de bied-knop gedrukt.
	 * @param evt Event informatie
	 */
	private void btnPlaceBetActionPerformed(ActionEvent evt) {
		System.out.println("btnPlaceBet.actionPerformed, event="+evt);

		try {
			game.playerBet(currentPlayerControl.getPlayer(), Integer.parseInt(spBet.getValue().toString()));
			// zet bod op huidige player control
			currentPlayerControl.setBet(spBet.getValue().toString());
			// game engine weer laten spelen
			this.game.updateCurrentRound();
		}
		catch (BlackJackException ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
	/**
	 * Er is een actie gestart (meestal is dit van de timer).
	 * @param e Event informatie
	 */
	public void actionPerformed(ActionEvent e) {
		// is dit de play timer?
		if (e.getSource() == tmrPlay) {
			tmrPlay.stop();
			System.out.println("***********************************");
			System.out.println("Play timer fired");
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
		else if (e.getSource() == tmrShoeShuffle) {
			System.out.println("***********************************");
			System.out.println("ShoeShuffle timer fired");
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

		/*
		 * Omdat de ronde klaar is zal er nu automatisch
		 * een nieuwe ronde worden aangemaakt.
		 */
		game.updateCurrentRound();
	}

	/**
	 * Het scherm wordt geresized. Alle controls worden netjes op hun plaats gehouden.
	 * @param evt Event informatie
	 */
	private void pnlPlayersComponentResized(ComponentEvent evt) {
		System.out.println("pnlPlayers.componentResized, event="+evt);

		int pnlWidth = pnlPlayers.getWidth() / 4 - 4;
		int pnlHeight = pnlPlayers.getHeight() / 2 - 4;
		int pnlLeft = 2;
		int pnlTopDealer = 2;
		int pnlTop1 = pnlHeight / 2 + 4;
		int pnlTop2 = pnlHeight + 4;

		pnlPlayer4.setBounds(pnlLeft, pnlTop1, pnlWidth, pnlHeight);
		pnlLeft += pnlWidth + 2;
		pnlPlayer3.setBounds(pnlLeft, pnlTop2, pnlWidth, pnlHeight);

		/* dealer is alignt met player 3 */
		pnlDealer.setBounds(pnlLeft, pnlTopDealer, pnlWidth * 2 + 2, pnlHeight);

		pnlLeft += pnlWidth + 2;
		pnlPlayer2.setBounds(pnlLeft, pnlTop2, pnlWidth, pnlHeight);
		pnlLeft += pnlWidth + 2;
		pnlPlayer1.setBounds(pnlLeft, pnlTop1, pnlWidth, pnlHeight);

		pnlPlayers.revalidate();
	}

}
