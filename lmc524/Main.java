package lmc524coolbaugh.lmc524;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    private static final String DB_URL = "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241";
    private static Connection conn;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        connectToDatabase(); // Method to establish database connection

        System.out.println("Select the number associated with the role you would like to proceed with: ");
        System.out.println("1. Property Manager");
        System.out.println("2. Tenant");
        System.out.println("3. Company Manager");

        int userRole = s.nextInt();
        s.nextLine();
        switch (userRole) {
            case 1:
                // PropertyManager logic
                PropertyManager propertyManager = new PropertyManager(conn);

                System.out.println("You've selected Property Manager.");
                System.out.println("Enter the number associated with the option you want to choose");
                System.out.println("1. record visit data");
                System.out.println("2. record lease data");
                System.out.println("3. add person/pet to lease");

                int pmChoice = s.nextInt();
                s.nextLine();

                switch (pmChoice) {
                    case 1:
                        // record visit data
                        System.out.println("Enter the Apartment Number:");
                        int aptNumber = s.nextInt();
                        s.nextLine(); // Consume the newline

                        System.out.println("Enter the Credit Score:");
                        int creditScore = s.nextInt();
                        s.nextLine(); // Consume the newline

                        System.out.println("Enter the Date Visited (yyyy-mm-dd):");
                        String dateStr = s.nextLine();
                        LocalDate dateVisited = LocalDate.parse(dateStr);

                        try {
                            propertyManager.recordProspectiveTenant(aptNumber, creditScore, dateVisited);
                        } catch (SQLException e) {
                            System.out.println("An error occurred while recording visit data: " + e.getMessage());
                            // Handle other exceptions or rollback if necessary
                        }
                        break;
                    case 2:
                        System.out.println("Enter the Prospective Tenant ID:");
                        int prospectiveTenantId = s.nextInt();
                        s.nextLine(); // Consume the newline character

                        System.out.println("Enter the tenant's first name:");
                        String firstNameT = s.nextLine();
                        System.out.println("Enter the tenant's last name:");
                        String lastNameT = s.nextLine();
                        System.out.println("Enter the tenant's phone:");
                        String phoneT = s.nextLine();
                        System.out.println("Enter the tenant's email:");
                        String emailT = s.nextLine();
                        System.out.println("Enter the tenant's payment method:");
                        String paymentMethodT = s.nextLine();

                        System.out.println("Enter the lease rate:");
                        double rateT = s.nextDouble();
                        s.nextLine(); // Consume the newline character

                        System.out.println("Enter the security deposit amount:");
                        double depositT = s.nextDouble();
                        s.nextLine(); // Consume the newline character

                        System.out.println("Enter the move-in date (YYYY-MM-DD):");
                        String moveInDateStrT = s.nextLine();
                        LocalDate moveInDateT = LocalDate.parse(moveInDateStrT);

                        try {
                            propertyManager.recordLeaseData(prospectiveTenantId, firstNameT, lastNameT, phoneT, emailT,
                                    paymentMethodT, rateT, depositT, moveInDateT);
                            System.out.println("Lease data recorded successfully.");
                        } catch (SQLException e) {
                            System.out.println("Error recording lease data: " + e.getMessage());
                        }
                        break;

                    case 3:

                        System.out.println("Enter the Apartment Number:");
                        int apartmentNum = s.nextInt();
                        s.nextLine(); // Consume the newline character

                        System.out.println("Enter the tenant's first name:");
                        String firstName = s.nextLine();
                        System.out.println("Enter the tenant's last name:");
                        String lastName = s.nextLine();
                        System.out.println("Enter the tenant's phone:");
                        String phone = s.nextLine();
                        System.out.println("Enter the tenant's email:");
                        String email = s.nextLine();
                        System.out.println("Enter the tenant's payment method:");
                        String paymentMethod = s.nextLine();

                        System.out.println("Enter the lease rate:");
                        double rate = s.nextDouble();
                        s.nextLine(); // Consume the newline character

                        System.out.println("Enter the security deposit amount:");
                        double deposit = s.nextDouble();
                        s.nextLine(); // Consume the newline character

                        System.out.println("Enter the move-in date (YYYY-MM-DD):");
                        String moveInDateStr = s.nextLine();
                        LocalDate moveInDate = LocalDate.parse(moveInDateStr);

                        try {
                            propertyManager.addTenantToLeaseWithApartmentNum(apartmentNum, phone, email, paymentMethod,
                                    firstName, lastName, rate, deposit, moveInDate);
                            System.out.println("Tenant added successfully to lease.");
                        } catch (SQLException e) {
                            System.out.println("Error adding tenant to lease: " + e.getMessage());
                        }

                        break;
                }
                break;

            case 2:
                // Tenant logic
                System.out.println("Enter your first name:");
                String firstName = s.nextLine();
                System.out.println("Enter your last name:");
                String lastName = s.nextLine();
                System.out.println("Enter your apartment number:");
                int apartmentNumber = s.nextInt();

                int tenantId = getTenantId(firstName, lastName, apartmentNumber);
                if (tenantId != -1) {
                    Tenant tenant = new Tenant(tenantId);

                    System.out.println("Select the number next to the option you want to choose");
                    System.out.println("1. Check Payment Status");
                    System.out.println("2. Make a Payment");
                    System.out.println("3. Update personal data");

                    int tenantChoice = s.nextInt();

                    switch (tenantChoice) {
                        case 1:
                            tenant.checkPaymentStatus(conn); // Check payment status
                            break;
                        case 2:
                            // make a payment
                            System.out.println("Enter the amount you would like to pay: ");
                            double amount = s.nextDouble();
                            tenant.makePayment(conn, amount);
                            break;
                        case 3:
                            // update personal data
                            System.out.println("");
                            break;
                        case 4:
                            System.out.println("invalid choice, try again");
                            // make this into a method to loop
                            break;
                    }

                } else {
                    System.out.println("Tenant not found. Please check the information provided.");
                }
                break;
            case 3:

                System.out.println("You've selected Company Manager.");
                System.out.println("Enter the street address of the new property:");
                String street = s.nextLine();

                System.out.println("Enter the city of the new property:");
                String city = s.nextLine();
                System.out.println("Enter the state of the new property:");
                String state = s.nextLine();
                System.out.println("Enter the ZIP code of the new property:");
                String zipCode = s.nextLine();

                System.out.println("Enter the number of apartments to add for this property:");
                int numOfApts = s.nextInt();

                System.out.println("Enter the size of each apartment:");
                int size = s.nextInt();
                System.out.println("Enter the number of bedrooms in each apartment:");
                int bedrooms = s.nextInt();
                s.nextLine();
                System.out.println("Enter the number of bathrooms in each apartment:");
                double bathrooms = s.nextDouble();
                s.nextLine(); // Consume the leftover newline
                System.out.println("Enter common amenities (comma-separated):");
                String commonAmenities = s.nextLine();

                CompanyManager cm = new CompanyManager();
                try {
                    cm.addPropertyWithApartments(conn, street, city, state, zipCode, numOfApts, size, bedrooms,
                            bathrooms, commonAmenities);
                } catch (SQLException e) {
                    System.out.println("An error occurred while adding the property: " + e.getMessage());
                    // Rollback transaction in case of failure
                    try {
                        if (conn != null) {
                            conn.rollback();
                        }
                    } catch (SQLException ex) {
                        System.out.println("An error occurred while rolling back the transaction: " + ex.getMessage());
                    }
                }
                break;
            default:
                // Error handling
                System.out.println("Invalid option selected.");
                break;
        }

        s.close(); // Close the scanner
    }

    public static void connectToDatabase() {
        Scanner s = new Scanner(System.in);
        try {

            System.out.println("Enter your username");
            String username = s.nextLine();
            System.out.println("Enter your password");
            String password = s.nextLine();
            conn = DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Connected to the database successfully.");

        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }

    }

    public static boolean validateString(Scanner s) {
        if (s.hasNextLine()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getTenantId(String firstName, String lastName, int apartmentNumber) {
        String sql = "select tenantid from tenants where firstname = ? and lastname = ? and apartmentnum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, apartmentNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("tenantId");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tenant ID: " + e.getMessage());
        }
        return -1; // Return -1 if tenant ID is not found
    }
}

