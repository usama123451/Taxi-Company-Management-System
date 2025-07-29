package taxi;

/**
 * Represents a single taxi with a unique ID.
 */
public class Taxi {
    private String id;
    private TaxiCompany company;
    private Passenger currentPassenger;
    private Place destination;

    /**
     * @param id The unique identifier for the taxi.
     */
    public Taxi(String id, TaxiCompany company) {
        this.id = id;
        this.company = company;
    }
    
    public String getId() {
        return this.id;
    }

    /**
     * 
     * @param p The passenger to assign.
     */
    void assignPassenger(Passenger p) {
        this.currentPassenger = p;
    }


    /**
     * @param dest The destination place.
     */
    public void beginTrip(Place dest) {
        this.destination = dest;
    }

    /**
     * Terminates the current trip.it Creates a new Trip object,adds it to the company's
     *  trip log, and places the taxi back in the available queue.
     */
    public void terminateTrip() {
        if (currentPassenger != null && destination != null) {
            Trip trip = new Trip(currentPassenger.getPlace(), destination, this);
            company.addTrip(trip);
            this.currentPassenger = null;
            this.destination = null;
            company.returnTaxi(this);
        }
    }

    /**
     * @return The string representation of taxi's ID.
     */
    @Override
    public String toString() {
        return id;
    }
}