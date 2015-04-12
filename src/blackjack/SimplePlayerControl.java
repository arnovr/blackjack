package blackjack;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 * Een spelerpaneel.
 *
 */
public class SimplePlayerControl extends javax.swing.JPanel
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
	private JLabel pnlCardsHand1;
	/**
	 * De control waar de kaarten in worden weergegeven voor de evt. 2e hand.
	 */
	private JLabel pnlCardsHand2;
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
		frame.getContentPane().add(new SimplePlayerControl());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * Maakt een nieuw object van het type PlayerControl en initialiseert/toont deze.
	 */
	public SimplePlayerControl() {
		super();
		initGUI();
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
				this.add(pnlInfo, BorderLayout.NORTH);
				pnlInfo.setPreferredSize(new java.awt.Dimension(160, 23));
				{
					lblBalance = new JLabel();
					pnlInfo.add(lblBalance);
					lblBalance.setText("Balance");
				}
				{
					lblName = new JLabel();
					pnlInfo.add(lblName);
					lblName.setText("Name");
				}
				{
					lblBet = new JLabel();
					pnlInfo.add(lblBet);
					lblBet.setText("Bet");
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
					pnlCardsHand1 = new JLabel();
					pnlCards.add(pnlCardsHand1, "Center");
					pnlCardsHand1.setText("Kaarten");
					pnlCardsHand1.setVerticalAlignment(SwingConstants.TOP);
					pnlCardsHand1.setVerticalTextPosition(SwingConstants.TOP);
					pnlCardsHand1.setBounds(0, 0, 160, 81);
				}
				{
					pnlCardsHand2 = new JLabel();
					pnlCards.add(pnlCardsHand2, "Center");
					pnlCardsHand2.setBounds(0, 93, 160, 81);
					pnlCardsHand2.setToolTipText("Hand 2");
					pnlCardsHand2.setText("Hand 2");
					pnlCardsHand2.setVerticalAlignment(SwingConstants.TOP);
					pnlCardsHand2.setVerticalTextPosition(SwingConstants.TOP);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		lblBet.setText("..");
	}
	/**
	 * Speler zet gegeven bod.
	 * @param s Het bod
	 */
	public void setBet(String s)
	{
		lblBet.setText(s);
	}
	/**
	 * Op het control weergeven dat deze speler een kaart mag gaan trekken.
	 */
	public void letPlay()
	{
		lblBalance.setText("..");
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
			updateHandControl(pnlCardsHand1, player.getHands().get(0));
		}
		else
		{
			pnlCardsHand1.setText("..");
		}
		if (player.getHands().size() >= 2)
		{
			updateHandControl(pnlCardsHand2, player.getHands().get(1));
		}
		else
		{
			pnlCardsHand2.setText("..");
		}
	}
	/**
	 * Werk gegeven control bij met gegeven hand met de huidige kaarten en de status.
	 * @param label Bij te werken control
	 * @param hand De bron-hand
	 */
	public void updateHandControl(JLabel label, Hand hand)
	{
		String text = "";
		for (Card card: hand.getCards()) {
			text += card.toString() + "<br>";
		}

		if (hand.isBust() != false) {
			text += "Busted!!<br>";
		} else if (hand.isStanded() != false) {
			text += "Standed.<br>";
		}
		text += hand.getScore();

		if (hand.getPayedOut()) {
			if (hand.getWon() != false) {
				text += " WON";
			} else if (hand.getLost() != false) {
				text += " LOST";
			} else {
				text += " EVEN";
			}
		}

		label.setText("<html>" + text + "</html>");
		label.setToolTipText("<html>" + text + "</html>");
	}
	/**
	 * Werk de informatie van deze speler bij.
	 */
	public void updateInfo()
	{
		if (player == null) return;

		if (player.getHands().size() >= 1)
		{
			lblBet.setText(((Integer)player.getHands().get(0).getBet()).toString());
		} else
		{
			lblBet.setText("..");
		}
		lblBalance.setText(((Double)player.getBalance()).toString());
	}
	/**
	 * Dit control wordt geresized. Resize de hand-controls zodat die precies passen.
	 * @param evt
	 */
	private void pnlCardsComponentResized(ComponentEvent evt) {
		System.out.println("pnlCards.componentResized, event="+evt);

		int pnlWidth = pnlCards.getWidth();
		int pnlHeight = pnlCards.getHeight() / 2;

		pnlCardsHand1.setBounds(0, 0, pnlWidth, pnlHeight);
		pnlCardsHand2.setBounds(0, pnlHeight, pnlWidth, pnlHeight);

		pnlCards.revalidate();
	}
}
