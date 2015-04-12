package blackjack;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

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
 * De klasse die het spelerselectiescherm vertegenwoodigt.
 * Het tonen van het Select Players scherm, zodat gegevens van de spelers zoals naam en geboortedatum op kunnen worden gegeven.
 *
 */

/*
 * TODO Het frame moet worden aangepast zodat een foto o.i.d. kan worden weergegeven die één van de spelers representeerd.
 * TODO Er dient een controle plaats te vinden of een speler wel enabled is.
 * TODO Plaating van het frame aanpassen
 */

public class SelectPlayersFrame extends javax.swing.JFrame implements ActionListener{

	private static final long serialVersionUID = 4327364349885889761L;

	/**
	 * Referentie naar het startscherm
	 */
	private StartFrame startFrame;
	/**
	 * Button waarmee het spel gestart kan worden.
	 */
	private JButton btnStartGame;
	/**
	 * Button waarmee dit venster gesloten kan worden.
	 */
	private JButton btnBack;
	/**
	 * Container voor de panelen.
	 */
	private JPanel pnlPlayers;
	/**
	 * Een verzameling van vier selectPlayersPanels.
	 */
	private ArrayList<SelectPlayerPanel> selectPlayersPanel;
	/**
	 * De huidig geselecteerde SelectPlayerPanel.
	 */
	private SelectPlayerPanel currentSelectPlayerPanel;
	/**
	 * Een paneel voor de buttons.
	 */
	private JPanel pnlButtons;
	/**
	 * Een paneel voor speler 1.
	 */
	private JPanel pnlPlayer1;
	/**
	 * Een paneel voor speler 2.
	 */
	private JPanel pnlPlayer2;
	/**
	 * Een paneel voor speler 3.
	 */
	private JPanel pnlPlayer3;
	/**
	 * Een paneel voor speler 4.
	 */
	private JPanel pnlPlayer4;

	/**
	 * Referentie naar het spel.
	 */
	private Game game;

	/**
	 * Alle games die gespeeld zijn.
	 */
	private ArrayList<Game> games;

	/*
	/**
	* Auto-generated main method to display this JFrame

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SelectPlayersFrame inst = new SelectPlayersFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/

	/**
	 * Maakt het scherm aan en toont deze zodat de spelers kunnen worden opgegeven.
	 * @param game Een Game die in een eerder statium is aangemaakt en in deze klasse bekent dient te zijn
	 * @param startFrame Het StartFrame, zodat deze weer getoond kan worden als er op terug gedrukt wordt.
	 */
	public SelectPlayersFrame(StartFrame startFrame, Game game, ArrayList<Game> games) {
		super();
		this.setLocationRelativeTo(null);

		this.startFrame = startFrame;
		this.game = game;
		this.games = games;

		initGUI();
		postInitGUI();
	}

