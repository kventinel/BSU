/**
 * Created by root on 12/19/16.
 */
class Product {
    private String _name;
    private String _country;
    private int _count;

    Product() {
        this("", "", 0);
    }

    Product(String name) {
        this(name, "", 0);
    }

    Product(String name, String country) {
        this(name, country, 0);
    }

    Product(String name, String country, int count) {
        _name = name;
        _country = country;
        _count = count;
    }

    @Override
    public String toString() {
        return _name + " " + _country + " " + _count;
    }

    String getName() {
        return _name;
    }

    String getCountry() {
        return _country;
    }

    int getCount() {
        return _count;
    }

    int compareTo(Product other) {
        int compareResult = _name.compareTo(other._name);
        if (compareResult != 0) {
            return compareResult;
        }
        compareResult = _country.compareTo(other._country);
        if (compareResult != 0) {
            return compareResult;
        }
        return Integer.compare(_count, other._count);
    }
}
