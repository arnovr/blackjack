package blackjack;

public class BlackJackException extends Exception {
	/**
	 * Exception klasse voor het blackjack-spel.
	 *
	 */

	/**
	 * De bron van de exceptie
	 */
	private String source;
	private static final long serialVersionUID = 537169773156176638L;

	public BlackJackException() {
		super();
	}
	public BlackJackException(String message) {
		super(message);

		System.out.println("BlackJackException: (unknown) " + message);
	}
	public BlackJackException(String message, String source) {
		super(message);

		this.source = source;

		System.out.println("BlackJackException: (" + this.source + ") " + message);
	}
}
