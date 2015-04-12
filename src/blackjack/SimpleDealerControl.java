package blackjack;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 * Het delerpaneel.
 * Dit control geeft de huidige status van de deler weer. Door middel van een label worden de huidige kaarten van zijn hand weergegeven.
 *
 */
public class SimpleDealerControl extends javax.swing.JPanel
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3882742551833776220L;
	/**
	 * De deler van de huidige ronde.
	 */
	private Player player;
	/**
	 * De panel voor de informatie controls.
	 */
	private JPanel pnlInfo;
	/**
	 * De control waar de kaarten in worden weergegeven.
	 */
	private JLabel lblCards;
	/**
	 * De panel voor de kaarten controls.
	 */
	private JPanel pnlCards;
	/**
	 * Control voor de naam.
	 */
	private JLabel lblName;
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.getContentPane().add(new DealerControl());
//		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		frame.pack();
//		frame.setVisible(true);
//	}
	/**
	 * Maakt een nieuw object van het type DealerControl en initialiseert/toont deze.
	 */
	public SimpleDealerControl() {
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
					lblName = new JLabel();
					pnlInfo.add(lblName);
					lblName.setText("Name");
				}
			}
			{
				pnlCards = new JPanel();
				BorderLayout pnlCardsLayout = new BorderLayout();
				this.add(pnlCards, BorderLayout.CENTER);
				pnlCards.setLayout(pnlCardsLayout);
				{
					lblCards = new JLabel();
					BorderLayout lblCardsLayout = new BorderLayout();
					lblCards.setLayout(lblCardsLayout);
					pnlCards.add(lblCards, BorderLayout.CENTER);
					lblCards.setText("Kaarten");
					lblCards.setPreferredSize(new java.awt.Dimension(152, 191));
					lblCards.setVerticalAlignment(SwingConstants.TOP);
					lblCards.setVerticalTextPosition(SwingConstants.TOP);
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
	}
	/**
	 * Geeft de speler terug.
	 * @return De huidige speler op dit control
	 */
	public Player getPlayer()
	{
		return this.player;
	}
	/**
	 * Werk de huidige kaartenlijsten bij.
	 */
	public void updateCardsList()
	{
		if (player.getHands().size() >= 1)
		{
			updateHandControl(lblCards, player.getHands().get(0));
		}
		else
		{
			lblCards.setText("..");
		}
	}
	/**
	 * Geeft aan op het control dat de kaarten zijn verzameld.
	 * @return boolean
	 */
	public boolean collectCards()
	{
		return true;
	}
	/**
	 * Geeft aan op het control dat de shoe is geschud.
	 * @return boolean
	 */
	public boolean shuffleShoe()
	{
		return true;
	}
	/**
	 * Werk gegeven control bij met gegeven hand met de huidige kaarten en de status.
	 * @param label Een object van het type JLabel, het bij te werken control
	 * @param hand Een object van het type Hand, de bron-hand
	 */
	public void updateHandControl(JLabel label, Hand hand)
	{
		String text = "";
		if (hand.getCards().size() == 2 && hand.isStanded() == false) {
			for (Card card: hand.getCards()) {
				if (text.length() == 0) {
					text += card.toString() + "<br>";
				} else {
					text += "2e kaart..<br>";
				}
			}
		} else {
			for (Card card: hand.getCards()) {
				text += card.toString() + "<br>";
			}
		}
		if (hand.isBust() != false) {
			text += "Busted!!<br>";
		} else if (hand.isStanded() != false) {
			text += "Standed.<br>";
		}
		if (!(hand.getCards().size() == 2 && hand.isStanded() == false)) {
			text += hand.getScore();
		}

		lblCards.setText("<html>" + text + "</html>");
		lblCards.setToolTipText("<html>" + text + "</html>");
	}
}
