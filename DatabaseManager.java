/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package projetdegestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:projetDeGestion.db";
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

    static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            logger.info("Connected to SQLite database.");
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Database connection error.", e);
        }
        return connection;
    }

    // Helper method to execute table creation SQL
    private static void executeUpdate(String sql, String successMessage) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info(successMessage);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL execution error: " + successMessage, e);
        }
    }

    public static void createStudentsTable() {
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS Students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom VARCHAR(100), " +
                "prenom VARCHAR(100), " +
                "date_naissance DATE, " +
                "numero_telephone VARCHAR(15), " +
                "email VARCHAR(100), " +
                "classe VARCHAR(50), " +
                "date_debut_inscription DATE, " +
                "date_fin_inscription DATE, " +
                "type_seance TEXT, " +
                "parent_id INTEGER, " +
                "acquitte VARCHAR(50), " +
                "jours VARCHAR(50), " +
                "FOREIGN KEY (parent_id) REFERENCES Parents(id)" +
                ");";
        executeUpdate(createStudentsTable, "Table 'Students' created successfully!");
    }

    public static void createParentsTable() {
        String createParentsTable = "CREATE TABLE IF NOT EXISTS Parents (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom_complet VARCHAR(100), " +
                "numero_telephone VARCHAR(15), " +
                "email VARCHAR(100), " +
                "adresse VARCHAR(255), " +
                "code_postal VARCHAR(10), " +
                "ville VARCHAR(50)" +
                ");";
        executeUpdate(createParentsTable, "Table 'Parents' created successfully!");
    }

    public static void createCoursesTable() {
        String createCoursesTable = "CREATE TABLE IF NOT EXISTS Courses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eleve_id INTEGER, " +
                "nombre_heures INTEGER, " +
                "type_seance TEXT, " +
                "date DATE, " +
                "FOREIGN KEY (eleve_id) REFERENCES Students(id)" +
                ");";
        executeUpdate(createCoursesTable, "Table 'Courses' created successfully!");
    }

    public static void createBillingTable() {
        String createBillingTable = "CREATE TABLE IF NOT EXISTS Billing (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eleve_id INTEGER, " +
                "montant_total FLOAT, " +
                "montant_paye FLOAT, " +
                "difference FLOAT, " +
                "date_facture DATE, " +
                "FOREIGN KEY (eleve_id) REFERENCES Students(id)" +
                ");";
        executeUpdate(createBillingTable, "Table 'Billing' created successfully!");
    }

    public static void createParametersTable() {
        String createParametersTable = "CREATE TABLE IF NOT EXISTS Parameters (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "prix_individuel FLOAT, " +
                "prix_groupe FLOAT, " +
                "mention_legale TEXT" +
                ");";
        executeUpdate(createParametersTable, "Table 'Parameters' created successfully!");
    }

    public static void createSessionsTable() {
        String createSessionsTable = "CREATE TABLE IF NOT EXISTS Sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eleve_id INTEGER, " +
                "mois TEXT, " +
                "semaine TINYINT, " +
                "heures FLOAT, " +
                "FOREIGN KEY (eleve_id) REFERENCES Students(id)" +
                ");";
        executeUpdate(createSessionsTable, "Table 'Sessions' created successfully!");
    }

    public static void createTables() {
        createStudentsTable();
        createParentsTable();
        createCoursesTable();
        createBillingTable();
        createParametersTable();
        createSessionsTable();
    }

    public static int insertParent(String nomComplet, String numeroTelephone, String email, String adresse, String codePostal, String ville) {
        String insertParentSQL = "INSERT INTO Parents (nom_complet, numero_telephone, email, adresse, code_postal, ville) VALUES (?, ?, ?, ?, ?, ?)";
        int parentId = -1;

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertParentSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, nomComplet);
            preparedStatement.setString(2, numeroTelephone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, adresse);
            preparedStatement.setString(5, codePostal);
            preparedStatement.setString(6, ville);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                parentId = generatedKeys.getInt(1);
            }

            logger.info("Parent inserted successfully!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to insert parent into 'Parents' table.", e);
        }

        return parentId;
    }

    public static void insertStudent(String nom, String prenom, String dateNaissance, String numeroTelephone, String email, String classe, String dateDebutInscription, int parentId) {
        // Validate input parameters
        if (nom == null || prenom == null || dateNaissance == null || numeroTelephone == null || email == null || classe == null || dateDebutInscription == null) {
            logger.log(Level.WARNING, "Attempted to insert student with invalid data: nom={0}, prenom={1}, dateNaissance={2}, numeroTelephone={3}, email={4}, classe={5}, dateDebutInscription={6}, parentId={7}",
                    new Object[]{nom, prenom, dateNaissance, numeroTelephone, email, classe, dateDebutInscription, parentId});
            return;
        }

        String insertStudentSQL = "INSERT INTO Students (nom, prenom, date_naissance, numero_telephone, email, classe, date_debut_inscription, parent_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertStudentSQL)) {

            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, dateNaissance);
            preparedStatement.setString(4, numeroTelephone);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, classe);
            preparedStatement.setString(7, dateDebutInscription);
            preparedStatement.setInt(8, parentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.log(Level.INFO, "Student '{0} {1}' inserted successfully with parent ID {2}.", new Object[]{nom, prenom, parentId});
            } else {
                logger.log(Level.WARNING, "No rows inserted for student '{0} {1}'.", new Object[]{nom, prenom});
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting student '{0} {1}' into the database.", new Object[]{nom, prenom, e});
        }
    }

    public static List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        String query = "SELECT nom, prenom FROM Students WHERE date_fin_inscription IS NULL";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String fullName = resultSet.getString("nom") + " " + resultSet.getString("prenom");
                students.add(fullName);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving students from the database.", e);
        }

        return students;
    }
    public static ObservableList<String> getAllStudentNames() {
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        String query = "SELECT nom, prenom FROM Students WHERE date_fin_inscription IS NULL";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String fullName = resultSet.getString("nom") + " " + resultSet.getString("prenom");
                studentNames.add(fullName);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving student names from the database.", e);
        }

        return studentNames;
    }


    public static void updateStudentUnregistrationDate(String nom, String prenom, LocalDate dateFinInscription) {
        String updateSQL = "UPDATE Students SET date_fin_inscription = ? WHERE nom = ? AND prenom = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, dateFinInscription.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Unregistration date updated successfully for student '" + nom + " " + prenom + "'.");
            } else {
                logger.warning("No rows updated for student '" + nom + " " + prenom + "'.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating unregistration date for student '" + nom + " " + prenom + "'.", e);
        }
    }

    public static Parameterss getParameters() {
    String query = "SELECT * FROM Parameters LIMIT 1";
    Parameterss parameters = null;

    try (Connection connection = connect();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        if (resultSet.next()) {
            // Fetch all the required fields for the Parameterss constructor
            String nomGestionnaire = resultSet.getString("nom_gestionnaire");
            String prenomGestionnaire = resultSet.getString("prenom_gestionnaire");
            String adresseGestionnaire = resultSet.getString("adresse_gestionnaire");
            String emailGestionnaire = resultSet.getString("email_gestionnaire");
            String telephoneGestionnaire = resultSet.getString("telephone_gestionnaire");
            String numeroSiret = resultSet.getString("numero_siret");
            String nomAssociation = resultSet.getString("nom_association");
            String adresseAssociation = resultSet.getString("adresse_association");
            String emailAssociation = resultSet.getString("email_association");
            String telephoneAssociation = resultSet.getString("telephone_association");
            float prixIndividuel = resultSet.getFloat("prix_individuel");
            float prixGroupe = resultSet.getFloat("prix_groupe");
            String mentionLegale = resultSet.getString("mention_legale");

            // Pass all the required arguments to the constructor
            parameters = new Parameterss(
                nomGestionnaire, prenomGestionnaire, adresseGestionnaire, 
                emailGestionnaire, telephoneGestionnaire, numeroSiret,
                nomAssociation, adresseAssociation, emailAssociation, 
                telephoneAssociation, prixIndividuel, prixGroupe, mentionLegale
            );
        } else {
            logger.log(Level.WARNING, "No data found in Parameters table.");
        }

    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error fetching parameters from the database.", e);
    }

    return parameters;
}




    public static void updateParameters(Parameterss parameters) {
    String updateSQL = "UPDATE Parameters SET prix_individuel = IFNULL(?, prix_individuel), " +
                        "prix_groupe = IFNULL(?, prix_groupe), " +
                        "mention_legale = IFNULL(?, mention_legale)";

    try (Connection connection = connect();
         PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

        preparedStatement.setObject(1, parameters.getPrixIndividuel());
        preparedStatement.setObject(2, parameters.getPrixGroupe());
        preparedStatement.setString(3, parameters.getMentionLegale());

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            logger.info("Parameters updated successfully.");
        } else {
            logger.warning("No rows updated for parameters.");
        }

    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error updating parameters in the database.", e);
    }
}


    public static void insertCourse(int eleveId, int nombreHeures, String typeSeance, LocalDate date) {
    String insertCourseSQL = "INSERT INTO Courses (eleve_id, nombre_heures, type_seance, date) VALUES (?, ?, ?, ?)";
    try (Connection connection = connect();
         PreparedStatement preparedStatement = connection.prepareStatement(insertCourseSQL)) {

        preparedStatement.setInt(1, eleveId);
        preparedStatement.setInt(2, nombreHeures);
        preparedStatement.setString(3, typeSeance);
        preparedStatement.setDate(4, Date.valueOf(date));

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            logger.info("Course inserted successfully!");
        } else {
            logger.warning("No rows inserted for course.");
        }

    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error inserting course into 'Courses' table.", e);
    }
}
               

    public static List<Course> getCoursesByStudent(int eleveId) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Courses WHERE eleve_id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, eleveId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int nombreHeures = resultSet.getInt("nombre_heures");
                String typeSeance = resultSet.getString("type_seance");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                courses.add(new Course(id, eleveId, nombreHeures, typeSeance, date));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving courses from the database.", e);
        }

        return courses;
    }

    public static int getStudentIdByName(String name) {
        String query = "SELECT id FROM Students WHERE nom || ' ' || prenom = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching student ID by name.", e);
        }

        return -1; 
    }

    public static Map<String, Object> calculateCourseDetails(int eleveId) {
        List<Course> courses = getCoursesByStudent(eleveId);
        int totalHours = courses.stream().mapToInt(Course::getNombreHeures).sum();
        float prixIndividuel = getParameters().getPrixIndividuel();
        float prixGroupe = getParameters().getPrixGroupe();
        float totalAmountDue = 0;

        for (Course course : courses) {
            if ("Individuel".equals(course.getTypeSeance())) {
                totalAmountDue += course.getNombreHeures() * prixIndividuel;
            } else if ("Groupe".equals(course.getTypeSeance())) {
                totalAmountDue += course.getNombreHeures() * prixGroupe;
            }
        }

        float amountPaid = getPaidAmount(eleveId);
        float difference = totalAmountDue - amountPaid;

        Map<String, Object> details = new HashMap<>();
        details.put("totalHours", totalHours);
        details.put("totalAmountDue", totalAmountDue);
        details.put("amountPaid", amountPaid);
        details.put("difference", difference);

        return details;
    }

    public static float getPaidAmount(int studentId) {
        float paidAmount = 0.0f;
        String query = "SELECT SUM(montant_paye) AS total_paid FROM Billing WHERE eleve_id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                paidAmount = resultSet.getFloat("total_paid");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching paid amount from the Billing table.", e);
        }

        return paidAmount;
    }

    public static List<StudentCourseDetails> getStudentCourseDetails(int studentId) {
        List<StudentCourseDetails> details = new ArrayList<>();
        String query = "SELECT strftime('%Y-%m', date) AS month, " +
                       "strftime('%W', date) AS week, SUM(nombre_heures) AS total_heures " +
                       "FROM Courses WHERE eleve_id = ? " +
                       "GROUP BY month, week";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int week = resultSet.getInt("week");
                int totalHours = resultSet.getInt("total_heures");

                details.add(new StudentCourseDetails(month, week, totalHours));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching course details from the database.", e);
        }

        return details;
    }

    public static void insertPayment(int eleveId, float montantPaye, LocalDate datePaiement) {
        String insertPaymentSQL = "INSERT INTO Billing (eleve_id, montant_paye, date_facture) VALUES (?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPaymentSQL)) {

            preparedStatement.setInt(1, eleveId);
            preparedStatement.setFloat(2, montantPaye);
            preparedStatement.setDate(3, Date.valueOf(datePaiement));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Payment inserted successfully!");
            } else {
                logger.warning("No rows inserted for payment.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting payment into 'Billing' table.", e);
        }
    }
    
    public static List<AllStudentsDetails> getAllStudentsDetailsByMonth(String month) {
    List<AllStudentsDetails> allStudentsDetails = new ArrayList<>();
    String query = "SELECT s.nom || ' ' || s.prenom AS studentName, " +
                   "SUM(CASE WHEN c.type_seance = 'Individuel' THEN c.nombre_heures ELSE 0 END) AS individualHours, " +
                   "SUM(CASE WHEN c.type_seance = 'Groupe' THEN c.nombre_heures ELSE 0 END) AS groupHours, " +
                   "(SELECT GROUP_CONCAT(b.montant_paye, ', ') FROM Billing b WHERE b.eleve_id = s.id AND strftime('%m', b.date_facture) = ?) AS monthPayments " +
                   "FROM Students s " +
                   "LEFT JOIN Courses c ON s.id = c.eleve_id " +
                   "WHERE strftime('%m', c.date) = ? " +
                   "GROUP BY s.nom, s.prenom";

    try (Connection connection = connect();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        String monthNumber = String.format("%02d", monthToNumber(month)); // Convert month name to number
        preparedStatement.setString(1, monthNumber);
        preparedStatement.setString(2, monthNumber);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String studentName = resultSet.getString("studentName");
            int individualHours = resultSet.getInt("individualHours");
            int groupHours = resultSet.getInt("groupHours");
            String monthPayments = resultSet.getString("monthPayments");

            allStudentsDetails.add(new AllStudentsDetails(studentName, individualHours, groupHours, monthPayments));
        }

    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error fetching students' details for the month.", e);
    }

    return allStudentsDetails;
}

    // Helper method to convert month name to month number
    private static int monthToNumber(String month) {
        switch (month) {
            case "Janvier": return 1;
            case "Février": return 2;
            case "Mars": return 3;
            case "Avril": return 4;
            case "Mai": return 5;
            case "Juin": return 6;
            case "Juillet": return 7;
            case "Août": return 8;
            case "Septembre": return 9;
            case "Octobre": return 10;
            case "Novembre": return 11;
            case "Décembre": return 12;
            default: return 0;
        }
    }


    public static void main(String[] args) {
        createTables();
    }
}
