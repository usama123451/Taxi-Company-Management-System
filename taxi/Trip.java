package taxi;

/**
 * Represents a single trip from a departure point to a destination.
 */
public class Trip {
    private Place departure;
    private Place destination;
    private Taxi taxi;

    /**
     * @param departure The starting place.
     * @param destination The ending place.
     * @param taxi The taxi that performed the trip.
     */
    public Trip(Place departure, Place destination, Taxi taxi) {
        this.departure = departure;
        this.destination = destination;
        this.taxi = taxi;
    }
    
    public Place getDestination() {
        return this.destination;
    }
    
    public Taxi getTaxi() {
        return this.taxi;
    }

    /**
     * @return A string in the format "departure,destination".
     */
    @Override
    public String toString() {
        return departure.toString() + "," + destination.toString();
    }
}