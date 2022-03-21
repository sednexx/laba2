import java.util.Random;
import java.util.Scanner;
//import java.util.Math;

public class test_rb{ 
    public static void main(String[] args) {
        RB_tree<Integer, Integer> tree = new RB_tree<>();
        Random random = new Random();

        System.out.println("Check empty: " + tree.empty());
        int number = 20;
        
        for(int i=0; i<number; i++){
            int buf = 1 + random.nextInt(number*4);
            tree.push(buf, buf);
        }

        System.out.println("The number of elements is: "+ number);
        System.out.println("Check empty: " + tree.empty());
        System.out.println("\nСhecking the total height of a tree: h = " + tree.height_three() + "; h <= " + (2*Math.log(number+1)/Math.log(2)) + "\n\n");
        tree.PrintTree();

        System.out.println("\n\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a key to search for a value in the tree");
        number = sc.nextInt();
        System.out.print("You value: ");
        tree.search(number);

        tree.delete();
        System.out.println("Check delete all: " + tree.empty());        
    }
}