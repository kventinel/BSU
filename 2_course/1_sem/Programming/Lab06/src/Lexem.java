/**
 * Created by alex on 1.11.16.
 */
public class Lexem {
    enum lex {plus, minus, product, div, open, close, num}
    lex lexem;
    double num;
    Lexem(String s) {
        switch(s.charAt(0)) {
            case '+':
                lexem = lex.plus;
                num = 1;
                break;
            case '-':
                lexem = lex.minus;
                num = 1;
                break;
            case '*':
                lexem = lex.product;
                num = 2;
                break;
            case '/':
                lexem = lex.div;
                num = 2;
                break;
            case '(':
                lexem = lex.open;
                num = 3;
                break;
            case ')':
                lexem = lex.close;
                num = 3;
                break;
            default:
                lexem = lex.num;
                num = Double.parseDouble(s);
        }
    }
    Lexem(double num_, lex lexem_) {
        num = num_;
        lexem = lexem_;
    }
}
