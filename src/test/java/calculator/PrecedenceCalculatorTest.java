package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import util.Precision;

/**
 *
 * @author tcolburn
 */
public class PrecedenceCalculatorTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new PrecedenceCalculator());
        primaryStage.setTitle("Testing Calculator with Precedence");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void testLaunch() {
        Application.launch();
        testResults();
        testErrors();
    }
    
    private void testResults() {
        PrecedenceCalculator calculator = new PrecedenceCalculator();
        calculator.handleInput("21 + 63 * 47");
        assertTrue(Precision.equals(calculator.getResult(), (double)21 + 63 * 47));
        
        calculator.handleInput("12 - 3.6 * 47 / 99.99 + 3.14159");
        assertTrue(Precision.equals(calculator.getResult(), (double)12 - 3.6 * 47 / 99.99 + 3.14159)) ;
        
        System.out.println("testResults passed");
    }

    private void testErrors() {
        PrecedenceCalculator calculator = new PrecedenceCalculator();
        calculator.handleInput("13.4 * 73.9 foobar");
        assertTrue(calculator.getErrorMessage().equals("Error -- operator or end of input expected. Found: foobar"));
        
        calculator.handleInput("13.4 * 73.9 + foobar");
        assertTrue(calculator.getErrorMessage().equals("Error -- number expected. Found: foobar"));
        
        calculator.handleInput("13.4 * 73.9 + 17");
        assertTrue(calculator.getErrorMessage().equals(""));
        
        calculator.handleInput("13.4 * 73.9 + 17 - / 69.6");
        assertTrue(calculator.getErrorMessage().equals("Error -- number expected. Found: /"));
        
        calculator.handleInput("13.4 * 73.9 + 17 - 69.6 foobar  ");
        assertTrue(calculator.getErrorMessage().equals("Error -- operator or end of input expected. Found: foobar"));
        
        calculator.handleInput("13.4 * 73.9 + 17 - 69.6 /  ");
        assertTrue(calculator.getErrorMessage().equals("Error -- number expected. Found: end of input"));
        
        calculator.handleInput("13.4 * 73.9 + 17 - 69.6 / 3.14159 ");
        assertTrue(calculator.getErrorMessage().equals(""));
        
        System.out.println("testErrors passed");
    }
    
}
