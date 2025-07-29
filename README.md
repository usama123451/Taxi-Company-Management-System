# Taxi-Company-Management-System
We create an application to run a taxi service. All parts of your code must be in the taxi package.
Requirement 1:Setting up the Company and Taxis.
A taxi company is represented by the TaxiCompany class.
Individual taxis are represented by the Taxi class. When you create a new taxi, you must give it a unique ID. The toString() method for a Taxi will show this ID.
The TaxiCompany has a method called addTaxi(String id) to add a new taxi to its fleet. If you try to add a taxi with an ID that already exists, the system will trigger an InvalidTaxiName exception.
When a taxi is added, it is placed at the back of the line of available taxis. You can see this line of waiting taxis by using the getAvailable() method.
Requirement 2: Handling Locations and Customers
A location is represented by the Place class, which takes a street address and a district name as arguments (for example, "Corso Duca Abruzzi 24" and "Crocetta"). The toString() method for a Place will show its address.
A customer is represented by the Passenger class. A passenger is created with their current location, which is where the taxi needs to pick them up. The getPlace() method will show where the passenger is located.
Requirement 3: Managing Taxi Rides
Available taxis are managed in a "first-in, first-out" queue.
The callTaxi(Passenger p) method takes the next available taxi from the queue and assigns it to the passenger. This method returns the assigned taxi. If no taxis are free, the trip is considered lost. You can check the number of lost trips with the getLostTrips() method.
The Taxi class handles the start and end of a ride:
A taxi driver begins the ride using the beginTrip(Place dest) method. The destination is provided as an argument, while the starting point is the passenger's current location.
The ride is finished with the terminateTrip() method. This action updates the passenger's location to the destination and sends the taxi to the back of the available taxis queue.
Requirement 4: Tracking Trips
A completed ride is represented by the Trip class. The toString() method for a Trip will show the start and end addresses, separated by a comma.
The TaxiCompany has a getTrips(String id) method that gives you a list of all trips completed by a specific taxi, in the order they happened. If the taxi ID doesn't exist, the system will trigger an InvalidTaxiName exception.
Requirement 5: Viewing Statistics
The TaxiCompany class provides two methods for statistics, both returning lists through the InfoI interface:
statsTaxi(): Returns a list of taxis, sorted from most to fewest completed trips. If there's a tie in trip count, they are sorted alphabetically by ID.
statsDistricts(): Returns a list of districts that have been a destination for at least one trip. The list is sorted from most to fewest trips to that district. If there's a tie, they are sorted alphabetically by the district name.
