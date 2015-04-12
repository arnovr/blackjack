package blackjack;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
 * De klasse die het instellingenscherm vertegenwoordigt.
 * Het scherm waarin de instellingen van het spel kan worden opgegeven. Deze instellingen zijn voor een gedeelijk bepalend voor het verloop van het spel.
 * Deze instellingen kunnen alleen voorafgaand van een spel worden gedaan, tijdens een spel is dit niet meer mogelijk.
 *
 */

/*
 * TODO Bij de radiobuttongroepen moet een extra check worden gemaakt voor het uitzonderlijke geval dat geen van de keuzes is opgegeven.
 * TODO Controles inbouwen zodat geen onjuiste waarden kunnen worden opgegeven, zoals bij minimale inzet en wanneer de dealer moet 'standen'
 * TODO Controleren of het inlezen van de settings juist is verlopen
 */

public class SettingsFrame extends javax.swing.JFrame implements ActionListener{

	private static final long serialVersionUID = 2710039089450665569L;
	/**
	 * Een JPanel, in dit paneel bevinden zich de attributen met betrekking tot de instellingen.
	 */
	private JPanel pnlSettings;
	/**
	 * Een JPanel, in dit paneel bevinden zicht de knoppen.
	 */
	private JPanel pnlButtons;
	/**
	 * Een JButton, een knop waarmee de speler de instellingen kan opslaan zonder het instellingen scherm af te sluiten.
	 */
	private JButton btnApply;
	/**
	 * Een JButton, een knop waarmee de speler de begin waarden kan laden.
	 */
	private JButton btnResetDefaults;
	/**
	 * Een JButton, een knop waarmee de speler het instellingen scherm af kan sluiten zonder dat eventuele wijzigingen worden opgeslagen.
	 */
	private JButton btnCancel;
	/**
	 * Een JButton, een knop waarmee de speler de instellingen opslaat en het instellingen scherm sluit.
	 */
	private JButton btnOk;
	/**
	 * Een ButtonGroup waarmee de speler aan kan geven of de optie 'Double Down' beschikbaar is tijdens het spelen.
	 * De speler kan bij deze optie Ja of Nee opgeven.
	 */
	private ButtonGroup bgrpDoubleDown;
	/**
	 * Een JLabel, een label behorende bij de buttongroep die betrekking heeft op 'Double Down'.
	 */
	private JLabel lblAllowDoubleDown;
	/**
	 * Een JRadioButton behorende bij de buttongroep die betrekking heeft op 'Double Down' en de waarde 'Ja' representeerd.
	 */
	private JRadioButton rbtnAllowDoubleDownTrue;
	/**
	 * Een JRadioButton behorende bij de buttongroep die betrekking heeft op 'Double Down' en de waarde 'Nee' representeerd.
	 */
	private JRadioButton rbtnAllowDoubleDownFalse;
	/**
	 * Een ButtonGroup waarmee de speler aan kan geven of de optie 'Split' beschikbaar is tijdens het spelen.
	 * De speler kan bij deze optie Ja of Nee opgeven.
	 */
	private ButtonGroup bgrpSplit;
	/**
	 * Een JLabel, een label behorende bij de buttongroep die betrekking heeft op 'Split'.
	 */
	private JLabel lblAllowSplit;
	/**
	 * Een JRadioButton behorende bij de buttongroep die betrekking heeft op 'Split' en de waarde 'Ja' representeerd.
	 */
	private JRadioButton rbtnAllowSplitTrue;
	/**
	 * Een JRadioButton behorende bij de buttongroep die betrekking heeft op 'Split' en de waarde 'Nee' representeerd.
	 */
	private JRadioButton rbtnAllowSplitFalse;

