import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ToolDriver extends Application implements FileMenuInterface{
    /*******************************************************************************************************************
     * Main Driver resources
     * ****************************************************************************************************************/
    private int userMaxPlayer = 6, maxPlayer = 6, playerIndex = 0,
            playerMaxHp = 10, playerMinHp = 0, playerHpDefault = 10,
            uniColumns = 7;
    double newValue = 1.0;
    private HBox bottomWorkArea = new HBox(8);
    private Stage MainWindow;
    private Scene scene_Main;
    // Layout for the main_class------------------------------------------------------------------------------------
    private BorderPane layoutMain = new BorderPane();
    private BorderPane centerWork = new BorderPane();
    private BorderPane centerW_A_BP[] = new BorderPane[maxPlayer];

    private HBox playerStats_c_L_AreaHB = new HBox(5);
    private HBox playerStatsLabelHBox[][] = new HBox[maxPlayer][2];
    private HBox playerAdd_Center_Area_HB = new HBox(10);
    private HBox playerIndLayout[] = new HBox[maxPlayer];
    private HBox playerSub[] = new HBox[maxPlayer];
    private HBox playerAddSub_HB[][]= new HBox[maxPlayer][2];
    private HBox playHp_Label_HBox[][] = new HBox[maxPlayer][2];

    private VBox playerADD_CR_AreaVB = new VBox(10);
    private VBox player_Marker[] = new VBox[maxPlayer];
    private VBox plyrStatsVBox[] = new VBox[maxPlayer];
    private VBox player_Hp_label_VBox[] = new VBox[maxPlayer];

    private TextField aC_Indicator_TF[] = new TextField[maxPlayer];
    private TextField hPIndicator[] = new TextField[maxPlayer];
    private TextField playerName[] = new TextField[maxPlayer];
    private TextField playerStats[][][] = new TextField[maxPlayer][6][2];

    private Label Ac_Label[] = new Label[maxPlayer];
    private Label plyrLvlSelection[] = new Label[maxPlayer];
    private Label plyrStatLabel[][][] = new Label[maxPlayer][6][2];

    private Slider pSlider[] = new Slider[maxPlayer];

    private Button tD_Button[] = new Button[20];
    private Button plusMinusButton[][] = new Button[maxPlayer][2];

    private ComboBox<String> tD_playerLvlCBox;
    private ComboBox<String> playerLevel_CBox[] = new ComboBox[maxPlayer];


    private MonsterGenerator mnGen = new MonsterGenerator(MainWindow, scene_Main);
    //******************************************************************************************************************
    public static void main(String[] args) { launch(args); }
    //******************************************************************************************************************
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindow = primaryStage;
        MainWindow.setScene(primaryStageBuild(MainWindow));
        MainWindow.show();
    }
    //---------------------------------------Creates_Main_Window--------------------------------------------------------
    private Scene primaryStageBuild(Stage MainWindow) {
        // Creates the main Stage for the main window
        MainWindow.setTitle("Dungeon Master Tools: Version: 0.001");

        // Creates the HBox that holds the top buttons under the file menu----------------------------------------------
        playerAdd_Center_Area_HB.setAlignment(Pos.CENTER_LEFT);
        playerAdd_Center_Area_HB.getChildren().addAll(
                createTop_CBox(),
                tD_makeButton(tD_Button,"add", 0));

        // Create the layout for the player addition--------------------------------------------------------------------
        playerStats_c_L_AreaHB.setAlignment(Pos.CENTER_LEFT);
        playerStats_c_L_AreaHB.getChildren().addAll();
        // Create the Center_RIGHT
        playerADD_CR_AreaVB.setAlignment(Pos.CENTER_LEFT);
        tD_makeButton(tD_Button,"+",1);
        tD_Button[1].setMinHeight(150);
        playerADD_CR_AreaVB.getChildren().addAll(tD_Button[1]);

        // Add an horizontal box for the main Scene---------------------------------------------------------------------
        bottomWorkArea.setAlignment(Pos.CENTER);
        bottomWorkArea.getChildren().addAll(tD_makeButton(tD_Button,"Generate Encounter", 2), tD_makeButton(tD_Button,"Reset",3));

        // Add the main center items------------------------------------------------------------------------------------
        centerWork.setTop(playerAdd_Center_Area_HB);                              //Holds the Level comboBox, and the add button
        centerWork.setCenter(playerStats_c_L_AreaHB);
        centerWork.setRight(playerADD_CR_AreaVB);

        // Add the Nodes to the main Stage------------------------------------------------------------------------------
        layoutMain.setTop(buildTopBar());
        layoutMain.setLeft(centerWork);
        layoutMain.setBottom(bottomWorkArea);

        // Create the scene for the Main Scene--------------------------------------------------------------------------
        scene_Main = new Scene(layoutMain, WIDTH, HEIGHT);

        createActions();                                                         // Add the action events to the buttons
        return scene_Main;
    }
    private BorderPane center_WA_BP(int inPlayerIndex) {
        /***************************************************************************************************************
         * Creates a border pane for each player health marker, uses player marker to create each pane.
         * Set the layout box position and add the name field and health box
         **************************************************************************************************************/
        playerIndLayout[inPlayerIndex] = new HBox();
        playerIndLayout[inPlayerIndex].setAlignment(Pos.CENTER_LEFT);
        playerIndLayout[inPlayerIndex].getChildren().addAll(
                init_playerName_Tf(playerName,inPlayerIndex, uniColumns),
                init_hp_Tf(hPIndicator,inPlayerIndex, uniColumns,"add Hp")
        );
        // Create an HBox's for the + and - health buttons
        playerAddSub_HB[inPlayerIndex][0] = new HBox();
        playerAddSub_HB[inPlayerIndex][0].setAlignment(Pos.CENTER);
        playerAddSub_HB[inPlayerIndex][0].getChildren().add(
                makeSliderButton(plusMinusButton, inPlayerIndex, "+")
        );
        playerAddSub_HB[inPlayerIndex][1] = new HBox();
        playerAddSub_HB[inPlayerIndex][1].setAlignment(Pos.CENTER);
        playerAddSub_HB[inPlayerIndex][1].getChildren().addAll(
                makeSliderButton(plusMinusButton, inPlayerIndex,"-")
        );
        // Vox used to create the layout for The slider-----------------------------------------------------------------
        player_Marker[inPlayerIndex] = new VBox();
        player_Marker[inPlayerIndex].setAlignment(Pos.CENTER_RIGHT);
        player_Marker[inPlayerIndex].getChildren().addAll(
                playerAddSub_HB[inPlayerIndex][0],
                init_HpVbTfLbl(inPlayerIndex),
                create_HP_slider(pSlider,inPlayerIndex, playerMinHp,playerHpDefault,playerMaxHp),
                playerAddSub_HB[inPlayerIndex][1]);
        player_Marker[inPlayerIndex].setFillWidth(true);

        playerSub[inPlayerIndex] = new HBox(5);
        playerSub[inPlayerIndex].setAlignment(Pos.CENTER_LEFT);
        playerSub[inPlayerIndex].getChildren().addAll(
                uni_makeLabel(plyrLvlSelection,inPlayerIndex,"Current Lvl."),
                createBot_CBox(
                    playerLevel_CBox,
                    inPlayerIndex),
                tD_makeButton(tD_Button,"-",3));

        //Main return box that holds everything in this method----------------------------------------------------------
        centerW_A_BP[inPlayerIndex] = new BorderPane();
        centerW_A_BP[inPlayerIndex].setTop(playerIndLayout[inPlayerIndex]);
        centerW_A_BP[inPlayerIndex].setLeft(player_Marker[inPlayerIndex]);
        centerW_A_BP[inPlayerIndex].setBottom(playerSub[inPlayerIndex]);
        centerW_A_BP[inPlayerIndex].setRight(
                init_PlayerStats(plyrStatsVBox, playerStatsLabelHBox, plyrStatLabel,
                playerStats, inPlayerIndex));

        return centerW_A_BP[inPlayerIndex];
    }
    private MenuBar buildTopBar() {
        //Creates the file menu bar(Should only be ran once)
        fileMenu.getItems().addAll(newCamp,openCamp, saveCamp);
        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }
    private ComboBox<String> createTop_CBox() {// Creates Player selection Combox
        tD_playerLvlCBox= new ComboBox<>();
        tD_playerLvlCBox.getItems().addAll("Level 1", "Level 2",
                "Level 3", "Level 4", "Level 5", "Level 6",
                "Level 7", "Level 8", "Level 9", "Level 10",
                "Level 11", "Level 12", "Level 13", "Level 14",
                "Level 15", "Level 16", "Level 17", "Level 18",
                "Level 19", "Level 20" );
        tD_playerLvlCBox.setPromptText("Select Lvl");
        return tD_playerLvlCBox;
    }
    private ComboBox<String> createBot_CBox(ComboBox<String> inComboBox[], int inPlayerIndex) {
        // Creates Player selection ComboBox----------------------------------------------------------------------------
        inComboBox[inPlayerIndex] = new ComboBox<>();
        inComboBox[inPlayerIndex].getItems().addAll("Level 1", "Level 2",
                "Level 3", "Level 4", "Level 5", "Level 6",
                "Level 7", "Level 8", "Level 9", "Level 10",
                "Level 11", "Level 12", "Level 13", "Level 14",
                "Level 15", "Level 16", "Level 17", "Level 18",
                "Level 19", "Level 20" );
        String playerLvlSelected = tD_playerLvlCBox.getValue();
        inComboBox[inPlayerIndex].setValue(playerLvlSelected);
        return inComboBox[inPlayerIndex] ;
    }
    private Button tD_makeButton(Button tD_Button[] , String text, int i) {
        /*******************************************************************************************************************
         * Creates buttons with a text and an integer
         * ****************************************************************************************************************/
        tD_Button[i] = new Button();
        tD_Button[i] = new Button(text);
        return tD_Button[i];
    }
    //----------------------------------------END_Main_Window_Methods---------------------------------------------------
    //---------------------------------------Universal_Methods----------------------------------------------------------
    private Label uni_makeLabel(Label inLabel[], int inPlayerIndex, String inText){
        inLabel[inPlayerIndex] = new Label(inText);
        return inLabel[inPlayerIndex];
    }
    private TextField uni_create_TF(TextField inTextField[], int inPlayerIndex) {
        /***************************************************************************************************************
         * Creates the Text field. It takes a text field array and uses the index create the text field.
         * ************************************************************************************************************/
        inTextField[inPlayerIndex]= new TextField();
        return inTextField[inPlayerIndex];
    }
    //-----------------------------------END_Universal_Methods----------------------------------------------------------
    private Button makeSliderButton(Button inPlusMinusButton[][],int playerIndex, String addSub) {
        if (addSub == "+") {
            inPlusMinusButton[playerIndex][0] = new Button(addSub);
            inPlusMinusButton[playerIndex][0].setMinWidth(100);
            inPlusMinusButton[playerIndex][0].setOnAction(event -> {
                System.out.println(pSlider[playerIndex].getValue());
                pSlider[playerIndex].adjustValue(pSlider[playerIndex].getValue() + newValue);
            });
            return inPlusMinusButton[playerIndex][0];
        }
        else {
            inPlusMinusButton[playerIndex][1] = new Button(addSub);
            inPlusMinusButton[playerIndex][1].setMinWidth(100);
            inPlusMinusButton[playerIndex][1].setOnAction(event -> {
                System.out.println(pSlider[playerIndex].getValue());
                pSlider[playerIndex].adjustValue(pSlider[playerIndex].getValue() - newValue);
            });
            return inPlusMinusButton[playerIndex][1];
        }
    }
    private VBox init_HpVbTfLbl(int inPlayerIndex) {
        /***************************************************************************************************************
         * Creates the VBox that holds the Amour class label and text field.
         * ************************************************************************************************************/
        player_Hp_label_VBox[inPlayerIndex] = new VBox();             // Holds the label and text field
        playHp_Label_HBox[inPlayerIndex][0] = new HBox();             // Holds the label for Hit points
        playHp_Label_HBox[inPlayerIndex][1] = new HBox();             // Holds the Text Field for quick hp change

        uni_create_TF(aC_Indicator_TF,inPlayerIndex);
        uni_makeLabel(Ac_Label,inPlayerIndex,"Armour class:");
        playHp_Label_HBox[inPlayerIndex][0].getChildren().addAll(Ac_Label[inPlayerIndex]);
        playHp_Label_HBox[inPlayerIndex][1].getChildren().addAll(aC_Indicator_TF[inPlayerIndex]);

        player_Hp_label_VBox[inPlayerIndex].getChildren().addAll(
                playHp_Label_HBox[inPlayerIndex][0],
                playHp_Label_HBox[inPlayerIndex][1]);

        return player_Hp_label_VBox[inPlayerIndex];
    }
    private VBox init_PlayerStats(VBox plyrStatsVBox[], HBox plyrStats_Lbl_HBox[][], Label inPlayerStatLabel[][][],
                                  TextField inPlayerStatTF[][][], int inPlayerIndex) {
        // Creates the VBox that holds the Player stats
        plyrStatsVBox[inPlayerIndex] = new VBox(3);

        plyrStats_Lbl_HBox[0][0] = new HBox();
        inPlayerStatLabel[inPlayerIndex][0][0]= new Label("Strength");
        inPlayerStatLabel[inPlayerIndex][0][1] =new Label("Mod");
        plyrStats_Lbl_HBox[0][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][0][0],
                inPlayerStatLabel[inPlayerIndex][0][1]);
        plyrStats_Lbl_HBox[0][1] = new HBox();
        inPlayerStatTF[inPlayerIndex][0][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][0][1] = new TextField();
        inPlayerStatTF[inPlayerIndex][0][0].setPromptText("Str");
        plyrStats_Lbl_HBox[0][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][0][0],
                inPlayerStatTF[inPlayerIndex][0][1]
        );

        plyrStats_Lbl_HBox[1][0] = new HBox();
        inPlayerStatLabel[inPlayerIndex][1][0] = new Label("Dexterity");
        inPlayerStatLabel[inPlayerIndex][1][1] = new Label("Mod");
        plyrStats_Lbl_HBox[1][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][1][0],
                inPlayerStatLabel[inPlayerIndex][1][1]);
        plyrStats_Lbl_HBox[1][1] = new HBox();
        inPlayerStatTF[inPlayerIndex][1][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][1][1] = new TextField();
        inPlayerStatTF[inPlayerIndex][1][0].setPromptText("Dex");
        plyrStats_Lbl_HBox[1][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][1][0],
                inPlayerStatTF[inPlayerIndex][1][1]
        );

        plyrStats_Lbl_HBox[2][0] = new HBox();
        inPlayerStatLabel[inPlayerIndex][2][0] = new Label("Constitution");
        inPlayerStatLabel[inPlayerIndex][2][1] = new Label("Mod");
        plyrStats_Lbl_HBox[2][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][2][0],
                inPlayerStatLabel[inPlayerIndex][2][1]);
        plyrStats_Lbl_HBox[2][1] = new HBox();
        inPlayerStatTF[inPlayerIndex][2][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][2][1] = new TextField();
        inPlayerStatTF[inPlayerIndex][2][0].setPromptText("Con");
        plyrStats_Lbl_HBox[2][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][2][0],
                inPlayerStatTF[inPlayerIndex][2][1]
        );

        plyrStats_Lbl_HBox[3][0] = new HBox();
        inPlayerStatLabel[inPlayerIndex][3][0] = new Label("Intelligence");
        inPlayerStatLabel[inPlayerIndex][3][1] = new Label("Mod");
        plyrStats_Lbl_HBox[3][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][3][0],
                inPlayerStatLabel[inPlayerIndex][3][1]);
        plyrStats_Lbl_HBox[3][1] = new HBox();
        inPlayerStatTF[inPlayerIndex][3][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][3][1] = new TextField();
        inPlayerStatTF[inPlayerIndex][3][0].setPromptText("Int");
        plyrStats_Lbl_HBox[3][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][3][0],
                inPlayerStatTF[inPlayerIndex][3][1]
        );

        plyrStats_Lbl_HBox[4][0] = new HBox();
        inPlayerStatLabel[inPlayerIndex][4][0] = new Label("Wisdom");
        inPlayerStatLabel[inPlayerIndex][4][1] = new Label("Mod");
        plyrStats_Lbl_HBox[4][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][4][0],
                inPlayerStatLabel[inPlayerIndex][4][1]);
        plyrStats_Lbl_HBox[4][1] = new HBox();
        inPlayerStatTF[inPlayerIndex][4][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][4][1] = new TextField();
        inPlayerStatTF[inPlayerIndex][4][0].setPromptText("Wis");
        plyrStats_Lbl_HBox[4][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][4][0],
                inPlayerStatTF[inPlayerIndex][4][1]
        );

        plyrStats_Lbl_HBox[5][0] = new HBox();
        inPlayerStatLabel[inPlayerIndex][5][0] = new Label("Charisma");
        inPlayerStatLabel[inPlayerIndex][5][1] = new Label("Mod");
        plyrStats_Lbl_HBox[5][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][5][0],
                inPlayerStatLabel[inPlayerIndex][5][1]);
        plyrStats_Lbl_HBox[5][1] = new HBox();
        inPlayerStatTF[inPlayerIndex][5][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][5][1] = new TextField();
        inPlayerStatTF[inPlayerIndex][5][0].setPromptText("Cha");
        plyrStats_Lbl_HBox[5][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][5][0],
                inPlayerStatTF[inPlayerIndex][5][1]
        );
        plyrStatsVBox[inPlayerIndex].setAlignment(Pos.CENTER_LEFT);
        plyrStatsVBox[inPlayerIndex].getChildren().addAll(
                plyrStats_Lbl_HBox[0][0], plyrStats_Lbl_HBox[0][1],
                plyrStats_Lbl_HBox[1][0], plyrStats_Lbl_HBox[1][1],
                plyrStats_Lbl_HBox[2][0], plyrStats_Lbl_HBox[2][1],
                plyrStats_Lbl_HBox[3][0], plyrStats_Lbl_HBox[3][1],
                plyrStats_Lbl_HBox[4][0], plyrStats_Lbl_HBox[4][1],
                plyrStats_Lbl_HBox[5][0], plyrStats_Lbl_HBox[5][1]

        );

        return plyrStatsVBox[inPlayerIndex];
    }
    private TextField init_playerName_Tf(TextField in_TF_Array[], int playerIndex, int columns){
        // creates a text field array to keep the track of elements (Done)
        uni_create_TF(in_TF_Array,playerIndex);
        in_TF_Array[playerIndex].setPrefColumnCount(columns);
        in_TF_Array[playerIndex].setPromptText("Add name");
        return in_TF_Array[playerIndex];
    }
    private TextField init_hp_Tf(TextField in_TF_Array[],int inPlayerIndex, int columns, String inText) {
        // creates a text field to keep the track of elements
        uni_create_TF(in_TF_Array,inPlayerIndex);

        in_TF_Array[inPlayerIndex].setPromptText(inText);
        in_TF_Array[inPlayerIndex].setPrefColumnCount(columns);
        in_TF_Array[inPlayerIndex].setOnAction(event -> {
            playerMaxHp = Integer.parseInt(in_TF_Array[inPlayerIndex].getText());
            pSlider[inPlayerIndex].setMax(playerMaxHp);
            pSlider[inPlayerIndex].setMajorTickUnit(playerMaxHp / 2);
            pSlider[inPlayerIndex].setMinorTickCount(playerMaxHp / 4);
            pSlider[inPlayerIndex].setBlockIncrement(1);
            pSlider[inPlayerIndex].setSnapToTicks(true);
        });
        return in_TF_Array[inPlayerIndex];
    }
    private Slider create_HP_slider(Slider inSlider[],int inPlayerIndex, int inMinHp, int inHpDefault, int inMaxHp) {
        inSlider[inPlayerIndex] = new Slider(inMinHp, inMaxHp, inHpDefault);

        inSlider[inPlayerIndex].setMaxHeight(300);
        inSlider[inPlayerIndex].setMaxWidth(300);
        inSlider[inPlayerIndex].setPrefSize(150,200);
        inSlider[inPlayerIndex].setShowTickMarks(true);
        inSlider[inPlayerIndex].setShowTickLabels(true);
        inSlider[inPlayerIndex].setMajorTickUnit(5.0);    // set at 0.25f
        inSlider[inPlayerIndex].setMinorTickCount(1);
        inSlider[inPlayerIndex].setBlockIncrement(1.0);
        inSlider[inPlayerIndex].setSnapToTicks(true);
        inSlider[inPlayerIndex].setOrientation(Orientation.VERTICAL);
        return inSlider[inPlayerIndex];
    }
