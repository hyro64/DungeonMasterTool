import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileImportExport {
    int playerSize;
    String playerNames[];
    String playerLvl [];
    String playerAc [];
    String playerHp [];
    String playerStr [];
    String playerDex [];
    String playerCon [];
    String playerInt [];
    String playerWis [];
    String playerCha [];

    FileImportExport(int tDPlayerSize, String tD_names[], String tD_playerLvl[], String tD_PlayerAC[],
                     String tD_PlayerHP[], String tD_PlayerStr[], String tD_PlayerDex[], String tD_PlayerCon[],
                     String tD_PlayerInt[], String tD_PlayerWis[], String tD_PlayerCha[]){
        playerSize = tDPlayerSize;
        playerNames = tD_names;
        playerLvl = tD_playerLvl;
        playerAc = tD_PlayerAC;
        playerHp = tD_PlayerHP;
        playerStr = tD_PlayerStr;
        playerDex = tD_PlayerDex;
        playerCon = tD_PlayerCon;
        playerInt = tD_PlayerInt;
        playerWis = tD_PlayerWis;
        playerCha = tD_PlayerCha;

    }

    public String saveCampaign() {
        String content = "";
        for (int i = 0; i < playerSize; i++) {
            content += "\n" +
                    "PlayerIndex:" + i +":\n"+
                    "Name:"+ playerNames[i] + ":\n"+
                    "Player Lvl: " + playerLvl[i] +":\n" +
                    "Amour Class:" + playerAc[i] + ":\n" +
                    "Hit:points" + playerHp[i] + ":\n" +
                    "Strength:" + playerStr[i] + ":\n" +
                    "Dexterity:" + playerDex[i] + ":\n" +
                    "Constitution:" + playerCon[i] + ":\n" +
                    "Intelligence:" + playerInt[i] + ":\n" +
                    "Wisdom:" + playerWis[i] + ":\n" +
                    "Charisma:" + playerCha[i] + ":\n";
        }
        return content;
    }

    public  void createFile(String fileName, String content){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write(content);
            System.out.println("done");
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try{
                if(bw != null) bw.close();
                if(fw != null)fw.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
