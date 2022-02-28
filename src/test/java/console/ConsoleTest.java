package console;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Your name and section here
 */
public class ConsoleTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Console());
        primaryStage.setTitle("Testing Console");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void testLaunch() {
        Application.launch();
    }
    
}
