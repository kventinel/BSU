public class Sum {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new NumberOfArgumentsExeption("Number of arguments isn't 2");
            } else {
                double x = Double.parseDouble(args[0]);
                double e = Double.parseDouble(args[1]);
                double sum = sum(x, e);
                System.out.println(sum);
            }
        } catch (NoConvergeExeption e) {
            System.out.println(e.getMessage());
        } catch (NumberOfArgumentsExeption e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    static double sum(double x, double e) throws NoConvergeExeption{
        double sum = 0;
        double element = x * x / 2;
        int k = 1;
        while (element >= e && k < 10000) {
            sum += element;
            ++k;
            element = element * x * x / 2 / k;
        }
        if(element >= e) {
            throw new NoConvergeExeption("Sum no converge");
        } else {
            return sum;
        }
    }
}

class NumberOfArgumentsExeption extends Exception {
    public NumberOfArgumentsExeption(String str) {
        super(str);
    }
};

class NoConvergeExeption extends Exception {
    public NoConvergeExeption(String str) {
        super(str);
    }
};
