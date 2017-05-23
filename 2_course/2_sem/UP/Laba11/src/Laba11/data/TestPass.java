package Laba11.data;

public class TestPass extends AbstractPass {
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    private boolean passed;

    @Override
    public boolean isPassed() {
        return passed;
    }

    public TestPass(String name, String examinator, boolean passed) {
        super(name, examinator);
        this.passed = passed;
    }

    public String toXML() {
        return "\t\t<TestPass examinator=\"" +
                examinator +
                "\" passed=\"" +
                passed +
                "\">" +
                name +
                "</TestPass>";
    }
}
