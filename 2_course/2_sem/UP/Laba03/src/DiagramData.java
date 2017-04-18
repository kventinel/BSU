import java.util.ArrayList;

public class DiagramData {

	private double values[];
	private String names[];
	private int count;

	public DiagramData(double tvalues[], String tnames[], int tcount) {
		count = tcount;
		values = tvalues;
		names = tnames;

		for (int i = 0; i < count - 1; i++)
			for (int j = 0; j < count - 1; j++)
				if (values[j] > values[j + 1]) {
					double temp = values[j + 1];
					values[j + 1] = values[j];
					values[j] = temp;
					String str = names[j + 1];
					names[j + 1] = names[j];
					names[j] = str;
				}
	}
    public String getName(int i){
    	return names[i];
    }
	public ArrayList<Double> angles() {
		ArrayList<Double> temp = new ArrayList<Double>();
		double sum = 0;
		for (double x : values) {
			sum += x;
		}
		for (double x : values) {
			temp.add(Math.toRadians(x * 360 / sum));
		}
		return temp;

	}
}
