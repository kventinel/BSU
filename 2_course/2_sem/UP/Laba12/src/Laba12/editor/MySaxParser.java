package Laba12.editor;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MySaxParser extends DefaultHandler {
    private String thisElement;
    private int amountOfStudents;
    private int amountOfBadStudents, amountOfBadMarks;
    private int sumOfMarks;
    private Attributes attsOfTag;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        super.startElement(namespaceURI, localName, qName, atts);
        attsOfTag = atts;
        thisElement = qName;
        if (qName.equals("StudentList")) {
            amountOfStudents = 0;
            amountOfBadStudents = 0;
            sumOfMarks = 0;
        }
        if (qName.equals("Student")) {
            amountOfBadMarks = 0;
            amountOfStudents++;
        }
        if(qName.equals("TestPass"))
            if(!Boolean.parseBoolean(atts.getValue("passed")))
                amountOfBadMarks++;
        if(qName.equals("ExamPass"))
            if(Integer.parseInt(atts.getValue("mark"))<4)
                amountOfBadMarks++;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (thisElement.equals("ExamPass") && (new String(ch, start,length)).equals("OS"))
            sumOfMarks+=Integer.parseInt(attsOfTag.getValue("mark"));

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("Student")) {
            if(amountOfBadMarks>0)
                amountOfBadStudents++;
        }
    }

    public int getAmountOfStudents(){
        return amountOfStudents;
    }

    public int getAmountOfBadStudents(){
        return amountOfBadStudents;
    }

    public double getAvgMark(){
        return (double)sumOfMarks/amountOfStudents;
    }
}
