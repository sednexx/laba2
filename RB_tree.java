public class RB_tree<TKeyType extends Comparable<TKeyType>, TDataType>{
    private static class Node<TKey, TData>{
        private TKey key;
        private TData data;
        private boolean color;

        private Node<TKey, TData> left, right, parent;

        public Node(){}

        public Node(TKey _key, TData _data){
            this.key  = _key;
            this.data = _data;
            this.color = true;
            this.parent = null;
            this.left = null;
            this.right = null;
        }
    }
    private Node<TKeyType, TDataType> head;
    public Node<TKeyType, TDataType> get_head(){return this.head;}

    public RB_tree(){head = null;}

    protected void finalize(){this.head = null;}
    
    private RB_tree(Node<TKeyType, TDataType> _head){this.head = _head;}
    public RB_tree(RB_tree<TKeyType, TDataType> another_tree){this(another_tree.get_head());}

    public RB_tree copy_rb(RB_tree<TKeyType, TDataType> another_tree){
        if (another_tree != this){
            head = new Node();
            head = another_tree.head;
        }
    
        return this;
    }

    public boolean empty() { // проверка на пустоту
        if(head == null)
            return true;
        else
            return false;
    }

    public void delete(){
        head = null;
    }

    private void left_rotation(Node<TKeyType, TDataType> G, Node<TKeyType, TDataType> P, Node<TKeyType, TDataType> X, Node<TKeyType, TDataType> two){
        G.left = X;
        X.parent = G;
        X.left = P;
        P.parent = X;
        P.right = two;
        if(two!=null)
            two.parent = P;
    }

    private void right_rotation(Node<TKeyType, TDataType> G, Node<TKeyType, TDataType> P, Node<TKeyType, TDataType> X, Node<TKeyType, TDataType> two){
        G.right = X;
        X.parent = G;
        X.right = P;
        P.parent = X;
        P.left = two;
        if(two!=null)
            two.parent = P;
    }

    private void big_right_rotation(Node<TKeyType, TDataType> par, Node<TKeyType, TDataType> G, Node<TKeyType, TDataType> X, Node<TKeyType, TDataType> three){
        if(par == null)
            head = X;
        else{
            if(par.left == G)
                par.left = X;
            else
                par.right = X;
        }
        X.parent = par;
        X.right = G;
        G.parent = X;
        G.left = three;
        if(three!=null)
            three.parent = G;
    }

    private void big_left_rotation(Node<TKeyType, TDataType> par, Node<TKeyType, TDataType> G, Node<TKeyType, TDataType> X, Node<TKeyType, TDataType> three){
        if(par == null)
            head = X;
        else{
            if(par.left == G)
                par.left = X;
            else
                par.right = X;
        }
        X.parent = par;
        X.left = G;
        G.parent = X;
        G.right = three;
        if(three!=null)
            three.parent = G;
    }

    private boolean add(Node<TKeyType, TDataType> node, Node<TKeyType, TDataType> x){
        int ch = (node.key).compareTo(x.key);
        if(ch<0){
            if(node.left != null){
                return add(node.left, x);
            }
            else{
                node.left = x;
                x.parent = node;
                return false;
            }
        }
        else if(ch>0){
            if(node.right != null){
                return add(node.right, x);
            }
            else{
                node.right = x;
                x.parent = node;
                return false;
            }
        }
        else{
            node.data = x.data;
            return true;
        }
    }

    public void push(TKeyType key, TDataType data){
        boolean balance = false;
        Node<TKeyType, TDataType> x = new Node<TKeyType, TDataType>(key, data);
        if(head == null)
            head = x;
        else
            balance = add(head, x);


        while(balance != true){
            if(head == x){ // х - корень?
                x.color = false;
                balance = true;
            }
            else{
                Node<TKeyType, TDataType> P = x.parent;
                if(!P.color){ //родитель х - чёрный?
                    balance = true;
                }
                else{
                    Node<TKeyType, TDataType> G = P.parent;
                    Node<TKeyType, TDataType> U;
                    if(G.left == P) //инициализируем U
                        U = G.right;
                    else
                        U = G.left;
                    
                    if(U!=null && U.color==true){//P и U красные
                        U.color = false;
                        P.color = false;
                        G.color = true;

                        x = G;
                    }
                    else{//P красный, U чёрный
                        if(P == G.left && x == P.right) //x внутренний
                            left_rotation(G, P, x, x.left);
                        else if(P == G.right && x == P.left)
                            right_rotation(G, P, x, x.right);


                        if(G.right == U)
                            big_right_rotation(G.parent, G, G.left, (G.left).right);
                        else
                            big_left_rotation(G.parent, G, G.right, (G.right).left);

                        G.color = true;
                        (G.parent).color = false;
                        balance = true;
                    }
                }
            }
        }
    }

    public void search(TKeyType key){
        Node<TKeyType, TDataType> node = head;
        while(node!=null && key.compareTo(node.key)!=0){
            if(key.compareTo(node.key) > 0)
                node = node.left;
            else
                node = node.right;
        }

        if(node!=null && key.compareTo(node.key) == 0)
            System.out.println(node.data);
        else
            System.out.println("not exist");
        
    }

    private int height(Node<TKeyType, TDataType> node){
        int h_left=0, h_right=0;
        if(node == null)
            return 0;
        
        if(node.left != null)
            h_left = height(node.left);
        
        if(node.right != null)
            h_right = height(node.right);
        
        return Math.max(h_left, h_right)+1;
    }

    public int height_three(){
        return height(head);
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private void print(Node<TKeyType, TDataType> node, int depth){
        if (node.right != null){
            print(node.right, depth+1);
        }

        String space = new String(new char[depth*3]).replace('\0', ' ');
        if(node.color)
            System.out.println(ANSI_RED + space + "{" + node.data+"}" + ANSI_RESET);
        else
            System.out.println(space + "{" + node.data+"}");

        if (node.left != null){
            print(node.left, depth+1);
        }
    }
    public void PrintTree(){
        if(this.head!=null)
            this.print(this.head,0);
        else
            System.out.println("Unfortunately the tree is empty :(");
    }
}