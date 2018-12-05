import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public interface FileMenuInterface {
    int WIDTH = 450, HEIGHT = 400;
    // "File" root menu that is diplayed
    Menu fileMenu = new Menu("File");
    MenuItem newCamp = new MenuItem("New Campaign...");
    MenuItem openCamp = new MenuItem("Open Campaign...");
    MenuItem saveCamp = new MenuItem("Save Campaign...");

    MenuBar menuBar = new MenuBar();

    /*******************************************************
     * fileMenu.getItems().addAll(newCamp,openCamp, saveCamp);
     * menuBar.getMenus().addAll(fileMenu);
     * return menuBar;
     * *******************************************************/
}
