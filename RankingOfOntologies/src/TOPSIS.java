/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 213539359
 */
public class TOPSIS {
    private int rows, columns;
    private double decisionMatrix[][];
    
    public TOPSIS(int rows, int columns){
        this.rows = 23;
        this.columns = 11;
        this.decisionMatrix = new double[rows][columns];
    }
    
    public static double[][] NORMDM(double matrix[][]) {
        double newMatrix[][] = new double[matrix.length][matrix[0].length];
        double sum = 0;

        for (int col = 0; col < matrix[0].length; col++) {
            for (int row = 0; row < matrix.length; row++) {
                sum += Math.pow(matrix[row][col], 2);
            }
            sum = Math.sqrt(sum);

            for (int row = 0; row < matrix.length; row++) {
                newMatrix[row][col] = matrix[row][col] / sum;
            }
        }//FOR
        return newMatrix;
    }//NORMDM
    
    public static double[][] WNORMM(double matrix[][]){
        double newMatrix[][] = new double[matrix.length][matrix[0].length];
        return newMatrix;
    }

    
}
