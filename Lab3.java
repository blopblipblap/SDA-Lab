import java.io.*;
import java.util.*;

//Method search dari https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
//Method countMinimal dan countMaximal dibantu oleh Tsanaativa Vinnera/1906350881 dan
//Niti Cahyaning Utami/1906350894
//Method insert dan rotate saya kembangkan dari slide-slide SDA

public class Lab3 { 

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        Tree stockTree = new Tree();
        Tree distanceTree = new Tree();
        //Input Query
        int Q = in.nextInt();
        for (int i = 0; i < Q; i++) { //O(Q)
            String query = in.next(); //input query
            if (query.equalsIgnoreCase("INSERT")) {
                String T = in.next();
                int S = in.nextInt();
                int J = in.nextInt();
                stockTree.insert(T, S); //insert ke stockTree
                distanceTree.insert(T, J); //insert ke distanceTree
            } else if (query.equalsIgnoreCase("STOK_MINIMAL")) {
                int S = in.nextInt();
                out.println(stockTree.countMinimal(S)); //print hasil countMinimal
            } else if (query.equalsIgnoreCase("JARAK_MAKSIMAL")) {
                int J = in.nextInt();
                out.println(distanceTree.countMaximal(J)); //print hasil countMaximal
            } else if (query.equalsIgnoreCase("TOKO_STOK")) {
                int S = in.nextInt();
                out.println(stockTree.exists(S)); //print hasil cek exists
            } else {
                int J = in.nextInt();
                out.println(distanceTree.exists(J)); //print hasil cek exists
            }
        }
        //stockTree.printPreOrder();
        //distanceTree.printPreOrder();
        out.flush();
    }
    
    // taken from https://codeforces.com/submissions/Petr
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

class Tree {
    TreeNode root;   

    public void insert(String storeName, int value) {
        root = insertNode(root, storeName, value);
    }    

    private TreeNode insertNode(TreeNode node, String storeName, int value) {
        if (node == null) {
            node = new TreeNode(storeName, value, null, null); //jika tree kosong
            
        //Jika value terletak disebelah kiri root
        } else if (value < node.value) {
            //Insert dengan root yang digeser ke kiri
            node.left = insertNode(node.left, storeName, value);
            if (getBalance(node) == 2) { //jika tree tidak balance
                if (value < node.left.value) {
                    node = leftRotate(node); //lakukan single left rotation
                } else {
                    node.left = rightRotate(node.left); //lakukan double left rotation
                    node = leftRotate(node);
                }
            }
            
        //Jika value terletak disebelah kanan root
        } else if (value > node.value) {
            //Insert dengan root yang digeser ke kanan
            node.right = insertNode(node.right, storeName, value);
            if ( getBalance(node) == -2) { //Jika tree tidak balance
                if (value > node.right.value) {
                    node = rightRotate(node); //Lakukan single right rotation
                } else {
                    node.right = leftRotate(node.right); //lakukan double right rotation
                    node = rightRotate(node);
                }
            }
        }
        node.leftCount = (node.left == null) ? 0 : node.left.getTotalCount();
        node.rightCount = (node.right == null) ? 0 : node.right.getTotalCount();
        //Update tinggi
        int hl = height(node.left);
        int hr = height(node.right);
        node.height = max(hl, hr) + 1;
        return node;
    }    

    private TreeNode rightRotate(TreeNode y) { 
        //Rotasi (node yang disebelah kanan root jadi root baru
        TreeNode temp = y.right;
        //Update y.rightCount sebelum rotate
        y.rightCount = (temp.left == null) ? 0 : temp.left.getTotalCount();
        y.right = temp.left;
        //Update temp.leftCount sebelum rotate
        temp.leftCount = y.getTotalCount();
        temp.left = y;
        
        //Update height dan count untuk node y
        int kiriY = height(y.left);
        int kananY = height(y.right);
        y.height = max(kiriY, kananY) + 1;
        
        //Update height dan count untuk node temp
        int kiriTemp = height(temp.left);
        int kananTemp = height(temp.right);
        temp.height = max(kiriTemp, kananTemp) + 1;
        
        return temp;
    } 
    
