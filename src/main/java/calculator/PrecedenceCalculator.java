package calculator;

import util.TokenDispenser;

/**
 *
 * @author Your name here
 */
public class PrecedenceCalculator extends NoPrecedenceCalculator {
    
    public PrecedenceCalculator(String title) {
        super(title);
    }
    
    public PrecedenceCalculator() {
        this("Calculator With Operator Precedence");
    }
    
    private void number() {
        this.shift();
        getDispenser().advance();
        reduce();

        if (getDispenser().tokenIsEOF()) {
            setState(State.END);
        }
        else if (getDispenser().tokenIsOperator()) {
            setState(State.OPERATOR);
        }
        else {
            syntaxError(OP_OR_END);
        }
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
                case END:
                    end();
                    return (Double)getStack().pop();
                default:
                    throw new Error("Something is wrong in noPrecedenceCalculator.evaluate");
            }
        }
    }
    
    private void operator() {
        this.shift();
        getDispenser().advance();
        if (!getDispenser().tokenIsNumber()) {
            syntaxError(NUM);
        }
        setState(State.NUMBER);
    }
    
    private void start() {
        getDispenser().advance();
        if (!getDispenser().tokenIsNumber()) {
            syntaxError(NUM);
        }
        setState(State.NUMBER);
    }
    
    @Override
    public void reduce() {
        if (numOpNumOnStack() && getDispenser().tokenIsOperator()) {
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
        }//end of if
        if (getDispenser().tokenIsEOF()) {
            while (numOpNumOnStack()) {
                reduceNumOpNum();   
            } //end of while
        }   
    } //end of reduce

}