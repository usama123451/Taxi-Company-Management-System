package taxi;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages a fleet of taxis, passengers, and trips.
 */
public class TaxiCompany {
    private Map<String, Taxi> taxis = new HashMap<>();
    private Queue<Taxi> availableTaxis = new LinkedList<>();
    private int lostTrips = 0;
    private List<Trip> completedTrips = new ArrayList<>();

    /**
     * @param id The unique ID of the new taxi.
     * @throws InvalidTaxiName if the ID is already in use.
     */
    public void addTaxi(String id) throws InvalidTaxiName {
        if (taxis.containsKey(id)) {
            throw new InvalidTaxiName("Taxi with ID " + id + " already exists.");
        }
        Taxi newTaxi = new Taxi(id, this);
        taxis.put(id, newTaxi);
        availableTaxis.add(newTaxi);
    }

    /**
     * @return The queue of available taxis.
     */
    public Queue<Taxi> getAvailable() {
        return availableTaxis;
    }

    /**
     * @param p The passenger requesting a taxi.
     * @return The assigned Taxi, or null if none are available.
     */
    public Taxi callTaxi(Passenger p) {
        Taxi taxi = availableTaxis.poll();
        if (taxi == null) {
            lostTrips++;
            return null;
        }
        taxi.assignPassenger(p);
        return taxi;
    }

    /**
     * @return The count of lost trips.
     */
    public int getLostTrips() {
        return lostTrips;
    }

    /**
     * Called by a Taxi to return to the available queue after a trip.(the method used is internal to the package)
     * @param taxi The taxi that has completed a trip.
     */
    void returnTaxi(Taxi taxi) {
        availableTaxis.offer(taxi);
    }

    /**
     * Adds a completed trip to the company's log.
     * @param trip The completed trip.
     */
    void addTrip(Trip trip) {
        completedTrips.add(trip);
    }

    /**
     * @param id The ID of the taxi.
     * @return A list of trips for the given taxi.
     * @throws InvalidTaxiName if the taxi ID does not exist.
     */
    public List<Trip> getTrips(String id) throws InvalidTaxiName {
        if (!taxis.containsKey(id)) {
            throw new InvalidTaxiName("Taxi with ID " + id + " not found.");
        }
        return completedTrips.stream()
                .filter(trip -> trip.getTaxi().toString().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * @return A sorted list of taxi statistics.
     */
    public List<InfoI> statsTaxi() {
        Map<String, Integer> tripCounts = new HashMap<>();
        for (Taxi taxi : taxis.values()) {
            tripCounts.put(taxi.toString(), 0);
        }
        for (Trip trip : completedTrips) {
            tripCounts.merge(trip.getTaxi().toString(), 1, Integer::sum);
        }

        return tripCounts.entrySet().stream()
                .map(entry -> new InfoI() {
                    public String getId() { return entry.getKey(); }
                    public int getCount() { return entry.getValue(); }
                })
                .sorted(Comparator.comparing(InfoI::getCount).reversed()
                        .thenComparing(InfoI::getId))
                .collect(Collectors.toList());
    }

    /**
     * @return A sorted list of district statistics.
     */
    public List<InfoI> statsDistricts() {
        Map<String, Integer> districtCounts = completedTrips.stream()
                .collect(Collectors.groupingBy(
                        trip -> trip.getDestination().getDistrict(),
                        Collectors.summingInt(t -> 1)
                ));

        return districtCounts.entrySet().stream()
                .map(entry -> new InfoI() {
                    public String getId() { return entry.getKey(); }
                    public int getCount() { return entry.getValue(); }
                })
                .sorted(Comparator.comparing(InfoI::getCount).reversed()
                        .thenComparing(InfoI::getId))
                .collect(Collectors.toList());
    }
}