/*
 * check payment status
 * make a payment
 * udpate personal data
 */
class Tenant {
    private int tenantId; // Tenant's unique identifier

    public Tenant(int tenantId) {
        this.tenantId = tenantId;
    }

    // Check payment status
    void checkPaymentStatus(Connection conn) {
        // Assume rent is due on the first of every month
        LocalDate firstOfThisMonth = LocalDate.now().withDayOfMonth(1);
        String sql = "SELECT leaseId, rate FROM LEASE WHERE tenantId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.tenantId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int leaseId = rs.getInt("leaseId");
                double leaseRate = rs.getDouble("rate");
                double totalPaid = getTotalPaidForCurrentMonth(conn, leaseId, firstOfThisMonth);

                double remainingRent = leaseRate - totalPaid;
                if (remainingRent > 0) {
                    System.out.println("Remaining rent due for the current month: $" + remainingRent);
                } else {
                    System.out.println("No outstanding rent for the current month.");
                }
            } else {
                System.out.println("No lease found for tenant ID: " + this.tenantId);
            }
        } catch (SQLException e) {
            System.out.println("Error when checking payment status: " + e.getMessage());
        }
    }

    private double getTotalPaidForCurrentMonth(Connection conn, int leaseId, LocalDate firstOfThisMonth)
            throws SQLException {
        String sql = "SELECT SUM(amount) as totalPaid FROM PAYMENTS WHERE leaseId = ? AND EXTRACT(MONTH FROM datepaid) = ? AND EXTRACT(YEAR FROM datepaid) = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, leaseId);
            pstmt.setInt(2, firstOfThisMonth.getMonthValue());
            pstmt.setInt(3, firstOfThisMonth.getYear());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("totalPaid");
            }
            return 0.0; // Return 0 if no payments found
        }
    }

    // Make a payment
    public void makePayment(Connection conn, double amount) {
        // First, retrieve the leaseId associated with the tenantId
        String leaseSql = "SELECT leaseId FROM LEASE WHERE tenantId = ?";
        int leaseId = -1;

        try (PreparedStatement leasePstmt = conn.prepareStatement(leaseSql)) {
            leasePstmt.setInt(1, this.tenantId);
            ResultSet rs = leasePstmt.executeQuery();
            if (rs.next()) {
                leaseId = rs.getInt("leaseId");
            } else {
                System.out.println("Lease ID not found for tenant ID: " + this.tenantId);
                return; // Exit the method if no leaseId is found
            }
        } catch (SQLException e) {
            System.out.println("Error when retrieving Lease ID: " + e.getMessage());
            return; // Exit the method if there is an SQL error
        }

        // Now, perform the insert into the PAYMENTS table
        // Retrieve the next paymentId
        String idSql = "SELECT MAX(paymentId) as maxId FROM PAYMENTS";
        int nextId = -1;

        try (PreparedStatement idPstmt = conn.prepareStatement(idSql)) {
            ResultSet rs = idPstmt.executeQuery();
            if (rs.next()) {
                nextId = rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error when retrieving next Payment ID: " + e.getMessage());
            return; // Exit the method if there is an SQL error
        }

        // Now, perform the insert into the PAYMENTS table
        String paymentSql = "INSERT INTO PAYMENTS (paymentId, leaseId, amount, datepaid) VALUES (?, ?, ?, SYSDATE)";

        try (PreparedStatement paymentPstmt = conn.prepareStatement(paymentSql)) {
            paymentPstmt.setInt(1, nextId);
            paymentPstmt.setInt(2, leaseId);
            paymentPstmt.setDouble(3, amount);

            int affectedRows = paymentPstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Payment recorded successfully.");
            } else {
                System.out.println("Payment could not be processed.");
            }
        } catch (SQLException e) {
            System.out.println("Error when making payment: " + e.getMessage());
        }

    }

    // Update personal data
    public void updatePersonalData(Connection conn, String phone, String email, String paymentMethod) {
        String sql = "UPDATE TENANTS SET phone = ?, email = ?, paymentMethod = ? WHERE tenantId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, email);
            pstmt.setString(3, paymentMethod);
            pstmt.setInt(4, this.tenantId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Personal data updated successfully.");
            } else {
                System.out.println("No update made. Please check the tenant ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error when updating personal data: " + e.getMessage());
        }
    }
}

