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
public class SimpleCalculatorTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new SimpleCalculator("Simple Calculator"));
        primaryStage.setTitle("Testing Simple Calculator");
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
        SimpleCalculator calculator = new SimpleCalculator("Simple Calculator");
        calculator.handleInput("7");
        assertTrue(Precision.equals(calculator.getResult(), (double)7));
        
        calculator.handleInput("123.45");
        assertTrue(Precision.equals(calculator.getResult(), 123.45));
        
        calculator.handleInput("7*8");
        assertTrue(Precision.equals(calculator.getResult(), (double)7 * 8));
        
        calculator.handleInput(" 17 + 82.3 ");
        assertTrue(Precision.equals(calculator.getResult(), (double)17 + 82.3));
        
        calculator.handleInput("27.3-8");
        assertTrue(Precision.equals(calculator.getResult(), 27.3 - 8));
        
        calculator.handleInput("7/8");
        assertTrue(Precision.equals(calculator.getResult(), (double)7 / 8));
        
        System.out.println("testResults passed");
    }

    private void testErrors() {
        SimpleCalculator calculator = new SimpleCalculator("Simple Calculator");
        calculator.handleInput("foobar");
        assertTrue(calculator.getErrorMessage().equals("Error -- number expected. Found: foobar"));
        
        calculator.handleInput("13.4 foobar");
        assertTrue(calculator.getErrorMessage().equals("Error -- operator or end of input expected. Found: foobar"));
        
        calculator.handleInput("13.4 * ");
        assertTrue(calculator.getErrorMessage().equals("Error -- number expected. Found: end of input"));
        
        calculator.handleInput("13.4 * foobar");
        assertTrue(calculator.getErrorMessage().equals("Error -- number expected. Found: foobar"));
        
        calculator.handleInput("13.4 * 73.9 foobar");
        assertTrue(calculator.getErrorMessage().equals("Error -- end of input expected. Found: foobar"));
        
        calculator.handleInput("13.4 * 73.9");
        assertTrue(calculator.getErrorMessage().equals(""))
                ;
        calculator.handleInput("21 + 63 * 47");
        assertTrue(calculator.getErrorMessage().equals("Error -- end of input expected. Found: *"));
        
        System.out.println("testErrors passed");
    }
    
}
