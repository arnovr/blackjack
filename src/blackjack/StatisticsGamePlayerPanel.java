package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

/** 
 * Dit is het sub statistieken scherm dat statistieken van de spelers laat zien
 * 
 * @author Arno van Rossum
 */
public class StatisticsGamePlayerPanel extends StatisticsGameMenuListPanel {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895019002331567519L;

	/**
	 * De menu items van de spelers.
	 */
	protected String[] menuItems = {"Overzicht", "Ronden overzicht", "Score door tijd", "Geld gewed door tijd"};
	/**
	 * De menu items wanneer de speler een dealer is.
	 */
	protected String[] menuDealerItems = {"Overzicht", "Ronden overzicht", "Score door tijd"};

	/**
	 * Op 1 of andere manier kreeg ik de hoogte van de kaart control er niet goed, in de grid maakt hij ze zo klein mogelijk ( overflow zeg maar )
	 * vandaar keiharde pixels op de kaart control
	 * 
	 * Hoogte in pixels van de kaart control
	 */
	private int cardControlHeight = 120;

	/**
	 * Constructor
	 * @param game
	 * @param player
	 */
	public StatisticsGamePlayerPanel(Game game, Player player) {
		super(game, player);
		this.setMenuItems(menuItems, menuDealerItems);
		initGUI();
	}
	
	/**
	 * Initialiseer de GUI
	 */
	private void initGUI() {
		layout = new BorderLayout();
		setLayout(layout);

		initializeMenu();

		JPanel panelDescription = new JPanel();
		panelDescription.setLayout(null);
		JLabel description = new JLabel();
		description.setText("<html>" +
				"Hier zijn de statistieken van " + this.selectedPlayer.getName() + " zichtbaar, je kunt hier opvragen wat zijn scores waren." +
				"</html>");
		description.setBounds(6, 4, 700, 120);
		panelDescription.setPreferredSize(new java.awt.Dimension(300,300));
		panelDescription.add(description);
		add(panelDescription, BorderLayout.CENTER);
	}

	/**
	 * Bij deze methode wordt 1 van de selectie componenten opgepakt
	 */
	public void valueChanged(ListSelectionEvent e) {
		if ( e.getValueIsAdjusting() == true ) {
			System.out.println("Selected: " + menuItems[menu.getSelectedIndex()]);
			showPanel(menu.getSelectedIndex());
		}
	}
	/**
	 * Laat de geselecteerde paneel zien
	 * @param menuIndex
	 */
	private void showPanel(int menuIndex) {
		// Remove the old layout
		if ( layout.getLayoutComponent(BorderLayout.CENTER) != null ) {
			remove(layout.getLayoutComponent(BorderLayout.CENTER));
		}

		JPanel panel = new JPanel();
		// Add menu
		add(menu, BorderLayout.WEST);
		switch(menuIndex) {
			case 0:
				panel = getPlayerOverview();
			break;
			case 1:
				panel = showCardsPerRound();
			break;
			case 2:
				panel = getHandScoreGraphPanel();
			break;
			case 3:
				panel = getHandBetGraphPanel();
			break;
		}
		menu.setSelectedIndex(menuIndex);
		add(panel, BorderLayout.CENTER);

		// Hiermee voert hij daadwerkelijk het removen en adden van de paneel uit
		validate();
	}
	
	/**
	 * Geeft een paneel terug waarin alle gegevens van de speler staan
	 * @return JPanel
	 */
	private JPanel getPlayerOverview() {
		JPanel panel = new JPanel(); 
		panel.setBounds(4, 26, getWidth() - menu.getWidth() - 5, getHeight() - 10); // 10 offset
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.LEFT);
		panel.setLayout(flow);

		JPanel panelStatistics = new JPanel();
		GridLayout grid = new GridLayout(0, 2);
		panelStatistics.setLayout(grid);
		panelStatistics.setPreferredSize(new Dimension((getWidth() - menu.getWidth() - 5), (getHeight() - 10)));

		panelStatistics.add(getLabelWithText("Naam"));
		panelStatistics.add(getLabelWithText(selectedPlayer.getName()));

