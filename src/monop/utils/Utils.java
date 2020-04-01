package monop.utils;

import java.util.Scanner;

public class Utils {
    static public String getString(String label) {
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println(label);
                String s = scan.nextLine();
                if (!s.equals(""))
                    return (s);
                else
                    System.out.println("Tapez quelque chose, s'il vous plaît.");
            } catch (Exception e) {
                System.out.println("Entrez un mot ou une phrase, s'il vous plaît.");
            }
        }
    }

    static public int getInt(String label) {

        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println(label);
                int i = scan.nextInt();
                return i;
            } catch (Exception e) {
                System.out.println("Entrez un nombre entier, s'il vous plaît.");
            }
        }

    }

    static public boolean getBool(String label) {
        Scanner scan = new Scanner(System.in);
        String string_answer;
        boolean answer;

        do {
            System.out.println(label);
            string_answer = scan.nextLine();
            answer = string_answer.equals("o") || string_answer.equals("O");
            if (!string_answer.equals("o") && !string_answer.equals("O") && !string_answer.equals("n") && !string_answer.equals("N"))
                System.out.println("Entrez \"o\" ou \"n\", s'il vous plaît");

        } while (!string_answer.equals("o") && !string_answer.equals("O") && !string_answer.equals("n") && !string_answer.equals("N"));
        return answer;

    }

    static public void waitEnter(String label) {
        System.out.println(label);
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}
