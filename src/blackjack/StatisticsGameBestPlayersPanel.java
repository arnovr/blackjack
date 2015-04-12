package blackjack;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
 * Dit is het sub statistieken scherm dat statistieken van de beste spelers laat zien
 * 
 * @author Arno van Rossum
 */
public class StatisticsGameBestPlayersPanel  extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8551731953901835312L;

	/**
	 * De game variablen waaruit de statistieken onttrokken worden
	 */
	private Game game;
	
	/**
	 * Constructor
	 * @param game
	 */
	public StatisticsGameBestPlayersPanel(Game game) {
		super();
		this.game = game;
		initGUI();
	}
	/**
	 * Initialiseer de GUI
	 */
	public void initGUI() {
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		
		int x1 = 6;
		int x2 = 186;

		JLabel lblMostPlayed = new JLabel();
		lblMostPlayed.setText("Meeste handen gespeeld: ");
		lblMostPlayed.setBounds(x1, 4, 200, 16);
		JLabel lblMostPlayedAnswer = new JLabel();
		lblMostPlayedAnswer.setText(getPlayerMostPlayed());
		lblMostPlayedAnswer.setBounds(x2, 4, 200, 16);

		JLabel lblBestGambler = new JLabel();
		lblBestGambler.setText("Meeste handen gewonnen: ");
		lblBestGambler.setBounds(x1, 24, 200, 16);
		JLabel lblBestGamblerAnswer = new JLabel();
		lblBestGamblerAnswer.setText(getPlayerMostWon());
		lblBestGamblerAnswer.setBounds(x2, 24, 200, 16);
		
		JLabel lblWorstGambler = new JLabel();
		lblWorstGambler.setText("Meeste handen verloren: ");
		lblWorstGambler.setBounds(x1, 44, 200, 16);
		JLabel lblWorstGamblerAnswer = new JLabel();
		lblWorstGamblerAnswer.setText(getPlayerMostLost());
		lblWorstGamblerAnswer.setBounds(x2, 44, 200, 16);
		
		
		JLabel lblBigSpender = new JLabel();
		lblBigSpender.setText("Meeste geld verspeeld: ");
		lblBigSpender.setBounds(x1, 64, 200, 16);

		JLabel lblBigSpenderAnswer = new JLabel();
		lblBigSpenderAnswer.setText(getPlayerMostMoneySpended());
		lblBigSpenderAnswer.setBounds(x2, 64, 200, 16);
		
		
		JLabel lblBigWinner = new JLabel();
		lblBigWinner.setText("Meeste geld gewonnen: ");
		lblBigWinner.setBounds(x1, 84, 200, 16);

		JLabel lblBigWinnerAnswer = new JLabel();
		lblBigWinnerAnswer.setText(getPlayerMostMoneyWon());
		lblBigWinnerAnswer.setBounds(x2, 84, 200, 16);
		
		//lblBestGambler.setBounds(x, y, width, height)
		add(lblMostPlayed, BorderLayout.WEST);
		add(lblMostPlayedAnswer, BorderLayout.CENTER);
		add(lblBestGambler, BorderLayout.WEST);
		add(lblBestGamblerAnswer, BorderLayout.CENTER);
		add(lblWorstGambler, BorderLayout.WEST);
		add(lblWorstGamblerAnswer, BorderLayout.CENTER);
		add(lblBigSpender, BorderLayout.WEST);
		add(lblBigSpenderAnswer, BorderLayout.CENTER);
		add(lblBigWinner, BorderLayout.WEST);
		add(lblBigWinnerAnswer, BorderLayout.CENTER);
		// Bogus to fill up, else it will bork
		add(new JLabel(), BorderLayout.WEST);
		add(new JLabel(), BorderLayout.CENTER);
	}
	
	/**
	 * Geef de naam van de speler wie de meeste rondes heeft gespeeld
	 * @return String
	 */
	private String getPlayerMostPlayed() {
		Player playerMost = null;
		int Most = 0;
		for(Round round : this.game.getRounds()) {
			for ( Player player : round.getPlayers() ) {
				if ( this.game.getHandsPlayed(player) > Most && player.getClass() != Dealer.class ) {
					Most = this.game.getHandsPlayed(player);
					playerMost = player;
				}
			}
		}
		if ( playerMost == null ) {
			return "N/A";
		}
		return playerMost.getName();
	}
	
	/**
	 * Geef de naam van de speler wie het meeste heeft gewonnen
	 * @return String
	 */
	private String getPlayerMostWon() {
		Player playerMost = null;
		int Most = 0;
		for(Round round : this.game.getRounds()) {
			for ( Player player : round.getPlayers() ) {
				if ( this.game.getHandsWon(player) > Most && player.getClass() != Dealer.class ) {
					Most = this.game.getHandsWon(player);
					playerMost = player;
				}
			}
		}
		if ( playerMost == null ) {
			return "N/A";
		}
		return playerMost.getName();		
	}

	/**
	 * Geef de naam van de speler wie het meeste heeft verloren
	 * @return String
	 */
	private String getPlayerMostLost() {
		Player playerMost = null;
		int Most = 0;
		for(Round round : this.game.getRounds()) {
			for ( Player player : round.getPlayers() ) {
				if ( this.game.getHandsLost(player) > Most && player.getClass() != Dealer.class ) {
					Most = this.game.getHandsLost(player);
					playerMost = player;
				}
			}
		}
		if ( playerMost == null ) {
			return "N/A";
		}
		return playerMost.getName();		
	}
	
	/**
	 * Geef de naam van de speler wie het meeste heeft gespendeerd
	 * @return String
	 */
	private String getPlayerMostMoneySpended() {
		Player playerMost = null;
		int Most = 0;
		for(Round round : this.game.getRounds()) {
			for ( Player player : round.getPlayers() ) {
				if ( this.game.getAmountLost(player) > Most && player.getClass() != Dealer.class ) {
					Most = this.game.getAmountLost(player);
					playerMost = player;
				}
			}
		}
		if ( playerMost == null ) {
			return "N/A";
		}
		return playerMost.getName();		
	}
	
	/**
	 * Geef de naam van de speler wie het meeste geld heeft gewonnen
	 * @return String
	 */
	private String getPlayerMostMoneyWon() {
		Player playerMost = null;
		double most = 0;
		for(Round round : this.game.getRounds()) {
			for ( Player player : round.getPlayers() ) {
				if ( this.game.getAmountWon(player) > most && player.getClass() != Dealer.class ) {
					most = this.game.getAmountWon(player);
					playerMost = player;
				}
			}
		}
		if ( playerMost == null ) {
			return "N/A";
		}
		return playerMost.getName();		
	}
}