import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.*;
import  java.text.DecimalFormat;

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
   
  //---------------- WEIGHTED NORMALIZED MATRIX ----------------//
   public static double [][] CALWNM(double dMatrix[][], double wMatrix[]){
        
      double wnMatrix[][] = new double[dMatrix.length][dMatrix[0].length];
        
      for(int i = 0; i < dMatrix.length; i++) {
         for(int j = 0; j < dMatrix[0].length; j++){
            wnMatrix[i][j] = dMatrix[i][j] * wMatrix[i];
         }
      }
      return wnMatrix;
   }
  
   //---------------- CALCULATING WEIGHTED MATRIX ----------------//  
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
     
   //---------------- CALCULATING NORMALIZED MATRIX ------------------//
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
   
   //---------------- PRINT OUT THE MATRIXES ----------------//
   public static void DISPLAY(double [][]matrix){   
     
      for (double dec[] : matrix) {
      
         for (double k : dec) {
            System.out.printf("%-12f", k);
         }       
         System.out.println();
      }
      System.out.println();
   }  
   
   public static void DISPLAY(double []matrix){   
      
      double weights = 0;
   
      for(double p: matrix){   
         weights += p;
         System.out.printf("%-15f", p);
      }            
      System.out.printf("\nWeight sum = %2f \n\n", weights);   
   }
   
   public static void DISPLAY(List<List<Double>> matrix){      
      for(List<Double> p: matrix){
         for(Double k: p){
            System.out.printf("%25s %20f"," ", k);
         }         
         System.out.println();
      }   
      System.out.println("\n");
   }      
   //---------------- POSITIVE AND NEGATIVE IDEAL SOLUTIONS ----------------//
   public static List<List<Double>> PANIS(double matrix[][] ){
      
      double min = matrix[0][0], max = matrix[0][0];
      List<List<Double>> negpos = new ArrayList<>();
      
      for(int j = 0; j < matrix[0].length; j++){      
         for(int i = 1; i < matrix.length; i++){
            if(matrix[i][j] > max)
               max = matrix[i][j];
            else if(matrix[i][j] < min)
               min = matrix[i][j];                                                             
         }
         negpos.add(j,Arrays.asList(min,max));      
         max = min = 0;
      } 
      return negpos;
   } 
   //---------------- CALCULATE THE DISTANCE FROM POSITIVE AND NEGATIVE IDEAL SOLUTIONS ----------------//
   public static List<List<Double>> DFPNIS(List<List<Double>> idsmatrix, double wmatrix [][]){
      List<List<Double>> dmatrix = new ArrayList<>();
      double distN = 0, distP = 0;     
   
      for(int i = 0; i < wmatrix.length; i++){      
         for(int j = 0; j < wmatrix[0].length; j++){
            
            distN += Math.pow(wmatrix[i][j] - idsmatrix.get(j).get(0),2);
            distP += Math.pow(wmatrix[i][j] - idsmatrix.get(j).get(1),2);               
         }      
         dmatrix.add(i, Arrays.asList(Math.sqrt(distN),Math.sqrt(distP)));
      }
   
      return dmatrix;
   }
   
   //---------------- CALCULATE THE RELATIVE CLOSENESS OF ALTERNATIVE TO IDEAL SOLUTIONS ----------------//
   public static double[] RCAIS(List<List<Double>> matrix){
      double[] closeness = new double[23];
     DecimalFormat df = new DecimalFormat("#.#####");
      int i = 0;
      for(List<Double> list: matrix){
         closeness[i] = list.get(0) / (list.get(0) + list.get(1));
         System.out.printf("%70d %10s \n", i, df.format(closeness[i]));
         i++;
      }
      return closeness;
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
      System.out.printf("%15s---------------------------------------- DECISION MATRIX ----------------------------------------\n","");
      DISPLAY(decMatrix);
      
      /* WEIGHTED MATRIX */
      double weightedM[] = WNORM(decMatrix);
      System.out.printf("%10s---------------------------------------- WEIGHTED MATRIX ----------------------------------------\n","");
      DISPLAY(weightedM);
      
      /* WEIGHTED NORMALIZED MATRIX */
      double weightNormM [][] = CALWNM(decMatrix, weightedM);
      System.out.printf("%10s---------------------------------------- WEIGHTED NORMALIZED MATRIX ----------------------------------------\n","");
      DISPLAY(weightNormM);          
     
      /* POSITIVE AND NEGATIVE IDEAL SOLUTIONS */
      List<List<Double>> minMax = PANIS(weightNormM);
      System.out.printf("%10s---------------------------------------- POSITIVE AND NEGATIVE IDEAL SOLUTIONS ----------------------------------------\n","");
      System.out.printf("\n%25s %18s %44s \n\n"," ", "MIN","MAX");
      DISPLAY(minMax);
      
      /* CALCULATE THE DISTANCE FROM POSITIVE AND NEGATIVE IDEAL SOLUTIONS */
      List<List<Double>> disPN= DFPNIS(minMax, weightNormM);
      System.out.printf("%10s---------------------------------------- DISTANCE FROM POSITIVE AND NEGATIVE IDEAL SOLUTIONS ----------------------------------------\n","");
      System.out.printf("\n%25s %18s %44s \n\n"," ", "d-","d+");
      DISPLAY(disPN);
     
      /* CALCULATE THE RELATIVE CLOSENESS OF ALTERNATIVE TO IDEAL SOLUTIONS */
      System.out.printf("%10s---------------------------------------- RELATIVE CLOSENESS OF ALTERNATIVE TO IDEAL SOLUTIONS ----------------------------------------\n\n","");
      double closeness [] = RCAIS(disPN);
   }      
}//*** CLASS  ***//