/*
 * add new property with info pertaining to it
 */
class PropertyManager {

    private Connection conn;

    public PropertyManager(Connection conn) {
        this.conn = conn;
    }

    public int recordProspectiveTenant(int apartmentNum, int creditScore, LocalDate dateVisited) throws SQLException {
        // Assuming PTID is auto-generated using a sequence, it's not included in the
        // INSERT statement
        String sql = "INSERT INTO ProspectiveTenant (ApartmentNum, CreditScore, DateVisited) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, apartmentNum);
        pstmt.setInt(2, creditScore);
        pstmt.setDate(3, Date.valueOf(dateVisited)); // Convert LocalDate to java.sql.Date
        pstmt.executeUpdate();

        String seqSql = "SELECT LMC524.PITD_SEQ.CURRVAL FROM DUAL";
        try (Statement seqStmt = conn.createStatement();
                ResultSet rs = seqStmt.executeQuery(seqSql)) {
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("insertPT done with id: " + generatedId);
                return generatedId;
            } else {
                throw new SQLException("Failed to retrieve generated pitd ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error in insertPT: " + e.getMessage());
            throw e;
        }
    }

    // Method to record lease data by converting a prospective tenant to an actual
    // tenant
    public void recordLeaseData(int prospectiveTenantId, String firstName, String lastName, String phone, String email,
            String paymentMethod, double rate, double securityDeposit, LocalDate moveInDate) throws SQLException {
        // Start transaction
        conn.setAutoCommit(false);
        try {
            // Retrieve apartment number and remove prospective tenant
            int apartmentNum = removeProspectiveTenantAndGetApartmentNum(prospectiveTenantId);

            // Insert new tenant
            int tenantId = insertNewTenant(apartmentNum, phone, email, paymentMethod, firstName, lastName);

            // Insert new lease
            insertLease(tenantId, rate, securityDeposit, moveInDate, null); // Assuming move-out date is initially not
                                                                            // set

            // Commit transaction
            conn.commit();
            System.out.println("Lease data recorded successfully for apartment number: " + apartmentNum);
        } catch (SQLException e) {
            System.out.println("Failed to record lease data: " + e.getMessage());
            conn.rollback(); // Rollback transaction on error
        } finally {
            conn.setAutoCommit(true); // Reset auto-commit to true
        }
    }

