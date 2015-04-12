package blackjack;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/** 
 * Het statistiekenscherm van 1 afzonderlijke game
 * 
 * @author Arno van Rossum
 */
public class StatisticsGamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4125247137367678560L;

	/**
	 * De game variablen waaruit de statistieken onttrokken worden
	 */
	private Game game;
	
	/**
	 * In dit paneel worden de beste speler statistieken weergegeven
	 */
	private StatisticsGameBestPlayersPanel gameBestPlayersPanel;
	/**
	 * In dit paneel worden de ronde statistieken weergegeven
	 */
	private StatisticsGameRoundPanel gameRoundsPanel;
	/**
	 * In deze lijst van panelen worden de statistieken per speler weergegeven
	 */
	private ArrayList<StatisticsGamePlayerPanel> gamePlayerPanel = new ArrayList<StatisticsGamePlayerPanel>();
	
	/**
	 * Met deze tabbed paneel staan de knoppen naar de statistieken van een speler/dealer/ronde of beste spelers
	 */
	private JTabbedPane tabbedTypePanel;

	/**
	 * Constructor
	 * @param game
	 */
	public StatisticsGamePanel(Game game) {
		super();
		this.game = game;
		initGUI();
	}
	
	/**
	 * Zet hierin alle dimensies van de panelen van de statistieken
	 * @param d
	 */
	public void setPanelDimensions(Dimension d) {
		gameRoundsPanel.setPreferredSize(d);
		for(StatisticsGamePlayerPanel panel : gamePlayerPanel) {
			panel.setPreferredSize(d);
		}
		gameBestPlayersPanel.setPreferredSize(d);
	}
	
	/**
	 * Initialiseer de GUI
	 */
	private void initGUI() {
		FlowLayout thisLayout = new FlowLayout();
		thisLayout.setAlignment(FlowLayout.LEFT);
		setLayout(thisLayout);
		tabbedTypePanel = new JTabbedPane();
		tabbedTypePanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		gameBestPlayersPanel = new StatisticsGameBestPlayersPanel(game);
		gameRoundsPanel = new StatisticsGameRoundPanel(game);
		tabbedTypePanel.addTab("Beste spelers", null, gameBestPlayersPanel, "Deze tab geeft de weergave van de statistieken van het spel");
		tabbedTypePanel.addTab("Statistieken per spel", null, gameRoundsPanel, "Deze tab geeft de weergave van de statistieken van het spel");
		int i = 0;
		// If no game has been played, the dealer does exist, this filters the dealer from the stats
		if ( this.game.getRounds().size() > 0) {
			for ( Player player : this.game.getPlayers()) {
				gamePlayerPanel.add(new StatisticsGamePlayerPanel(game, player));
				tabbedTypePanel.addTab("Stats van " + player.getName(), null, gamePlayerPanel.get(i), "Deze tab geeft de statistieken van " + player.getName());
				i++;
			}
		}

		add(tabbedTypePanel);
	}
}