		if ( this.selectedPlayer.getClass() != Dealer.class ) {
			panelStatistics.add(getLabelWithText("Geboortedatum"));
			panelStatistics.add(getLabelWithText(parseDate(selectedPlayer.getDateOfBirth(), true)));
		}

		int roundsPlayed = 0;
		int numBlackjacks = 0;
		int numSplits = 0;
		int numDoubles = 0;
		for(Round round : this.game.getRounds()) {
			for(Player player : round.getPlayers()) {
				if ( player.getName().equals(selectedPlayer.getName())) {
					roundsPlayed++;
					for(Hand hand : player.getHands() ) {
						if ( hand.isBlackjack() ) {
							numBlackjacks++;
						}
						if ( player.hasSplitted() ) {
							numSplits++;
						}
						if ( player.hasDoubleDowned() ) {
							numDoubles++;
						}
					}
				}
			}
		}

		panelStatistics.add(getLabelWithText("Rondes gespeeld"));
		panelStatistics.add(getLabelWithText("" + roundsPlayed));
		panelStatistics.add(getLabelWithText("Handen gespeeld"));
		panelStatistics.add(getLabelWithText("" + game.getHandsPlayed(selectedPlayer)));
		panelStatistics.add(getLabelWithText("Handen gewonnen/verloren/even"));
		panelStatistics.add(getLabelWithText("" + game.getHandsWon(selectedPlayer) + "/" + game.getHandsLost(selectedPlayer) + "/" + game.getHandsEven(selectedPlayer)));
		panelStatistics.add(getLabelWithText("Bedrag gewonnen/verloren/totaal"));
		panelStatistics.add(getLabelWithText("" + game.getAmountWon(selectedPlayer) + "/" + game.getAmountLost(selectedPlayer) + "/" + selectedPlayer.getBalance()));
		panelStatistics.add(getLabelWithText("Aantal blackjacks"));
		panelStatistics.add(getLabelWithText("" + numBlackjacks));
		panelStatistics.add(getLabelWithText("Aantal splits"));
		panelStatistics.add(getLabelWithText("" + numSplits));
		panelStatistics.add(getLabelWithText("Aantal doubledowns"));
		panelStatistics.add(getLabelWithText("" + numDoubles));

