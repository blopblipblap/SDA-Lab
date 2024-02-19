import java.io.*;
import java.util.*;

//Referensi dalam membuat class DoublyLinkedList dari: 
//https://www.softwaretestinghelp.com/doubly-linked-list-in-java/

//Ide untuk membuat tanda pada head dan tail yang berupa char 0
//didapat oleh teman saya Gabriel Erwhian W. / 1906399852

public class Lab2 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        DoublyLinkedList newDLL = new DoublyLinkedList();
        String kata = in.next();
        //elemen pertama
        newDLL.add(kata.charAt(0));
        //memasukkan setiap char ke node yang berbeda
        for (int i = 1; i < kata.length(); i++) {
            newDLL.add((kata.charAt(i)));
        }
        newDLL.addHeadTail();
        //banyak query
        int Q = in.nextInt();
        for (int q = 0; q < Q; q++) {   //O(N)
            String perintah = in.next();
            if (perintah.equals("GESER_KANAN")) {
                int P = in.nextInt();
                newDLL.geserKanan(P);
            }else if (perintah.equals("GESER_KIRI")) {
                int P = in.nextInt();
                newDLL.geserKiri(P);
            }else if (perintah.equals("TULIS")) {
                int P = in.nextInt();
                String Astr = in.next();
                char A = Astr.charAt(0);
                newDLL.tulis(P, A);
            }else if (perintah.equals("HAPUS")) {
                int P = in.nextInt();
                newDLL.hapus(P);
            }else {
                newDLL.swap();
            }
        }
        newDLL.print(); //O(N)
        out.close();
    }
    
    static class DoublyLinkedList { //Class DLL
        class Node {
            char item;
            Node prev;
            Node next;
            
            public Node (char item) {
                this.item = item;
            }
            
            public void setItem (char item) {
                this.item = item;
            }
        }
        //Kedua pointer null terlebih dahulu
        Node head = null;
        Node tail = null;
        Node pointer1 = null;
        Node pointer2 = null;
        
        //Method untuk add node
        public void add(char item) {
            Node newNode = new Node(item);
            if (pointer1 == null && pointer2 == null) { //Jika node = node pertama
                pointer1 = pointer2 = tail = head = newNode;
                head.prev = null;
                tail.next = null;
            } else {    //selain itu
               tail.next = newNode;
               newNode.prev = tail;
               tail = newNode;
               tail.next = null;
            }
        }
        
        //Method untuk print node
        public void print() {
            Node current = head.next;
            char nol = 0;
            if (head == tail) {
                out.print("");
            } else {
                while (current.item != nol) {
                    out.print(current.item);
                    current = current.next;
                }
            }
            out.println();
        }
        
        //Method untuk membuat tanda pada head dan tail
        public void addHeadTail() {
            char nol = 0;
            //Head
            Node awal = new Node(nol); //Set head jadi char 0
            head.prev = awal;
            head.prev.next = head;
            head = awal;
            pointer1 = awal;
            pointer2 = awal;
            head.prev = null;
            
            //Tail
            Node akhir = new Node(nol); //Set tail jadi char 0 juga
            tail.next = akhir;
            tail.next.prev = tail;
            tail = akhir;
            tail.next = null;
        }
        
        //Method untuk GESER_KANAN
        public void geserKanan(int P) {
            if (P == 1) {
                pointer1 = pointer1.next;
            } else {
                pointer2 = pointer2.next;
            }
        }
        
        //Method untuk GESER_KIRI
        public void geserKiri(int P) {
            if (P == 1) {
                pointer1 = pointer1.prev;
            } else {
                pointer2 = pointer2.prev;
            } 
        }
        
        //Method untuk TULIS
        public void tulis(int P, char A) {
            if (pointer1 == pointer2) { //Jika pointer 1 = 2
                Node newNode = new Node(A);
                newNode.prev = pointer1;
                newNode.next = pointer1.next;
                newNode.prev.next = newNode;
                newNode.next.prev = newNode;
                pointer1 = newNode;
                pointer2 = newNode; //yang dipindahkan dua-duanya
            } else {
                Node newNode = new Node(A);
                if (P == 1) {   //Jika yang dipanggil pointer1
                    newNode.prev = pointer1;
                    newNode.next = pointer1.next;
                    newNode.prev.next = newNode;
                    newNode.next.prev = newNode;
                    pointer1 = newNode;
                } else {    //Jika yang dipanggil pointer2
                    newNode.prev = pointer2;
                    newNode.next = pointer2.next;
                    newNode.prev.next = newNode;
                    newNode.next.prev = newNode;
                    pointer2 = newNode;
                }
            }
        }
        
        //Method untuk HAPUS
        public void hapus(int P) {
            if (pointer1 == pointer2) { //Jika pointer 1 = 2
                pointer1.prev.next = pointer1.next;
                pointer1.next.prev = pointer1.prev;
                pointer1 = pointer1.prev;
                pointer2 = pointer2.prev;
            } else {    
                if (P == 1) {   //Jika pointer yang dipanggil pointer1
                    pointer1.prev.next = pointer1.next;
                    pointer1.next.prev = pointer1.prev;
                    pointer1 = pointer1.prev;
                } else {    //Jika pointer yang dipanggil pointer2
                    pointer2.prev.next = pointer2.next;
                    pointer2.next.prev = pointer2.prev;
                    pointer2 = pointer2.prev;
                }
            }
        }
        
        //Method untuk SWAP
       public void swap() {
           char prev1 = pointer1.item;
           char prev2 = pointer2.item;
           pointer1.setItem(prev2);
           pointer2.setItem(prev1);
           
       }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}