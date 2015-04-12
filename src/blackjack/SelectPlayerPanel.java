package blackjack;
import java.util.*;
import java.io.*;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;

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
 * Een spelerselectiepaneel.
 * Dit paneel wordt gebruikt in het SelectPlayerFrame.
 *
*/
public class SelectPlayerPanel extends javax.swing.JPanel{

	private static final long serialVersionUID = 7233384560106024465L;
	/**
	 * Een JLabel waarin de tekst 'Speler X' komt te staan, waarbij de X de waarde 1 tot en met 4 kan hebben.
	 */
	public JLabel title;
	/**
	 * Een JLabel met als tekst 'Naam'.
	 */
	private JLabel lblNamePlayer;
	/**
	 * Een JTextField voor het invoeren van de naam.
	 */
	private JTextField txtNamePlayer;
	/**
	 * Een JLabel met als tekst 'Geboortedatum'.
	 */
	private JLabel lblBirthdayPlayer;
	/**
	 * Een JSpinner voor het kunnen opgeven van de geboortedag.
	 */
	private JSpinner spDayPlayer;
	/**
	 * Een JSpinner voor het kunnen opgeven van de geboortemaand.
	 */
	private JSpinner spMonthPlayer;
	/**
	 * Een JTextField voor het kunnen invoeren van een geboortejaar.
	 */
	private JTextField txtYearPlayer;

	/*/**
	* Auto-generated main method to display this
	* JPanel inside a new JFrame.
	*/
	/*public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SelectPlayerPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}*/
	/**
	 * Zorgt voor het aanmaken van het paneel.
	 */

	public SelectPlayerPanel() {
		super();
		initGUI();
	}
	/**
	 * Het aanmaken van alle elementen van het paneel.
	 */
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(160, 230));
			this.setLayout(null);
			{
				lblNamePlayer = new JLabel();
				this.add(lblNamePlayer);
				lblNamePlayer.setBounds(6, 56, 66, 15);
				lblNamePlayer.setText("Naam");
				lblNamePlayer.setLayout(null);
			}
			{
				txtNamePlayer = new JTextField();
				this.add(txtNamePlayer);
				txtNamePlayer.setBounds(6, 77, 149, 21);
			}
			{
				SpinnerListModel spDayPlayerModel =
					new SpinnerListModel(
							new String[] { "1", "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" , "11", "12" , "13" , "14" , "15" , "16" , "17" , "18" , "19" , "20", "21", "22" , "23" , "24" , "25" , "26" , "27" , "28" , "29" , "30", "31" });
				spDayPlayer = new JSpinner();
				this.add(spDayPlayer);
				spDayPlayer.setModel(spDayPlayerModel);
				spDayPlayer.setBounds(4, 137, 43, 21);
			}
			{
				lblBirthdayPlayer = new JLabel();
				this.add(lblBirthdayPlayer);
				lblBirthdayPlayer.setBounds(4, 116, 144, 15);
				lblBirthdayPlayer.setText("Geboortedatum");
			}
			{
				SpinnerListModel spMonthPlayerModel =
					new SpinnerListModel(
							new String[] { "1", "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" , "11", "12" });
				spMonthPlayer = new JSpinner();
				this.add(spMonthPlayer);
				spMonthPlayer.setModel(spMonthPlayerModel);
				spMonthPlayer.setBounds(53, 137, 42, 21);
			}
			{
				txtYearPlayer = new JTextField();
				txtYearPlayer.setText("1980");
				this.add(txtYearPlayer);
				txtYearPlayer.setBounds(101, 137, 49, 21);
			}
			{
				title = new JLabel();
				this.add(title);
				title.setBounds(20, 17, 103, 22);
				title.setHorizontalAlignment(SwingConstants.CENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zet de titel van het paneel waarvan de waarde afhankelijk is van de volgnummer van de speler.
	 * @param title De titel van het paneel.
	 */
	public void setTitle(String title){
		this.title.setText(title);
	}

	/**
	 * Geeft de naam van de speler terug.
	 * @return String
	 */
	public String getName(){
		return (txtNamePlayer.getText());
	}
	/**
	 * Stelt een calander entry op uit de drie opgegeven waarden.
	 * @return Calendar
	 */
	public Calendar getDate(){
		//Bij Month -1 omdat jan. 0 is.
		Calendar calendarPlayer = new GregorianCalendar(Integer.parseInt(txtYearPlayer.getText()), Integer.parseInt((String)spMonthPlayer.getValue())-1, Integer.parseInt((String)spDayPlayer.getValue()));
		return (calendarPlayer);
	}

	/**
	 * Geeft aan of de speler wel op niet geactiveerd is
	 * @return Boolean
	 */
	public boolean isEnabled(){
		boolean enabled;
		if(txtNamePlayer.getText().equals("")){
			enabled=false;
		}
		else enabled=true;
		return enabled;
	}
	/**
	 * Controleert of de diverse velden juist zijn ingevoerd
	 * @return boolean
	 * @throws IOException
	 */
	public int checkSettings() throws IOException
	{
		int errorCode = 0;
		Calendar today = Calendar.getInstance();
		today.add(Calendar.YEAR, -18);
		Date dateOfTodayMinus18Years = today.getTime();
		Calendar calendarPlayer = getDate();
		Date birthdayPlayer = calendarPlayer.getTime();

		//Eerst controle of een naam is opgegeven.
		if(txtNamePlayer.getText().equals("")){
			errorCode += 1;
		}


		//vervolgens controle of de speler ouder is dan 18 jaar.
			if(birthdayPlayer.after(dateOfTodayMinus18Years)){
				errorCode +=  2;
			}

		return (errorCode);
	}
}