	/**
	 * Een ButtonGroup waarmee de speler aan kan geven of de optie 'Rood staan' beschikbaar is tijdens het spelen.
	 * De speler kan bij deze optie Ja of Nee opgeven.
	 */
	private ButtonGroup bgrpNegativeBalance;
	/**
	 * Een JLabel, een label behorende bij de buttongroep die betrekking heeft op 'Rood staan'.
	 */
	private JLabel lblAllowNegativeBalance;
	/**
	 * JRadioButton behorende bij de buttongroep die betrekking heeft op 'Rood staan' en de waarde 'Ja' representeerd.
	 */
	private JRadioButton rbtnAllowNegativeBalanceTrue;
	/**
	 * JRadioButton behorende bij de buttongroep die betrekking heeft op 'Rood staan' en de waarde 'Nee' representeerd.
	 */
	private JRadioButton rbtAllowNegativeBalanceFalse;
	/**
	 * Een ButtonGroup waarmee de speler aan kan geven of na elke rounde alle kaarten worden verzameld en opnieuw worden geschud.
	 * De speler kan bij deze optie Ja of Nee opgeven.
	 */
	private ButtonGroup bgrpShuffleAfterEachRound;
	/**
	 * Een JLabel, een label behorende bij de buttongroep die betrekking heeft op 'Na elke rounde schudden'.
	 */
	private JLabel lblShuffleAfterEachRound;
	/**
	 * JRadioButton behorende bij de buttongroep die betrekking heeft op 'Na elke ronde schudden' en de waarde 'Ja' representeerd.
	 */
	private JRadioButton rbtnShuffleAfterEachRoundTrue;
	/**
	 * JRadioButton behorende bij de buttongroep die betrekking heeft op 'Na elke ronde schudden' en de waarde 'Nee' representeerd.
	 */
	private JRadioButton rbtnShuffleAfterEachRoundFalse;
	/**
	 * Een JLabel, een label voor bij een invoer veld waar de speler op kan geven welke start saldo elke speler krijgt.
	 */
	private JLabel lblStartBalancePlayer;
	/**
	 * Een JTextField waar de start saldo kan worden opgegeven.
	 */
	private JTextField txtStartBalancePlayer;
	/**
	 * Een JLabel, een label voor bij een invoer veld waar de speler op kan geven wat de minimale inzet zal zijn tijdens het spel.
	 */
	private JLabel lblMinBet;
	/**
	 * Een JTextField waar de minamale inzet kan worden opgegeven.
	 */
	private JTextField txtMinBet;
	/**
	 * Een JLabel, een label voor bij een invoer veld waar de speler op kan geven wat de maximale inzet zal zijn tijdens het spel.
	 */
	private JLabel lblMaxBet;
	/**
	 * Een JTextField waar de maximale inzet kan worden opgegeven.
	 */
	private JTextField txtMaxBet;
	/**
	 * Een JLabel, een label voor bij een invoer veld waar de speler op kan geven bij welke kaartscore de deler past.
	 */
	private JLabel lblDealerStandsAt;
	/**
	 * Een JTextField waar de score kan worden opgegeven waarbij de deler past.
	 */
	private JTextField txtDealerStandsAt;
	/**
	 * Een JLabel, een label voor bij het invoer veld waar de speler op kan geven hoeveel kaartspellen worden opgenomen in het spel.
	 */
	private JLabel lblNumberOfDecksInShoe;
	/**
	 * Een JTextField waar het aantal kaartspellen kan worden opgegeven.
	 */
	private JTextField txtNumberOfDecksInShoe;
	/**
	 * Een JLabel, een label voor bij het invoerveld waar de speler op kan geven wanneer alle kaarten worden verzameld en opnieuw worden geschud.
	 * Dit wordt aangegeven in een percentage. Als deze is bereikt zal na de lopende ronden het schudden plaats vinden.
	 * Deze optie is alleen van toepassing wanneer is aangegeven dat niet na elke ronde wordt geschud.
	 */
	private JLabel lblPercentageToShuffleShoeAt;
	/**
	 * Een JTextField waar de speler het percentage kan opgeven.
	 */
	private JTextField txtPercentageToShuffleShoeAt;
	/**
	 * Een Game die zal worden gebruikt voor de verwijzing naar de eerder aangemaakte game.
	 */
	private Game game;
	/**
	 * Referentie naar het startscherm, om deze weer te tonen zodra dit scherm gesloten wordt.
	 */
	private StartFrame startFrame;
/*	/**
	* Auto-generated main method to display this JFrame

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SettingsFrame inst = new SettingsFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/


	/**
	 * Maakt het scherm aan en toont deze zodat de speler de instellingen kan doen. Tevens worden de instellingen opgehaald vanuit de klasse Game.
	 * In Game bevinden zich in eerste instatie die standaard instellingen.
	 * @param game Een Game die in een eerder stadium is aangemaakt en in deze klasse bekent dient te zijn.
	 */
	public SettingsFrame(StartFrame startFrame, Game game) {
		super();
		this.setLocationRelativeTo(null);
		this.game = game;
		this.startFrame = startFrame;
		initGUI();
		getSettings();
	}
	/**
	 * Maakt alle elementen aan die benodigd zijn voor het weergeven van het scherm en beeld deze vervolgens af op het scherm.
	 */
	private void initGUI() {
		try {

			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					thisWindowClosed(evt);
				}
			});
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			FlowLayout thisLayout = new FlowLayout();
			getContentPane().setLayout(thisLayout);
			this.setTitle("Instellingen");
			{
				pnlSettings = new JPanel();
				getContentPane().add(pnlSettings);
				pnlSettings.setLayout(null);
				pnlSettings.setPreferredSize(new java.awt.Dimension(532, 303));
				pnlSettings.setLayout(null);
				{
					rbtnAllowDoubleDownTrue = new JRadioButton();
					pnlSettings.add(rbtnAllowDoubleDownTrue);
					rbtnAllowDoubleDownTrue.setText("Ja");
					rbtnAllowDoubleDownTrue.setBounds(378, 7, 49, 21);
					getButtonGroupDoubleDown().add(rbtnAllowDoubleDownTrue);
				}
				{
					rbtnAllowDoubleDownFalse = new JRadioButton();
					pnlSettings.add(rbtnAllowDoubleDownFalse);
					rbtnAllowDoubleDownFalse.setText("Nee");
					rbtnAllowDoubleDownFalse.setBounds(434, 7, 86, 21);
					getButtonGroupDoubleDown().add(rbtnAllowDoubleDownFalse);
				}
				{
					lblAllowDoubleDown = new JLabel();
					pnlSettings.add(lblAllowDoubleDown);
					lblAllowDoubleDown.setText("Double Down toestaan");
					lblAllowDoubleDown.setBounds(14, 7, 259, 14);
				}
				{
					lblAllowSplit = new JLabel();
					pnlSettings.add(lblAllowSplit);
					lblAllowSplit.setText("Split toestaan");
					lblAllowSplit.setBounds(14, 28, 259, 14);
				}
				{
					lblAllowNegativeBalance = new JLabel();
					pnlSettings.add(lblAllowNegativeBalance);
					lblAllowNegativeBalance.setText("Rood staan toestaan");
					lblAllowNegativeBalance.setBounds(12, 48, 252, 14);
				}
				{
					lblShuffleAfterEachRound = new JLabel();
					pnlSettings.add(lblShuffleAfterEachRound);
					lblShuffleAfterEachRound.setText("Na elke ronde opnieuw schudden");
					lblShuffleAfterEachRound.setBounds(12, 68, 252, 14);
				}
				{
					rbtnAllowSplitTrue = new JRadioButton();
					pnlSettings.add(rbtnAllowSplitTrue);
					rbtnAllowSplitTrue.setText("Ja");
					rbtnAllowSplitTrue.setBounds(378, 28, 52, 21);
					getButtonGroupSplit().add(rbtnAllowSplitTrue);
				}
				{
					rbtnAllowSplitFalse = new JRadioButton();
					pnlSettings.add(rbtnAllowSplitFalse);
					rbtnAllowSplitFalse.setText("Nee");
					rbtnAllowSplitFalse.setBounds(434, 28, 79, 21);
					getButtonGroupSplit().add(rbtnAllowSplitFalse);
				}
				{
					rbtnAllowNegativeBalanceTrue = new JRadioButton();
					pnlSettings.add(rbtnAllowNegativeBalanceTrue);
					rbtnAllowNegativeBalanceTrue.setText("Ja");
					rbtnAllowNegativeBalanceTrue.setBounds(379, 49, 49, 21);
					getButtonGroupNegativeBalance().add(rbtnAllowNegativeBalanceTrue);
				}
				{
					rbtAllowNegativeBalanceFalse = new JRadioButton();
					pnlSettings.add(rbtAllowNegativeBalanceFalse);
					rbtAllowNegativeBalanceFalse.setText("Nee");
					rbtAllowNegativeBalanceFalse.setBounds(434, 49, 79, 21);
					getButtonGroupNegativeBalance().add(rbtAllowNegativeBalanceFalse);
				}
				{
					rbtnShuffleAfterEachRoundTrue = new JRadioButton();
					pnlSettings.add(rbtnShuffleAfterEachRoundTrue);
					rbtnShuffleAfterEachRoundTrue.setText("Ja");
					rbtnShuffleAfterEachRoundTrue.setBounds(379, 69, 49, 21);
					getButtonGroupShuffleAfterEachRound().add(rbtnShuffleAfterEachRoundTrue);
				}
				{
					rbtnShuffleAfterEachRoundFalse = new JRadioButton();
					pnlSettings.add(rbtnShuffleAfterEachRoundFalse);
					rbtnShuffleAfterEachRoundFalse.setText("Nee");
					rbtnShuffleAfterEachRoundFalse.setBounds(434, 69, 74, 21);
					getButtonGroupShuffleAfterEachRound().add(rbtnShuffleAfterEachRoundFalse);
				}
				{
					lblMinBet = new JLabel();
					pnlSettings.add(lblMinBet);
					lblMinBet.setText("Minimale inzet");
					lblMinBet.setBounds(12, 128, 245, 14);
				}
				{
					lblMaxBet = new JLabel();
					pnlSettings.add(lblMaxBet);
					lblMaxBet.setText("Maximale inzet");
					lblMaxBet.setBounds(12, 155, 245, 14);
				}
				{
					lblStartBalancePlayer = new JLabel();
					pnlSettings.add(lblStartBalancePlayer);
					lblStartBalancePlayer.setText("Start saldo speler");
					lblStartBalancePlayer.setBounds(12, 101, 252, 14);
				}
				{
					lblDealerStandsAt = new JLabel();
					pnlSettings.add(lblDealerStandsAt);
					lblDealerStandsAt.setText("Deler past op");
					lblDealerStandsAt.setBounds(12, 182, 259, 14);
				}
				{
					lblNumberOfDecksInShoe = new JLabel();
					pnlSettings.add(lblNumberOfDecksInShoe);
					pnlSettings.add(getPercentageToShuffleShoeAtLabel());
					pnlSettings.add(getStartBalancePlayer());
					pnlSettings.add(getMinBet());
					pnlSettings.add(getMaxBet());
					pnlSettings.add(getNumberOfDecksInShoe());
					pnlSettings.add(getPercentageToShuffleShoeAt());
					pnlSettings.add(getDealerStandsAt());
					lblNumberOfDecksInShoe.setText("Aantal kaartspellen");
					lblNumberOfDecksInShoe.setBounds(12, 209, 252, 14);
				}
			}
			{
				pnlButtons = new JPanel();
				getContentPane().add(pnlButtons);
				pnlButtons.setLayout(null);
				pnlButtons.setPreferredSize(new java.awt.Dimension(532, 49));
				pnlButtons.setLayout(null);
				{
					btnResetDefaults = new JButton();
					pnlButtons.add(btnResetDefaults);
					btnResetDefaults.setText("Beginwaarden");
					btnResetDefaults.setBounds(21, 7, 119, 21);
					btnResetDefaults.addActionListener(this);
				}
				{
					btnApply = new JButton();
					pnlButtons.add(btnApply);
					btnApply.setText("Toepassen");
					btnApply.setBounds(367, 7, 103, 21);
					btnApply.addActionListener(this);
				}
				{
					btnOk = new JButton();
					pnlButtons.add(btnOk);
					btnOk.setText("OK");
					btnOk.setBounds(476, 7, 56, 21);
					btnOk.addActionListener(this);
				}
				{
					btnCancel = new JButton();
					pnlButtons.add(btnCancel);
					btnCancel.setText("Annuleren");
					btnCancel.setBounds(261, 7, 100, 21);
					btnCancel.addActionListener(this);
				}
			}
			pack();
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setSize(554, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	/**
	 *Maakt de knoppengroep met betrekking op 'Double Down' aan als deze nog niet bestaat.
	 * @return ButtonGroup
	 */
	private ButtonGroup getButtonGroupDoubleDown() {
		if(bgrpDoubleDown == null) {
			bgrpDoubleDown = new ButtonGroup();
		}
		return bgrpDoubleDown;
	}

	/**
	 * Maakt de knoppengroep met betrekking op 'Split' aan als deze nog niet bestaat.
	 * @return ButtonGroup
	 */
	private ButtonGroup getButtonGroupSplit() {
		if(bgrpSplit == null) {
			bgrpSplit = new ButtonGroup();
		}
		return bgrpSplit;
	}


	/**
	 * Maakt de knoppengroep met betrekking op 'Rood staan' aan als deze nog niet bestaat.
	 * @return ButtonGroup
	 */
	private ButtonGroup getButtonGroupNegativeBalance() {
		if(bgrpNegativeBalance == null) {
			bgrpNegativeBalance = new ButtonGroup();
		}
		return bgrpNegativeBalance;
	}

	/**
	 * Maakt de knoppengroep met betrekking op 'Na elke ronde schudden' aan als deze nog niet bestaat.
	 * @return ButtonGroup
	 */
	private ButtonGroup getButtonGroupShuffleAfterEachRound() {
		if(bgrpShuffleAfterEachRound == null) {
			bgrpShuffleAfterEachRound = new ButtonGroup();
		}
		return bgrpShuffleAfterEachRound;
	}

	/**
	 * Maakt de label lblPercentageToShuffleShoeAt aan als deze nog niet bestaat.
	 * @return JLabel
	 */
	private JLabel getPercentageToShuffleShoeAtLabel() {
		if(lblPercentageToShuffleShoeAt == null) {
			lblPercentageToShuffleShoeAt = new JLabel();
			lblPercentageToShuffleShoeAt.setText("Percentage waarna de shoe moet worden geschud");
			lblPercentageToShuffleShoeAt.setBounds(12, 236, 308, 14);
		}
		return lblPercentageToShuffleShoeAt;
	}

	/**
	 * Maakt het invoerveld txtStartBalancePlayer aan als deze nog niet bestaat.
	 * @return JTextField
	 */
	private JTextField getStartBalancePlayer() {
		if(txtStartBalancePlayer == null) {
			txtStartBalancePlayer = new JTextField();
			txtStartBalancePlayer.setBounds(379, 98, 70, 21);
		}
		return txtStartBalancePlayer;
	}

	/**
	 * Maakt het invoerveld txtMinBet aan als deze nog niet bestaat.
	 * @return JTextField
	 */
	private JTextField getMinBet() {
		if(txtMinBet == null) {
			txtMinBet = new JTextField();
			txtMinBet.setBounds(379, 125, 70, 21);
		}
		return txtMinBet;
	}

	/**
	 * Maakt het invoerveld txtMaxBet aan als deze nog niet bestaat.
	 * @return JTextField
	 */
	private JTextField getMaxBet() {
		if(txtMaxBet == null) {
			txtMaxBet = new JTextField();
			txtMaxBet.setBounds(379, 152, 70, 21);
		}
		return txtMaxBet;
	}

	/**
	 * Maakt het invoerveld txtNumberOfDecksInShoe aan als deze nog niet bestaat.
	 * @return JTextField
	 */
	private JTextField getNumberOfDecksInShoe() {
		if(txtNumberOfDecksInShoe == null) {
			txtNumberOfDecksInShoe = new JTextField();
			txtNumberOfDecksInShoe.setBounds(379, 206, 70, 21);
		}
		return txtNumberOfDecksInShoe;
	}

	/**
	 * Maakt het invoerveld txtPercentageToShuffleShoeAt aan als deze nog niet bestaat.
	 * @return JTextField
	 */
	private JTextField getPercentageToShuffleShoeAt() {
		if(txtPercentageToShuffleShoeAt == null) {
			txtPercentageToShuffleShoeAt = new JTextField();
			txtPercentageToShuffleShoeAt.setBounds(379, 233, 70, 21);
		}
		return txtPercentageToShuffleShoeAt;
	}
	/**
	 * Maakt het invoerveld txtDealerStandsAt aan als deze nog niet bestaat.
	 * @return JTextField
	 */
	private JTextField getDealerStandsAt() {
		if(txtDealerStandsAt == null) {
			txtDealerStandsAt = new JTextField();
			txtDealerStandsAt.setBounds(379, 179, 70, 21);
		}
		return txtDealerStandsAt;
	}

	/**
	 * Vraagt de instellingen op zoals deze zijn gedefinieerd in Game.
	 */
	private void getSettings(){
		txtMaxBet.setText(""+game.getMaxBetAllowed());
		txtMinBet.setText(""+game.getMinBetAllowed());
		txtDealerStandsAt.setText(""+game.getDealerStandsAt());
		txtStartBalancePlayer.setText(""+game.getStartingBalance());
		txtNumberOfDecksInShoe.setText(""+game.getNumberOfDecksInShoe());
		txtPercentageToShuffleShoeAt.setText(""+game.percentageToShuffleShoeAt());
		boolean inlees = game.shuffleAfterEachRound();
		if (inlees==true){
			rbtnShuffleAfterEachRoundTrue.setSelected(true);
			}
		else {
			rbtnShuffleAfterEachRoundFalse.setSelected(true);
			}

		inlees = game.getDoubleDownAllowed();
		if (inlees==true){
			rbtnAllowDoubleDownTrue.setSelected(true);
			}
		else {
			rbtnAllowDoubleDownFalse.setSelected(true);
			}
		inlees = game.getNegativeBalanceAllowed();
		if (inlees==true){
			rbtnAllowNegativeBalanceTrue.setSelected(true);
			}
		else {
			rbtAllowNegativeBalanceFalse.setSelected(true);
			}
		inlees = game.getSplitAllowed();
		if (inlees==true){
			rbtnAllowSplitTrue.setSelected(true);
			}
		else {
			rbtnAllowSplitFalse.setSelected(true);
			}
	}

	/**
	 * Slaat de instellingen op in Game.
	 */
	public void setSettings() throws IOException
	{
		boolean doubleDownAllowed, negativeBalanceAllowed, splitAllowed, shuffleAfterEachRound;
		if(rbtnAllowDoubleDownTrue.isSelected()){
			doubleDownAllowed=true;
			}
		else {
			doubleDownAllowed=false;
			}
		if(rbtnAllowNegativeBalanceTrue.isSelected()){
			negativeBalanceAllowed=true;
			}
		else {
			negativeBalanceAllowed=false;
			}
		if(rbtnAllowSplitTrue.isSelected()){
			splitAllowed=true;
			}
		else {
			splitAllowed=false;
			}
		if(rbtnShuffleAfterEachRoundTrue.isSelected()){
			shuffleAfterEachRound=true;
			}
		else {
			shuffleAfterEachRound=false;
			}
		game.setSettings(doubleDownAllowed,negativeBalanceAllowed,splitAllowed,shuffleAfterEachRound,Integer.parseInt(txtMaxBet.getText()),Integer.parseInt(txtMinBet.getText()),Integer.parseInt(txtDealerStandsAt.getText()),Integer.parseInt(txtStartBalancePlayer.getText()),Integer.parseInt(txtNumberOfDecksInShoe.getText()),Integer.parseInt(txtPercentageToShuffleShoeAt.getText()));
	}

	/**
	 * Handelt de gewenste actie af wanneer een van de knoppen 'Ok', 'Annuleren', 'Toepassen' of 'Beginwaarden' wordt aangeklikt.
	 */
	public void actionPerformed(ActionEvent e) {
		String s="In de invoervelden mogen alleen getallen worden opgegeven.\nDe beginwaarden zullen worden ingesteld";

		if(e.getSource() == btnOk){
			try{
				setSettings();
				this.setVisible(false);
				startFrame.setVisible(true);
			}
			catch (NumberFormatException e1){
				JOptionPane.showMessageDialog(null, s);
				game.makeSettings();
				getSettings();
			}
			catch(IOException e1){}
		}
		if(e.getSource() == btnCancel){
			this.setVisible(false);
			startFrame.setVisible(true);
		}
		if(e.getSource() == btnApply){
			try{
				setSettings();
			}
			catch (NumberFormatException e1){
				JOptionPane.showMessageDialog(null, s);
				game.makeSettings();
				getSettings();
			}
			catch(IOException e1){}
		}
		if(e.getSource() == btnResetDefaults){
			game.makeSettings();
			getSettings();
		}
	}

	private void thisWindowClosed(WindowEvent evt) {
		System.out.println("this.windowClosed, event="+evt);
		/* forceer de applicatie om af te sluiten */
		if ( this.startFrame != null ) {
			this.startFrame.setVisible(true);
		}
		this.setVisible(false);
	}
}
