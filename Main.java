import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la expresión regular:");
        // String expresion = scanner.nextLine();
        String expresion = "((a*).(c.b))";
        ERtoGLC erToGLC = new ERtoGLC();
        System.out.println(expresion);

        ArrayList<String> listaExpresion = ERtoGLC.transformarALista(expresion);
        erToGLC.procesarExpresion(listaExpresion);
        erToGLC.formalizacion();

        Set<String> nonTerminals = new HashSet<>(erToGLC.noTerminales);
        Set<String> terminals = new HashSet<>(erToGLC.terminales);
        List<ProductionRule> rules = formatearAReglas(erToGLC.glc);

        AutomataDePila ap = convertToAP(nonTerminals, terminals, rules, "<S0>");

        ap.printAutomata();
        scanner.close();
    }

    public static List<ProductionRule> formatearAReglas(ArrayList<String> glc) {
        List<ProductionRule> productionRules = new ArrayList<>();

        for (String rule : glc) {
            rule = rule.replaceAll("[<>() ]", "");
            String[] parts = rule.split(",");
            ProductionRule productionRule = new ProductionRule(parts[0], parts[1]);
            productionRules.add(productionRule);
        }

        return productionRules;
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

        ap.transicionesTerminales(terminals);
        ap.transicionesGLC_AP(rules);

        return ap;
    }

}
