package PennysGame;

import java.util.*;

public class Graph1 {
    public Graph1(){
    }

    public void method(int characterLength, int alphabetSize) {
        //int size = characterLength * alphabetSize;
        int size = 6;
        List<Integer> l[] = new LinkedList[10];
        for (int i=0; i< size; i++)
            l[i] = new LinkedList<>();

        l[0].add(10); l[0].add(12); l[0].add(13);
        l[1].add(20); l[1].add(9);
        l[5].add(36); l[5].add(10);
        l[9].add(100);

        for(int i=0; i<size; i++){
            System.out.println(i + " ----> " + l[i]);
        }

    }



}
