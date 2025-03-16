/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package projetdegestion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ProjetDeGestion extends Application {
    
    private ObservableList<Eleves> studentData = FXCollections.observableArrayList(
    new Eleves("John Doe", "Individuel", "Doe Parent", "123456789", "john@example.com", 
               "Science", "Math", "Mars 2024", "01-01-2023", "Monday 9-11"),
    new Eleves("Jane Smith", "Groupe", "Smith Parent", "987654321", "jane@example.com", 
               "Math", "English", "Février 2024", "15-02-2023", "Tuesday 10-12")
    );
    private ObservableList<CoursDetails> allCourseDetails = FXCollections.observableArrayList();
    private TableView<Eleves> tableView = new TableView<>();
    private HBox selectedNavItem = null;
    private BorderPane root;
    private VBox formContainer;
    private VBox coursContainer;
    private VBox facContainer;
    private int rowCounter = 7;
    private ComboBox<String> monthFilter;
    private VBox detailsContainer; 
    private ComboBox<String> monthComboBox; 


    
    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.createTables();
        root = new BorderPane();
        
        VBox navigation = new VBox();
        navigation.setBackground(new Background(new BackgroundFill(Color.web("#00008b"), CornerRadii.EMPTY, Insets.EMPTY))); 
        navigation.setAlignment(Pos.TOP_CENTER);
        navigation.setPadding(new Insets(100, 10, 10, 50)); 
        navigation.setSpacing(20);
        
        navigation.getChildren().addAll(
            createNavItem("Accueil", "Accueil Page"),
            createNavItem("Inscription", "Inscription Page"),
            createNavItem("Désinscription", "Desinscription Page"),
            createNavItem("Élèves", "Élèves Page"),
            createNavItem("Calendrier", "Calendrier Page"),
            createNavItem("Cours", "Cours Page"),
            createNavItem("Facturation", "Facturation Page"),
            createNavItem("Paiement", "Paiement Page"),
            createNavItem("Contact", "Contact Page"),
            createNavItem("Paramètre", "Paramètre Page")
        );

        Label nomDeLassociation = new Label("Coup De Pouce");
        nomDeLassociation.setFont(new Font("Arial", 65));
        nomDeLassociation.setTextFill(Color.WHITE);
        
        HBox topContainer = new HBox();
        topContainer.setBackground(new Background(new BackgroundFill(Color.web("#00008b"), CornerRadii.EMPTY, Insets.EMPTY)));
        topContainer.getChildren().add(nomDeLassociation);
        topContainer.setPadding(new Insets(10));  
        topContainer.setAlignment(Pos.CENTER);

        updateCenterContent("Accueil Page");

        topContainer.setPrefHeight(150);
        navigation.setPrefWidth(300);
        
        root.setTop(topContainer);
        root.setLeft(navigation);

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //
    //
    //
    //Navigation :
    private HBox createNavItem(String text, String pageTitle) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 20));        
        label.setTextFill(Color.WHITE);

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 0.0, 10.0, 5.0, 0.0, 10.0);
        triangle.setFill(Color.WHITE);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(triangle, label);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-padding: 10;");

        hbox.setOnMouseClicked(e -> {
            handleNavItemClick(hbox, pageTitle);
        });

        hbox.setOnMouseEntered(e -> {
            if (hbox != selectedNavItem) {
                label.setTextFill(Color.GRAY);
                triangle.setFill(Color.GRAY);
            }
        });
        hbox.setOnMouseExited(e -> {
            if (hbox != selectedNavItem) {
                label.setTextFill(Color.WHITE);
                triangle.setFill(Color.WHITE);
            }
        });

        return hbox;
    }
    private void handleNavItemClick(HBox clickedItem, String pageTitle) {
        if (selectedNavItem != null) {
            Label oldLabel = (Label) selectedNavItem.getChildren().get(1);
            Polygon oldTriangle = (Polygon) selectedNavItem.getChildren().get(0);
            oldLabel.setTextFill(Color.WHITE);  
            oldTriangle.setFill(Color.WHITE);  
        }

        selectedNavItem = clickedItem;
        Label newLabel = (Label) selectedNavItem.getChildren().get(1);
        Polygon newTriangle = (Polygon) selectedNavItem.getChildren().get(0);
        newLabel.setTextFill(Color.GRAY);  
        newTriangle.setFill(Color.GRAY);

        updateCenterContent(pageTitle);
    }
    
    //
    //
    //
    //
    //Content of each item :
    private void updateCenterContent(String pageTitle) {
        switch (pageTitle) {
            case "Accueil Page":
                VBox vbox = new VBox(); 
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(20));
                Image image = new Image("file:C:/Users/anasa/OneDrive/Desktop/projetDeGestion/projetDeGestion/sources/cours-soutien-scolaire.jpg");  
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(800);  
                imageView.setPreserveRatio(true);
                Label citation = new Label("« L'éducation est l'arme la plus puissante que vous puissiez utiliser pour changer le monde. » Nelson Mandela");
                citation.setPadding(new Insets(70));
                citation.setFont(Font.font("Comic Sans MS", 25));
                vbox.getChildren().addAll(imageView,citation);
                root.setCenter(vbox);
                break;
            case "Inscription Page":
                formContainer = new VBox(20);
                formContainer.setAlignment(Pos.CENTER);
                formContainer.setPadding(new Insets(20));

                Button impButton = new Button("Imprimer le formulaire");
                impButton.setPrefSize(300, 70);
                impButton.setFont(new Font("Arial", 20)); 

                Button rempButton = new Button("Remplir le formulaire");
                rempButton.setPrefSize(300, 70);
                rempButton.setFont(new Font("Arial", 20)); 

                formContainer.getChildren().addAll(rempButton, impButton);
                rempButton.setOnAction(e -> inscriptionContent());
                root.setCenter(formContainer);
                
                break;
                
            case "Desinscription Page":
                GridPane desinscriptionGrid = new GridPane();
                desinscriptionGrid.setAlignment(Pos.CENTER);
                desinscriptionGrid.setPadding(new Insets(20));
                desinscriptionGrid.setHgap(10);
                desinscriptionGrid.setVgap(10);

                Label elevesLabel = new Label("Élève(s) :");
                elevesLabel.setFont(new Font("Arial", 25));
                
                Button dropdownButton = new Button(" ▼  ");
                dropdownButton.setPrefSize(150,30);

                Popup popup = new Popup();
                popup.setAutoHide(true);

                VBox checkboxContainer= new VBox(10);
                checkboxContainer.setPadding(new Insets(10));
                checkboxContainer.setStyle("-fx-background-color: white; -fx-border-color: black;");
                checkboxContainer.setAlignment(Pos.CENTER);

                List <String> studentList = DatabaseManager.getAllStudents();
                ObservableList<String> students = FXCollections.observableArrayList(studentList);

                for (String student : students) {
                    CheckBox checkBox = new CheckBox(student);
                    checkboxContainer.getChildren().add(checkBox);
                }

                popup.getContent().add(checkboxContainer);

                dropdownButton.setOnAction(e -> {
                    if (popup.isShowing()) {
                        popup.hide();
                    } else {
                        popup.show(dropdownButton, dropdownButton.localToScreen(0, 0).getX(),
                                dropdownButton.localToScreen(0, 0).getY() + dropdownButton.getHeight());
                    }
                });
                Label selectedStudentsLabel = new Label("Selected : ");
                selectedStudentsLabel.setFont(new Font("Arial", 22));

                dropdownButton.setOnAction(e -> {
                    if (popup.isShowing()) {
                        popup.hide();
                    } else {
                        popup.show(dropdownButton, dropdownButton.localToScreen(0, 0).getX(),
                                dropdownButton.localToScreen(0, 0).getY() + dropdownButton.getHeight());
                    }
                });

                for (Node node : checkboxContainer.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) node;
                        checkBox.setOnAction(event -> {
                            updateSelectedStudentsLabel(checkboxContainer, selectedStudentsLabel);
                        });
                    }
                }

                HBox eleveSelection = new HBox(dropdownButton);
                desinscriptionGrid.add(elevesLabel, 0, 0);
                desinscriptionGrid.add(eleveSelection, 1, 0);
                desinscriptionGrid.add(selectedStudentsLabel, 2, 0);
                
                Label dateLabel = new Label("Date :");
                dateLabel.setFont(new Font("Arial", 25));
                DatePicker datePicker = new DatePicker();
                setDatePickerToFrench(datePicker);
                datePicker.setPrefSize(150,30);
                datePicker.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");
                desinscriptionGrid.add(dateLabel, 0, 1);
                desinscriptionGrid.add(datePicker, 1, 1);

                Button desinscriptionButton = new Button("Désinscrire");
                desinscriptionButton.setFont(new Font("Arial", 14));
                desinscriptionButton.setPrefSize(150, 40);
                desinscriptionButton.setOnAction(e -> {
                    List<String> selectedStudents = checkboxContainer.getChildren()
                            .stream()
                            .filter(node -> node instanceof CheckBox)
                            .map(node -> (CheckBox) node)
                            .filter(CheckBox::isSelected)
                            .map(CheckBox::getText)
                            .collect(Collectors.toList());

                    LocalDate selectedDate = datePicker.getValue();
                    if (!selectedStudents.isEmpty() && selectedDate != null) {
                        for (String student : selectedStudents) {
                            String[] nameParts = student.split(" ");
                            String nom = nameParts[0];
                            String prenom = nameParts[1];

                            DatabaseManager.updateStudentUnregistrationDate(nom, prenom, selectedDate);
                        }

                        studentList.clear();
                        studentList.addAll(DatabaseManager.getAllStudents());
                        checkboxContainer.getChildren().clear();
                        for (String student : studentList) {
                            CheckBox checkBox = new CheckBox(student);
                            checkboxContainer.getChildren().add(checkBox);
                        }
                        System.out.println("Students successfully unregistered!");
                    }
            });

                desinscriptionGrid.add(desinscriptionButton, 1, 3);

                root.setCenter(desinscriptionGrid);
                break;
                
            case "Élèves Page":
                CheckBox individuel = new CheckBox("Individuel");
                CheckBox nomParent = new CheckBox("Nom du parent");
                CheckBox phone = new CheckBox("Numéro de téléphone");
                CheckBox mail = new CheckBox("Mail");
                CheckBox classe = new CheckBox("Classe");
                CheckBox matiere = new CheckBox("Matière");
                CheckBox paye = new CheckBox("Acquitté");
                CheckBox debut = new CheckBox("Debut d'inscription");
                CheckBox jours = new CheckBox("Jours choisis");
                CheckBox fin = new CheckBox("Fin d'inscription");
                CheckBox selectAll = new CheckBox("Tout sélectionner");

                selectAll.setOnAction(e -> {
                    boolean isSelected = selectAll.isSelected();
                    individuel.setSelected(isSelected);
                    nomParent.setSelected(isSelected);
                    phone.setSelected(isSelected);
                    mail.setSelected(isSelected);
                    classe.setSelected(isSelected);
                    matiere.setSelected(isSelected);
                    paye.setSelected(isSelected);
                    debut.setSelected(isSelected);
                    jours.setSelected(isSelected);
                    updateTableColumns(isSelected, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours);
                });

                individuel.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                nomParent.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                phone.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                mail.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                classe.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                matiere.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                paye.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                debut.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));
                jours.setOnAction(e -> updateTableColumns(false, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours));

                HBox filterBox = new HBox(10, new Label("Filtre : "), selectAll, individuel, nomParent, phone, mail, classe, matiere, paye, debut,fin, jours);
                filterBox.setPadding(new Insets(10));
                filterBox.setAlignment(Pos.CENTER_LEFT);

                tableView.getColumns().clear();
                tableView.setStyle("-fx-font-size: 14px;");

                TableColumn<Eleves, String> nameColumn = new TableColumn<>("Nom");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
                tableView.getColumns().add(nameColumn);
                tableView.setItems(studentData);
                nameColumn.setPrefWidth(1650 / 11);

                VBox vboxx = new VBox(10, filterBox, tableView);
                vboxx.setPadding(new Insets(20));
                vboxx.setAlignment(Pos.CENTER_LEFT);

                root.setCenter(vboxx);
                break;                          
            case "Calendrier Page":
                root.setCenter(createCalendrierContent());
                break;
            case "Cours Page":
                coursContainer = new VBox(20);
                coursContainer.setAlignment(Pos.CENTER);
                coursContainer.setPadding(new Insets(20));

                Button entrerCoursButton = new Button("Entrer un cours");
                entrerCoursButton.setPrefSize(300, 70);
                entrerCoursButton.setFont(new Font("Arial", 20));

                Button voirDetailsButton = new Button("Voir les détails");
                voirDetailsButton.setPrefSize(300, 70);
                voirDetailsButton.setFont(new Font("Arial", 20));

                coursContainer.getChildren().addAll(entrerCoursButton, voirDetailsButton);

                entrerCoursButton.setOnAction(e -> entrerCours());
                voirDetailsButton.setOnAction(e -> voirDetails());
                root.setCenter(coursContainer);
                break;
            case "Facturation Page":
                facContainer = new VBox(20);
                facContainer.setAlignment(Pos.CENTER);
                facContainer.setPadding(new Insets(20));

                Button genererButton = new Button("Générer les factures");
                genererButton.setPrefSize(300, 70);
                genererButton.setFont(new Font("Arial", 20)); 

                Button impFacButton = new Button("Imprimer une facture");
                impFacButton.setPrefSize(300, 70);
                impFacButton.setFont(new Font("Arial", 20)); 

                facContainer.getChildren().addAll(genererButton, impFacButton);
                root.setCenter(facContainer);
                break;
            case "Paiement Page":
                GridPane paiementGrid = new GridPane();
                paiementGrid.setAlignment(Pos.CENTER);
                paiementGrid.setPadding(new Insets(20));
                paiementGrid.setHgap(10);
                paiementGrid.setVgap(10);
                
                Label eleveLabel = new Label("Élève(s) :");
                eleveLabel.setFont(new Font("Arial", 25));
                
                Button dropdownButtonn = new Button(" ▼  ");
                dropdownButtonn.setPrefSize(150,30);

                Popup popupp = new Popup();
                popupp.setAutoHide(true);

                VBox checkboxContainerr = new VBox(10);
                checkboxContainerr.setPadding(new Insets(10));
                checkboxContainerr.setStyle("-fx-background-color: white; -fx-border-color: black;");
                checkboxContainerr.setAlignment(Pos.CENTER);

                // Exemple de liste d'élèves 
                ObservableList<String> studentss = FXCollections.observableArrayList("John Doe", "Jane Smith", "David Brown");

                for (String student : studentss) {
                    CheckBox checkBox = new CheckBox(student);
                    checkboxContainerr.getChildren().add(checkBox);
                }

                popupp.getContent().add(checkboxContainerr);

                dropdownButtonn.setOnAction(e -> {
                    if (popupp.isShowing()) {
                        popupp.hide();
                    } else {
                        popupp.show(dropdownButtonn, dropdownButtonn.localToScreen(0, 0).getX(),
                                dropdownButtonn.localToScreen(0, 0).getY() + dropdownButtonn.getHeight());
                    }
                });

                HBox eleveSelectionn = new HBox(dropdownButtonn);
                paiementGrid.add(eleveLabel, 0, 0);
                paiementGrid.add(eleveSelectionn, 1, 0);
                
                Label montantLabel = new Label("Montant :");
                montantLabel.setFont(new Font("Arial", 25));
                TextField montantField = new TextField();
                montantField.setFont(new Font("Arial", 18));
                paiementGrid.add(montantLabel, 0, 1);
                paiementGrid.add(montantField, 1, 1);
                
                Label dateLabell = new Label("Date :");
                dateLabell.setFont(new Font("Arial", 25));
                DatePicker datePickerr = new DatePicker();
                setDatePickerToFrench(datePickerr);
                datePickerr.setPrefSize(150,30);
                datePickerr.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");
                paiementGrid.add(dateLabell, 0, 2);
                paiementGrid.add(datePickerr, 1, 2);
                
                Button paiementButton = new Button("Enregistrer");
                paiementButton.setFont(new Font("Arial", 14));
                paiementButton.setPrefSize(150, 40);    
                paiementGrid.add(paiementButton, 1, 3);
         
                root.setCenter(paiementGrid);
            break;
            case "Contact Page":
            break;
            case "Paramètre Page":
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(15);  
    gridPane.setHgap(15);  
    gridPane.setPadding(new Insets(20));
    gridPane.setMaxWidth(600); 

    projetdegestion.Parameterss parameters = DatabaseManager.getParameters();

    if (parameters == null) {
        // Initialize text fields with empty values if no parameters are found
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("No parameters found. Please enter new details.");
        alert.showAndWait();
    }

    // Gestionnaire section
    Label gestionnaire = new Label("Le gestionnaire :");
    gestionnaire.setFont(new Font("Arial", 25));
    gridPane.add(gestionnaire, 0, 0);

    Label nomGestLabel = new Label("Nom :");
    nomGestLabel.setFont(new Font("Arial", 20));
    TextField nomGestField = new TextField(parameters != null ? parameters.getNomGestionnaire() : "");
    nomGestField.setFont(new Font("Arial", 18));
    gridPane.add(nomGestLabel, 0, 1);
    gridPane.add(nomGestField, 1, 1);

    Label prenomGestLabel = new Label("Prénom :");
    prenomGestLabel.setFont(new Font("Arial", 20));
    TextField prenomGestField = new TextField(parameters != null ? parameters.getPrenomGestionnaire() : "");
    prenomGestField.setFont(new Font("Arial", 18));
    gridPane.add(prenomGestLabel, 0, 2);
    gridPane.add(prenomGestField, 1, 2);

    Label adresseGestLabel = new Label("Adresse :");
    adresseGestLabel.setFont(new Font("Arial", 20));
    TextField adresseGestField = new TextField(parameters != null ? parameters.getAdresseGestionnaire() : "");
    adresseGestField.setFont(new Font("Arial", 18));
    gridPane.add(adresseGestLabel, 0, 3);
    gridPane.add(adresseGestField, 1, 3);

    Label emailGestLabel = new Label("Email :");
    emailGestLabel.setFont(new Font("Arial", 20));
    TextField emailGestField = new TextField(parameters != null ? parameters.getEmailGestionnaire() : "");
    emailGestField.setFont(new Font("Arial", 18));
    gridPane.add(emailGestLabel, 0, 4);
    gridPane.add(emailGestField, 1, 4);

    Label telephoneGestLabel = new Label("Numéro de téléphone :");
    telephoneGestLabel.setFont(new Font("Arial", 20));
    TextField telephoneGestField = new TextField(parameters != null ? parameters.getTelephoneGestionnaire() : "");
    telephoneGestField.setFont(new Font("Arial", 18));
    gridPane.add(telephoneGestLabel, 0, 5);
    gridPane.add(telephoneGestField, 1, 5);

    Label numSiretLabel = new Label("Numéro de siret :");
    numSiretLabel.setFont(new Font("Arial", 20));
    TextField numSiretField = new TextField(parameters != null ? parameters.getNumeroSiret() : "");
    numSiretField.setFont(new Font("Arial", 18));
    gridPane.add(numSiretLabel, 0, 6);
    gridPane.add(numSiretField, 1, 6);

    // Association section
    Label association = new Label("Association :");
    association.setFont(new Font("Arial", 25));
    gridPane.add(association, 0, 7);

    Label nomAssocLabel = new Label("Nom :");
    nomAssocLabel.setFont(new Font("Arial", 20));
    TextField nomAssocField = new TextField(parameters != null ? parameters.getNomAssociation() : "");
    nomAssocField.setFont(new Font("Arial", 18));
    gridPane.add(nomAssocLabel, 0, 8);
    gridPane.add(nomAssocField, 1, 8);

    Label adresseAssocLabel = new Label("Adresse :");
    adresseAssocLabel.setFont(new Font("Arial", 20));
    TextField adresseAssocField = new TextField(parameters != null ? parameters.getAdresseAssociation() : "");
    adresseAssocField.setFont(new Font("Arial", 18));
    gridPane.add(adresseAssocLabel, 0, 9);
    gridPane.add(adresseAssocField, 1, 9);

    Label emailAssocLabel = new Label("Email :");
    emailAssocLabel.setFont(new Font("Arial", 20));
    TextField emailAssocField = new TextField(parameters != null ? parameters.getEmailAssociation() : "");
    emailAssocField.setFont(new Font("Arial", 18));
    gridPane.add(emailAssocLabel, 0, 10);
    gridPane.add(emailAssocField, 1, 10);

    Label telephoneAssocLabel = new Label("Numéro de téléphone :");
    telephoneAssocLabel.setFont(new Font("Arial", 20));
    TextField telephoneAssocField = new TextField(parameters != null ? parameters.getTelephoneAssociation() : "");
    telephoneAssocField.setFont(new Font("Arial", 18));
    gridPane.add(telephoneAssocLabel, 0, 11);
    gridPane.add(telephoneAssocField, 1, 11);

    // Paramètres fixes section
    Label parametresFix = new Label("Paramètres fixes :");
    parametresFix.setFont(new Font("Arial", 25));
    gridPane.add(parametresFix, 0, 12);

    Label prixIndiLabel = new Label("Prix individuel :");
    prixIndiLabel.setFont(new Font("Arial", 20));
    TextField prixIndiField = new TextField(parameters != null ? String.valueOf(parameters.getPrixIndividuel()) : "");
    prixIndiField.setFont(new Font("Arial", 18));
    gridPane.add(prixIndiLabel, 0, 13);
    gridPane.add(prixIndiField, 1, 13);

    Label prixGroupLabel = new Label("Prix groupe :");
    prixGroupLabel.setFont(new Font("Arial", 20));
    TextField prixGroupField = new TextField(parameters != null ? String.valueOf(parameters.getPrixGroupe()) : "");
    prixGroupField.setFont(new Font("Arial", 18));
    gridPane.add(prixGroupLabel, 0, 14);
    gridPane.add(prixGroupField, 1, 14);

    Label mentionLegaLabel = new Label("Mention légale :");
    mentionLegaLabel.setFont(new Font("Arial", 20));
    TextField mentionLegaField = new TextField(parameters != null ? parameters.getMentionLegale() : "");
    mentionLegaField.setFont(new Font("Arial", 18));
    gridPane.add(mentionLegaLabel, 0, 15);
    gridPane.add(mentionLegaField, 1, 15);

    // Button to save changes
    Button parButton = new Button("Enregistrer");
    parButton.setFont(new Font("Arial", 14));
    parButton.setPrefSize(150, 40);
    gridPane.add(parButton, 1, 16);

    parButton.setOnAction(event -> {
        Parameterss updatedParameters = new Parameterss(
            nomGestField.getText(),
            prenomGestField.getText(),
            adresseGestField.getText(),
            emailGestField.getText(),
            telephoneGestField.getText(),
            numSiretField.getText(),    
            nomAssocField.getText(),
            adresseAssocField.getText(),
            emailAssocField.getText(),
            telephoneAssocField.getText(),
            Float.parseFloat(prixIndiField.getText()),
            Float.parseFloat(prixGroupField.getText()),
            mentionLegaField.getText()
        );

        DatabaseManager.updateParameters(updatedParameters);
    });

    root.setCenter(gridPane);
    break;


            default:
                root.setCenter(new Label("This is the " + pageTitle));
                break;
        }
        BorderPane.setAlignment(root.getCenter(), Pos.CENTER);
    }
    
    //
    //
    //
    //inscription :
    private void inscriptionContent() {
        formContainer.getChildren().clear();
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);  
        gridPane.setHgap(15);  
        gridPane.setPadding(new Insets(20));
        gridPane.setMaxWidth(600); 

        TextField[] textFields = new TextField[10];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new TextField();
            textFields[i].setPrefWidth(400); 
        }
        //Elève
        Label eleve = new Label("Elève :");
        eleve.setFont(new Font("Arial", 25));
        gridPane.add(eleve, 0, 0);
        
        Label nomLabel = new Label("Nom :");
        nomLabel.setFont(new Font("Arial", 20));
        TextField nomTextField = new TextField();
        nomTextField.setFont(new Font("Arial", 18));
        gridPane.add(nomLabel, 0, 2);
        gridPane.add(nomTextField, 1, 2);

        Label prenomLabel = new Label("Prénom :");
        prenomLabel.setFont(new Font("Arial", 20));
        TextField prenomTextField = new TextField();
        prenomTextField.setFont(new Font("Arial", 18));
        gridPane.add(prenomLabel, 0, 3);
        gridPane.add(prenomTextField, 1, 3);
        
        Label naissanceLabel = new Label("Date de naissance :");
        naissanceLabel.setFont(new Font("Arial", 20));
        DatePicker naissancePicker = new DatePicker();
        setDatePickerToFrench(naissancePicker);
        naissancePicker.setPrefSize(150,30);
        naissancePicker.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");
        gridPane.add(naissanceLabel, 0, 4);
        gridPane.add(naissancePicker, 1, 4);
        
        Label telephoneEleLabel = new Label("Numéro de téléphone :");
        telephoneEleLabel.setFont(new Font("Arial", 20));
        TextField telephoneEleField = new TextField();
        telephoneEleField.setFont(new Font("Arial", 18));
        gridPane.add(telephoneEleLabel, 0, 5);
        gridPane.add(telephoneEleField, 1, 5);
        
        Label emailEleLabel = new Label("Email :");
        emailEleLabel.setFont(new Font("Arial", 20));
        TextField emailEleField = new TextField();
        emailEleField.setFont(new Font("Arial", 18));
        gridPane.add(emailEleLabel, 0, 6);
        gridPane.add(emailEleField, 1, 6);

        Label classeLabel = new Label("Classe :");
        classeLabel.setFont(new Font("Arial", 20));
        TextField classeTextField = new TextField();
        classeTextField.setFont(new Font("Arial", 18));
        gridPane.add(classeLabel, 0, 7);
        gridPane.add(classeTextField, 1, 7);
        
        Label dateLabel = new Label("Date début d'inscription :");
        dateLabel.setFont(new Font("Arial", 20));
        DatePicker dateInsPicker = new DatePicker();
        setDatePickerToFrench(dateInsPicker);
        dateInsPicker.setPrefSize(150,30);
        dateInsPicker.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");
        gridPane.add(dateLabel, 0, 8);
        gridPane.add(dateInsPicker, 1, 8);
     
        Label parent = new Label("Parent :");
        parent.setFont(new Font("Arial", 25));
        gridPane.add(parent, 0, 11);
        
        Label parentLabel = new Label("Nom complet du parent :");
        parentLabel.setFont(new Font("Arial", 20));
        TextField parentTextField = new TextField();
        parentTextField.setFont(new Font("Arial", 18));
        gridPane.add(parentLabel, 0, 13);
        gridPane.add(parentTextField, 1, 13);

        Label telephoneParLabel = new Label("Numéro de téléphone :");
        telephoneParLabel.setFont(new Font("Arial", 20));
        TextField telephoneParField = new TextField();
        telephoneParField.setFont(new Font("Arial", 18));
        gridPane.add(telephoneParLabel, 0, 14);
        gridPane.add(telephoneParField, 1, 14);

        Label emailParLabel = new Label("Email :");
        emailParLabel.setFont(new Font("Arial", 20));
        TextField emailParField = new TextField();
        emailParField.setFont(new Font("Arial", 18));
        gridPane.add(emailParLabel, 0, 15);
        gridPane.add(emailParField, 1, 15);
        
        Label adresseLabel = new Label("Adresse :");
        adresseLabel.setFont(new Font("Arial", 20));
        TextField adresseField = new TextField();
        adresseField.setFont(new Font("Arial", 18));
        gridPane.add(adresseLabel, 0, 16);
        gridPane.add(adresseField, 1, 16);
        
        Label cPostalLabel = new Label("Code postal :");
        cPostalLabel.setFont(new Font("Arial", 20));
        TextField cPostalField = new TextField();
        cPostalField.setFont(new Font("Arial", 18));
        gridPane.add(cPostalLabel, 0, 17);
        gridPane.add(cPostalField, 1, 17);
        
        Label villeLabel = new Label("Ville :");
        villeLabel.setFont(new Font("Arial", 20));
        TextField villeField = new TextField();
        villeField.setFont(new Font("Arial", 18));
        gridPane.add(villeLabel, 0, 18);
        gridPane.add(villeField, 1, 18);
     
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Enregistrer");
        saveButton.setPrefSize(100, 30);
        Button printFormButton = new Button("Imprimer");
        printFormButton.setPrefSize(100, 30);

        buttonBox.getChildren().addAll(saveButton, printFormButton);
        gridPane.add(buttonBox, 0, 19, 2, 1);
        
        saveButton.setOnAction(e -> {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String dateNaissance = naissancePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String telephoneEleve = telephoneEleField.getText();
        String emailEleve = emailEleField.getText();
        String classe = classeTextField.getText();
        String dateDebutInscription = dateInsPicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String nomParent = parentTextField.getText();
        String telephoneParent = telephoneParField.getText();
        String emailParent = emailParField.getText();
        String adresseParent = adresseField.getText();
        String codePostal = cPostalField.getText();
        String ville = villeField.getText();

        int parentId = DatabaseManager.insertParent(nomParent, telephoneParent, emailParent, adresseParent, codePostal, ville);

        DatabaseManager.insertStudent(nom, prenom, dateNaissance, telephoneEleve, emailEleve, classe, dateDebutInscription, parentId);

        System.out.println("Données enregistrées avec succès !");
        });
       
        formContainer.getChildren().add(gridPane); 
    }
    //
    //
    private void updateSelectedStudentsLabel(VBox checkboxContainer, Label selectedStudentsLabel) {
        String selectedStudents = checkboxContainer.getChildren().stream()
            .filter(node -> node instanceof CheckBox)
            .map(node -> (CheckBox) node)
            .filter(CheckBox::isSelected)
            .map(CheckBox::getText)
            .collect(Collectors.joining(", "));
        selectedStudentsLabel.setText("Selected: " + selectedStudents);
        selectedStudentsLabel.setStyle("-fx-font-size: 18px;");
    }
    //
    //
    //
    //
    //eleves
    private void updateTableColumns(boolean isSelected, CheckBox... checkBoxes) {
    tableView.getColumns().clear();

    TableColumn<Eleves, String> nameColumn = new TableColumn<>("Nom");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    tableView.getColumns().add(nameColumn);

    for (CheckBox checkBox : checkBoxes) {
        if (checkBox.isSelected()) {
            String propertyName = getPropertyName(checkBox.getText());
            if (!propertyName.isEmpty()) {
                TableColumn<Eleves, String> column = new TableColumn<>(checkBox.getText());
                column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                tableView.getColumns().add(column);
            }
        }
    }
    tableView.setItems(studentData);
}

    private String getPropertyName(String columnName) {
        switch (columnName) {
            case "Individuel":
                return "type";  
            case "Nom du parent":
                return "nomParent";
            case "Numéro de téléphone":
                return "phone";
            case "Mail":
                return "mail";
            case "Classe":
                return "classe";
            case "Matière":
                return "matiere";
            case "Acquitté":
                return "acquitte";  
            case "Debut de l'inscription":
                return "debut";
            case "Jours choisis":
                return "jours";
            default:
                return "";
        }
    }

    //
    //
    //
    //Calendrier :
    private GridPane createCalendrierContent() {
        TextField champEmail = new TextField();
        champEmail.setPromptText("Adresse Email");
        champEmail.setFont(new Font("Arial", 25));

        PasswordField champMotDePasse = new PasswordField();
        champMotDePasse.setPromptText("Mot de passe");
        champMotDePasse.setFont(new Font("Arial", 25));


        CheckBox caseSeSouvenir = new CheckBox("Se souvenir de moi");
        caseSeSouvenir.setSelected(false);

        Button boutonConnexion = new Button("Se connecter");
        boutonConnexion.setFont(new Font("Arial", 14));
        boutonConnexion.setPrefSize(150, 40);

        GridPane grille = new GridPane();
        grille.setAlignment(Pos.CENTER);
        grille.setPadding(new Insets(10));
        grille.setHgap(10);
        grille.setVgap(10);

        grille.add(champEmail, 0, 0);
        grille.add(champMotDePasse, 0, 1);
        grille.add(caseSeSouvenir, 0, 2);
        grille.add(boutonConnexion, 0, 3);
 
        return grille;
    }
    //
    //
    //
    //Cours :

