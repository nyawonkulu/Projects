import java.util.*;

class Iris{
   Iris query;
   int index;
   double xone, xtwo, xthree, xfour, output;
   String  type; 
   
   Iris(Iris query){
      this.query=query;
   }

   		//attributes of each flower
   Iris(String[]div){
      xone = Double.parseDouble(div[0]);
      xtwo = Double.parseDouble(div[1]);
      xthree = Double.parseDouble(div[2]);
      xfour = Double.parseDouble(div[3]);
      type = div[4];
   }
   
   Iris(int index, String[]div){
      this.index = index;
      xone = Double.parseDouble(div[0]);
      xtwo = Double.parseDouble(div[1]);
      xthree = Double.parseDouble(div[2]);
      xfour = Double.parseDouble(div[3]);
      type = div[4];
   }
      
   public double getXONE()
   {
      return xone;
   }
	
   public double getXTWO()
   {
      return xtwo;
   }
	
   public double getXTHREE()
   {
      return xthree;
   }
	
   public double getXFOUR()
   {
      return xfour;
   }		
  
   public int getindex()
   {
      return index;
   } 
   
   public String getType()
   {
      return type;
   }
   
   public String toString()
   {
      return " " + xone + ", " + xtwo + ", " + xthree + ", " + xfour + ", " + type; 
   }     
}
//Iris class, instance of a flower
