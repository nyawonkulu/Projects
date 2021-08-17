/*  for (int i = 0; i < matrix.length; i++) {
         double sum1 = 0;
      
         for (int j = 0; j < matrix[0].length; j++) {
            double sum2 = 0;
         
            for (int k = 0; k < matrix.length; k++) {
               sum2 += matrix[k][j];
            }
         
            sum1 += matrix[i][j] / sum2;
         }
      
         weight[i] = sum1 / matrix.length;
      }*/
   
        /*for (int col = 0; col < matrix[0].length; col++) {
            double sum = 0;
            for (int row = 0; row < matrix.length; row++) {
                sum += Math.pow(matrix[row][col], 2);
            }
            sum = Math.sqrt(sum);

            for (int row = 0; row < matrix.length; row++) {
                normArrayList[row][col] = matrix[row][col] / sum;
            }
        }//FOR*/


/*   
 for (ArrayList<Double> row : normMatrix) {
 for (Double element : row) {
 sum += Math.pow(element, 2);
                
 }
 }
 sum = Math.sqrt(sum);

 for (ArrayList<Double> row : normMatrix) {
 ArrayList<Double> calculated = new ArrayList<>();

 for (Double element : row) {
 double valueR = element / sum;
 calculated.add(valueR);
 }
 normArrayList.add(calculated);
 }
 */
/*for (Double arr[] : ontology) {
 for (Double ele : arr) {
 System.out.printf("%2f ", ele);
 }
 System.out.println();
 }*/
/*
 for (double dec[] : normArrayList) {

 for (double k : dec) {
 System.out.printf("%12f", k);
 //sum += k;
 }
 System.out.println();
 }*/
/*
 for (double dec[] : weightedNM) {
 double sum = 0;
 for (double k : dec) {
 //System.out.printf("%12f", k);
 sum += k;
 }
 System.out.println(sum);
 }*/
/*for (int row = 0; row < matrix.length; row++) {
 double sum = 0, weight = 0;

 for (int col = 0; col < matrix[0].length; col++) {

 sum += matrix[row][col];
 }

 for (int col = 0; col < matrix[0].length; col++) {
 normArrayList[row][col] = matrix[row][col] / sum;
 }
 System.out.println(sum);
 }//FOR*/