		panel.add(panelStatistics);
		return panel;
	}

	/**
	 * Maak een label aan met de gegeven tekst
	 * @param s De tekst
	 * @return JLabel
	 */
	private JLabel getLabelWithText(String s) {
		JLabel lbl = new JLabel();
		lbl.setText(s);
		return lbl;
	}
	
	/**
	 * Laat alle kaarten zien van de rondes gespeeld
	 * @return JPanel
	 */
	private JPanel showCardsPerRound() {
		JPanel panelStatistics = new JPanel();
		panelStatistics.setLayout(null);

		JScrollPane scrollStatisticsPanel = new JScrollPane();
		scrollStatisticsPanel.setViewportView(getCardsPerRoundPanel());
		panelStatistics.add(scrollStatisticsPanel);
		JLabel lblStatisticsDescription = new JLabel();
		lblStatisticsDescription.setText("Gespeelde rondes");
		lblStatisticsDescription.setBounds(6, 4, getWidth() - menu.getWidth(), 20);
		scrollStatisticsPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollStatisticsPanel.setBounds(4, 26, getWidth() - menu.getWidth() - 5, getHeight() - lblStatisticsDescription.getHeight() - 10); // 10 offset
		scrollStatisticsPanel.setBackground(Color.BLACK);

		panelStatistics.add(lblStatisticsDescription);
		panelStatistics.add(scrollStatisticsPanel);

		return panelStatistics;
	}
	/**
	 * Geeft een verzamel paneel terug met alle kaarten van elke ronde die deze persoon in deze game heeft gespeeld.
	 * @return JPanel
	 */
	private JPanel getCardsPerRoundPanel() {
		int panelLength = 0;
		for(Round round : this.game.getRounds()) {
			for(Player player : round.getPlayers()) {
				if ( player.getName().equals(selectedPlayer.getName())) {
					panelLength++;
				}
			}
		}

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));

		int i = 0;
		for(Round round : this.game.getRounds()) {
			for(Player player : round.getPlayers()) {	
				if ( player.getName().equals(selectedPlayer.getName())) {
					JLabel lblRound = new JLabel();
					Date date = round.getRoundStarted();
				    lblRound.setText("<html>Ronde: " + i + "<br>Gespeeld om: <br>" + parseDate(date) + "</html>");
					lblRound.setBounds(5,0,100,cardControlHeight);

					panel.add(lblRound);
					for(Hand hand : player.getHands()) {
						GraphicalHandPanel gPanel = new GraphicalHandPanel();
						gPanel.setPreferredSize(new java.awt.Dimension(100, cardControlHeight));
						gPanel.setHand(hand, true);
						panel.add(gPanel);
					}
					// Fill up the grid when the person did not a split action
					if ( player.getHands().size() == 1 ) {
						JPanel bogusPanel = new JPanel();
						bogusPanel.setPreferredSize(new java.awt.Dimension(100, cardControlHeight));
						panel.add(bogusPanel);
					}
				}
			}
			i++;
		}

		// Op 1 of andere manier wanneer je maar 1 rij in gridlayout hebt is de layout vernagelt, even een bogus panel erbij dus
		if ( i == 1 ) {
			JPanel bogusPanel = new JPanel();
			bogusPanel.setPreferredSize(new java.awt.Dimension(100, cardControlHeight));
			panel.add(bogusPanel);
			JPanel bogusPanelx = new JPanel();
			bogusPanelx.setPreferredSize(new java.awt.Dimension(100, cardControlHeight));
			panel.add(bogusPanelx);
		}
		
		return panel;
	}
	
	/**
	 * Geeft een grafiek terug welke de gemiddelde hand score is van deze persoon
	 * @return JScrollPane
	 */
	private JPanel getHandScoreGraphPanel() {
		int[] y = new int[this.game.getRounds().size()];
		int index = 0;
		for(Round round : this.game.getRounds()) {
			if ( round.getState() != Round.RoundState.BET ) {
				int roundHands = 0;
				int roundTotal = 0;
				for ( Player player : round.getPlayers() ) {
					if (player.getName().equals(selectedPlayer.getName()) ) {
						for( Hand hand : player.getHands() ) {
							roundHands++;
							roundTotal += hand.getScore();
						}
					}
				}
				y[index] = Math.round(roundTotal / roundHands);
				System.out.println("Avg score: " + y[index] + " - totaal: " + roundTotal + " - handen: " + roundHands);
				index++;
			}
		}

	    return this.getGraphPanel("Hieronder zie je de gemiddelde hand score per ronde van deze speler door de tijd heen.", "tijd", y, 30, 5);
	}
	/**
	 * Geeft een grafiek terug welke de gemiddelde wed score is van deze persoon
	 * @return JScrollPane
	 */
	private JPanel getHandBetGraphPanel() {
		int[] y = new int[this.game.getRounds().size()];
		int index = 0;
		int highestBet = 0;
		for(Round round : this.game.getRounds()) {
			if ( round.getState() != Round.RoundState.BET ) {
				int roundHands = 0;
				int roundTotal = 0;
				for ( Player player : round.getPlayers() ) {
					if (player.getName().equals(selectedPlayer.getName()) ) {
						for( Hand hand : player.getHands() ) {
							roundHands++;
							roundTotal += hand.getBet();
							if ( hand.getBet() > highestBet ) {
								highestBet = hand.getBet();
							}
						}
					}
				}
				y[index] = Math.round(roundTotal / roundHands);
				System.out.println("Avg bet: " + y[index] + " - totaal: " + roundTotal + " - handen: " + roundHands);
				index++;
			}
		}
	    return this.getGraphPanel("Hieronder zie je de gemiddelde hand weddenschap per ronde van deze speler door de tijd heen.", "tijd", y, highestBet, 10);
	}
}