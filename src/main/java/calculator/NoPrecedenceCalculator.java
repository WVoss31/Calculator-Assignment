package calculator;

import util.TokenDispenser;

/**
 *
 * @author Your name here
 */
public class NoPrecedenceCalculator extends SimpleCalculator {
    
    public NoPrecedenceCalculator(String title) {
        super(title);
    }
    
    public NoPrecedenceCalculator() {
        this("Calculator Without Operator Precedence");
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
    
    private void start() {
        getDispenser().advance();
        if (!getDispenser().tokenIsNumber()) {
            syntaxError(NUM);
        }
        setState(State.NUMBER);
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
    
    private void operator() {
        this.shift();
        getDispenser().advance();
        if (!getDispenser().tokenIsNumber()) {
            syntaxError(NUM);
        }
        setState(State.NUMBER);
    }
}