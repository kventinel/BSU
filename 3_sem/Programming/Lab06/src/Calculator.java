import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by Rak Alexey on 1.11.16.
 */

public class Calculator {
    public static void main(String[] args) {
        try {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg);
            }
            StringTokenizer st = new StringTokenizer(sb.toString(), "()+-*/", true);
            Vector<Lexem> lexems = new Vector<Lexem>();
            Stack<Lexem> operations = new Stack<Lexem>();
            int count_braces = 0;
            while (st.hasMoreTokens()) {
                Lexem temp = new Lexem(st.nextToken());
                if (temp.lexem == Lexem.lex.num) {
                    lexems.add(temp);
                } else {
                    if (temp.num == 3) {
                        if (temp.lexem == Lexem.lex.open) {
                            operations.push(temp);
                            ++count_braces;
                        } else {
                            --count_braces;
                            if (count_braces < 0) {
                                throw new MyExeption("Too much close braces");
                            }
                            while (!operations.empty()) {
                                if (operations.peek().lexem != Lexem.lex.open) {
                                    lexems.add(operations.peek());
                                    operations.pop();
                                } else {
                                    operations.pop();
                                    break;
                                }
                            }
                        }
                    } else {
                        while (!operations.empty()) {
                            if (operations.peek().num <= temp.num) {
                                lexems.add(operations.peek());
                                operations.pop();
                            } else {
                                break;
                            }
                        }
                        operations.push(temp);
                    }
                }
            }
            if (count_braces != 0) {
                throw new MyExeption("Too much open braces");
            }
            while (!operations.empty()) {
                lexems.add(operations.peek());
                operations.pop();
            }
            for (int i = 0; i < lexems.size(); ++i) {
                if (lexems.elementAt(i).lexem == Lexem.lex.num) {
                    operations.push(lexems.elementAt(i));
                } else {
                    if (operations.size() >= 2) {
                        double second_operand = operations.peek().num;
                        operations.pop();
                        double first_operand = operations.peek().num;
                        operations.pop();
                        switch (lexems.elementAt(i).lexem) {
                            case plus:
                                first_operand += second_operand;
                                break;
                            case minus:
                                first_operand -= second_operand;
                                break;
                            case product:
                                first_operand *= second_operand;
                                break;
                            case div:
                                first_operand /= second_operand;
                                break;
                            default:
                                throw new MyExeption("Bad lexem");
                        }
                        operations.push(new Lexem(first_operand, Lexem.lex.num));
                    } else {
                        throw new MyExeption("Too much operations");
                    }
                }
            }
            if(operations.size() != 1) {
                throw new MyExeption("Too much numbers");
            }
            System.out.println(operations.peek().num);
        } catch (MyExeption e) {
            System.out.println(e.getMessage());
        }
    }
}

class MyExeption extends Exception {
    public MyExeption(String str) {
        super(str);
    }
};
