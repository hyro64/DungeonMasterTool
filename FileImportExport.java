import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileImportExport {
    static int playerSize = 6;

    FileImportExport(int tDPlayerSize){
        playerSize = tDPlayerSize;
    }
    public static void main(String[] args) {
        String fileName = "Op1.txt";
        String names[] = {"James","Jake"};
        String playerLvl [] = new String[playerSize];
        String playerAc [] = new String[playerSize];
        String playerHp [] = new String[playerSize];
        String playerStr [] = new String[playerSize];
        String playerDex [] = new String[playerSize];
        String playerCon [] = new String[playerSize];
        String playerInt [] = new String[playerSize];
        String playerWis [] = new String[playerSize];
        String playerCha [] = new String[playerSize];

        createFile(fileName,saveCampaign(1, names));

    }

    public static String saveCampaign(int playerIndex, String playerName[],String playerLvl [],String playerAc [],
                                      String playerHp [],String playerStr [],String playerDex [],String playerCon [],
                                      String playerInt [],String playerWis[],String playerCha []) {
        String content = "";
        for (int i = 0; i< (playerIndex+1); i++) {
            content += "\n" +
                    "PlayerIndex:" + i +"\n"+
                    "Name:"+ playerName[i] + "\n"+
                    "Player Lvl: " + playerLvl[i] +"\n",
                    "" +  + "\n";

        }
        return content;
    }

    public static void createFile(String fileName, String content){
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
