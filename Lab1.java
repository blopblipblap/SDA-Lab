import java.io.*;
import java.util.*;

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        // Instruksi Pertama
        int L = in.nextInt();
        int N = in.nextInt();
        int Q = in.nextInt();
        
        //Operasi pembangunannya
        int tembok[] = new int[L+1];
        int a = 0; 
        int b = 0;
        int K = 0;
        for (int info = 0; info < N; info++) {
            a = in.nextInt();
            b = in.nextInt();
            K = in.nextInt();
            tembok[a-1] = tembok[a-1] + K; //menambahkan k di a (-1 karena idx java)
            tembok[b] = tembok[b] - K; //mengurangi k di b
        }
        for (int i = 0; i < L; i++) { //algoritma prefix sum
            tembok[i+1] = tembok[i+1] + tembok[i];
        }
        
      //Peletakkan air mancur
        List<Integer> awal = new ArrayList<Integer>(); //kumpulan awal
        List<Integer> akhir = new ArrayList<Integer>();//kumpulan note
        int helpMe = 0; //variable bantuan untuk menyetor index arraylist "akhir"
        for (int index = 0; index < L; index++) {
            int indexkanan = (L-1) - index; //idx yang dihitung dari ujung kanan
            if (index == 0) { //jika posisi pancuran di tembok 1
                awal.add(1);
                if (L + index == L) { //jika posisi pancuran di tembok L
                    akhir.add(L);
                }
                else {
                    continue;
                }
            }
            else { 
                if (tembok[index-1] <= tembok[index]) { //compare size tembok dari kiri
                    awal.add(awal.get(index-1));
                    if (tembok[indexkanan] >= tembok[indexkanan+1]) { //compare size tembok dari kanan
                        akhir.add(akhir.get(helpMe));
                        helpMe++; //karena isi arraylist "akhir" bertambah 1
                    }
                    else {
                        akhir.add(indexkanan+1); //yang di add adalah tembok itu sendiri
                        helpMe++; //karena isi arrayList "akhir" bertambah 1
                    }
                }
                else {
                    awal.add(index+1); //yang di add adalah tembok itu sendiri
                    if (tembok[indexkanan] >= tembok[indexkanan+1]) { //compare size tembok dari kanan
                        akhir.add(akhir.get(helpMe));
                        helpMe++;
                    }
                    else {
                        akhir.add(indexkanan+1);
                        helpMe++;
                    }
                }
            }
        }
        for (int baris = 1; baris <= Q; baris++) { //iterasi untuk input Q
            int letak = in.nextInt();
            out.println(awal.get(letak-1)+" "+akhir.get(L-letak)); //awal = letak-1 karena index java, 
                                                                   //akhir = L-letak karena akhir untuk tembok 1 ada di paling akhir
        } 
        out.close();
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
