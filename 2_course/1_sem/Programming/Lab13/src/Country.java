/**
 * Created by Rak Alexey on 12/20/16.
 */
class Country {
    private String _country;
    private int _count;

    Country() {
        this("", 0);
    }

    Country(String country) {
        this(country, 0);
    }

    Country(String country, int count) {
        _country = country;
        _count = count;
    }

    @Override
    public String toString() {
        return _country + " " + _count;
    }
}
