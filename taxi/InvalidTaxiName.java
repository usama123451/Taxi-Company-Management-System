package taxi;

/**
 * An exception thrown when an invalid taxi ID is used.
 */
@SuppressWarnings("serial")
public class InvalidTaxiName extends Exception {
    public InvalidTaxiName(String message) {
        super(message);
    }
}