    private TreeNode leftRotate(TreeNode x) { 
        //Rotasi (node yang disebelah kiri root jadi root baru
        TreeNode temp = x.left;
        //Update x.leftCount sebelum rotate
        x.leftCount = (temp.right == null) ? 0 : temp.right.getTotalCount();
        x.left = temp.right;
        //Update temp.rightCount sebelum rotate
        temp.rightCount = x.getTotalCount();
        temp.right = x;
        
        //Update height dan count untuk node x
        int kiriX = height(x.left);
        int kananX = height(x.right);
        x.height = max(kiriX, kananX) + 1;
        
        //Update height dan count untuk node temp
        int kiriTemp = height(temp.left);
        int kananTemp = height(temp.right);
        temp.height = max(kiriTemp, kananTemp) + 1;
        
        return temp;
    }

    public void search(int value) {
        root = searchNode(root, value);
    }
    
    private TreeNode searchNode(TreeNode root, int value) {
        //Base case = jika root null atau root sama dengan value yang dicari
        if (root == null || root.value == value) {
            return root;
        }
        //Jika value berada disebelah kanan root
        if (root.value < value) {
            return searchNode(root.right, value);
        }
        //Jika value berada disebelah kiri root
        return searchNode(root.left, value);
    }

    public boolean exists(int value) {
        return searchNode(root, value) != null;
    } 

    public int countMinimal(int min) {
        return this.root.countMinimal(min);
    }

    public int countMaximal(int max) {
        return this.root.countMaximal(max);
    }
    
    //Method preOrder untuk cek
    public void printPreOrder() {
        if (this.root != null) {
            this.root.printPreOrder();
        }
    }

    // Utility function to get height of node
    private int height(TreeNode n) { 
        return n == null ? 0 : n.height;
    }
    
    // Utility function to get max between two values
    private int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 

    // Utility function to get balance factor of node
    private int getBalance(TreeNode N) { 
        if (N == null) 
            return 0;
        
        return height(N.left) - height(N.right); 
    }
}

class TreeNode {
    String storeName;
    int value;
    TreeNode left;
    TreeNode right;
    int leftCount;
    int rightCount;
    int height;
    
    public TreeNode(String storeName, int value, TreeNode left, TreeNode right){
        this.left = left;
        this.right = right;
        this.storeName = storeName;
        this.value = value;
        this.height = 1;
    }

    public int countMinimal(int min) {
        // TODO: get count of nodes with at least value min recursively
        if (this.value == min) {
            return this.right == null ? 1 : 1 + this.getRightCount();
        } else if (this.value > min){
            return 1 + this.getRightCount() + (this.left == null ? 0 : this.left.countMinimal(min));
        } else{
            return this.right == null ? 0 : this.right.countMinimal(min);
        }
    }

    public int countMaximal(int max) {
        // TODO: get count of nodes with at most value max recursively
        if (this.value == max) {
            return (this.left == null) ? 1 : 1 + this.getLeftCount();
        } else if (this.value < max){
            return 1 + this.getLeftCount() +
                    ((this.right == null) ? 0 : this.right.countMaximal(max));
        } else {
            return (this.left == null) ? 0 : this.left.countMaximal(max);
        }
    }

    public int getTotalCount() {
        return this.leftCount + this.rightCount + 1;
    }

    public int getLeftCount() {
        return this.leftCount;
    }

    public int getRightCount() {
        return this.rightCount;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setLeftCount(int count) {
        this.leftCount = count;
    }

    public void setRightCount(int count) {
        this.rightCount = count;
    }
    
    public void printPreOrder() {
        System.out.print(this.value + "  " +this.leftCount + " " + this.rightCount + " ");
        if (this.left != null) 
            this.left.printPreOrder();
        if (this.right != null) 
            this.right.printPreOrder();
    }
}