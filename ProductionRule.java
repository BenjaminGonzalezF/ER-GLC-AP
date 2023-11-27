
public class ProductionRule {
    String nonTerminal;
    String derivation;

    public ProductionRule(String nonTerminal, String derivation) {
        this.nonTerminal = nonTerminal;
        this.derivation = derivation;
    }

    // Getters
    public String getNonTerminal() {
        return nonTerminal;
    }

    public String getDerivation() {
        return derivation;
    }
}
