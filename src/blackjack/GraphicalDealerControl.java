package blackjack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Het delerpaneel.
 * Dit control geeft de huidige status van de deler weer. Door middel van een label worden de huidige kaarten van zijn hand weergegeven.
 *
 */
public class GraphicalDealerControl extends javax.swing.JPanel
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
	private GraphicalHandPanel pnlCardsHand;
	/**
	 * De stack (linksboven)
	 */
	private GraphicalCardsCollectionPanel pnlStack;
	/**
	 * De shoe (rechtboven)
	 */
	private GraphicalCardsCollectionPanel pnlShoe;

	/**
	 * Indiceert of de kaart-verzamel-actie naar de shoe moet (anders stack)
	 */
	boolean collectToShoe = false;

	/**
	 * De panel voor de kaarten controls.
	 */
	private JPanel pnlCards;
	/**
	 * Control voor de naam.
	 */
	private JLabel lblName;

	/**
	 * Maakt een nieuw object van het type DealerControl en initialiseert/toont deze.
	 */
	public GraphicalDealerControl () {
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
				this.add(pnlCards, BorderLayout.CENTER);
				pnlCards.setLayout(null);
				pnlCards.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent evt) {
						pnlCardsComponentResized(evt);
					}
				});
				{
					pnlCardsHand = new GraphicalHandPanel();
					pnlCardsHand.setHandInfoFormat(GraphicalHandPanel.HandInfoFormat.BUSTED_BLACKJACK);
					pnlCards.add(pnlCardsHand, "Center");
					pnlCardsHand.setPreferredSize(new java.awt.Dimension(152, 191));
				}
				{
					pnlStack = new GraphicalCardsCollectionPanel();
					pnlStack.setCardsPosition(GraphicalCardsCollectionPanel.CardsCollectionPosition.LEFTTOP);
					pnlCards.add(pnlStack, "Center");
					pnlStack.setNumberOfCardsToCollect(8);
					pnlStack.setPreferredSize(new java.awt.Dimension(152, 191));
				}
				{
					pnlShoe = new GraphicalCardsCollectionPanel();
					pnlShoe.setCardsPosition(GraphicalCardsCollectionPanel.CardsCollectionPosition.RIGHTTOP);
					pnlShoe.setAlwaysFull(true);
					pnlCards.add(pnlShoe, "Center");
					pnlShoe.setPreferredSize(new java.awt.Dimension(152, 191));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Extra initialisaties voor de gui
	 */
	private void postInitGui()
	{
		pnlCards.setBackground(new Color(0,220,0));
		pnlInfo.setBackground(new Color(0,220,0));
		pnlCardsHand.setBackground(new Color(0,220,0));
		pnlShoe.setBackground(new Color(0,220,0));
		pnlStack.setBackground(new Color(0,220,0));
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
	 * De verzamelactie gaat naar de shoe (of stack)
	 * @param collectToShoe Indien true dan naar de shoe anders stack
	 */
	public void setCollectToShoe(boolean collectToShoe)
	{
		this.collectToShoe = collectToShoe;

		// Wanneer naar de shoe dan simuleren dat de shoe aan het verzamelen is
		// hiervoor stellen we even in dat de shoe niet altijd vol is zodat
		// de simulatie hetzelfde is als die van de stack
		pnlShoe.setAlwaysFull(!collectToShoe);
	}
	/**
	 * Willen we de kaarten Solid zien of niet. (Busted)
	 * @param enable
	 */
	public void enableHands(boolean enable)
	{
		pnlCardsHand.setSolid(enable);
	}
	/**
	 * Er wordt een kaart gedeeld uit de shoe. Dit laten visualiseren door de shoe.
	 */
	public void dealCard()
	{
		pnlShoe.previousPosition();
	}
	/**
	 * Werk de huidige kaartenlijsten bij.
	 * @param showSecondCard In de kaart-deel-status wordt de 2e kaart niet weergegeven.
	 */
	public void updateCardsList(boolean showSecondCard)
	{
		if (player.getHands().size() >= 1)
		{
			pnlCardsHand.setHand(player.getHands().get(0), showSecondCard);
		}
		else
		{
			pnlCardsHand.clear();
		}
	}
	/**
	 * Geeft aan op het control dat de kaarten zijn verzameld.
	 * @return boolean
	 */
	public boolean collectCards()
	{
		if (collectToShoe == false)
		{
			return pnlStack.nextPosition();
		}
		else
		{
			if (pnlShoe.nextPosition() != false)
			{
				pnlShoe.setAlwaysFull(true);
				return true;
			}
			return false;
		}
	}
	/**
	 * Geeft aan op het control dat de shoe is geschud.
	 * @return boolean
	 */
	public boolean shuffleShoe()
	{
		return pnlShoe.shuffle();
	}
	/**
	 * Dit control wordt geresized. Resize de hand-controls zodat die precies passen.
	 * @param evt
	 */
	private void pnlCardsComponentResized(ComponentEvent evt) {
		System.out.println("pnlCards.componentResized, event="+evt);

		int pnlWidth = pnlCards.getWidth() / 3 + 1;
		int pnlHeight = pnlCards.getHeight();

		pnlStack.setBounds(30, 0, pnlWidth - 30, pnlHeight);
		pnlCardsHand.setBounds(pnlWidth + 70, 0, pnlWidth - 70, pnlHeight);
		pnlShoe.setBounds(30 + pnlWidth * 2, 0, pnlWidth - 30, pnlHeight);

		pnlCards.revalidate();
	}
}
