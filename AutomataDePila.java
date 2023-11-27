
import java.util.*;

public class AutomataDePila {
    List<String> states;
    String initialState;
    String finalState;
    List<String> inputAlphabet;
    List<String> stackAlphabet;
    ArrayList<String> transiciones;

    public AutomataDePila() {
        states = new ArrayList<>();
        inputAlphabet = new ArrayList<>();
        stackAlphabet = new ArrayList<>();
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
    public void transicionesGLC_AP(List<ProductionRule> rules) {
        for (ProductionRule rule : rules) {
            String nonTerminal = rule.getNonTerminal();
            String derivation = rule.getDerivation();
            transiciones.add("((q1,_," + nonTerminal + "),(q1," + derivation + "))");
        }
    }

    // Método para imprimir el AP
    public void printAutomata() {
        System.out.println("Estados: " + states);
        System.out.println("Estado inicial: " + initialState);
        System.out.println("Estado final: " + finalState);
        System.out.println("Alfabeto de entrada: " + inputAlphabet);
        System.out.println("Alfabeto de pila: " + stackAlphabet);
        System.out.println("Tabla de transiciones: " + transiciones);
    }

}