    // Method to remove a prospective tenant entry and return the ApartmentNum
    public int removeProspectiveTenantAndGetApartmentNum(int ptid) throws SQLException {
        // First, retrieve the ApartmentNum
        String selectSql = "SELECT ApartmentNum FROM ProspectiveTenant WHERE pitd = ?";
        int apartmentNum = -1; // Default value if not found
        try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
            selectPstmt.setInt(1, ptid);
            ResultSet rs = selectPstmt.executeQuery();
            if (rs.next()) {
                apartmentNum = rs.getInt("ApartmentNum");
            } else {
                throw new SQLException("Prospective tenant with PTID " + ptid + " not found.");
            }
        }

        // If ApartmentNum was found, delete the entry
        if (apartmentNum != -1) {
            String deleteSql = "DELETE FROM ProspectiveTenant WHERE pitd = ?";
            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                deletePstmt.setInt(1, ptid);
                int affectedRows = deletePstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException(
                            "Deletion of prospective tenant with PTID " + ptid + " failed, no rows affected.");
                }
            }
        }

        return apartmentNum; // Return the ApartmentNum of the deleted entry
    }

    // Method to insert a new tenant with the provided details
    public int insertNewTenant(int apartmentNum, String phone, String email, String paymentMethod, String firstName,
            String lastName) throws SQLException {
        // SQL statement for inserting a new tenant
        String sql = "INSERT INTO Tenants (ApartmentNum, Phone, Email, PaymentMethod, FirstName, LastName) VALUES (?, ?, ?, ?, ?, ?)";
        // Prepare the statement to get generated keys (TenantID)
        try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[] { "TENANTID" })) {
            pstmt.setInt(1, apartmentNum);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, paymentMethod);
            pstmt.setString(5, firstName);
            pstmt.setString(6, lastName);

            pstmt.executeUpdate();
            String seqSql = "SELECT LMC524.TENANTID_SEQ.CURRVAL FROM DUAL";
            try (Statement seqStmt = conn.createStatement();
                    ResultSet rs = seqStmt.executeQuery(seqSql)) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("insertTenant done with id: " + generatedId);
                    return generatedId;
                } else {
                    throw new SQLException("Failed to retrieve generated tenant ID.");
                }
            } catch (SQLException e) {
                System.out.println("Error in insertTenant: " + e.getMessage());
                throw e;
            }

        }
    }

    // Method to insert a new lease record
    public int insertLease(int tenantId, double rate, double securityDeposit, LocalDate moveInDate,
            LocalDate moveOutDate) throws SQLException {
        // SQL statement for inserting a new lease
        String sql = "INSERT INTO Lease (TenantID, Rate, SecurityDeposit, MoveInDate, MoveOutDate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tenantId);
            pstmt.setDouble(2, rate);
            pstmt.setDouble(3, securityDeposit);
            pstmt.setDate(4, java.sql.Date.valueOf(moveInDate));
            if (moveOutDate != null) {
                pstmt.setDate(5, java.sql.Date.valueOf(moveOutDate));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE); // Handle the case where move out date is null
            }

            pstmt.executeUpdate();
            String seqSql = "SELECT LMC524.LEASEID_SEQ.CURRVAL FROM DUAL";
            try (Statement seqStmt = conn.createStatement();
                    ResultSet rs = seqStmt.executeQuery(seqSql)) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("insertLease done with id: " + generatedId);
                    return generatedId;
                } else {
                    throw new SQLException("Failed to retrieve generated lease ID.");
                }
            } catch (SQLException e) {
                System.out.println("Error in insertLease: " + e.getMessage());
                throw e;
            }

        }
    }

    public void addTenantToLeaseWithApartmentNum(int apartmentNum, String phone, String email, String paymentMethod,
            String firstName, String lastName, double rate, double deposit, LocalDate moveInDate) throws SQLException {
        // Begin transaction block
        conn.setAutoCommit(false);
        try {
            // Insert the tenant into the Tenants table
            int tenantId = insertNewTenant(apartmentNum, phone, email, paymentMethod, firstName, lastName);

            // // Check that the apartment number exists and is available
            // if (!isApartmentAvailable(apartmentNum)) {
            // throw new SQLException("Apartment is not available for leasing.");
            // }

            // Create a new lease for the tenant
            insertLease(tenantId, rate, deposit, moveInDate, null); // Assuming move out date is initially not set

            // Commit the transaction
            conn.commit();
            System.out.println("New tenant added to lease for apartment number: " + apartmentNum);
        } catch (SQLException e) {
            System.out.println("Failed to add tenant to lease: " + e.getMessage());
            conn.rollback(); // Rollback transaction on error
        } finally {
            conn.setAutoCommit(true); // Reset auto-commit to true
        }
    }
}

