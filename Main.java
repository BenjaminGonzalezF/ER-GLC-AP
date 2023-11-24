import java.util.*;
import GLC.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la expresión regular:");
        String regex = scanner.nextLine();

        // Ejemplo de GLC
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("<S0>"));
        Set<String> terminals = new HashSet<>(Arrays.asList("a"));
        List<ProductionRule> rules = new ArrayList<>();
        rules.add(new ProductionRule("<S0>", "a"));

        // Convertir GLC a AP
        AutomataDePila ap = convertToAP(nonTerminals, terminals, rules, "<S0>");

        // Imprimir el AP
        ap.printAutomata();

        scanner.close();
    }

    private static AutomataDePila convertToAP(Set<String> nonTerminals, Set<String> terminals,
            List<ProductionRule> rules, String startSymbol) {
        AutomataDePila ap = new AutomataDePila();

        // Configurar el AP basado en la GLC
        ap.states.add("q0");
        ap.states.add("q1");
        ap.initialState = "q0";
        ap.finalState = "q1";
        ap.inputAlphabet.addAll(terminals);
        ap.stackAlphabet.addAll(nonTerminals);
        ap.stackAlphabet.add("$"); // Símbolo inicial de la pila

        // Asumiendo una estructura simple de la GLC a AP
        for (ProductionRule rule : rules) {
            ap.addTransition("q0", rule.derivation, "$", "q1", "$");
        }

        return ap;
    }
}
