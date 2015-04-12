package blackjack;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;
/** 
 * Het hulpscherm.
 * 
 * @author Arno van Rossum
 */
public class HelpFrame extends javax.swing.JFrame
{
	private static final long serialVersionUID = -6186877073710363407L;
	/**
	 * Het paneel waarin de content van deze frame staat.
	 */
	private JPanel panelContent;
	/**
	 * Containerpaneel voor alle content in dit frame.
	 * Dit is binnen de panelContent de top paneel, hierin staat extra informatie over dit frame.
	 */
	private JPanel panelTop;
	/**
	 * Containerpaneel voor het label dat omschrijft wat er in dit frame te zien is.
	 */
	private JPanel panelHelp;
	/**
	 * Een label dat omschrijft wat er in dit frame te zien is.
	 */
	private JLabel lblBlackjackTitle;
	/**
	 * Het tekstveld waarin het hulpbestand wordt getoond.
	 */
	private JTextPane txtHelp;
	/**
	 * Het scrollpaneel welke de hulptekst scrollbaar maakt.
	 */
	private JScrollPane scrollHelp;
	/**
	 * referentie naar het startframe, om deze te kunnen tonen/verbergen wanneer dit venster
	 * geopend of gesloten wordt.
	 */
	private StartFrame startFrame;
//	/**
//	* Auto-generated main method to display this JFrame
//	*/
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				HelpFrame inst = new HelpFrame();
//				inst.setLocationRelativeTo(null);
//				inst.setVisible(true);
//			}
//		});
//	}
	/**
	 * Maakt een nieuw HelpFrame aan en initialiseert en toont deze.
	 */
	public HelpFrame(StartFrame startFrame)
	{
		super();
		this.startFrame = startFrame;
		initGUI();
	}
	/**
	 * Initialiseert de gui, en laadt het hulpbestand in.
	 */
	private void initGUI() {
		try {
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					thisWindowClosed(evt);
				}
			});
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Help");
			this.setResizable(false);
			{
				panelContent = new JPanel();
				getContentPane().add(panelContent, BorderLayout.CENTER);
				panelContent.setEnabled(false);
				{
					panelTop = new JPanel();
					panelContent.add(panelTop);
					panelTop.setPreferredSize(new java.awt.Dimension(570, 13));
					panelTop.setLayout(null);
					{
						lblBlackjackTitle = new JLabel();
						panelTop.add(lblBlackjackTitle);
						lblBlackjackTitle.setText("Spel uitleg voor blackjack");
						lblBlackjackTitle.setBounds(6, -3, 255, 16);
						lblBlackjackTitle.setFont(new java.awt.Font("Segoe UI",1,12));
					}
				}
				{
					panelHelp = new JPanel();
					panelContent.add(panelHelp);
					panelHelp.setPreferredSize(new java.awt.Dimension(563, 219));
					{
						scrollHelp = new JScrollPane();
						panelHelp.add(scrollHelp);
						scrollHelp.setPreferredSize(new java.awt.Dimension(557, 203));
						{
							txtHelp = new JTextPane();
							txtHelp.setEditable(false);
							scrollHelp.setViewportView(txtHelp);
							//java.net.URL helpURL = HelpFrame.class.getResource("help.html");
							String url = "file:///" + System.getProperty("user.dir") + "\\doc\\help.html";
							/* String helpURL = "../help.html"; */
							try {
								txtHelp.setPage(url);
								txtHelp.setPreferredSize(new java.awt.Dimension(554, 206));
							} 
							catch (IOException e) {
								System.err.println("Attempted to read a bad URL: " + url);
							}
						}
					}
				}
			}
			pack();
			this.setSize(582, 274);
			setLocationRelativeTo(null);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
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
