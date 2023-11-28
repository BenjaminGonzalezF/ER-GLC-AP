import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese la expresión regular:");
            String expresion = scanner.nextLine();
            ERtoGLC erToGLC = new ERtoGLC();

            ArrayList<String> listaExpresion = ERtoGLC.transformarALista(expresion);
            erToGLC.procesarExpresion(listaExpresion);
            erToGLC.formalizacion();

            Set<String> nonTerminals = new HashSet<>(erToGLC.noTerminales);
            Set<String> terminals = new HashSet<>(erToGLC.terminales);
            List<ReglasDeProduccion> reglas = formatearAReglas(erToGLC.glc);

            AutomataDePila ap = convertToAP(nonTerminals, terminals, reglas, "<S0>");

            ap.formalizacion();
            scanner.close();
        } catch (Exception e) {
            System.out.println("Compruebe la escritura de la expresión regular");
        }
    }

    public static List<ReglasDeProduccion> formatearAReglas(ArrayList<String> glc) {
        List<ReglasDeProduccion> productionRules = new ArrayList<>();

        for (String rule : glc) {
            rule = rule.replaceAll("[<>() ]", "");
            String[] parts = rule.split(",");
            ReglasDeProduccion productionRule = new ReglasDeProduccion(parts[0], parts[1]);
            productionRules.add(productionRule);
        }

        return productionRules;
    }

    private static AutomataDePila convertToAP(Set<String> nonTerminals, Set<String> terminals,
            List<ReglasDeProduccion> rules, String startSymbol) {
        AutomataDePila ap = new AutomataDePila();

        // Configurar el AP basado en la GLC
        ap.estados.add("q0");
        ap.estados.add("q1");
        ap.initialState = "q0";
        ap.finalState = "q1";
        ap.alfabeto.addAll(terminals);
        ap.alfabetoPila.addAll(nonTerminals);

        ap.transicionesTerminales(terminals);
        ap.transicionesGLC_AP(rules);

        return ap;
    }

}
