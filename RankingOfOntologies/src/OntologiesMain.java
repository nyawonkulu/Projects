import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 213539359
 */
public class OntologiesMain {
   /**
    * @param args the command line arguments
    * @throws java.lang.Exception
    */   
   
  //** WEIGHTED NORMALIZED MATRIX **//
   public static double [][] CALWNM(double dMatrix[][], double wMatrix[]){
        
      double wnMatrix[][] = new double[dMatrix.length][dMatrix[0].length];
        
      for(int i = 0; i < dMatrix.length; i++) {
         for(int j = 0; j < dMatrix[0].length; j++){
            wnMatrix[i][j] = dMatrix[i][j] * wMatrix[i];
         }
      }
      return wnMatrix;
   }
  
   //** CALCULATING WEIGHTED MATRIX **//  
   public static double[] WNORM(double matrix[][]) {
   
      double weight[] = new double[matrix.length];
      double sum1[] = new double [matrix[0].length];
      
      /* SUMMATION OF (A)kj */
      for(int j = 0; j < matrix[0].length; j++){
         double sum2 = 0;
         for(int k = 0; k < matrix.length; k++) {
            sum2 += matrix[k][j];
         }        
         sum1[j] = sum2;        
      }   
      /* SUMMATION OF (A)ij DIVIDED BY SUMMATION OF (A)kj */ 
      for(int i = 0; i < matrix.length; i++){
         double sum3 = 0;
         for(int j = 0; j < matrix[0].length; j++){
            sum3 += matrix[i][j]/sum1[j];
         }
         weight[i] = sum3/matrix.length;      
      }      
    
      return weight;
   }
     
   //** CALCULATING NORMALIZED MATRIX **//
   public static double[][] NORMDM(double matrix[][]) {
   
      double normM[][] = new double[matrix.length][matrix[0].length];
      double sum1[] = new double[matrix[0].length];
      
      /* SUMMATION OF THE DENOMENATOR */             
      for(int j = 0; j < matrix[0].length; j++){         
         double sum2 = 0;
         for(int i = 0; i < matrix.length; i++){
            sum2 += Math.pow(matrix[i][j], 2);                     
         }
         sum1[j] = Math.sqrt(sum2);
      }
      
      /* CALCULATING VALUES OF (R)ij */
      for(int i = 0; i < matrix.length; i++){
         for(int j = 0; j < matrix[0].length; j++){
            normM[i][j] = matrix[i][j]/sum1[j];
         }
      }   
      
      return normM;
   }

   //************************ MAIN ************************// 
   public static void main(String[] args) throws Exception {
      // TODO code application logic here
      File QualityMetrics = new File("QualityMetrics.txt");
      BufferedReader fileRead = new BufferedReader(new FileReader(QualityMetrics));
   
      String read;
      int rows = 23, columns = 11;
      double ontologies[][] = new double[rows][columns];
      int outter = 0;
   
      while ((read = fileRead.readLine()) != null && outter < rows) {
         read = read.replaceAll("	", " ");
         read = read.replaceAll(",", ".");
         String line[] = read.trim().split("\\s+");
         line = Arrays.copyOfRange(line, 1, line.length);
      
         for (int inner = 0; inner < line.length; inner++) {
            ontologies[outter][inner] = Double.parseDouble(line[inner]);
         }
         outter++;
      }
      
      /* DECISION MATRIX */ 
      double decMatrix[][] = NORMDM(ontologies);
      /* WEIGHTED MATRIX */
      double weightedM[] = WNORM(decMatrix);
      
      double weights = 0;
      int r = 0;
      for(double p: weightedM){   
         weights += p;
         System.out.printf("%-15f", p);
         r++;
      }
      
      System.out.println(r);
         
         
      System.out.printf("Weight sum = %2f \n\n", weights);
      
      /* WEIGHTED NORMALIZED MATRIX */
      double weightNormM [][] = CALWNM(decMatrix, weightedM);
      
      double sum = 0;      
      for (double dec[] : weightNormM) {
      
         for (double k : dec) {
            System.out.printf("%-12f", k);
            sum += k;
         }       
         System.out.println();
      }
      System.out.printf("\n %f",sum);         
   }
        
}//*** CLASS  ***//

