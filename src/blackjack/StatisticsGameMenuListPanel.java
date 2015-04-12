package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

abstract public class StatisticsGameMenuListPanel extends JPanel implements ListSelectionListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5814420672767278041L;
	/**
	 * De game variablen waaruit de statistieken onttrokken worden
	 */
	protected Game game;

	/**
	 * De geselecteerde speler waarvan de statistieken opgepakt moeten worden
	 */
	protected Player selectedPlayer = null;
	
	/**
	 * Het submenu waarin de opties staan
	 */
	protected JList menu;
	
	/**
	 * De layout van deze panel
	 */
	protected BorderLayout layout;
	
	/**
	 * Hier komen alle te selecteren items in wanneer er een speler is
	 */
	public String[] menuItems;
	/**
	 * Hier komen de menu items van de dealer in terecht
	 */
	public String[] menuDealerItems;

	/**
	 * Constructor
	 * @param game
	 */
	public StatisticsGameMenuListPanel(Game game) {
		this.game = game;
	}
	
	/**
	 * Constructor met player selectie
	 * @param game
	 * @param player
	 */
	public StatisticsGameMenuListPanel(Game game, Player player) {
		this.selectedPlayer = player;
		this.game = game;
	}

	/**
	 * Hiermee kan je de menuitems zetten.
	 * @param menuItems
	 * @param menuDealerItems
	 */
	public void setMenuItems(String[] menuItems, String[] menuDealerItems) {
		this.menuItems = menuItems;
		this.menuDealerItems = menuDealerItems;
	}
	
	/**
	 * Hiermee wordt het menu gecreeerd
	 */
	protected void initializeMenu() {
		if ( this.selectedPlayer != null && this.selectedPlayer.getClass() == Dealer.class) {
			menu = new JList(menuDealerItems);
		}
		else {
			menu = new JList(menuItems);
		}
		menu.setSelectionMode(0);
		menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		menu.setPreferredSize(new java.awt.Dimension(150, 10000));
		add(menu, BorderLayout.WEST);
		menu.addListSelectionListener(this);
	}
	
	/**
	 * Genereer een label en een paneel waarin de grafiek weergegeven is.
	 * @param labelName
	 * @param y
	 * @return  JPanel
	 */
	protected JPanel getGraphPanel(String labelName, String bottomLabel, int[] y, int numberOfLabels, int stepsPerLabel) {
		JPanel panelGraph = new JPanel();
		add(panelGraph);
		panelGraph.setLayout(null);

		JLabel lblGraphDescription = new JLabel();
		lblGraphDescription.setText(labelName);
		lblGraphDescription.setBounds(6, 4, getWidth() - menu.getWidth(), 20);

		JScrollPane scrollGraph = new JScrollPane();
		scrollGraph.setBounds(4, 26, getWidth() - menu.getWidth() - 5, getHeight() - lblGraphDescription.getHeight() - 10);
		GraphingGameData panelGraphic = new GraphingGameData();
		panelGraphic.setY(y, numberOfLabels, stepsPerLabel);
		scrollGraph.setViewportView(panelGraphic);

		panelGraph.add(scrollGraph);
		panelGraph.add(lblGraphDescription);
				
		return panelGraph;
	}
	
	/**
	 * Parse datum met 1 param
	 * @param date
	 * @return String
	 */
	protected String parseDate(Date date) {
		return parseDate(date, false);
	}

	/**
	 * Parse de datum naar iets leesbaars
	 * @param date
	 * @param noTime zonder tijd
	 * @return String
	 */
	protected String parseDate(Date date, boolean noTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String dString = "" + fillLeft(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + fillLeft(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
		if ( noTime == false ) {
			dString += " " + fillLeft(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + fillLeft(calendar.get(Calendar.MINUTE)) + ":" + fillLeft(calendar.get(Calendar.SECOND));
		}
		return dString;
	}
	
	/**
	 * Wanneer de lengte van het nummer 1 is, plak er een 0 voor
	 * @param number
	 * @return String
	 */
	public static String fillLeft(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(number);
	}

	/**
	 * Wanneer er geklikt wordt op 1 van de menu items is deze methode gecalled.
	 */
	abstract public void valueChanged(ListSelectionEvent e);
}
