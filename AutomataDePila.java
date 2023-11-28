
import java.util.*;

public class AutomataDePila {
    List<String> estados;
    String initialState;
    String finalState;
    List<String> alfabeto;
    List<String> alfabetoPila;
    ArrayList<String> transiciones;

    public AutomataDePila() {
        estados = new ArrayList<>();
        alfabeto = new ArrayList<>();
        alfabetoPila = new ArrayList<>();
        transiciones = new ArrayList<>();
    }

    // Método para crear las transiciones de lectura del alfabeto, estas debe: lear
    // terminal de la cadena, sacar terminal de la pila e insertar vacio en la pila
    public void transicionesTerminales(Set<String> terminales) {
        transiciones.add("((q0,_,_ ),(q1,S0))");

        for (String terminal : terminales) {
            transiciones.add("((q1," + terminal + "," + terminal + "),(q1,_))");
        }
    }

    // Método para crear las transiciones dada las transiciones del GLC: leer vacio
    // de la cadena, insertar no terminal en la pila y sacar la derivacion de la
    // pila
    public void transicionesGLC_AP(List<ReglasDeProduccion> rules) {
        for (ReglasDeProduccion rule : rules) {
            String nonTerminal = rule.getNonTerminal();
            String derivation = rule.getDerivation();
            transiciones.add("((q1,_," + nonTerminal + "),(q1," + derivation + "))");
        }
    }

    public void formalizacion() {
        System.out.println("\nAP M:");
        System.out.println("K: " + estados);
        System.out.println("Sigma: " + alfabeto);
        System.out.println("Gamma: " + alfabetoPila + alfabeto);
        System.out.println("Delta: " + transiciones);
        System.out.println("S: " + initialState);
        System.out.println("F: " + finalState);
    }

}
