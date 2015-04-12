package blackjack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/** 
 * Dit is het sub statistieken scherm welke de statistieken per game laat zien
 * 
 * @author Arno van Rossum
 */
public class StatisticsGameRoundPanel extends StatisticsGameMenuListPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7912495922074294067L;

	/**
	 * De menu items van de rondes
	 */
	public String[] menuItems = {"Rondes", "Scores", "Score door tijd", "Geld gewed door tijd"};
	/**
	 * De menu items van de rondes
	 */
	public String[] menuDealerItems = {"Rondes", "Scores", "Score door tijd", "Geld gewed door tijd"};
	
	/**
	 * Hierin staat de informatie over de rondes die gespeeld zijn
	 */
	private JPanel panelRoundsPlayed;
	
	/**
	 * Constructor
	 * @param game
	 */
	public StatisticsGameRoundPanel(Game game) {
		super(game);

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
				"Hier zijn de statistieken van het spel zichtbaar, zoals welke rondes gespeeld zijn <br>" +
				"en wie wat in welke ronde gescoord heeft.<br>" +
				"Daarbij is te zien hoe vaak de spelers hebben gewonnen of verloren. <br>" +
				"En kun je via de grafieken zien wat de gemiddelde score van de spelers is door de tijd heen, <br>" +
				"of wat zij gemiddeld gewed hebben door de tijd heen. <br>" +
				"Wil je weten wat een speler individueel heeft gedaan, pak dan het tabblad van die speler.</html>");
		description.setBounds(6, 4, 700, 120);
		panelDescription.setPreferredSize(new java.awt.Dimension(300,300));
		panelDescription.add(description);
		add(panelDescription, BorderLayout.CENTER);
	}
	
	/**
	 * Met deze methode wordt de klik op het menu item opgevangen
	 */
	public void valueChanged(ListSelectionEvent e) {
		if ( e.getValueIsAdjusting() == true ) {
			System.out.println("Selected: " + menuItems[menu.getSelectedIndex()]);
			showPanel(menu.getSelectedIndex());
		}
	}
	
	/**
	 * Selecteer de juiste menu item
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
				panel = getRoundsPlayedPanel();
			break;
			case 1:
				panel = getPlayerOverviewPanel();
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
		
		// Hiermee doet ie het ook daadwerkelijk
		validate();
	}
	
	
	/**
	 * Gets the history rounds played panel
	 * @return JPanel
	 */
	private JPanel getRoundsPlayedPanel() {

		JPanel panelTop = new JPanel();
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.LEFT);
		//panelTop.setBounds(10, 10, );
		panelTop.setPreferredSize(new Dimension(getWidth() - menu.getWidth() - 5, getHeight() - 10));
		panelTop.setLayout(flow);
		
		panelRoundsPlayed = new JPanel();

		panelTop.add(panelRoundsPlayed);
		panelRoundsPlayed.setLayout(new BorderLayout());

		JScrollPane scrollRoundsPlayed = new JScrollPane();
		panelRoundsPlayed.add(scrollRoundsPlayed, BorderLayout.CENTER);

		JLabel lblRoundsPlayedDescription = new JLabel();
		lblRoundsPlayedDescription.setText("<html>Hier is te zien welke speler wat heeft gescored in welke ronde. <br>Wanneer de cel rood is heeft de speler verloren van de dealer, indien de cel groen is heeft de speler gewonnen, bij grijs heeft de speler gelijk gespeeld.</html>");
		lblRoundsPlayedDescription.setPreferredSize(new Dimension(getWidth() - menu.getWidth() - 5, 50));
		scrollRoundsPlayed.setPreferredSize(new Dimension(getWidth() - menu.getWidth() - 5, getHeight() - lblRoundsPlayedDescription.getHeight() - 10));
		panelRoundsPlayed.add(lblRoundsPlayedDescription, BorderLayout.NORTH);
		scrollRoundsPlayed.setViewportView(getRoundPlayedTable());

		return panelTop;
	}
	
	/**
	 * Creeert een tabel waarin per ronde staat wie hoeveel score heeft gehaald en wat hij of zij gewonnen heeft.
	 * @return JTable Een tabel met statistieken
	 */
	private JTable getRoundPlayedTable() {		
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
	
		model.addColumn("#");
		model.addColumn("Gespeeld om");
		int columnCount = 2;
		// Load each player, and make a column for him
		ArrayList<String> playerNames = new ArrayList<String>();
		try {
			for(Round round : this.game.getRounds()) {
				for(Player player : round.getPlayers() ) {
					if ( !playerNames.contains(player.getName()) ) {
						playerNames.add(player.getName());
						model.addColumn(player.getName());

						columnCount++;
					}
				}
			}
		}
		catch ( NullPointerException e ) {
		}
		for(int i = 0; i < columnCount; i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(new RoundsCellRenderer(this.game));
		}

		try {
			// Vanuit rondes de players ophalen die hebben hands.
			for(Round round : this.game.getRounds()) {
			    Vector<Object> row = new Vector<Object>();
			    // Show round number 
			    row.add(this.game.getRounds().indexOf(round) + 1);
			    // Round number
				row.add(parseDate(round.getRoundStarted()));
				
				// Opslaan welke speler deze rondes hebben gewonnen
				//ArrayList<String> playerNames = new ArrayList<String>();
				
				try {
					// Walk through the columns
					for(String playerName : playerNames) {
						// Walk through the players for each round
						for(Player player : round.getPlayers()) {
							// When this player of column is player in round, calculate score
							if ( player.getName().equals(playerName) ) {
								String Score = "";
								int index = 0;
								// Walk the hands in this game of this player
								for(Hand hand : player.getHands()) {
									index++;
									Score += hand.getScore();
									// when multiple hands are played that round, delimiter it with a /
									if ( index < player.getHands().size() )
									{
										Score += " / ";
									}
								}
								row.add(Score);
							}
						}
					}
				}
				catch ( NullPointerException e ) {
				}

				model.addRow(row);
			}
		}
		catch ( NullPointerException e ) {
		}


		TableColumn hdr = table.getTableHeader().getColumnModel().getColumn(0);
				hdr.setPreferredWidth(30);
				hdr.setMaxWidth(30);
				hdr.setMinWidth(30);
				hdr.setWidth(30);
		int columnWidth = 130;
			TableColumn hdr2 = table.getTableHeader().getColumnModel().getColumn(1);
				hdr2.setPreferredWidth(columnWidth);
				hdr2.setMaxWidth(columnWidth);
				hdr2.setMinWidth(columnWidth);
				hdr2.setWidth(columnWidth);
		return table;
	}
	
	
	/**
	 * gets the overview of the players
	 * @return JPanel
	 */
	private JPanel getPlayerOverviewPanel() {
		JPanel panelStatistics = new JPanel();
		panelStatistics.setLayout(null);

		JScrollPane scrollStatisticsPanel = new JScrollPane();
		scrollStatisticsPanel.setViewportView(getTableStatistics());
		panelStatistics.add(scrollStatisticsPanel);
		JLabel lblStatisticsDescription = new JLabel();
		lblStatisticsDescription.setText("Hier is per speler te zien hoeveel rondes hij of zij gespeeld gewonnen en verloren heeft.");
		lblStatisticsDescription.setBounds(6, 4, getWidth() - menu.getWidth(), 20);
		scrollStatisticsPanel.setBounds(4, 26, getWidth() - menu.getWidth() - 5, getHeight() - lblStatisticsDescription.getHeight() - 10); // 10 offset
		panelStatistics.add(lblStatisticsDescription);
		panelStatistics.add(scrollStatisticsPanel);

		return panelStatistics;
	}
	/**
	 * Geeft een tabel terug met statistieken waarin per speler te zien is hoeveel hij gewonnen verloren etc heeft.
	 * 
	 * @return JTable
	 */
	private JTable getTableStatistics() {
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Speler");
		model.addColumn("Gespeeld");
		model.addColumn("Gewonnen");
		model.addColumn("Verloren");
		model.addColumn("Gelijk");
		model.addColumn("Geld gewonnen");
		model.addColumn("Geld verloren");

		// Walk each players
		try {
			ArrayList<String> playerNames = new ArrayList<String>();
			for(Round round : this.game.getRounds()) {
				for(Player player : round.getPlayers() ) {
					// When this player of column is player in round, calculate score
					if (!playerNames.contains(player.getName()) ) {
						Vector<Object> row = new Vector<Object>();
					    row.add(player.getName());
					    row.add(game.getHandsPlayed(player));
					    row.add(game.getHandsWon(player));
					    row.add(game.getHandsLost(player));
					    row.add(game.getHandsEven(player));
					    row.add(game.getAmountWon(player));
					    row.add(game.getAmountLost(player));
					    model.addRow(row);
					    playerNames.add(player.getName());
					}
				}
			}
		}
		catch ( NullPointerException e ) {
		}

		return table;
	}
	
	
	/**
	 * Geeft een grafiek met de gemiddelde score van de rondes terug
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
						for( Hand hand : player.getHands() ) {
							roundHands++;
							roundTotal += hand.getScore();
						}
				}
				y[index] = Math.round(roundTotal / roundHands);
				System.out.println("Avg score: " + y[index] + " - totaal: " + roundTotal + " - handen: " + roundHands);
				index++;
			}
		}

	    return this.getGraphPanel("Hieronder zie je de gemiddelde hand score per ronde door de tijd heen.", "tijd", y, 30, 5);
	}
	
	/**
	 * Geeft een grafiek met de gemiddelde wed score van de rondes terug
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
					// Dealer wed niet mee
					if (player.getClass() != Dealer.class) {
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
	    return this.getGraphPanel("Hieronder zie je de gemiddelde hand weddenschap per ronde door de tijd heen.", "tijd", y, highestBet, 10);
	}

}
