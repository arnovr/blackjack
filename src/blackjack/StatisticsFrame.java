package blackjack;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

/** 
 * Het statistiekenscherm.
 * 
 * @author Arno van Rossum
 */
public class StatisticsFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9040733679184056754L;

	/**
	 * Dit is het hoofd paneel, hierin bevind zich alle informatie van de statistieken
	 */
	private JPanel panelGames;
	/**
	 * In deze panel worden de tabs aangemaakt van elke game
	 */
	private JTabbedPane tabbedPanelGames;
	
	/**
	 * Sluiten knop van de statistieken frame.
	 */
	private JButton btnClose;
	
	/**
	 * Het paneel welke de sluiten button bevat.  
	 */
	private JPanel panelClose;
	/**
	 * Instelling om de hoogte van de sluiten paneel in te stellen
	 */
	private int panelCloseHeight = 32;
	
	/**
	 * De breedte van het statistieken scherm
	 */
	private int windowWidth = 800;
	/**
	 * De hoogte van het statistieken scherm
	 */
	private int windowHeight = 500;

	/**
	 * Het startframe om naar terug te gaan wanneer statistieken scherm wordt gesloten, indien beschikbaar.
	 */
	private StartFrame startFrame = null;

	/**
	 * De lijst met alle gespeelde games
	 */
	private ArrayList<Game>	games = new ArrayList<Game>();
	
	/**
	 * Constructor
	 * @param games
	 */
	public StatisticsFrame(ArrayList<Game> games) {
		super();
		this.games = games;
		initGUI();
		
		setLocationRelativeTo(null);
	}
	
	/**
	 * Constructor waarin de startframe word gezet, zodat je bij het sluiten van statistieken
	 * terug naar het startscherm gaat.
	 * @param games
	 * @param start
	 */
	public StatisticsFrame(ArrayList<Game> games, StartFrame start) {
		this(games);
		this.startFrame = start;
	}
	
	/**
	 * Initialiseer de GUI
	 */
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			FlowLayout thisLayout = new FlowLayout();
			getContentPane().setLayout(thisLayout);
			setTitle("Statistieken");
			setResizable(false);
			initializeGamePanel();
			setLocationRelativeTo(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					thisWindowClosed(evt);
				}
			});
			setSize(windowWidth, windowHeight);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialiseer het spel paneel, hierin worden de tabs aangemaakt en het informatie paneel
	 */
	private void initializeGamePanel() {
		panelGames = new JPanel();
		panelGames.setPreferredSize(new java.awt.Dimension((windowWidth), (windowHeight)));

		FlowLayout layout = new FlowLayout();
		panelGames.setLayout(layout);

		tabbedPanelGames = new JTabbedPane();
		tabbedPanelGames.setBounds(0, 0, windowWidth, windowHeight);
		tabbedPanelGames.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		int i = 0;

		Dimension dimensions = new java.awt.Dimension((windowWidth - 34), (windowHeight - 110 - panelCloseHeight));

		ArrayList<StatisticsGamePanel> gamePanels = new ArrayList<StatisticsGamePanel>();
		
		for(Game game : this.games) {
			i++;
			StatisticsGamePanel gamePanel = new StatisticsGamePanel(game);
			tabbedPanelGames.addTab("Spel " + i, null, gamePanel, "Dit is spel " + i);
			gamePanel.setPanelDimensions(dimensions);

			// 20 en 72 zijn de offset.
			gamePanel.setPreferredSize(new java.awt.Dimension((windowWidth - 20), (windowHeight - 72 - panelCloseHeight)));
			gamePanels.add(gamePanel);
		}
		
		panelGames.add(tabbedPanelGames);
		panelClose = getPanelClose();
		panelGames.add(panelClose);


		getContentPane().add(panelGames);
		
	}
	
	
	/**
	 * Geeft een paneel terug waarin de sluiten knop te zien is.
	 * @return JPanel 
	 */
	private JPanel getPanelClose() {
		if(panelClose == null) {
			panelClose = new JPanel();
			panelClose.setPreferredSize(new java.awt.Dimension((windowWidth - 20), panelCloseHeight));
			panelClose.setLayout(null);
			panelClose.add(getBtnClose());
		}
		return panelClose;
	}
	/**
	 * Geeft de sluiten knop terug.
	 * @return JButton
	 */
	private JButton getBtnClose() {
		if(btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("Sluiten");
			btnClose.setBounds((windowWidth - 112), 4, 92, 23);
			btnClose.addActionListener(this);
		}
		return btnClose;
	}
	
	/**
	 * Wanneer de window gesloten wordt, maak het startframe zichtbaar
	 * @param evt
	 */
	private void thisWindowClosed(WindowEvent evt) {
		System.out.println("this.windowClosed, event="+evt);
		/* forceer de applicatie om af te sluiten */
		if ( this.startFrame != null ) {
			this.startFrame.setVisible(true);
		}
		this.setVisible(false);
	}
	
	/**
	 * Uitvoeren van de sluiten knop.
	 * @param e Een ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		if ( this.startFrame != null ) {
			this.startFrame.setVisible(true);
		}
		this.setVisible(false);
	}
}