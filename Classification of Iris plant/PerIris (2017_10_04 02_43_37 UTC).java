import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import java.io.PrintWriter;
import java.lang.System.*;

class PerIris
{
   static double LEARNING_RATE = -1;
   static int MAX_ITER = 100;
   static double theta = -1;   
   static int[] outputs = new int[100];   
   static double [] weights = {0.0, 0.0, 0.0, 0.0, 0.0};
   static ArrayList< double[]> arrW= new ArrayList<double[]>();
   static Iris []iris = new Iris[100];   
   static int index = 0;
   static int iteration = 0;  
   static double bias = 0;
   static DecimalFormat df = new DecimalFormat("#.####");  
   static ArrayList<Integer> random = new ArrayList<Integer>(20);     
   static int acc = 0;  
   static boolean show = false;


   public static void ReadT(Scanner input)
   {     
      while(input.hasNext())
      {
         String readin = input.nextLine();       
         String[]div = readin.split(",");            
         iris[index] = new Iris(div);                                
         
         switch(iris[index].getType())
         {  
            case "Iris-setosa":outputs[index]= 0;
               break;            
            case "Iris-virginica":outputs[index]= 1;
               break;
            case "Iris-versicolor":outputs[index]= -1;
               break;            
         }
         index++;
      }                       
   }
   
   public static double xvalue(int k, int z)
   {  
      double X = 0;
      
      switch(z)
      {
         case 0: X = 1;
            break;
         case 1:X = iris[k].getXONE();
            break;
         case 2:X = iris[k].getXTWO();
            break;
         case 3:X = iris[k].getXTHREE();   
            break;
         case 4:X = iris[k].getXFOUR(); 
            break;      
      }
      return X;
   }         
   
   public static void calcWeight()
   {
      int iteration = 0;      
      double localError, globalError;
      int output;            
   
      for(int k=0; k<20; k++)
      {
         int h = 0;
         
         do{
            h = (int)(Math.random()*100);    
         }while(random.contains(h));
      
         random.add(h);
      }
                   
      do{
         iteration++;         
         globalError = 0;       
         
         for(int k=0; k<index; k++)
         {                      
            if(!random.contains(k))
            {                                        
               int y_value = calculateY(k);               
               localError = outputs[k] - y_value;
                           
               if(y_value!=outputs[k])
               {                  
                  for(int I=0; I<5; I++)
                  {                                                      
                     weights[I] += xvalue(k,I) * outputs[k] * LEARNING_RATE;
                  }
                  showW();
                  bias += outputs[k]*LEARNING_RATE;                
               }
               globalError += Double.parseDouble(df.format(localError*localError));
               System.out.printf("%5d: ",k);
               showW();
            }         
            --MAX_ITER;            
         }   
      }while(MAX_ITER!=0 && globalError != 0);        
      
      System.out.printf("\nIteration %2d : RMSE = %2f", iteration,Math.sqrt(globalError/index));           
      System.out.println("\n\n======= Decision boundary equation =======");
      System.out.printf("%-3f*w0 + %3f*w1 + %3f*w2 + %3f*w3 + %3f*w4 = 0\n",
         weights[0],
         weights[1],
         weights[2],
         weights[3],
         weights[4]
         );                             
   }
   
   public static void showW()
   {
      System.out.printf("%-10f %10f %10f %10f %10f \n",
         (weights[0]),
         (weights[1]),
         (weights[2]),
         (weights[3]),
         (weights[4])
         );      
   }
      
   public static int calculateY(int k)
   {        
      double y_in = weights[0];
   
      for(int i=1; i<5; i++)      
         y_in+= weights[i]*xvalue(k,i);       
   
      if(y_in>theta)
         return 1;
      else
         return 0;   
   }
   
   public static void classC(int n)
   {
      int Y = calculateY(n);
   
      System.out.printf("\nFlower %2d : \n", n);
      if(Y==outputs[n])
      {         
         System.out.printf("In class : %2d\n", Y);         
         acc+=1;
      }      
      else
         System.out.println("Not in class");      
   }
   
   public static void ReadTest()
   {      
      System.out.print("\n========== Testing Set ==========");
            
      for(int j=0; j<20; j++)      
         classC(random.get(j));   
            
      System.out.printf("\nTesting accuracy: %2d%1c", (acc*100)/20, '%');
   }
        
   public static void main(String[] args)throws Exception
   {      
      String dataset[] = new  String[]{"datasetA.txt", "datasetA.txt"};
      Scanner file = new Scanner(System.in);      
      Scanner input = new Scanner(System.in);   
      int opt = -1;

      while(opt!=0 && opt!=1)
      {
         System.out.println("Please Enter file name [0] datasetA | [1] datasetB:");         
         opt = input.nextInt();           
      }      
            
      if(opt == 0)
         file = new Scanner(new File("datasetA.txt"));
      else if(opt == 1)
         file = new Scanner(new File("datasetA.txt"));                  
      
      do
      {
         System.out.println("Please Enter value of learning rate [0,1]");      
         LEARNING_RATE= input.nextDouble();      
      }
      while(LEARNING_RATE<0 && LEARNING_RATE>1);
      
      do
      {
         System.out.println("Please Enter value of theta [0,1]");      
         theta= input.nextDouble();      
      }
      while(theta<0 && theta>1);
      
      long startTime = System.currentTimeMillis();   
      long total = 0;
      for (int i = 0; i < 10000000; i++) {
         total += i;
      }
      
      ReadT(file);                     
      calcWeight();            
      ReadTest();
   	
      System.out.println("\n========== Values of the Weights after training ==========");               
      for(int i=0; i<weights.length; i++)     
         System.out.printf("w%d:%2s\t", i, df.format(weights[i])); 
         
      System.out.printf("\n\nBias : %2f", bias);           
      
      long stopTime = System.currentTimeMillis();
      long elapsedTime = stopTime - startTime;
      System.out.println(elapsedTime);
   }
}

class Compare implements Comparator<String[]>{
   
   public int compare(String[] flower1, String[] flower2)
   {
      if(flower1[4].compareTo(flower2[4])<0)
         return -1;
      else if(flower1[4].compareTo(flower2[4])>0)
         return 1;
      else
         return 0;          
   }
}
