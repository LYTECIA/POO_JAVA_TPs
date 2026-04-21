package tp03.model;

import java.util.Stack;

public class Calculator implements ICalculator {

    // ATTRIBUTS
    private final IAnalyzer analyzer;
    private String lastFormula;

    // CONSTRUCTEUR
    public Calculator() {
        analyzer = new Analyzer();
        lastFormula = "";
    }

    // REQUETES
    @Override
    public String getLastFormula() {
        return lastFormula;
    }

    @Override
    public int evaluation(String formula) throws SyntaxException {
        if (formula == null || !analyzer.isValidInput(formula)) {
            throw new SyntaxException("Formule invalide");
        }

        analyzer.setInput(formula);
        Stack<Integer> stack = new Stack<Integer>();

        while (analyzer.hasNext()) {
            analyzer.toNext();
            Token token = analyzer.current();
            int arity = token.arity();

            if (stack.size() < arity) {
                throw new SyntaxException("Pas assez d'opérandes");
            }

            int[] operands = popOperands(stack, arity);
            stack.push(token.value(operands));
        }

        if (stack.size() != 1) {
            throw new SyntaxException("Expression mal formée");
        }

        lastFormula = analyzer.getNormalizedInput();
        return stack.pop();
    }

    // OUTILS
    private int[] popOperands(Stack<Integer> stack, int arity) {
        int[] operands = new int[arity];
        for (int i = arity - 1; i >= 0; i--) {
            operands[i] = stack.pop();
        }
        return operands;
    }
}