//-------------------------------------------FILE_MENU_METHODS----------------------------------------------------------
    private void saveCampaign() {
        String FIE_playerNames[] = new String[playerIndex];
        String FIE_playerLvl[] = new String[playerIndex];
        String FIE_playerAC[] = new String[playerIndex];
        String FIE_playerHP[] = new String[playerIndex];
        String FIE_playerStr[] = new String[playerIndex];
        String FIE_playerDex[] = new String[playerIndex];
        String FIE_playerCon[] = new String[playerIndex];
        String FIE_playerInt[] = new String[playerIndex];
        String FIE_playerWis[] = new String[playerIndex];
        String FIE_playerCha[] = new String[playerIndex];
        for (int currentplyr = 0; currentplyr < playerIndex ; currentplyr++){
            FIE_playerNames[currentplyr] = String.valueOf(playerName[currentplyr].getText());
            FIE_playerLvl[currentplyr] = String.valueOf(playerLevel_CBox[currentplyr].getValue());
            FIE_playerAC[currentplyr] = String.valueOf(aC_Indicator_TF[currentplyr].getText());
            FIE_playerHP[currentplyr] = String.valueOf(hPIndicator[currentplyr].getText());
            FIE_playerStr[currentplyr] = playerStats[currentplyr][0][0].getText();
            FIE_playerDex[currentplyr] = playerStats[currentplyr][1][0].getText();
            FIE_playerCon[currentplyr] = playerStats[currentplyr][2][0].getText();
            FIE_playerInt[currentplyr] = playerStats[currentplyr][3][0].getText();
            FIE_playerWis[currentplyr] = playerStats[currentplyr][4][0].getText();
            FIE_playerCha[currentplyr] = playerStats[currentplyr][5][0].getText();


        }
        FileImportExport sC = new FileImportExport(playerIndex,FIE_playerNames, FIE_playerLvl, FIE_playerAC,
                FIE_playerHP, FIE_playerStr);
        sC.createFile("Op1.txt",sC.saveCampaign(FIE_playerNames));
//        FileImportExport sC = new FileImportExport(playerIndex,FIE_playerNames, FIE_playerLvl, FIE_playerAC,
//                FIE_playerHP,FIE_playerStr,FIE_playerDex,FIE_playerCon,FIE_playerInt,FIE_playerWis,FIE_playerCha);

    }
