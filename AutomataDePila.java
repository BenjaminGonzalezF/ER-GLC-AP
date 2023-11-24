
import java.util.*;

public class AutomataDePila {
    List<String> states;
    String initialState;
    String finalState;
    List<String> inputAlphabet;
    List<String> stackAlphabet;
    Map<String, Map<String, String>> transitionTable; // Transiciones

    public AutomataDePila() {
        states = new ArrayList<>();
        inputAlphabet = new ArrayList<>();
        stackAlphabet = new ArrayList<>();
        transitionTable = new HashMap<>();
    }

    // Método para agregar una transición
    public void addTransition(String state, String inputSymbol, String stackSymbol, String newState,
            String newStackSymbol) {
        if (!transitionTable.containsKey(state)) {
            transitionTable.put(state, new HashMap<>());
        }
        transitionTable.get(state).put(inputSymbol + "," + stackSymbol, newState + "," + newStackSymbol);
    }

    // Método para imprimir el AP
    public void printAutomata() {
        System.out.println("Estados: " + states);
        System.out.println("Estado inicial: " + initialState);
        System.out.println("Estado final: " + finalState);
        System.out.println("Alfabeto de entrada: " + inputAlphabet);
        System.out.println("Alfabeto de pila: " + stackAlphabet);
        System.out.println("Tabla de transiciones: " + transitionTable);
    }

    // Setters para configurar el autómata
    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public void addState(String state) {
        states.add(state);
    }

    public void addInputAlphabet(String symbol) {
        inputAlphabet.add(symbol);
    }

    public void addStackAlphabet(String symbol) {
        stackAlphabet.add(symbol);
    }
}
