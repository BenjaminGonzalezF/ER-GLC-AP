import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la expresión regular:");
        String regex = scanner.nextLine();

        String grammar = transformToCFG(regex);

        System.out.println("Gramática libre de contexto resultante:");
        System.out.println(grammar);
    }

    private static String transformToCFG(String regex) {

        return "";
    }
}
