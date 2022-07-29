package Model;
/**
 *
 * @author Jose Arvizu
 */
/** OutSourced class with information.*/
public class OutSourced extends Part {

    private String companyName;
    /** Constructor for OutSourced. */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** Getter for companyname.
        @return the company name */
    public String getCompanyName() {
        return companyName;
    }
    /** Setter for companyname.
        @param companyName the company name to set */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