private void entrerCours() {
    coursContainer.getChildren().clear();
    GridPane coursGridPane = new GridPane();
    coursGridPane.setAlignment(Pos.CENTER);
    coursGridPane.setVgap(15);  
    coursGridPane.setHgap(15);  
    coursGridPane.setPadding(new Insets(20));
    coursGridPane.setMaxWidth(600);

    Label elevesLabel = new Label("Élève(s) :");
    elevesLabel.setFont(new Font("Arial", 25));
    coursGridPane.add(elevesLabel, 0, 0);

    ComboBox<String> studentComboBox = new ComboBox<>();
    studentComboBox.setPrefSize(150, 30);
    studentComboBox.setItems(DatabaseManager.getAllStudentNames()); // Fetch students from DatabaseManager

    coursGridPane.add(studentComboBox, 1, 0);

    Label nbrHeuresLabel = new Label("Nombre d'heures :");
    nbrHeuresLabel.setFont(new Font("Arial", 25));
    Spinner<Integer> nbrHeuresSpinner = new Spinner<>(0, 5, 1); 
    TextField spinnerEditor = nbrHeuresSpinner.getEditor();
    spinnerEditor.setFont(new Font("Arial", 14));
    nbrHeuresSpinner.setPrefSize(150, 40);
    coursGridPane.add(nbrHeuresLabel, 0, 1);
    coursGridPane.add(nbrHeuresSpinner, 1, 1);

    Label typeSeanceLabel = new Label("Type de séance :");
    typeSeanceLabel.setFont(new Font("Arial", 25));
    ToggleGroup group = new ToggleGroup();
    RadioButton individuelRadio = new RadioButton("Individuel");
    individuelRadio.setFont(new Font("Arial", 15));
    RadioButton groupeRadio = new RadioButton("Groupe");
    groupeRadio.setFont(new Font("Arial", 15));
    individuelRadio.setToggleGroup(group);
    groupeRadio.setToggleGroup(group);
    coursGridPane.add(typeSeanceLabel, 0, 2);
    coursGridPane.add(individuelRadio, 1, 2);
    coursGridPane.add(groupeRadio, 2, 2);

    Label dateLabel = new Label("Date :");
    dateLabel.setFont(new Font("Arial", 25));
    DatePicker datePicker = new DatePicker();
    setDatePickerToFrench(datePicker);
    datePicker.setPrefSize(150,30);
    datePicker.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");
    coursGridPane.add(dateLabel, 0, 3);
    coursGridPane.add(datePicker, 1, 3);

    Button saveButton = new Button("Enregistrer");
    saveButton.setFont(new Font("Arial", 14));
    saveButton.setPrefSize(150, 40);
    coursContainer.getChildren().addAll(coursGridPane, saveButton);
    saveButton.setOnAction(event -> {
        String selectedType = individuelRadio.isSelected() ? "Individuel" : "Groupe";
        int hours = nbrHeuresSpinner.getValue();
        LocalDate selectedDate = datePicker.getValue();

        // Get selected student
        String selectedStudent = studentComboBox.getValue();
        if (selectedStudent != null && !selectedStudent.isEmpty()) {
            int studentId = DatabaseManager.getStudentIdByName(selectedStudent);
            DatabaseManager.insertCourse(studentId, hours, selectedType, selectedDate);

            // Refresh the details view
            loadAllStudentsDetails(detailsContainer, monthComboBox.getValue()); // Refresh with the current month

            // Confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course Entry");
            alert.setHeaderText(null);
            alert.setContentText("Course has been successfully recorded for " + selectedStudent + ".");
            alert.showAndWait();
        } else {
            // Show error if no student is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Course Entry");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student.");
            alert.showAndWait();
        }
    });

}


    
    private void voirDetails() {
        if (coursContainer != null) {
            coursContainer.getChildren().clear();

            VBox detailsContainer = new VBox(10);
            detailsContainer.setPadding(new Insets(10));
            detailsContainer.setAlignment(Pos.CENTER);

            Button singleStudentButton = new Button("Voir les détails d'un élève");
            singleStudentButton.setPrefSize(300, 70);
            singleStudentButton.setFont(new Font("Arial", 20));
            
            Button allStudentsButton = new Button("Voir les détails de tous les enfants");
            allStudentsButton.setPrefSize(300, 70);
            allStudentsButton.setFont(new Font("Arial", 20));

            singleStudentButton.setOnAction(e -> showSingleStudentSelection(detailsContainer));
            allStudentsButton.setOnAction(e -> showAllStudentsDetails(detailsContainer)); // Modified this line

            detailsContainer.getChildren().addAll(singleStudentButton, allStudentsButton);
            coursContainer.getChildren().add(detailsContainer);
        }
    }

    private void showSingleStudentSelection(VBox detailsContainer) {
        detailsContainer.getChildren().clear();

        GridPane SingleStudentData = new GridPane();
        SingleStudentData.setAlignment(Pos.CENTER);
        SingleStudentData.setPadding(new Insets(20));
        SingleStudentData.setHgap(10);
        SingleStudentData.setVgap(10);
        Label studentDataLabel = new Label("Élève(s) :");
        studentDataLabel.setFont(new Font("Arial", 25));

        Button studentDataDropdownButton = new Button(" ▼  ");
        studentDataDropdownButton.setPrefSize(150, 30);

        Popup studentDataPopup = new Popup();
        studentDataPopup.setAutoHide(true);

        VBox studentDataCheckboxContainer = new VBox(10);
        studentDataCheckboxContainer.setPadding(new Insets(10));
        studentDataCheckboxContainer.setStyle("-fx-background-color: white; -fx-border-color: black;");
        studentDataCheckboxContainer.setAlignment(Pos.CENTER);

        // Fetch students from the database
        ObservableList<String> studentDataStudents = DatabaseManager.getAllStudentNames();

        ToggleGroup group = new ToggleGroup();

        for (String student : studentDataStudents) {
            RadioButton radioButton = new RadioButton(student);
            radioButton.setToggleGroup(group);
            radioButton.setOnAction(e -> showStudentDetails(detailsContainer, student)); // Set action to show student details
            studentDataCheckboxContainer.getChildren().add(radioButton);
        }

        studentDataPopup.getContent().add(studentDataCheckboxContainer);

        studentDataDropdownButton.setOnAction(e -> {
            if (studentDataPopup.isShowing()) {
                studentDataPopup.hide();
            } else {
                studentDataPopup.show(studentDataDropdownButton, studentDataDropdownButton.localToScreen(0, 0).getX(),
                        studentDataDropdownButton.localToScreen(0, 0).getY() + studentDataDropdownButton.getHeight());
            }
        });

        HBox eleveSelectionn = new HBox(studentDataDropdownButton);
        SingleStudentData.add(studentDataLabel, 0, 0);
        SingleStudentData.add(eleveSelectionn, 1, 0);
        detailsContainer.getChildren().add(SingleStudentData);
    }



    private void showStudentDetails(VBox detailsContainer, String studentName) {
        // Clear the existing UI components in the container
        detailsContainer.getChildren().clear();

        // Create a label to display the student's name
        Label studentNameLabel = new Label(studentName);

        // Create the TableView for displaying course details
        TableView<StudentCourseDetails> detailsTable = new TableView<>();
        detailsTable.setPrefHeight(200);

        // Define columns for month, week, and total hours in the TableView
        TableColumn<StudentCourseDetails, String> monthColumn = new TableColumn<>("Mois");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<StudentCourseDetails, Integer> weekColumn = new TableColumn<>("Semaine");
        weekColumn.setCellValueFactory(new PropertyValueFactory<>("week"));

        TableColumn<StudentCourseDetails, Integer> hoursColumn = new TableColumn<>("Heures Totales");
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));

        // Add columns to the detailsTable
        detailsTable.getColumns().addAll(monthColumn, weekColumn, hoursColumn);

        // Fetch the student ID based on the student's name using DatabaseManager
        int studentId = DatabaseManager.getStudentIdByName(studentName);
        if (studentId == -1) {
            Label errorLabel = new Label("Erreur : Étudiant non trouvé.");
            detailsContainer.getChildren().add(errorLabel);
            return;
        }

        // Fetch course details for the student from the database
        List<StudentCourseDetails> studentDetails = DatabaseManager.getStudentCourseDetails(studentId);
        detailsTable.setItems(FXCollections.observableArrayList(studentDetails));

        // Calculate the total hours from the course details
        int totalHours = studentDetails.stream().mapToInt(StudentCourseDetails::getTotalHours).sum();
        Label totalHoursLabel = new Label("Nombre total d'heures jusqu'à présent : " + totalHours);

        // Calculate the total amount to pay using the student ID and course details
        Map<String, Object> courseDetails = DatabaseManager.calculateCourseDetails(studentId);
        float totalAmountToPay = (float) courseDetails.get("totalAmountDue");
        Label totalAmountLabel = new Label("Montant total à payer : " + totalAmountToPay);

        // Fetch the amount already paid by the student
        float paidAmount = (float) courseDetails.get("amountPaid");
        Label paidAmountLabel = new Label("Montant payé : " + paidAmount);

        // Calculate the difference between the total amount to pay and the amount paid
        float difference = totalAmountToPay - paidAmount;
        Label differenceLabel = new Label("Différence : " + difference);

        // Add all the labels and the detailsTable to the container
        detailsContainer.getChildren().addAll(studentNameLabel, detailsTable, totalHoursLabel, totalAmountLabel, paidAmountLabel, differenceLabel);

        // Create another TableView for displaying weekly hours breakdown
        TableView<WeeklyHours> weeklyHoursTable = new TableView<>();
        weeklyHoursTable.setPrefHeight(200);

        // Define the columns for each week in the TableView
        String[] weeks = {"S1", "S2", "S3", "S4"};
        for (String week : weeks) {
            TableColumn<WeeklyHours, Integer> weeklyColumn = new TableColumn<>(week);
            weeklyColumn.setCellValueFactory(new PropertyValueFactory<>(week.toLowerCase()));
            weeklyHoursTable.getColumns().add(weeklyColumn);
        }

        // Fill the weekly hours data based on studentDetails
        Map<String, Map<Integer, Integer>> hoursByMonthAndWeek = new HashMap<>();
        for (StudentCourseDetails detail : studentDetails) {
            hoursByMonthAndWeek
                .computeIfAbsent(detail.getMonth(), k -> new HashMap<>())
                .merge(detail.getWeek(), detail.getTotalHours(), Integer::sum);
        }

        // Create a list to store weekly hours data
        List<WeeklyHours> weeklyHoursData = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hoursByMonthAndWeek.entrySet()) {
            String month = entry.getKey();
            Map<Integer, Integer> weeklyData = entry.getValue();
            WeeklyHours weeklyHours = new WeeklyHours(
                weeklyData.getOrDefault(1, 0),
                weeklyData.getOrDefault(2, 0),
                weeklyData.getOrDefault(3, 0),
                weeklyData.getOrDefault(4, 0)
            );
            weeklyHours.setMonth(month);
            weeklyHoursData.add(weeklyHours);
        }

        // Set the data to the weeklyHoursTable
        weeklyHoursTable.setItems(FXCollections.observableArrayList(weeklyHoursData));

        // Add the weeklyHoursTable to the container
        detailsContainer.getChildren().addAll(weeklyHoursTable);
    }

    private void showAllStudentsDetails(VBox detailsContainer) {
        detailsContainer.getChildren().clear();

        Label monthLabel = new Label("Sélectionnez un mois :");
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll("Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre");
        monthComboBox.setValue("Janvier"); // Default selection

        Button loadButton = new Button("Charger");
        
        loadButton.setOnAction(e -> loadAllStudentsDetails(detailsContainer, monthComboBox.getValue()));

        HBox selectionContainer = new HBox(10, monthLabel, monthComboBox, loadButton);
        selectionContainer.setAlignment(Pos.CENTER);

        detailsContainer.getChildren().add(selectionContainer);
    }
    
    private void loadAllStudentsDetails(VBox detailsContainer, String selectedMonth) {
        // Clear the current details in the container
        detailsContainer.getChildren().clear();

        // Display the selected month
        Label selectedMonthLabel = new Label("Détails pour le mois de " + selectedMonth);
        selectedMonthLabel.setFont(new Font("Arial", 18));

        // Create a TableView for displaying students' details
        TableView<AllStudentsDetails> studentsDetailsTable = new TableView<>();
        studentsDetailsTable.setPrefHeight(400);

        // Columns for the students' details table
        TableColumn<AllStudentsDetails, String> nameColumn = new TableColumn<>("Nom de l'élève");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<AllStudentsDetails, Integer> individualHoursColumn = new TableColumn<>("Heures Individuelles");
        individualHoursColumn.setCellValueFactory(new PropertyValueFactory<>("individualHours"));

        TableColumn<AllStudentsDetails, Integer> groupHoursColumn = new TableColumn<>("Heures de Groupe");
        groupHoursColumn.setCellValueFactory(new PropertyValueFactory<>("groupHours"));

        TableColumn<AllStudentsDetails, String> amountColumn = new TableColumn<>("Montant");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("monthAmount"));

        // Add columns to the studentsDetailsTable
        studentsDetailsTable.getColumns().addAll(nameColumn, individualHoursColumn, groupHoursColumn, amountColumn);

        // Fetch data from the database
        List<AllStudentsDetails> allStudentsDetails = DatabaseManager.getAllStudentsDetailsByMonth(selectedMonth);
        studentsDetailsTable.setItems(FXCollections.observableArrayList(allStudentsDetails));

        // Add the selected month label and table to the container
        detailsContainer.getChildren().addAll(selectedMonthLabel, studentsDetailsTable);
    }


    private void setDatePickerToFrench(DatePicker datePicker) {
        Locale.setDefault(Locale.FRENCH);

        DateTimeFormatter frenchDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH);

        datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(java.time.LocalDate date) {
                return (date != null) ? frenchDateFormatter.format(date) : "";
            }

            @Override
            public java.time.LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? java.time.LocalDate.parse(string, frenchDateFormatter) : null;
            }
        });

        datePicker.setPromptText("jj-mm-aaaa");
    }

    public static void main(String[] args) { 
        launch(args);
    }
}

