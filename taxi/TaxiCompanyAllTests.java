package taxi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;


public class TaxiCompanyAllTests {

    private TaxiCompany company;

    // R1: Companies and Taxi Tests


    @Test
    public void testR1_AddAndGetAvailable() throws InvalidTaxiName {
        TaxiCompany company = new TaxiCompany();
        company.addTaxi("T1");
        company.addTaxi("T2");

        assertNotNull("R1: Available queue should not be null", company.getAvailable());
        assertEquals("R1: Should have 2 available taxis", 2, company.getAvailable().size());
        assertEquals("R1: First taxi added should be T1", "T1", company.getAvailable().peek().toString());
    }

    @Test(expected = InvalidTaxiName.class)
    public void testR1_AddDuplicateTaxi() throws InvalidTaxiName {
        TaxiCompany company = new TaxiCompany();
        company.addTaxi("T1");
        company.addTaxi("T1"); // Should throw exception
    }

    // R2: Places and Passengers Tests


    @Test
    public void testR2_PlaceAndPassenger() {
        Place place = new Place("Corso Duca Abruzzi 24", "Crocetta");
        assertEquals("R2: toString should return the address", "Corso Duca Abruzzi 24", place.toString());
        assertEquals("R2: getDistrict should return the district", "Crocetta", place.getDistrict());

        Passenger passenger = new Passenger(place);
        assertSame("R2: getPlace should return the same place object", place, passenger.getPlace());
    }

    
    // R3: Taxi Management Tests
    

    @Test
    public void testR3_CallTaxiAndTripCycle() throws InvalidTaxiName {
        TaxiCompany company = new TaxiCompany();
        company.addTaxi("T1");

        Place start = new Place("Start", "S");
        Place end = new Place("End", "E");
        Passenger passenger = new Passenger(start);

        assertEquals("R3: Lost trips should be 0 initially", 0, company.getLostTrips());

        Taxi taxi = company.callTaxi(passenger);
        assertNotNull("R3: A taxi should be assigned", taxi);
        assertEquals("R3: Assigned taxi should be T1", "T1", taxi.toString());
        assertTrue("R3: Available queue should be empty after call", company.getAvailable().isEmpty());

        taxi.beginTrip(end);
        taxi.terminateTrip();

        assertEquals("R3: Taxi should be back in available queue", "T1", company.getAvailable().peek().toString());
    }

    @Test
    public void testR3_LostTrip() {
        TaxiCompany company = new TaxiCompany();
        Passenger passenger = new Passenger(new Place("Someplace", "SomeDistrict"));

        Taxi taxi = company.callTaxi(passenger);
        assertNull("R3: No taxi should be assigned", taxi);
        assertEquals("R3: Lost trips should be 1", 1, company.getLostTrips());
    }

    // R4: Trips Tests

    @Test
    public void testR4_GetTrips() throws InvalidTaxiName {
        TaxiCompany company = new TaxiCompany();
        company.addTaxi("T1");
        company.addTaxi("T2");

        // Perform a trip with T1
        Passenger p1 = new Passenger(new Place("Start1", "D1"));
        Taxi t1 = company.callTaxi(p1);
        t1.beginTrip(new Place("End1", "D2"));
        t1.terminateTrip();

        // Perform a trip with T2
        Passenger p2 = new Passenger(new Place("Start2", "D3"));
        Taxi t2 = company.callTaxi(p2);
        t2.beginTrip(new Place("End2", "D4"));
        t2.terminateTrip();

        List<Trip> tripsT1 = company.getTrips("T1");
        assertEquals("R4: T1 should have 1 trip", 1, tripsT1.size());
        assertEquals("R4: Check trip details for T1", "Start1,End1", tripsT1.get(0).toString());
    }

    @Test(expected = InvalidTaxiName.class)
    public void testR4_GetTripsInvalidId() throws InvalidTaxiName {
        TaxiCompany company = new TaxiCompany();
        company.getTrips("NonExistentID");
    }

    
    // R5: Statistics Tests
    

    // Helper method to set up a predictable state for statistics testing
    private void setupCompanyForStats() throws InvalidTaxiName {
        this.company = new TaxiCompany();
        this.company.addTaxi("T2"); // 1 trip
        this.company.addTaxi("T3"); // 3 trips
        this.company.addTaxi("T1"); // 2 trips

        // To make testing predictable, i bypass the queue and manually create trip records.

        // T1 Trips (2)
        company.addTrip(new Trip(new Place("S", "D"), new Place("E", "DA"), new Taxi("T1", company)));
        company.addTrip(new Trip(new Place("S", "D"), new Place("E", "DB"), new Taxi("T1", company)));

        // T2 Trips (1)
        company.addTrip(new Trip(new Place("S", "D"), new Place("E", "DA"), new Taxi("T2", company)));

        // T3 Trips (3)
        company.addTrip(new Trip(new Place("S", "D"), new Place("E", "DC"), new Taxi("T3", company)));
        company.addTrip(new Trip(new Place("S", "D"), new Place("E", "DC"), new Taxi("T3", company)));
        company.addTrip(new Trip(new Place("S", "D"), new Place("E", "DA"), new Taxi("T3", company)));
    }


    @Test
    public void testR5_StatsTaxi() throws InvalidTaxiName {
        setupCompanyForStats();
        List<InfoI> stats = this.company.statsTaxi();

        // Expected order: T3 (3 trips), T1 (2 trips), T2 (1 trip)
        assertEquals("R5: Should be 3 taxis in stats", 3, stats.size());

        assertEquals("R5: First should be T3", "T3", stats.get(0).getId());
        assertEquals(3, stats.get(0).getCount());

        assertEquals("R5: Second should be T1", "T1", stats.get(1).getId());
        assertEquals(2, stats.get(1).getCount());

        assertEquals("R5: Third should be T2", "T2", stats.get(2).getId());
        assertEquals(1, stats.get(2).getCount());
    }

    @Test
    public void testR5_StatsDistricts() throws InvalidTaxiName {
        setupCompanyForStats();
        List<InfoI> stats = this.company.statsDistricts();

        // Expected counts from setup: DA=3, DC=2, DB=1
        // Expected sorted order: DA (3), DC (2), DB (1)
        assertEquals("R5: Should be 3 districts in stats", 3, stats.size());

        assertEquals("R5: First district should be DA", "DA", stats.get(0).getId());
        assertEquals(3, stats.get(0).getCount());

        assertEquals("R5: Second district should be DC", "DC", stats.get(1).getId());
        assertEquals(2, stats.get(1).getCount());

        assertEquals("R5: Third district should be DB", "DB", stats.get(2).getId());
        assertEquals(1, stats.get(2).getCount());
    }
}