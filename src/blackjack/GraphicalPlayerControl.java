package blackjack;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Een spelerpaneel.
 *
 */
public class GraphicalPlayerControl extends javax.swing.JPanel
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3882742551833776220L;
	/**
	 * De speler gekoppeld aan dit control.
	 */
	private Player player;
	/**
	 * De panel voor de informatie controls.
	 */
	private JPanel pnlInfo;
	/**
	 * Control voor het huidige saldo.
	 */
	private JLabel lblBalance;
	/**
	 * De control waar de kaarten in worden weergegeven voor de 1e hand.
	 */
	private GraphicalHandPanel pnlCardsHand1;
	/**
	 * De control waar de kaarten in worden weergegeven voor de evt. 2e hand.
	 */
	private GraphicalHandPanel pnlCardsHand2;
	/**
	 * De panel voor de kaarten controls.
	 */
	private JPanel pnlCards;
	/**
	 * Control voor het huidige bod.
	 */
	private JLabel lblBet;
	/**
	 * Control voor de naam.
	 */
	private JLabel lblName;
	/**
	* Auto-generated main method to display this
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new GraphicalPlayerControl());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * Maakt een nieuw object van het type PlayerControl en initialiseert/toont deze.
	 */
	public GraphicalPlayerControl() {
		super();
		initGUI();
		postInitGui();
	}
	/**
	 * Initialiseert de GUI.
	 */
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(160, 232));
			{
				pnlInfo = new JPanel();
				this.add(pnlInfo, BorderLayout.SOUTH);
				pnlInfo.setPreferredSize(new java.awt.Dimension(160, 23));
				{
					lblBalance = new JLabel();
					pnlInfo.add(lblBalance);
					lblBalance.setText("");
				}
				{
					lblName = new JLabel();
					pnlInfo.add(lblName);
					lblName.setText("");
				}
				{
					lblBet = new JLabel();
					pnlInfo.add(lblBet);
					lblBet.setText("");
				}
			}
			{
				pnlCards = new JPanel();
				this.add(pnlCards, BorderLayout.CENTER);
				pnlCards.setLayout(null);
				pnlCards.setPreferredSize(new java.awt.Dimension(160, 79));
				pnlCards.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent evt) {
						pnlCardsComponentResized(evt);
					}
				});
				{
					pnlCardsHand1 = new GraphicalHandPanel();
					pnlCardsHand1.setHandInfoFormat(GraphicalHandPanel.HandInfoFormat.ALL);
					pnlCards.add(pnlCardsHand1, "Center");
					pnlCardsHand1.setBounds(0, 93, 160, 81);
				}
				{
					pnlCardsHand2 = new GraphicalHandPanel();
					pnlCardsHand2.setHandInfoFormat(GraphicalHandPanel.HandInfoFormat.ALL);
					pnlCards.add(pnlCardsHand2, "Center");
					pnlCardsHand2.setBounds(0, 0, 160, 81);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void postInitGui()
	{
		this.setBackground(new Color(0,220,0));
		pnlInfo.setBackground(new Color(0,220,0));
		pnlCardsHand1.setBackground(new Color(0,220,0));
		pnlCardsHand2.setBackground(new Color(0,220,0));
	}
	/**
	 * De speler zetten op dit control.
	 * @param player De speler
	 */
	public void setPlayer(Player player) {
		this.player = player;
		lblName.setText(player.getName());
		updateInfo();
	}
	/**
	 * Geeft de speler terug.
	 * @return Player De huidige speler op dit control
	 */
	public Player getPlayer()
	{
		return this.player;
	}
	/**
	 * Op het control weergeven dat deze speler aan het bieden is.
	 */
	public void letBet()
	{
		lblBet.setText("$$");
	}
	/**
	 * Speler zet gegeven bod.
	 */
	public void updateBet()
	{
		lblBet.setText("");
		pnlCardsHand1.setHand(player.getCurrentHand(), true);
	}
	/**
	 * Geeft de kaarten solid weer omdat deze gespeeld kan worden
	 * @param enable Wanneer true dan kaarten weergeven
	 */
	public void enableHands(boolean enable)
	{
		pnlCardsHand1.setSolid(enable);
		pnlCardsHand2.setSolid(enable);
	}
	/**
	 * Op het control weergeven dat deze speler een kaart mag gaan trekken.
	 */
	public void letPlay()
	{
		if (pnlCardsHand1.getHand() == player.getCurrentHand())
		{
			pnlCardsHand1.setSolid(true);
		}
		else if (pnlCardsHand2.getHand() == player.getCurrentHand())
		{
			pnlCardsHand2.setSolid(true);
		}
	}
	/**
	 * Op het control weergeven dat deze speler 'gestand' heeft.
	 */
	public void stand()
	{
		lblBalance.setText(((Double)player.getBalance()).toString());
	}
	/**
	 * Werk de huidige kaartenlijsten bij.
	 */
	public void updateCardsList()
	{
		if (player == null) return;

		if (player.getHands().size() >= 1)
		{
			pnlCardsHand1.setHand(player.getHands().get(0), true);
		}
		else
		{
			pnlCardsHand1.clear();
		}
		if (player.getHands().size() >= 2)
		{
			pnlCardsHand2.setHand(player.getHands().get(1), true);
		}
		else
		{
			pnlCardsHand2.clear();
		}
	}
	/**
	 * Werk de informatie van deze speler bij.
	 */
	public void updateInfo()
	{
		if (player == null) return;

		lblBalance.setText("$ " + ((Double)player.getBalance()).toString());
	}
	/**
	 * Dit control wordt geresized. Resize de hand-controls zodat die precies passen.
	 * @param evt
	 */
	private void pnlCardsComponentResized(ComponentEvent evt) {
		System.out.println("pnlCards.componentResized, event="+evt);

		int pnlWidth = pnlCards.getWidth();
		int pnlHeight = pnlCards.getHeight() / 2 + 1;

		pnlCardsHand2.setBounds(0, 0, pnlWidth, pnlHeight);
		pnlCardsHand1.setBounds(0, pnlHeight, pnlWidth, pnlHeight);

		pnlCards.revalidate();
	}
}
