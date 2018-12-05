import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MonsterGenerator implements fileMenu {
    private Stage MonWindow, aD_createMonWindow = new Stage();
    private Scene scene_MonGen,aD_scene, returnMain;
    private BorderPane layoutMonGen, minMaxMonGenBp, aD_outerPane;
    private HBox minMaxHB, monGen,aD_layout,monGenListArea,aD_labelLayout;
    private Button  genMonster = new Button("Random Encounter"),
            seedEncounter = new Button("Select Seed"),
            addMonster  = new Button("Create Monster entry"),
            playerReturn = new Button("Back"),
            closeButton = new Button("Close"),
            aD_addButton = new Button("New");
    private Label aD_label = new Label();
    private TableView<MonObjs> monTable;
    private ComboBox<String> minMonster = new ComboBox<>();
    private ComboBox<String> maxMonster = new ComboBox<>();
    //******************************************************************************************************************
    MonsterGenerator(Stage mainWindow, Scene scene_Main){
        this.MonWindow = mainWindow;
        this.returnMain = scene_Main;
    }
    public Scene monGenStart(Stage MainWindow,BorderPane layoutMain, Scene scene_Main) {
        // Create the Scene for monster generator
        layoutMonGen = new BorderPane();                //Outer BorderPane
        /***************************************************************************************************************
         * Add nodes to the Main layout
         * Add the HBox to the top of the border Pane. Adds the menuBar from the Interface
         * Set the center working area in random encounter window
         * ************************************************************************************************************/
        layoutMonGen.setTop(menuBar);
        layoutMonGen.setCenter(createBorderPane());
        layoutMonGen.setBottom(createHBoxList());

        createAction();
        playerReturn.setOnAction(e -> {
            layoutMain.setTop(menuBar);
            MainWindow.setScene(scene_Main);
        });
        // Creates the scene
        scene_MonGen = new Scene(layoutMonGen,WIDTH,HEIGHT);
        return scene_MonGen;
    }   // End MonsterGenerator
    /*******************************************************************************************************************
     * This method creates the columns for the list values. It creates the columns of the list in each category and in
     * the first block of each column is what will be seen. the new property value string is the way that the list area
     * gets the variables of the objects
     * ****************************************************************************************************************/
    private TableView<MonObjs> monTableCreate() {
        TableColumn<MonObjs, String> nameColumn = new TableColumn<>("Name");    // The top ones are the name of the column
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));         // Name of the variable in the objects

        TableColumn<MonObjs, Integer> aC_column = new TableColumn<>("Amour Class");
        aC_column.setMinWidth(100);
        aC_column.setCellValueFactory(new PropertyValueFactory<>("amourClass"));

        TableColumn<MonObjs, Integer> hP_Column = new TableColumn<>("Hit Points");
        hP_Column.setMinWidth(100);
        hP_Column.setCellValueFactory(new PropertyValueFactory<>("hitP"));

        TableColumn<MonObjs, String> spdColumn = new TableColumn<>("Speed");
        spdColumn.setMinWidth(100);
        spdColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<MonObjs, Integer> strColumn = new TableColumn<>("Strength");
        strColumn.setMinWidth(100);
        strColumn.setCellValueFactory(new PropertyValueFactory<>("str"));

        TableColumn<MonObjs, Integer> strModColumn = new TableColumn<>("Str Mod.");
        strModColumn.setMinWidth(100);
        strModColumn.setCellValueFactory(new PropertyValueFactory<>("strModifier"));

        TableColumn<MonObjs, Integer> dexColumn = new TableColumn<>("Dexterity");
        dexColumn.setMinWidth(100);
        dexColumn.setCellValueFactory(new PropertyValueFactory<>("dex"));

        TableColumn<MonObjs, Integer> dexModColumn = new TableColumn<>("Dex.Mod");
        dexModColumn.setMinWidth(100);
        dexModColumn.setCellValueFactory(new PropertyValueFactory<>("dexModifier"));

        TableColumn<MonObjs, Integer> consColumn = new TableColumn<>("Constitution");
        consColumn.setMinWidth(100);
        consColumn.setCellValueFactory(new PropertyValueFactory<>("con"));

        TableColumn<MonObjs, Integer> consModColumn = new TableColumn<>("Con.Mod");
        consModColumn.setMinWidth(100);
        consModColumn.setCellValueFactory(new PropertyValueFactory<>("conModifier"));

        TableColumn<MonObjs, Integer> intelColumn = new TableColumn<>("Intelligence");
        intelColumn.setMinWidth(100);
        intelColumn.setCellValueFactory(new PropertyValueFactory<>("intel"));

        TableColumn<MonObjs, Integer> intModColumn = new TableColumn<>("Int.Mod");
        intModColumn.setMinWidth(100);
        intModColumn.setCellValueFactory(new PropertyValueFactory<>("intelModifier"));

        TableColumn<MonObjs, Integer> wisColumn = new TableColumn<>("Wisdom");
        wisColumn.setMinWidth(100);
        wisColumn.setCellValueFactory(new PropertyValueFactory<>("wis"));

        TableColumn<MonObjs, Integer> wisModColumn = new TableColumn<>("Wis.Mod");
        wisModColumn.setMinWidth(100);
        wisModColumn.setCellValueFactory(new PropertyValueFactory<>("wisModifier"));

        TableColumn<MonObjs, Integer> chaColumn = new TableColumn<>("Charisma");
        chaColumn.setMinWidth(100);
        chaColumn.setCellValueFactory(new PropertyValueFactory<>("cha"));

        TableColumn<MonObjs, Integer> chaModColumn = new TableColumn<>("Cha Mod");
        chaModColumn.setMinWidth(100);
        chaModColumn.setCellValueFactory(new PropertyValueFactory<>("chaModifier"));

        TableColumn<MonObjs, Integer> profiBonusColumn = new TableColumn<>("Proficiency");
        profiBonusColumn.setMinWidth(100);
        profiBonusColumn.setCellValueFactory(new PropertyValueFactory<>("profiBonus"));

        TableColumn<MonObjs, Integer> skillUpBonusColumn = new TableColumn<>("Exp");
        skillUpBonusColumn.setMinWidth(100);
        skillUpBonusColumn.setCellValueFactory(new PropertyValueFactory<>("skillUp"));

        TableColumn<MonObjs, Integer> challengeColumn = new TableColumn<>("Challenge");
        challengeColumn.setMinWidth(100);
        challengeColumn.setCellValueFactory(new PropertyValueFactory<>("challenge"));

        monTable = new TableView<>();
        monTable.setItems(createMonList());
        monTable.getColumns().addAll(nameColumn,aC_column,
                hP_Column,spdColumn,
                strColumn,strModColumn,
                dexColumn, dexModColumn,
                consColumn, consModColumn,
                intelColumn, intModColumn,
                wisColumn, wisModColumn,
                chaColumn, chaModColumn,
                profiBonusColumn,skillUpBonusColumn,
                challengeColumn);

        return monTable;
    }
    /*******************************************************************************************************************
     * Creates the monsters in the random Encounter list area. The list of the objects are created from a text file.
     * (File(s) not added yet)
     * ****************************************************************************************************************/
    private ObservableList<MonObjs> createMonList() {
        ObservableList<MonObjs> monsterEncounterList = FXCollections.observableArrayList();
        monsterEncounterList.add(new MonObjs("Goblin",15,15));
        monsterEncounterList.add(new MonObjs("Bugbear",16,27,"30 ft.",15,
                2));
        return monsterEncounterList;
    }
    /*******************************************************************************************************************
     * Create the main center panel for the Scene. Uses createHBox,createHBoxList
     ******************************************************************************************************************/
    private BorderPane createBorderPane() {
        minMaxMonGenBp = new BorderPane();
        minMaxHB = new HBox();
        minMaxHB.getChildren().addAll(createCBox(minMonster),createCBox(maxMonster));
        minMaxMonGenBp.setTop(createHBox(monGen));
        minMaxMonGenBp.setCenter(minMaxHB);
        return minMaxMonGenBp;
    }
    private HBox createHBox(HBox monGen) {
        //Add a horizontal box for MonGenerator Scene
        monGen = new HBox();
        monGen.setAlignment(Pos.CENTER_LEFT);
        monGen.getChildren().addAll(playerReturn, genMonster,seedEncounter,addMonster);
        return monGen;
    }
    private HBox createHBoxList() {
        // Create housing layout for monGen Window
        monGenListArea = new HBox(10);
        monGenListArea.setAlignment(Pos.CENTER_LEFT);
        monGenListArea.getChildren().addAll(monTableCreate());
        return monGenListArea;
    }
    /*******************************************************************************************************************
     *
     * ****************************************************************************************************************/
    private ComboBox<String> createCBox(ComboBox<String> inComboBox) {
        if (inComboBox.equals(minMonster)) {
            inComboBox.getItems().addAll("Min Monster: 1", "Min Monster: 2",
                    "Min Monster: 3", "Min Monster: 4", "Min Monster: 5", "Min Monster: 6",
                    "Min Monster: 7", "Min Monster: 8","Min Monster: 9", "Min Monster: 10",
                    "Min Monster: 11","Min Monster: 12","Min Monster: 13","Min Monster: 14",
                    "Min Monster: 15","Min Monster: 16","Min Monster: 17","Min Monster: 18",
                    "Min Monster: 19","Min Monster: 20" );
            inComboBox.setPromptText("Min Monster: 1");
        }
        else if (inComboBox.equals(maxMonster)) {
            inComboBox.getItems().addAll("Max Monster: 1", "Max Monster: 2",
                    "Max Monster: 3", "Max Monster: 4", "Max Monster: 5", "Max Monster: 6",
                    "Max Monster: 7", "Max Monster: 8","Max Monster: 9", "Max Monster: 10",
                    "Max Monster: 11","Max Monster: 12","Max Monster: 13","Max Monster: 14",
                    "Max Monster: 15","Max Monster: 16","Max Monster: 17","Max Monster: 18",
                    "Max Monster: 19","Max Monster: 20");
            inComboBox.setPromptText("Max Monster: 1");
        }
        return inComboBox;
    }
    /*******************************************************************************************************************
     * Create the monster object
     * ****************************************************************************************************************/
    public void monNewEntry(String title, String message) {
        aD_createMonWindow.setTitle(title);
        aD_createMonWindow.setMinWidth(WIDTH);
        aD_createMonWindow.setMinHeight(HEIGHT);
        aD_createMonWindow.setMaxHeight(HEIGHT*2);

        aD_label.setText(message);

        aD_labelLayout = new HBox();
        aD_labelLayout = new HBox();
        aD_labelLayout.setAlignment(Pos.CENTER);
        aD_labelLayout.getChildren().add(aD_label);

        aD_layout  = new HBox(10);
        aD_layout.getChildren().addAll(aD_addButton, closeButton);
        aD_layout.setAlignment(Pos.CENTER);

        aD_outerPane = new BorderPane();
        aD_outerPane.setTop(aD_labelLayout);
        aD_outerPane.setBottom(aD_layout);

        //Display aD_createMonWindow
        aD_scene = new Scene(aD_outerPane);
        aD_createMonWindow.setScene(aD_scene);
        aD_createMonWindow.show();
    }
    private VBox statsCreate() {
        VBox outerSC = new VBox(2);
        HBox statsHb[] = new HBox[24];
        HBox statsHbSE[] = new HBox[24];
        Label statsLabel[] = new Label[24];
        TextField statsEntry [] = new TextField[23];

        statsLabel[0] = new Label("Name");
        statsLabel[1] = new Label("Amour Class");
        statsLabel[2] = new Label("Hit points:");
        statsLabel[3] = new Label("Speed");
        statsLabel[4] = new Label("Strength");
        statsLabel[5] = new Label("Str mod.");
        statsLabel[6] = new Label("Dexterity");
        statsLabel[7] = new Label("Dex Mod.");
        statsLabel[8] = new Label("Constitution");
        statsLabel[9] = new Label("Con Mod.");
        statsLabel[10] = new Label("Intelligence");
        statsLabel[11] = new Label("Int mod.");
        statsLabel[12] = new Label("Wisdom");
        statsLabel[13] = new Label("Wis mod.");
        statsLabel[14] = new Label("Charisma");
        statsLabel[15] = new Label("Cha mod.");
        statsLabel[16] = new Label("Saving throws");
        statsLabel[17] = new Label("Proficiency bonus");
        statsLabel[18] = new Label("Experience:");
        statsLabel[19] = new Label("Damage reistances");
        statsLabel[20] = new Label("Condition immunities");
        statsLabel[21] = new Label("Senses");
        statsLabel[22] = new Label("Language");
        statsLabel[23] = new Label("Challenge");

        for (int i = 0; i < 23; i++) {
            statsHb[i] = new HBox(5);
            statsHbSE[i] = new HBox(5);
            statsEntry[i] = new TextField();
            statsEntry[i].setPromptText("add");
            statsEntry[i].setMaxWidth(80);
            statsHb[i].getChildren().add(statsLabel[i]);
            statsHbSE[i].getChildren().add(statsEntry[i]);
        }
        for (int i = 0; i < 23; i++) {
            outerSC.getChildren().addAll(statsHb[i],statsEntry[i]);
        }
        return outerSC;
    }
    private void createAction() {
        addMonster.setOnAction(e -> {
            monNewEntry("Add new Monster", "Use this window to create a new entry");
        });
        closeButton.setOnAction(e -> aD_createMonWindow.close());
        aD_addButton.setOnAction(e -> {
            aD_outerPane.setBottom(aD_layout);
            aD_outerPane.setCenter(statsCreate());
        });

    }
}
