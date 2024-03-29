package TADS.LinkedList.Test;

import TADS.LinkedList.src.LinkedListKV;
import org.junit.jupiter.api.Test;

public class LinkedListTest {

    public void print(LinkedListKV lista, int index){
        System.out.println(lista.get(index));
    }

    @Test
    public void add() {

        LinkedListKV lista = new LinkedListKV();

        lista.add(86,86);
        lista.add(2,2);
        lista.add("Joselito vaca",1);
        lista.add(4,5);

        System.out.println(lista.get(0));
        System.out.println(lista.get(1));
        System.out.println(lista.get(2));
        System.out.println(lista.get(-2));
        System.out.println(lista.get(3));
        System.out.println(lista.get(3));
        System.out.println(lista.get(50));

    }

    @Test
    public void remove() {

        LinkedListKV lista = new LinkedListKV();
/*
        lista.add(0);
        lista.add(1);
        lista.add(2);
        lista.add(3);
        lista.add(4);
*/
        print(lista,0);
        print(lista,1);
        print(lista,2);
        print(lista,3);
        print(lista,4);

        lista.remove(2);

        System.out.println("");
        System.out.println("");

        print(lista,0);
        print(lista,1);
        print(lista,2);
        print(lista,3);
        print(lista,4);

        System.out.println("");
        System.out.println("Remover con numeros malos: ");
        lista.remove(100000);
        lista.remove(-1);

    }
}