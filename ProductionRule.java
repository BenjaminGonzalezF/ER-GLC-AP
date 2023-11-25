
public class ProductionRule {
    String nonTerminal;
    String derivation;

    public ProductionRule(String nonTerminal, String derivation) {
        this.nonTerminal = nonTerminal;
        this.derivation = derivation;
    }

    // Getters para acceso a las propiedades
    public String getNonTerminal() {
        return nonTerminal;
    }

    public String getDerivation() {
        return derivation;
    }
}