	/**
	 * Maakt alle elementen van het scherm aan en toont deze.
	 */
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Selecteer spelers");
			{
				pnlPlayers = new JPanel();
				getContentPane().add(pnlPlayers, BorderLayout.CENTER);
				pnlPlayers.setLayout(null);
				pnlPlayers.setPreferredSize(new java.awt.Dimension(381, 402));
				pnlPlayers.setSize(320, 460);
				{
					pnlPlayer1 = new JPanel();
					BorderLayout pnlPlayer1Layout = new BorderLayout();
					pnlPlayers.add(pnlPlayer1);
					pnlPlayer1.setLayout(pnlPlayer1Layout);
					pnlPlayer1.setBounds(0, 0, 160, 230);
					pnlPlayer1.setSize(160, 230);
					pnlPlayer1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlPlayer2 = new JPanel();
					BorderLayout pnlPlayer2Layout = new BorderLayout();
					pnlPlayers.add(pnlPlayer2);
					pnlPlayer2.setBounds(160, 0, 160, 460);
					pnlPlayer2.setLayout(pnlPlayer2Layout);
					pnlPlayer2.setSize(160, 230);
					pnlPlayer2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlPlayer3 = new JPanel();
					BorderLayout pnlPlayer3Layout = new BorderLayout();
					pnlPlayers.add(pnlPlayer3);
					pnlPlayer3.setBounds(0, 230, 160, 460);
					pnlPlayer3.setSize(160, 230);
					pnlPlayer3.setLayout(pnlPlayer3Layout);
					pnlPlayer3.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					pnlPlayer4 = new JPanel();
					BorderLayout pnlPlayer4Layout = new BorderLayout();
					pnlPlayers.add(pnlPlayer4);
					pnlPlayer4.setBounds(160, 230, 320, 460);
					pnlPlayer4.setLayout(pnlPlayer4Layout);
					pnlPlayer4.setSize(160, 230);
					pnlPlayer4.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
			}
			{
				pnlButtons = new JPanel();
				getContentPane().add(pnlButtons, BorderLayout.SOUTH);
				pnlButtons.setBounds(7, 350, 525, 35);
				pnlButtons.setLayout(null);
				pnlButtons.setPreferredSize(new java.awt.Dimension(319, 31));
				{
					btnStartGame = new JButton();
					pnlButtons.add(btnStartGame);
					btnStartGame.setText("Start spel");
					btnStartGame.setBounds(190, 0, 129, 25);
					btnStartGame.addActionListener(this);
				}
				{
					btnBack = new JButton();
					pnlButtons.add(btnBack);
					btnBack.setText("Terug");
					btnBack.setBounds(0, 0, 129, 25);
					btnBack.addActionListener(this);
				}
			}
			pack();
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setSize(327, 527);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	/**
	 * Maakt de 4 SelectPlayerPanels aan.
	 */
	private void postInitGUI() {
		selectPlayersPanel = new ArrayList<SelectPlayerPanel>();
		SelectPlayerPanel panel;
		panel = new SelectPlayerPanel();
		panel.setTitle("Speler 1");
		selectPlayersPanel.add(panel);
		pnlPlayer1.add(panel, BorderLayout.CENTER);
		pnlPlayer1.validate();
		panel = new SelectPlayerPanel();
		panel.setTitle("Speler 2");
		selectPlayersPanel.add(panel);
		pnlPlayer2.add(panel, BorderLayout.CENTER);
		panel = new SelectPlayerPanel();
		panel.setTitle("Speler 3");
		selectPlayersPanel.add(panel);
		pnlPlayer3.add(panel, BorderLayout.CENTER);
		panel = new SelectPlayerPanel();
		panel.setTitle("Speler 4");
		selectPlayersPanel.add(panel);;
		pnlPlayer4.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Voegt de spelers die zijn ingesteld en geactiveerd toe aan het spel. Tevens wordt het game frame aangemaakt.
	 */
	public void addPlayers(){
		game.createComponents();
		for (SelectPlayerPanel panel : selectPlayersPanel) {
			currentSelectPlayerPanel=panel;
			if(currentSelectPlayerPanel.isEnabled()){
				Calendar calendarPlayer = currentSelectPlayerPanel.getDate();
				Date birthdayPlayer = calendarPlayer.getTime();
				game.addPlayer(new Player(currentSelectPlayerPanel.getName(), birthdayPlayer, game.getStartingBalance()));
			}
		}
		GraphicalGameFrame gameFrame = new GraphicalGameFrame(startFrame, game, games);
		this.setVisible(false);
		gameFrame.setVisible(true);
	}

	/**
	 * Handeld de gewenste actie af wanneer de speler op een van de knoppen 'Start Game' of 'Terug' heeft geklikt.
	 * @param e Een ActionEvent.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStartGame){
			String naamLijst[] = {"qpwoeirutyu","alskjdhfkdsoie","adfoieqjkds","adsflkjhadf"};
			int teller=0;
			int index=0;
			int errorCode=0;
			boolean error=false;
			String errorMessage="Het spel kan nog niet worden gestart omdat nog niet aan alle voorwaarden wordt voldaan.\n\n";
			for (SelectPlayerPanel panel : selectPlayersPanel) {
				currentSelectPlayerPanel=panel;
				if(currentSelectPlayerPanel.isEnabled()){
					naamLijst[index]=(currentSelectPlayerPanel.getName());
					index++;
					try{
						errorCode = currentSelectPlayerPanel.checkSettings();
						if(errorCode==0){
							teller++;
						}
						else {
							error=true;
							if (errorCode==1||errorCode==3){
								errorMessage +="Voor "+currentSelectPlayerPanel.title.getText()+" is geen naam opgegeven.\n";
							}
							if(errorCode==2||errorCode==3){
								errorMessage +=currentSelectPlayerPanel.title.getText()+" is nog geen 18 en mag niet deelnemen aan het spel.\n";
							}
						}
					}
					catch (NumberFormatException e1){
						errorMessage+= "Het opgegeven jaartal van "+currentSelectPlayerPanel.title.getText()+" kan niet worden ingelezen.\n";
						error=true;
					}
					catch(IOException e1)
					{error=true;}
				}
			}
			if (naamLijst[0].equals(naamLijst[1]) || naamLijst[0].equals(naamLijst[1]) || naamLijst[0].equals(naamLijst[2]) || naamLijst[0].equals(naamLijst[3]) || naamLijst[1].equals(naamLijst[2]) || naamLijst[1].equals(naamLijst[3]) || naamLijst[2].equals(naamLijst[3]) ){
					errorMessage+="Spelers mogen tijdens het spelen van het spel niet eenzelfde naam gebruiken.";
					error=true;
			}

			if(teller==4&&error==false){
				addPlayers();
			}
			if(teller<4 && error==false){
				int reply=JOptionPane.showConfirmDialog(null, "Nog niet alle spelers zijn opgegeven. \nWilt u doorgaan?");
				if(reply == JOptionPane.YES_OPTION){
					addPlayers();
				}
			}
			if (error==true){
				JOptionPane.showMessageDialog(null, errorMessage);
			}
		}
		if(e.getSource() == btnBack){
			this.setVisible(false);
			startFrame.setVisible(true);
		}
	}
}
