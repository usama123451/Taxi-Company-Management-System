package taxi;

/**
 * Represents a passenger with a current location.
 */
public class Passenger {
    private Place place;

    /**
     * @param place The passenger's current location.
     */
    public Passenger(Place place) {
        this.place = place;
    }

    /**
     * @return The current place of the passenger.
     */
    public Place getPlace() {
        return place;
    }
}