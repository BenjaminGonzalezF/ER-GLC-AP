import java.util.ArrayList;
import java.util.Stack;

public class ERtoGLC {
    ArrayList<String> glc = new ArrayList<>();
    ArrayList<String> noTerminales = new ArrayList<>();
    ArrayList<String> terminales = new ArrayList<>();
    String simboloInicial = "<S0>";

    public static void main(String[] args) {
        ERtoGLC erToGLC = new ERtoGLC();
        String expresion = "((a*).(c.b))";
        System.out.println(expresion);
        ArrayList<String> listaExpresion = transformarALista(expresion);
        erToGLC.procesarExpresion(listaExpresion);
        erToGLC.formalizacion();
    }

    // Se reemplazan los terminales por no terminales agregando las transiciones a
    // la GLC, luego se procesan los pares de operaciones para que cada par sea un
    // nuevo nodo terminal, así considerar el orden con los parentesis
    public void procesarExpresion(ArrayList<String> expresion) {
        noTerminales.add("<S0>");
        glc.add("(" + "<" + "S0" + ">" + "," + "S1" + ")");

        for (int i = 0; i < expresion.size(); i++) {
            String c = expresion.get(i);
            if (!esOperador(c) && !c.equals(")") && !c.equals("(")) {
                String noTerminalNuevo = "S" + noTerminales.size();
                noTerminales.add("<" + noTerminalNuevo + ">");
                expresion.set(i, noTerminalNuevo);
                glc.add("(" + "<" + noTerminalNuevo + ">" + "," + c + ")");
                terminales.add(c);
            }
        }

        while (expresion.size() > 1) {
            if (expresion.contains("(")) {
                expresion = eliminarParentesis(expresion);
            }
            expresion = operarParejas(expresion);
            /*
             * System.out.println(expresion);
             * System.out.println(glc);
             */
        }
        glc.set(0, "S0 -> " + expresion.get(0));
        glc.set(0, "(" + "<" + "S0" + ">" + "," + expresion.get(0) + ")");
        /* System.out.println(glc); */
    }

    // Cuando encuentra un parentesis abierto pregunta si el siguiente parentesis
    // cerrado esta en 2 posiciones siguientes,
    // si es asi, elimina ambos parentesis, para eliminar las sentencias:
    // "(SimboloNoTerminal)"
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

    // Recorre la expresion y si encuentra estre patron: noTerminal operador
    // noTerminal opera, en el caso de Kleene se opera con un solo no terminal
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
                        noTerminales.add("<" + noTerminalNuevo + ">");
                        aplicarOperacion(noTerminal1, "", operador, noTerminalNuevo);
                        nuevaExpresion = actualizarExpresion(nuevaExpresion, noTerminal1, operador, noTerminalNuevo);
                        /*
                         * System.out
                         * .println(noTerminalNuevo + "=" + noTerminal1 + " " + operador + " " +
                         * noTerminalNuevo);
                         * System.out.println(nuevaExpresion);
                         */

                    }

                } else {
                    pilaNoTerminales.add(c);

                    if (pilaNoTerminales.size() >= 2) {
                        String noTerminal2 = pilaNoTerminales.pop();
                        String noTerminal1 = pilaNoTerminales.pop();
                        String operador = pilaOperadores.pop();
                        String noTerminalNuevo = "S" + noTerminales.size();
                        noTerminales.add("<" + noTerminalNuevo + ">");
                        aplicarOperacion(noTerminal1, noTerminal2, operador, noTerminalNuevo);
                        nuevaExpresion = actualizarExpresion(nuevaExpresion, noTerminal1, operador, noTerminalNuevo);
                        /*
                         * System.out.println(noTerminalNuevo + "=" + noTerminal1 + " " + operador + " "
                         * + noTerminal2);
                         * System.out.println(nuevaExpresion);
                         */
                    }
                }
            } else {
                pilaNoTerminales.clear();
                pilaOperadores.clear();

            }
        }
        return nuevaExpresion;
    }

    // En el caso que se genere un nuevo simbolo no terminal producto de una
    // operacion, se reemplazan los simbolos no terminales asociados a la operacion
    // por el resultante
    private ArrayList<String> actualizarExpresion(ArrayList<String> expresion, String noTerminalInical, String operador,
            String nuevoNoTerminal) {
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

    // Crea la produccion de la GLC dependiendo del operador
    public void aplicarOperacion(String terminal1, String terminal2, String operador, String noTerminalNuevo) {
        switch (operador) {
            case ".":
                glc.add("(" + "<" + noTerminalNuevo + ">" + "," + terminal1 + terminal2 + ")");
                break;
            case "|":
                glc.add("(" + "<" + noTerminalNuevo + ">" + "," + terminal1 + ")");
                glc.add("(" + "<" + noTerminalNuevo + ">" + "," + terminal2 + ")");
                break;
            case "*":
                glc.add("(" + "<" + noTerminalNuevo + ">" + "," + terminal1 + noTerminalNuevo + ")");
                glc.add("(" + "<" + noTerminalNuevo + ">" + "," + "_" + ")");

                break;
        }
    }

    // Comprueba si el simbolo es operador
    public boolean esOperador(String c) {
        return c.equals("|") || c.equals(".") || c.equals("*");
    }

    // Transforma la expresion (String)a una lista de Strings
    private static ArrayList<String> transformarALista(String expresion) {
        ArrayList<String> expresionLista = new ArrayList<>();
        for (int i = 0; i < expresion.length(); i++) {
            expresionLista.add(String.valueOf(expresion.charAt(i)));
        }
        return expresionLista;
    }

    private void formalizacion() {
        System.out.println("GLC 1 M:");
        System.out.println("V = {" + noTerminales + "}");
        System.out.println("Sigma = {" + terminales + "}");
        System.out.println("R = {" + glc + "}");
        System.out.println("S = " + simboloInicial);
    }

}