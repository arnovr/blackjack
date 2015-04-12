package blackjack;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RoundsCellRenderer extends DefaultTableCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8838559330460098098L;
	
	private Game game = null;
	
	public RoundsCellRenderer(Game g) {
		super();
		this.game = g;
	}
	
	/**
	 * Do the cell rendering
	 */
	public Component getTableCellRendererComponent (JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
		Component curCell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
		curCell.setBackground(Color.WHITE);
		int columnCount = 0;
		// Load each player, and make a column for him
		ArrayList<String> playerNames = new ArrayList<String>();
		try {
			// Walk through each round
			for(Round r : this.game.getRounds()) {
				columnCount = 2;
				// Check if the cell row is the same as the round row
				if ( row == this.game.getRounds().indexOf(r) ) {
					// Get each player from that row
					for(Player p : r.getPlayers() ) {
						// Dealer doet niet mee
						if ( !playerNames.contains(p.getName()) && p.getClass() != Dealer.class ) {
							// Is the column/player selected the same as the column loop?
							if ( column == columnCount ) {
								// Walk each hand, make the cell a color if a person has won or lost
								for(Hand h : p.getHands()) {
									if ( h.getWon() ) {
										curCell.setBackground(Color.GREEN);
									}
									if ( h.getLost() ) {
										curCell.setBackground(Color.RED);
									}
									if ( h.getEven() ) {
										curCell.setBackground(Color.GRAY);
									}
									return curCell;
								}
							}
							columnCount++;
						}
					}
				}
			}
		}
		catch ( NullPointerException e ) {
		}

		return curCell;
	}
}