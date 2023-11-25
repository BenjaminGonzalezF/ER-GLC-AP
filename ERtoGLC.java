import java.util.ArrayList;
import java.util.Stack;

public class ERtoGLC {
    ArrayList<String> glc = new ArrayList<>();
    ArrayList<String> noTerminales = new ArrayList<>();

    public static void main(String[] args) {
        ERtoGLC erToGLC = new ERtoGLC();
        String expresion = "((a*).(c.b))";
        System.out.println(expresion);
        ArrayList<String> listaExpresion = transformarALista(expresion);
        erToGLC.procesarExpresion(listaExpresion);
    }

    // Se reemplazan los terminales por no terminales agregando las transiciones a
    // la GLC
    public void procesarExpresion(ArrayList<String> expresion) {
        ArrayList<String> terminales = new ArrayList<>();
        ArrayList<String> nuevaExpresion = new ArrayList<>();

        noTerminales.add("S0");
        glc.add("S0 -> S1");

        for (int i = 0; i < expresion.size(); i++) {
            String c = expresion.get(i);
            if (!esOperador(c) && !c.equals(")") && !c.equals("(")) {
                String noTerminalNuevo = "S" + noTerminales.size();
                noTerminales.add(noTerminalNuevo);
                nuevaExpresion.add(noTerminalNuevo);
                expresion.set(i, noTerminalNuevo);
                glc.add(noTerminalNuevo + " -> " + c);
            } else {
                nuevaExpresion.add(c);
            }
        }
        /*
         * System.out.println(glc);
         * System.out.println(noTerminales);
         * System.out.println(nuevaExpresion);
         */

        while (expresion.size() > 1) {
            if (expresion.contains("(")) {
                expresion = eliminarParentesis(expresion);
            }
            expresion = operarParejas(expresion);
            System.out.println(expresion);
            System.out.println(glc);
        }
        glc.set(0, "S0 -> " + expresion.get(0));
        System.out.println(glc);
    }

    private ArrayList<String> eliminarParentesis(ArrayList<String> expresion) {
        for (int i = 0; i < expresion.size(); i++) {
            if (expresion.get(i).equals("(")) {
                int j = i + 1;
                if (expresion.get(j + 1).equals(")")) {
                    expresion.remove(i);
                    expresion.remove(j);
                    i--;
                }
            }
        }

        return expresion;
    }

    private ArrayList<String> operarParejas(ArrayList<String> expresion) {
        Stack<String> pilaNoTerminales = new Stack<>();
        Stack<String> pilaOperadores = new Stack<>();

        ArrayList<String> nuevaExpresion = expresion;

        for (int i = 0; i < expresion.size(); i++) {
            String c = expresion.get(i);
            if (!c.equals("(") && !c.equals(")")) {
                if (esOperador(c)) {
                    pilaOperadores.add(c);
                    if (c.equals("*") && pilaNoTerminales.size() == 1) {
                        String noTerminal1 = pilaNoTerminales.pop();
                        String operador = pilaOperadores.pop();
                        String noTerminalNuevo = "S" + noTerminales.size();
                        noTerminales.add(noTerminalNuevo);
                        aplicarOperacion(noTerminal1, "", operador, noTerminalNuevo);
                        nuevaExpresion = actualizarExpresion(nuevaExpresion, noTerminal1, operador, noTerminalNuevo);
                        System.out
                                .println(noTerminalNuevo + "=" + noTerminal1 + " " + operador + " " + noTerminalNuevo);
                        System.out.println(nuevaExpresion);

                    }

                } else {
                    pilaNoTerminales.add(c);

                    if (pilaNoTerminales.size() >= 2) {
                        String noTerminal2 = pilaNoTerminales.pop();
                        String noTerminal1 = pilaNoTerminales.pop();
                        String operador = pilaOperadores.pop();
                        String noTerminalNuevo = "S" + noTerminales.size();
                        noTerminales.add(noTerminalNuevo);
                        aplicarOperacion(noTerminal1, noTerminal2, operador, noTerminalNuevo);
                        nuevaExpresion = actualizarExpresion(nuevaExpresion, noTerminal1, operador, noTerminalNuevo);
                        System.out.println(noTerminalNuevo + "=" + noTerminal1 + " " + operador + " " + noTerminal2);
                        System.out.println(nuevaExpresion);
                    }
                }
            } else {
                pilaNoTerminales.clear();
                pilaOperadores.clear();

            }
        }
        return nuevaExpresion;
    }

    private ArrayList<String> actualizarExpresion(ArrayList<String> expresion, String noTerminalInical, String operador,
            String nuevoNoTerminal) {

        // Encontrar el primer noTerminalInical en la expresion y eliminar elementos
        // siguientes
        int index = expresion.indexOf(noTerminalInical);
        expresion.set(index, nuevoNoTerminal);
        if (operador.equals("*")) {
            expresion.remove(index + 1);
        } else {
            expresion.remove(index + 1);
            expresion.remove(index + 1);
        }
        return expresion;

    }

    public boolean esOperador(String c) {
        return c.equals("|") || c.equals(".") || c.equals("*");
    }

    private void buscarTerminal() {
    }

    public void aplicarOperacion(String terminal1, String terminal2, String operador, String noTerminalNuevo) {
        switch (operador) {
            case ".":
                glc.add(noTerminalNuevo + " -> " + terminal1 + terminal2);
                break;
            case "|":
                glc.add(noTerminalNuevo + " -> " + terminal1);
                glc.add(noTerminalNuevo + " -> " + terminal2);
                break;
            case "*":
                glc.add(noTerminalNuevo + " -> " + terminal1 + noTerminalNuevo);
                glc.add(noTerminalNuevo + " -> " + "_");
                break;
        }
    }

    private static ArrayList<String> transformarALista(String expresion) {
        ArrayList<String> expresionLista = new ArrayList<>();
        for (int i = 0; i < expresion.length(); i++) {
            expresionLista.add(String.valueOf(expresion.charAt(i)));
        }
        return expresionLista;
    }

}