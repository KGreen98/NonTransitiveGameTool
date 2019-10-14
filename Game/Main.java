import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter word length between 3 and 50");
        int myintL = keyboard.nextInt();
        System.out.println(myintL);
        keyboard.nextLine();

        System.out.println("Player 1 input an binary number of length " + myintL);
        String myint = keyboard.nextLine();
        System.out.println(myint);

        System.out.println("Player 2 input an binary number of length " + myintL);
        String myint2 = keyboard.nextLine();
        System.out.println(myint2);

        Pennys p = new Pennys(myint, myint2, myintL);

        System.out.println("Player 1: " + p.getP1());
        System.out.println("Player 2: " + p.getP2());

        Graph g = new Graph();
        g.method(1,1);
    }
}