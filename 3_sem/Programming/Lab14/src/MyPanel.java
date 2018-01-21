import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Rak Alexey on 12/19/16.
 */

class MyPanel extends JPanel {
    private JList<Product> list1;
    private JList<Country> list2;
    private JPanel panel1;
    private JScrollPane scrollPane2;
    private JScrollPane scrollPane1;
    private JTextField textField1;
    private ArrayList<Product> _data;
    private ArrayList<Country> _requestData;

    MyPanel() {
        super();
        panel1.setPreferredSize(new Dimension(1000, 750));
        scrollPane1.setPreferredSize(new Dimension(300, 750));
        scrollPane2.setPreferredSize(new Dimension(300, 750));
        setVisible(true);

        _data = new ArrayList<>();

        textField1.addActionListener(new Request());

        add(panel1);
    }

    void setData(ArrayList<Product> data) {
        _data.clear();
        Iterator<Product> iterator = data.iterator();
        while(iterator.hasNext()) {
            _data.add(iterator.next());
        }
        Product[] listData = new Product[]{};
        listData = _data.toArray(listData);
        list1.setListData(listData);
    }

    void addElement(Product newProduct) {
        _data.add(newProduct);
        Product[] listData = new Product[]{};
        listData = _data.toArray(listData);
        list1.setListData(listData);
    }

    class Request implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String request = textField1.getText();
            int count = 0;
            Iterator<Product> thisProduct = _data.listIterator(0);
            TreeMap<String, Integer> countries = new TreeMap<>();
            while (thisProduct.hasNext()) {
                Product element = thisProduct.next();
                if (element.getName().equals(request)) {
                    if (countries.containsKey(element.getCountry())) {
                        Map.Entry<String, Integer> country = countries.floorEntry(element.getCountry());
                        countries.replace(country.getKey(), country.getValue(), country.getValue() + element
                                .getCount());
                    } else {
                        countries.put(element.getCountry(), element.getCount());
                    }
                }
            }
            Set<Map.Entry<String, Integer>> set = countries.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
            _requestData = new ArrayList<>();
            while(iterator.hasNext()) {
                Map.Entry<String, Integer> thisCountry = iterator.next();
                _requestData.add(new Country(thisCountry.getKey(), thisCountry.getValue()));
            }
            Country[] listData = new Country[]{};
            listData = _requestData.toArray(listData);
            list2.setListData(listData);
        }
    }

    ArrayList<Country> getRequestData() {
        return _requestData;
    }
}
