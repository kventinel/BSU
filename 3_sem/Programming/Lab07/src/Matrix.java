import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

/**
 * Created by alex on 11.11.16.
 * Проверить, можно ли перемножить две матрицы. Если это возможно, то найти произведение.
 */
public class Matrix {
    public static void main(String[] args) {
        try{
            Scanner in = new Scanner(new File("src/input"));
            int n1 = in.nextInt();
            int m1 = in.nextInt();
            double [][] matrix1 = new double[n1][m1];
            for(int i = 0; i < n1; ++i) {
                for(int j = 0; j < m1; ++j) {
                    matrix1[i][j] = in.nextDouble();
                }
            }
            int n2 = in.nextInt();
            int m2 = in.nextInt();
            double [][] matrix2 = new double[n2][m2];
            for(int i = 0; i < n2; ++i) {
                for(int j = 0; j < m2; ++j) {
                    matrix2[i][j] = in.nextDouble();
                }
            }
            if(m1 != n2) {
                System.out.println("Matrix not a join");
                return;
            }
            double [][] matrixAns = new double [n1][m2];
            for(int i = 0; i < n1; ++i) {
                for(int j = 0; j < m2; ++j) {
                    matrixAns[i][j] = 0;
                    for(int k = 0; k < m1; ++k) {
                        matrixAns[i][j]+=matrix1[i][k]*matrix2[k][j];
                    }
                    System.out.print(matrixAns[i][j] + " ");
                }
                System.out.print("\n");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Expected valid size and matrix");
        } catch (Exception e) {
            System.out.println("Unexpected exception");
        }
    }
}
