package taxi;

/**
 * Represents a physical location with an address and a district.
 */
public class Place {
    private String address;
    private String district;

    /**
     * @param address The street address.
     * @param district The district name.
     */
    public Place(String address, String district) {
        this.address = address;
        this.district = district;
    }

    /**
     * @return The district name.
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @return The address.
     */
    @Override
    public String toString() {
        return address;
    }
}