//-------------------------------------------END_FILE_METHODS-----------------------------------------------------------
    private void createActions() {
        /***************************************************************************************************************
         * Creates the action for the buttons of the main window
         * ************************************************************************************************************/
        // td_button[0] adds the action for add button
        tD_Button[0].setOnAction(e -> createPlayerMarker(MainWindow, playerStats_c_L_AreaHB));
        tD_Button[1].setOnAction(e -> createPlayerMarker(MainWindow, playerStats_c_L_AreaHB));
        // Call to start new Scene
        tD_Button[2].setOnAction(e -> MainWindow.setScene(mnGen.monGenStart(MainWindow,layoutMain,scene_Main)));
        tD_Button[3].setOnAction(e -> reset());
        saveCamp.setOnAction(e -> saveCampaign());

    }
    private void createPlayerMarker(Stage inMainWindow, HBox inCenterAreaHB) {
        // Adds player indicators to the Main window
        if(playerIndex != maxPlayer || playerIndex != userMaxPlayer) {
            centerWork.setTop(playerAdd_Center_Area_HB);
            inCenterAreaHB.getChildren().addAll(center_WA_BP(playerIndex));
            layoutMain.setLeft(centerWork);
            inMainWindow.setScene(scene_Main);
            playerIndex++;
        }
        else { alertDisplay("Warning","No more players allowed"); }
    }
    // Creates the AlertDisplay for any message and issue
    public static void alertDisplay(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    // Must warn the player about reset<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void reset(){
        centerWork.setCenter(null);
        playerIndex = 0;
    }


}// End Of Class--------------------------------------------------------------------------------------------------------
