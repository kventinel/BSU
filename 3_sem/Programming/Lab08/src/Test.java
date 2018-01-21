import java.util.Scanner;

/**
 * Created by Rak Alexey on 11.11.16.
 */
public class Test {
    public static void main(String[] args) {
        BinaryTree<Integer> bt = new BinaryTree<Integer>();
//        BinaryTree<Complex> bt = new BinaryTree<Complex>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int u = in.nextInt();
            switch (u) {
                case 1:
                    bt.insert(in.nextInt());
//                    bt.insert(new Complex(in.nextDouble(), in.nextDouble()));
                    break;
                case 2:
                    Node n = bt.find(in.nextInt());
                    if(n == null) {
                        System.out.println("haven't this element");
                    } else {
                        System.out.println(n.getKey());
                    }
//                    bt.find(new Complex(in.nextDouble(), in.nextDouble()));
                    break;
                case 3:
                    bt.erase(in.nextInt());
//                    bt.erase(new Complex(in.nextDouble(), in.nextDouble()));
                    break;
                case 4:
                    bt.rootLeftRight(bt.getRoot());
                    break;
                case 5:
                    bt.leftRightRoot(bt.getRoot());
                    break;
                case 6:
                    bt.leftRootRight(bt.getRoot());
                    break;
                default:
                    System.out.println("Bad command");
            }
        }
    }
}
