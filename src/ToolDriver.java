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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


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
    private BorderPane layoutMain = new BorderPane();                       // Outerpane for main window
    private BorderPane centerWork = new BorderPane();                       // CenterPane that holds the players
    private BorderPane centerW_A_BP[] = new BorderPane[maxPlayer];          // CenterArea working

    private HBox playerStats_c_L_AreaHB = new HBox(5);
    private HBox playerStatsOuterHBox[][] = new HBox[maxPlayer][6];
    private HBox playerAdd_Center_Area_HB = new HBox(10);
    private HBox playerIndLayout[] = new HBox[maxPlayer];
    private HBox playerSub[] = new HBox[maxPlayer];
    private HBox playerAddSub_HB[][]= new HBox[maxPlayer][2];
    private HBox playHp_Label_HBox[][] = new HBox[maxPlayer][2];
    private HBox playerStats_LblTFSep_HBox[][][] = new HBox[maxPlayer][6][4];

    private VBox playerADD_CR_AreaVB = new VBox(10);
    private VBox player_Marker[] = new VBox[maxPlayer];
    private VBox playerStats_Outer_VBox[] = new VBox[maxPlayer];
    private VBox playerStats_Lbl_TF_VBox[][][] = new VBox[maxPlayer][6][2];
    private VBox player_Hp_label_VBox[] = new VBox[maxPlayer];

    private TextField aC_Indicator_TF[] = new TextField[maxPlayer];
    private TextField hPIndicator[] = new TextField[maxPlayer];
    private TextField playerName_TF[] = new TextField[maxPlayer];
    private TextField playerStats_TF[][][] = new TextField[maxPlayer][6][2];

    private Label Ac_Label[] = new Label[maxPlayer];
    private Label playerLvlSelection[] = new Label[maxPlayer];
    private Label playerStatLabel[][][] = new Label[maxPlayer][6][2];

    private Slider pSlider[] = new Slider[maxPlayer];

    private Button tD_Button[] = new Button[20];
    private Button plusMinusButton[][] = new Button[maxPlayer][2];

    private ComboBox<String> tD_playerLvlCBox;
    private ComboBox<String> playerLevel_CBox[] = new ComboBox[maxPlayer];


    private MonsterGenerator mnGen = new MonsterGenerator(MainWindow, scene_Main);

    private int inPlayerIndex = 0;
    private String playerLoadArray[][] = new String[maxPlayer][11];
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
                init_playerName_Tf(playerName_TF,inPlayerIndex, uniColumns),
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
                init_Hp_VB_TF_Lbl(inPlayerIndex),
                create_HP_slider(pSlider,inPlayerIndex, playerMinHp,playerHpDefault,playerMaxHp),
                playerAddSub_HB[inPlayerIndex][1]);
        player_Marker[inPlayerIndex].setFillWidth(true);

        playerSub[inPlayerIndex] = new HBox(5);
        playerSub[inPlayerIndex].setAlignment(Pos.CENTER_LEFT);
        playerSub[inPlayerIndex].getChildren().addAll(
                uni_makeLabel(playerLvlSelection,inPlayerIndex,"Current Lvl."),
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
                init_PlayerStats(inPlayerIndex,
                        playerStats_Outer_VBox,
                        playerStatsOuterHBox, playerStats_Lbl_TF_VBox,
                        playerStats_LblTFSep_HBox,
                        playerStatLabel, playerStats_TF
                        ));

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
    private HBox helperPlayerStatsHBox(int inPlayerIndex, int statsIndicator, String statsLabel,
                                       HBox inPlayerStats_Outer_HBox[][], VBox playerStats_LblTF_VBox[][][],
                                       HBox playerStats_LblTFSep_HBox[][][], Label inPlayerStatLabel[][][],
                                       TextField inPlayerStatTF[][][]) {
        inPlayerStats_Outer_HBox[inPlayerIndex][statsIndicator] = new HBox();
        playerStats_LblTF_VBox[inPlayerIndex][statsIndicator][0] = new VBox();   // Holds Label and Textfield
        playerStats_LblTF_VBox[inPlayerIndex][statsIndicator][1] = new VBox();   // Holds Label and Modifier

        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][0] = new HBox();    // Holds statsLabel label
        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][1] = new HBox();    // Holds statsLabel textField
        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][2] = new HBox();    // Holds statsLabel mod Lable
        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][3] = new HBox();    // Holds statsLabel mod result

        inPlayerStatLabel[inPlayerIndex][statsIndicator][0]= new Label(statsLabel);
        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][0].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][statsIndicator][0]);
        inPlayerStatTF[inPlayerIndex][statsIndicator][0] = new TextField();
        inPlayerStatTF[inPlayerIndex][statsIndicator][0].setPromptText(statsLabel.substring(0,3));
        inPlayerStatTF[inPlayerIndex][statsIndicator][0].setPrefColumnCount(5);
        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][1].getChildren().addAll(
                inPlayerStatTF[inPlayerIndex][statsIndicator][0]);

        inPlayerStatLabel[inPlayerIndex][statsIndicator][1] = new Label("Modifier:");
        playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][2].getChildren().addAll(
                inPlayerStatLabel[inPlayerIndex][statsIndicator][1]);
        playerStats_LblTF_VBox[inPlayerIndex][statsIndicator][0].getChildren().addAll(
                playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][0],
                playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][1]
        );

        playerStats_LblTF_VBox[inPlayerIndex][statsIndicator][1].getChildren().addAll(
                playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][2],
                playerStats_LblTFSep_HBox[inPlayerIndex][statsIndicator][3]);

        inPlayerStats_Outer_HBox[inPlayerIndex][statsIndicator].getChildren().addAll(
                playerStats_LblTF_VBox[inPlayerIndex][statsIndicator][0],
                playerStats_LblTF_VBox[inPlayerIndex][statsIndicator][1]);
        return inPlayerStats_Outer_HBox[inPlayerIndex][statsIndicator];
    }
    //-----------------------------------END_Universal_Methods----------------------------------------------------------
    private Button makeSliderButton(Button inPlusMinusButton[][],int playerIndex, String addSub) {
        /***************************************************************************************************************
         * Creates the Slider for health. Uses a multidimensional array that holds the playerIndex and one array of
         * buttons that holds a plus and minus. Then creates the action that goes to each button
         * ************************************************************************************************************/
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
    private VBox init_Hp_VB_TF_Lbl(int inPlayerIndex) {
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
    private VBox init_PlayerStats(int inPlayerIndex, VBox inPlayerStatsVBox[], HBox inPlayerStats_Outer_HBox[][],
                                  VBox playerStats_LblTF_VBox[][][], HBox playerStats_LblTFSep_HBox[][][],
                                  Label inPlayerStatLabel[][][], TextField inPlayerStatTF[][][]) {
        /***************************************************************************************************************
         * Player Index goes from 1-6 or whatever the "MaxPlayer" is.
         * inPlayerStatsVBox holds all the inPlayerStats_Outer_HBox.
         * Those HBoxes hold 2 VBoxes
         * Those VBoxes holds 2 HBoxes each
         * Those HBoxes holds the labels and textFields
         * ************************************************************************************************************/
        // Creates the VBox that holds the Player stats
        inPlayerStatsVBox[inPlayerIndex] = new VBox(3);     // Outer VBox that holds everything
        //------------------------------------Strength_Score_&_MOD------------------------------------------------------
        helperPlayerStatsHBox(inPlayerIndex,0,"Strength",inPlayerStats_Outer_HBox,
                playerStats_LblTF_VBox, playerStats_LblTFSep_HBox,inPlayerStatLabel,inPlayerStatTF);
        //------------------------------------Dexterity_Score_&_MOD-----------------------------------------------------
        helperPlayerStatsHBox(inPlayerIndex,1,"Dexterity",inPlayerStats_Outer_HBox,
                playerStats_LblTF_VBox, playerStats_LblTFSep_HBox,inPlayerStatLabel,inPlayerStatTF);
        //-------------------------------Constitution_score_&_MOD-------------------------------------------------------
        helperPlayerStatsHBox(inPlayerIndex,2,"Constitution",inPlayerStats_Outer_HBox,
                playerStats_LblTF_VBox, playerStats_LblTFSep_HBox,inPlayerStatLabel,inPlayerStatTF);
        //-------------------------------Intelligence_score_&_MOD-------------------------------------------------------
        helperPlayerStatsHBox(inPlayerIndex,3,"Intelligence",inPlayerStats_Outer_HBox,
                playerStats_LblTF_VBox, playerStats_LblTFSep_HBox,inPlayerStatLabel,inPlayerStatTF);
        //-------------------------------Wisdom_score_&_Mod-------_-----------------------------------------------------
        helperPlayerStatsHBox(inPlayerIndex,4,"Wisdom",inPlayerStats_Outer_HBox,
                playerStats_LblTF_VBox, playerStats_LblTFSep_HBox,inPlayerStatLabel,inPlayerStatTF);
        //-------------------------------Charisma_Score_&_Mod-----------------------------------------------------------
        helperPlayerStatsHBox(inPlayerIndex,5,"Charisma",inPlayerStats_Outer_HBox,
                playerStats_LblTF_VBox, playerStats_LblTFSep_HBox,inPlayerStatLabel,inPlayerStatTF);
    //-----------------------------------Add_to_Main_VBox---------------------------------------------------------------
        inPlayerStatsVBox[inPlayerIndex].setAlignment(Pos.CENTER_LEFT);
        inPlayerStatsVBox[inPlayerIndex].getChildren().addAll(
                inPlayerStats_Outer_HBox[inPlayerIndex][0],
                inPlayerStats_Outer_HBox[inPlayerIndex][1],
                inPlayerStats_Outer_HBox[inPlayerIndex][2],
                inPlayerStats_Outer_HBox[inPlayerIndex][3],
                inPlayerStats_Outer_HBox[inPlayerIndex][4],
                inPlayerStats_Outer_HBox[inPlayerIndex][5]
        );
        return inPlayerStatsVBox[inPlayerIndex];
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
        for (int currentplayer = 0; currentplayer < playerIndex ; currentplayer++){
            FIE_playerNames[currentplayer] = playerName_TF[currentplayer].getText();
            FIE_playerLvl[currentplayer] = playerLevel_CBox[currentplayer].getValue();

            FIE_playerAC[currentplayer] = aC_Indicator_TF[currentplayer].getText();
            FIE_playerHP[currentplayer] = hPIndicator[currentplayer].getText();
            FIE_playerStr[currentplayer] = playerStats_TF[currentplayer][0][0].getText();
            FIE_playerDex[currentplayer] = playerStats_TF[currentplayer][1][0].getText();
            FIE_playerCon[currentplayer] = playerStats_TF[currentplayer][2][0].getText();
            FIE_playerInt[currentplayer] = playerStats_TF[currentplayer][3][0].getText();
            FIE_playerWis[currentplayer] = playerStats_TF[currentplayer][4][0].getText();
            FIE_playerCha[currentplayer] = playerStats_TF[currentplayer][5][0].getText();
        }
        // Creates the object to save the information
        FileExport sC = new FileExport(playerIndex,FIE_playerNames, FIE_playerLvl, FIE_playerAC,
                FIE_playerHP, FIE_playerStr, FIE_playerDex, FIE_playerCon, FIE_playerInt,FIE_playerWis, FIE_playerCha);
        //Calls the method to create the file through FileExport.
        sC.createFile("Op1.txt",sC.saveCampaign());
    }
    private void loadCampaign() {
        /***************************************************************************************************************
         * The try and catch is used to Open the file from File chooser.
         * It then uses the file and BufferedReader to break it into the string and skip White spaces
         * ************************************************************************************************************/
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File x1 = fileChooser.showOpenDialog(MainWindow);
            FileReader x2 = new FileReader(x1);
            BufferedReader reader = new BufferedReader(x2);

            String key = "";
            String line = reader.readLine();
                while(line != null){
                    key += line;
                    line = reader.readLine();
                }
            String [] strings = key.split(":");

            for (int i = 0; i <= strings.length; i++) {
                if (strings[i].equalsIgnoreCase("PlayerIndex")) {
                    inPlayerIndex = Integer.parseInt(strings[i+1]);
                    playerLoadArray[playerIndex][0] = strings[i+1];
                    System.out.println(playerLoadArray[playerIndex][0]);
                }
                else if(strings[i].equalsIgnoreCase("Name")){
                    playerLoadArray[playerIndex][1] = strings[i+1];
                    System.out.println(playerLoadArray[playerIndex][1]);
                }
                else if(strings[i].equalsIgnoreCase("Player Lvl")){
                    playerLoadArray[playerIndex][2] = strings[i+1];
                    System.out.println(playerLoadArray[playerIndex][2]);
                }
                else if(strings[i].equalsIgnoreCase("Amour Class")){
                    playerLoadArray[playerIndex][3] = strings[i+1];
                    System.out.println(playerLoadArray[playerIndex][3]);
                }
                else if(strings[i].equalsIgnoreCase("Hit points")){
                    playerLoadArray[playerIndex][4] = strings[i+1];
                    System.out.println(playerLoadArray[playerIndex][4]);
                }
                else if(strings[i].equalsIgnoreCase("Strength")) {
                    playerLoadArray[playerIndex][5] = strings[i + 1];
                    System.out.println(playerLoadArray[playerIndex][5]);
                }
                else if(strings[i].equalsIgnoreCase("Dexterity")) {
                    playerLoadArray[playerIndex][6] = strings[i + 1];
                    System.out.println(playerLoadArray[playerIndex][6]);
                }
                else if(strings[i].equalsIgnoreCase("Constitution")) {
                    playerLoadArray[playerIndex][7] = strings[i + 1];
                    System.out.println(playerLoadArray[playerIndex][7]);
                }
                else if(strings[i].equalsIgnoreCase("Intelligence")) {
                    playerLoadArray[playerIndex][8] = strings[i + 1];
                    System.out.println(playerLoadArray[playerIndex][8]);
                }
                else if(strings[i].equalsIgnoreCase("Wisdom")) {
                    playerLoadArray[playerIndex][9] = strings[i + 1];
                    System.out.println(playerLoadArray[playerIndex][9]);
                }
                else if(strings[i].equalsIgnoreCase("Charisma")) {
                    playerLoadArray[playerIndex][10] = strings[i + 1];
                    System.out.println(playerLoadArray[playerIndex][10]);
                }
            }
        }
        catch (Exception e){
            e.getMessage();
            e.getStackTrace();
        }
    }
//-------------------------------------------END_FILE_METHODS-----------------------------------------------------------
    private void createActions() {
        /****************************************************************************************************************
         * Creates the action for the buttons of the main window
         * ************************************************************************************************************/
        // td_button[0] adds the action for add button
        tD_Button[0].setOnAction(e -> createPlayerMarker(MainWindow, playerStats_c_L_AreaHB));
        // td_button[1]
        tD_Button[1].setOnAction(e -> createPlayerMarker(MainWindow, playerStats_c_L_AreaHB));
        // Call to start new Scene
        tD_Button[2].setOnAction(e -> MainWindow.setScene(mnGen.monGenStart(MainWindow,layoutMain,scene_Main)));
        tD_Button[3].setOnAction(e -> reset());
        newCamp.setOnAction(e-> reset());
        openCamp.setOnAction(e-> loadCampaign());
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
        // TODO Fix the issue of file not allowing the area to re-acquired for players
        centerWork.setCenter(null);
        playerIndex = 0;
    }


}// End Of Class--------------------------------------------------------------------------------------------------------
