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
public class ParenthesisCalculatorTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new ParenthesisCalculator());
        primaryStage.setTitle("Testing Calculator with Parentheses");
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
        ParenthesisCalculator calculator = new ParenthesisCalculator();
        calculator.handleInput("21 + 63 * 47");
        assertTrue(Precision.equals(calculator.getResult(), (double)21 + 63 * 47));
        
        calculator.handleInput("(21 + 63 * 47)");
        assertTrue(Precision.equals(calculator.getResult(), (double)(21 + 63 * 47)));
        
        calculator.handleInput("21 + (63 * 47)");
        assertTrue(Precision.equals(calculator.getResult(), (double)21 + 63 * 47));
        
        calculator.handleInput("(21 + 63) * 47");
        assertTrue(Precision.equals(calculator.getResult(), ((double)21 + 63) * 47));
        
        calculator.handleInput("((2 + 3) * 4)");
        assertTrue(Precision.equals(calculator.getResult(), ((double)2 + 3) * 4));
        
        calculator.handleInput("((2 + (3)) * (((4))))");
        assertTrue(Precision.equals(calculator.getResult(), ((double)2 + 3) * 4));
        
        calculator.handleInput("((2+(3+(4*5))))");
        assertTrue(Precision.equals(calculator.getResult(), (((double)2+(3+(4*5))))));
        
        calculator.handleInput("1 * 2 * 3 * (4 + 5) * 6 * 7");
        assertTrue(Precision.equals(calculator.getResult(), 1 * 2 * 3 * (4 + 5) * 6 * 7));
        
        calculator.handleInput("12 - 3.6 * 47 / 99.99 + 3.14159");
        assertTrue(Precision.equals(calculator.getResult(), (double)12 - 3.6 * 47 / 99.99 + 3.14159)) ;
        
        calculator.handleInput("(12 - 3.6) * 47 / (99.99 + 3.14159)");
        assertTrue(Precision.equals(calculator.getResult(), ((double)12 - 3.6) * 47 / (99.99 + 3.14159))) ;
        
        System.out.println("testResults passed");
    }

    private void testErrors() {
        ParenthesisCalculator calculator = new ParenthesisCalculator();
        calculator.handleInput("( 2 + 3 ");
        assertTrue(calculator.getErrorMessage().equals("Error -- mismatched parentheses"));
        
        calculator.handleInput("2 + 3 )");
        assertTrue(calculator.getErrorMessage().equals("Error -- mismatched parentheses"));
        
        calculator.handleInput("2 + ((3 + 4) * 5");
        assertTrue(calculator.getErrorMessage().equals("Error -- mismatched parentheses"));
        
        calculator.handleInput("2 + ((3 + 4) * 5))");
        assertTrue(calculator.getErrorMessage().equals("Error -- mismatched parentheses"));
        
        calculator.handleInput("2 + ((3 + 4) * 5)(");
        assertTrue(calculator.getErrorMessage().equals("Error -- operator expected. Found: ("));
        
        calculator.handleInput("2 + () + 3");
        assertTrue(calculator.getErrorMessage().equals("Error -- number or left parenthesis expected. Found: )"));
        
        System.out.println("testErrors passed");
    }
    
}
