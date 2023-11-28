
public class ReglasDeProduccion {
    String nonTerminal;
    String derivation;

    public ReglasDeProduccion(String nonTerminal, String derivation) {
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
