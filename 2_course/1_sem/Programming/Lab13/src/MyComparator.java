import java.util.Comparator;

/**
 * Created by Rak Alexey on 12/21/16.
 */

class MyComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o1.compareTo(o2);
    }
}
