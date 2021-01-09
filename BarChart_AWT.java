import org.jfree.chart.* ;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;
import org.jfree.ui.*;
import java.io.*;
import java.util.*;

public class FraudDetector extends ApplicationFrame {
   
   public FraudDetector( String applicationTitle , String chartTitle )throws Exception  {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "First Digit",            
         "Frequency (%)",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }

    CategoryDataset createDataset( ) throws Exception {
      String filesname="/D:/sales.csv";
      double[] frequency=occurance(filesname);
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
      for (int i=0;i<frequency.length;i++){
      dataset.addValue( frequency[i], "", String.valueOf(i+1) );        
      }
      return dataset; 
   }
      public static void main( String[ ] args )throws Exception {

      FraudDetector chart = new FraudDetector("Sales fraud detector", 
         "Frequency of First Digit of Sales");
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
         public static double[] occurance (String filesname) throws Exception{
           File file = new File(filesname); 
           BufferedReader reader = new BufferedReader(new FileReader(file));
           
           double[] frequency= new double[9];
           int [] check={1,2,3,4,5,6,7,8,9};
           String line;
           int num=0;
           while ((line=reader.readLine()) !=null){
             String[] array=line.split(",");
             num=Character.getNumericValue(array[1].charAt(0));
             for (int i= 0; i<check.length;i++){
               if(num==check[i]){
                 frequency[i]=frequency[i]+1;
               }
               
             }
             
           }
           reader.close();
    double total=0;
    for(int i =0;i<frequency.length;i++){
     total=total+frequency[i];
    }
    for (int i =0; i<frequency.length; i++){
     frequency[i]=(frequency[i]/total)*100; 
    }
    if (frequency[0]<29.00 || frequency[0]>32){
     System.out.println("There is a fraudulent sale present"); 
    }else{
     System.out.println("The sales are not fraudulent");
    }
    return frequency;
  }
}

   