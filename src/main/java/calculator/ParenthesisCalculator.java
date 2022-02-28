package calculator;

import util.TokenDispenser;

/**
 *
 * @author tcolburn
 */
public class ParenthesisCalculator extends PrecedenceCalculator {
    
    public ParenthesisCalculator(String title) {
        super(title);
    }
    
    public ParenthesisCalculator() {
        this("Calculator With Operator Precedence and Parentheses");
    }
    
    @Override
    public double evaluate() {
        setState(State.START);
        while (true) {            
            switch (getState()) {
                case START:
                    start();
                    break;
                case NUMBER:
                    number();
                    break;
                case OPERATOR:
                    operator();
                    break;
                case LEFT_PAREN:
                    leftParen();
                    break;
                case RIGHT_PAREN:
                    rightParen();
                    break;
                case END:
                    end();
                    return (Double)getStack().pop();
                default:
                    throw new Error("Something is wrong in noPrecedenceCalculator.evaluate");
            }
        }
    }
    
    private void number() {
        shift();
        getDispenser().advance();
        if (getDispenser().tokenIsEOF()) {
            setState(State.END);
        }
        else if (getDispenser().tokenIsOperator()) {
            setState(State.OPERATOR);
        }
        else if (getDispenser().tokenIsRightParen()) {
            setState(State.RIGHT_PAREN);
        }
        else {
            syntaxError(OP_OR_END);
        }
    }

    private void operator() {
        reduce();
        shift();
        getDispenser().advance();
        if (getDispenser().tokenIsNumber()) {
            setState(State.NUMBER);
        }
        else if (getDispenser().tokenIsLeftParen()) {
            setState(State.LEFT_PAREN);
        }
        else {
            syntaxError(NUM);
        }
    }
    
    private void start() {
        getDispenser().advance();
        if (getDispenser().tokenIsNumber()) {
            setState(State.NUMBER);
        }
        else if (getDispenser().tokenIsLeftParen()) {
            setState(State.LEFT_PAREN);
        }
        else {
            syntaxError(NUM_OR_LEFT_PAREN);
        }
    }
    
    @Override
    public void reduce() {
        if (getState() != null){
            switch (getState()) {
                case OPERATOR:
                    if (getDispenser().tokenIsOperator() && numOpNumOnStack()) {
                        precedence();
                    }
                    break;
                case RIGHT_PAREN:
                    if (getDispenser().tokenIsRightParen()) {
                        if (!getStack().contains('(')){
                            throw new RuntimeException("Error -- mismatched parentheses");
                        } //end of if
                        while ((char)getStack().get(getStack().size()-2) != '(') {
                            reduceNumOpNum();
                        } //end of while
                        double num = (double)getStack().pop();
                        getStack().pop();
                        getStack().push(num);
                    } //end of if
                    break;
                case END:
                    if (getDispenser().tokenIsEOF()) {
                        if (getStack().contains('(')) {
                            throw new RuntimeException("Error -- mismatched parentheses");
                        }
                        while (numOpNumOnStack()) {
                            reduceNumOpNum();
                        }
                        if (getStack().size() != 1) {
                            throw new RuntimeException("Error -- mismatched parentheses");
                        }
                    }
                    break;
                default:
                    throw new Error("something is wrong inside reduce switch statement");
            } //end of switch
        } //end of if
    } //end of reduce
        
    public void precedence() {
        char ch = (Character)getStack().get(getStack().size()-2);
            if (ch == '*' || ch == '/') {
                reduceNumOpNum();
            } //end of if
            if (getStack().size() > 1) {
                char ch2 = (Character)getStack().get(getStack().size()-2);
                ch = (Character)getDispenser().getToken();
                if ((ch == '+' || ch == '-') && (ch2 == '+' || ch2 == '-')) {  
                    reduceNumOpNum();
                } //end of if
            } //end of if
        }//end of precedence
    
    private void leftParen() {
        shift();
        getDispenser().advance();
        if (getDispenser().tokenIsLeftParen()) {
            setState(State.LEFT_PAREN);
        }
        else if (getDispenser().tokenIsNumber()) {
            setState(State.NUMBER);
        }
        else {
            syntaxError(NUM_OR_LEFT_PAREN);
        }
    }
    
    private void rightParen() {
        reduce();
        getDispenser().advance();
        if (getDispenser().tokenIsEOF()) {
            setState(State.END);
        }
        else if (getDispenser().tokenIsOperator()) {
            setState(State.OPERATOR);
        }
        else if (getDispenser().tokenIsRightParen()) {
            setState(State.RIGHT_PAREN);
        }
        else if (getDispenser().tokenIsLeftParen()) {
            syntaxError(OP);
        }
        else {
            syntaxError(OP_OR_END);
        }
    }
    
    
    
}
    
