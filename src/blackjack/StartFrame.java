package blackjack;
import javax.swing.JButton;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.WindowConstants;

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
 * Het startscherm.
 *
 */
public class StartFrame extends javax.swing.JFrame implements ActionListener{
	/**
	 *
	 */
	private static final long serialVersionUID = 6403807741208581555L;
	/**
	 * <p>Button waarmee een nieuwe game gestart kan worden.</p>
	 * <p>Opent het spelerselectiescherm.</p>
	 */
	private JButton 		newGameButton;
	/**
	 * Button waarmee het instellingenscherm kan worden geopend.
	 */
	private JButton 		settingsButton;
	/**
	 * Button waarmee het statistiekenscherm kan worden geopend.
	 */
	private JButton 		statisticsButton;
	/**
	 * Button waarmee het hulpscherm kan worden geopend.
	 */
	private JButton 		helpButton;
	/**
	 * Referentie naar het Game-object.
	 */
	private Game 			currentGame;
	/**
	 * De lijst met alle gespeelde games
	 */
	private ArrayList<Game>	games = new ArrayList<Game>();
	/**
	 * Maakt een nieuw object van het type StartFrame en initialiseert/toont deze.
	 */
	public StartFrame() {
		super();
		createNewGame();
		initGUI();
	}
	/**
	 * Initialiseert de GUI.
	 */
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle("Blackjack");
			{
				newGameButton = new JButton();
				getContentPane().add(newGameButton);
				newGameButton.setText("Nieuw spel");
				newGameButton.setBounds(132, 42, 116, 21);
				newGameButton.setVerifyInputWhenFocusTarget(false);
				newGameButton.addActionListener(this);
			}
			{
				settingsButton = new JButton();
				getContentPane().add(settingsButton);
				settingsButton.setText("Instellingen");
				settingsButton.setBounds(132, 84, 116, 21);
				settingsButton.addActionListener(this);
			}
			{
				statisticsButton = new JButton();
				getContentPane().add(statisticsButton);
				statisticsButton.setText("Statistieken");
				statisticsButton.setBounds(132, 126, 116, 21);
				statisticsButton.addActionListener(this);
			}
			{
				helpButton = new JButton();
				getContentPane().add(helpButton);
				helpButton.setText("Help");
				helpButton.setBounds(132, 168, 116, 21);
				helpButton.addActionListener(this);
				this.setResizable(false);
			}
			pack();
			this.setLocationRelativeTo(null);
			//HIERRRRR!
			this.addWindowListener(new WindowAdapter()
			{
				public void windowClosed(WindowEvent evt)
				{
					thisWindowClosed(evt);
				}
			});
			this.setSize(401, 276);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGameButton)
		{
			/* als het huidige game als is beeindigd, dan een nieuwe creeeren */
			if (currentGame.isEnded() != false) createNewGame();
			/* spel starten */
			SelectPlayersFrame selectPlayersFrame = new SelectPlayersFrame(this, currentGame, games);
			selectPlayersFrame.setVisible(true);

			/*GameFrame gameFrame = new GameFrame(game);
			gameFrame.setVisible(true);*/
		}
		if(e.getSource() == settingsButton)
		{
			/* als het huidige game als is beeindigd, dan een nieuwe creeeren */
			if (currentGame.isEnded() != false) createNewGame();
			/* spel configureren */
			SettingsFrame settingsFrame = new SettingsFrame(this, currentGame);
			settingsFrame.setVisible(true);
		}
		if(e.getSource() == statisticsButton)
		{
			StatisticsFrame statisticsFrame = new StatisticsFrame(games, this);
			statisticsFrame.setVisible(true);
		}

		if(e.getSource() == helpButton)
		{
			HelpFrame helpFrame = new HelpFrame(this);
			helpFrame.setVisible(true);
		}
		setVisible(false);
	}
	///HIERRRRRRR
	private void thisWindowClosed(WindowEvent evt) {
		System.out.println("this.windowClosed, event="+evt);
		/* forceer de applicatie om af te sluiten */
		System.exit(0);
	}

	public void createNewGame()
	{
		if (currentGame != null)
		{
			currentGame = currentGame.getCopy();
		}
		else
		{
			currentGame = new Game();
		}
		games.add(currentGame);
	}
}