/*
 * record visit data
 * record lease data
 * record move-out
 * add person/pet to lease
 * set move-out
 */

class CompanyManager {

    public CompanyManager() {

    }

    public void addPropertyWithApartments(Connection conn, String street, String city, String state, String zipCode,
            int numOfApts, int size, int bedrooms, double bathrooms,
            String commonAmenities) throws SQLException {
        // Start transaction
        conn.setAutoCommit(false);

        // Insert the new property into the PROPERTIES table
        int propertyId = insertProperty(conn, street, city, state, zipCode);
        System.out.println("insertProperty done with id: " + propertyId);
        if (propertyId != -1) {

            // Generate and insert apartment data for the property
            for (int i = 0; i < numOfApts; i++) {
                insertApartment(conn, propertyId, size, bedrooms, bathrooms, commonAmenities);
            }

            // Commit transaction
            conn.commit();
            System.out.println("Property with apartments added successfully.");
        } else {
            // Rollback in case of error
            conn.rollback();
            System.out.println("Failed to add property and apartments.");
        }

        // End transaction
        conn.setAutoCommit(true);
    }

    private int insertProperty(Connection conn, String street, String city, String state, String zipCode)
            throws SQLException {
        String sql = "INSERT INTO PROPERTIES (STREET, CITY, STATE, ZIPCODE) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        System.out.println("Inserting Property - Street: " + street + ", City: " + city + ", State: " + state
                + ", ZipCode: " + zipCode);

