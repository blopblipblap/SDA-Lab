import java.io.*;
import java.util.*;

//Ref cek substring:
//https://stackoverflow.com/questions/23025773/print-every-substring-of-a-string-using-for-loop-and-not-string-methods

public class Lab4 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        List<List<Node>> hashTable = new ArrayList<>();
        List<String> substring = new ArrayList<>();
        int X = in.nextInt(); //base
        int Y = in.nextInt(); //size hash table
        addLinkedList(hashTable, Y);
        String P = in.next();
        
        //Panjang string
        int PLength = P.length();
        
        //Method substring
        for(int c = 0; c < PLength; c++) { //O(PLength^2)
            for(int i = 1; i <= PLength-c; i++) { //O(PLength)
                String sub = P.substring(c,c+i);
                substring.add(sub);
            }
        }
        
        //Proses mod substring
        for (String sub : substring) { //O(substring.size()*(length*b))
            countMod(sub, X, Y, hashTable);
        }
        
        //Proses count Raja Namlas
        int count = 0;
        Node sub;
        for (int i = 0; i < Y; i++) {
            int size = 0;
            int sizeSub = hashTable.get(i).size(); //Ambil size dari linkedlist
            //Jika isi linkedlist tidak kosong, proses sizenya
            if (sizeSub != 0) {
                sub = hashTable.get(i).get(0);
                //Iterasi penambahan size
                while (sub != null) { //O(banyak substring pada indeks)
                    size++;
                    sub = sub.next;
                }
            }
            //Jika size lebih dari 1, count pair
            if (size > 1) {
                count += countPair(size);
            }
        }
        
        out.println(count);
        out.close();
    }
    
    //Inisialisasi hashset kosong
    public static void addLinkedList(List<List<Node>> hashTable, int Y) {
        for(int i = 0; i < Y; i++) {
            hashTable.add(new LinkedList<Node>());
        }
    }
    
    //Menghitung banyak kemungkinan pair
    public static int countPair(int a) {
        int count = 0;
        //Rumus: a-1 + a-2 + ... + 0
        for (int c = a-1; c >= 0; c--) { //O(size)
            count += c;
        }
        return count;
    }
    
    
    public static void countMod(String sub, int X, int Y, List<List<Node>> hashTable) {
        char huruf; //Menyimpan huruf
        int ascii; //Menyimpan ascii - 96
        long temp; //Variable bantuan
        long hasil = 0; //Menyimpan index dari substring
        int length = sub.length(); //Panjang substring
        for (int i = 0; i < length; i++) { //O(length*b)
            huruf = sub.charAt(i); 
            ascii = ((int) huruf)-96;
            temp = (ascii % Y) * powerMod(X, i, Y); //(G(Si))modY * (X^i)modY
            temp = temp % Y; //((G(Si))modY * (X^i)modY)modY
            hasil += temp; //hasil + ((G(Si))modY * (X^i)modY)modY
            hasil = hasil % Y; //(hasil + ((G(Si))modY * (X^i)modY)modY)modY
        }
        int size =  hashTable.get((int) hasil).size(); //size dari linkedlist pada index hasil
        Node newNode = new Node(sub); //Node baru
        if (size == 0) { //Jika isi linked list masih kosong
            newNode.next = null;
            hashTable.get((int) hasil).add(newNode); //Langsung ditambah
        } else { //Jika sudah ada isinya
            Node node = hashTable.get((int) hasil).get(0);
            //Berhenti saat node == null
            while (node != null) { 
                if (node.substring.equals(sub)) { //Jika ketemua node dengan substring sama
                    break;
                }
                if (node.next == null) { //Jika sudah ada dinode paling akhir
                    node.next = newNode;
                    newNode.next = null;
                }
                node = node.next;
            }
        }
    }
    
    //Menghitung (X^i)modY
    public static long powerMod(int X, int b, int Y) {
        long temp;
        long hasil = 1;
        for (int i = 0; i < b; i++) { //O(b), b = pangkat
            temp = X % Y; //XmodY
            hasil *= temp; //hasil * XmodY
            hasil = hasil % Y; //(hasil * XmodY)modY
        }
        return hasil;
    }

    static class Node {
        String substring;
        Node next;
        
        public Node(String substring) {
            this.substring = substring;
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