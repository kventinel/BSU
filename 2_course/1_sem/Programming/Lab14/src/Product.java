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

    void setName(String name) {
        _name = name;
    }

    void setCountry(String country) {
        _country = country;
    }

    void setCount (int count) {
        _count = count;
    }
}