        pstmt.setString(1, street);
        pstmt.setString(2, city);
        pstmt.setString(3, state);
        pstmt.setString(4, zipCode);
        pstmt.executeUpdate();

        // Query the current value of the sequence
        String seqSql = "SELECT LMC524.PROPERTY_SEQ.CURRVAL FROM DUAL";
        try (Statement seqStmt = conn.createStatement();
                ResultSet rs = seqStmt.executeQuery(seqSql)) {
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("insertProperty done with id: " + generatedId);
                return generatedId;
            } else {
                throw new SQLException("Failed to retrieve generated property ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error in insertProperty: " + e.getMessage());
            throw e;
        }
    }

    private int insertApartment(Connection conn, int propertyId, double size, int bedrooms, double bathrooms,
            String commonAmenities) throws SQLException {
        String sql = "INSERT INTO APARTMENTS (PROPERTYID, APARTMENTSIZE, BEDROOMS, BATHROOMS, COMMONAMENITIES) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Debugging: Print the values being inserted
        System.out.println("Inserting Apartment - PropertyID: " + propertyId + ", Size: " + size + ", Bedrooms: "
                + bedrooms + ", Bathrooms: " + bathrooms + ", Amenities: " + commonAmenities);

        pstmt.setInt(1, propertyId);
        pstmt.setDouble(2, size);
        pstmt.setInt(3, bedrooms);
        pstmt.setDouble(4, bathrooms);
        pstmt.setString(5, commonAmenities);

        pstmt.executeUpdate();
        String seqSql = "SELECT LMC524.APARTMENT_NUM_SEQ.CURRVAL FROM DUAL";
        try (Statement seqStmt = conn.createStatement();
                ResultSet rs = seqStmt.executeQuery(seqSql)) {
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("insertApartment done with id: " + generatedId);
                return generatedId;
            } else {
                throw new SQLException("Failed to retrieve generated apartment ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error in insertApartment: " + e.getMessage());
            throw e;
        }
    }

}

class ApartmentTemplate {
    int size;
    int bedrooms;
    double bathrooms;
    String features;
    String amenities;

    public ApartmentTemplate(int size, int bedrooms, double bathrooms, String features, String amenities) {
        this.size = size;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.features = features;
        this.amenities = amenities;
    }

    // Getters and setters or other methods if necessary
    //
}