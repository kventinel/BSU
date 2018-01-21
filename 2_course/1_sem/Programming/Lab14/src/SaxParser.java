import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Rak Alexey on 12/20/16.
 */
class SaxParser extends DefaultHandler {
    private String thisElement;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
        if (!qName.equals("product")) {
            return;
        }
        products.add(new Product(atts.getValue("name"), atts.getValue("country"),
                Integer.parseInt(atts.getValue("count"))));
    }

    public ArrayList<Product> getResult() {
        return products;
    }
}
