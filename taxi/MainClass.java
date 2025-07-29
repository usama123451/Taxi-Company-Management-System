package taxi;

import java.util.List;


public class MainClass {

    public static void main(String[] args) throws InvalidTaxiName {
        TaxiCompany company = new TaxiCompany();

        // R1: Adding taxis
        company.addTaxi("T1");
        company.addTaxi("T2");
        company.addTaxi("T3");

        System.out.println("Available Taxis: " + company.getAvailable()); // [T1, T2, T3]

        // R2: Creating places and passengers
        Place p1 = new Place("Corso Duca Abruzzi 24", "Crocetta");
        Place p2 = new Place("Via Roma 10", "Centro");
        Passenger passenger1 = new Passenger(p1);

        // R3: Calling a taxi and managing trips
        Taxi taxi = company.callTaxi(passenger1);
        System.out.println("Assigned Taxi: " + taxi); 

        taxi.beginTrip(p2);
        taxi.terminateTrip();

        System.out.println("Available Taxis after trip: " + company.getAvailable()); // [T2, T3, T1]

        // R4: Getting trip history
        List<Trip> trips = company.getTrips("T1");
        System.out.println("Trips for T1: " + trips); // [Corso Duca Abruzzi 24,Via Roma 10]

        // R5: Statistics
        System.out.println("Taxi Stats: ");
        company.statsTaxi().forEach(s -> System.out.println(s.getId() + ": " + s.getCount()));

        System.out.println("\nDistrict Stats: ");
        company.statsDistricts().forEach(s -> System.out.println(s.getId() + ": " + s.getCount()));
    }
}