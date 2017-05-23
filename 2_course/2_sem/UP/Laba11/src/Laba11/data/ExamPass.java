package Laba11.data;

public class ExamPass extends AbstractPass {
    @Override
    public String toXML() {
        return "\t\t<ExamPass examinator=\"" +
                examinator +
                "\" mark=\"" +
                mark +
                "\">" +
                name +
                "</ExamPass>";
    }

    public ExamPass(String name, String examinator, int mark) {
        super(name, examinator);
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    private int mark;

    @Override
    public boolean isPassed() {
        return mark >= 4;
    }
}
