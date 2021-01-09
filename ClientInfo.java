/*Sadiq Hussain
 * December 4th 2018
 * Program to input and validate client information
 */


import java.io.*;
import java.util.*;

public class ClientInfo{
   
  
  public static void main(String[]args) throws Exception{
    //Modify file pathway to desired file name or destination
    //Will create new file if it dosent currently exist****
    String fileName="/D:/ClientInfo.csv";
      Scanner reader= new Scanner(System.in);
      //Writes to bottom of csv file if file already exists
      BufferedWriter out = new BufferedWriter(new FileWriter(fileName,true)); 
      Random rand=new Random();

      //Impliments headers for csv file if needed
      header(fileName);
      
      //While loop begins here so that the user may input as many clients as they desire
      while (true){
      System.out.println("First Name: ");
      String firstName=reader.nextLine();
      
      //Break statement for when user wishes to stop inputting client info
      if((firstName.toLowerCase()).equals ("quit")){
       break; 
      }
      
      System.out.println("Last name: ");
      String lastName=reader.nextLine();
      
      System.out.println("City:");
      String city=reader.nextLine();
      
      System.out.println("Postal Code: ");
      String postalcode=reader.nextLine();
      
      //Prompts the user to enter a new postal code if it dosent match with any in the list
      while (postalverify(postalcode)==false){
       System.out.println("Invalid Postal Code, try again");
       postalcode=reader.nextLine();
      }
      
      System.out.println("CreditCard number:\n (Please enter number without any spaces)");
      String creditCard=reader.nextLine();
     
      /*User is prompted to keep entering a creditcard number until it is valid
       * as well as mkaing sure it is only the 16 digits without the spaces
       */

      while (creditvalidation(creditCard)==false||creditCard.length()<16 || creditCard.length()>16){
       System.out.println ("Invalid CreditCard, try again\n(Make sure it is the right length)");
       creditCard=reader.nextLine();

      }
      //Generates random client ID
      String id=Integer.toString(rand.nextInt(99999));
      
      //Populating string array with variables which user assighned values to
      //Variables order coresponds to headers
      String[] info = {firstName, lastName, city, postalcode.toUpperCase(), creditCard, id};
      
      //Goes to next line(row) of csv file and inputs are written there
      out.write('\n');
      
      //Writing all the values from the string array into the csv file
      for (int i=0;i<info.length;i++){
        out.write(info[i] + ',');
      }
      System.out.println("Type in 'quit' if you wish to stop entering clients");
      }
      out.close();
      reader.close();

    }
       
  /*This method will check a given file and see if it is blank
   * if it is, that means that it dosen't have headers
   * the method will then write out the headers on the first line
   */
  public static void header(String file)throws Exception{
    BufferedWriter out = new BufferedWriter(new FileWriter(file,true)); 
    BufferedReader br = new BufferedReader(new FileReader(file));
    String text = br.readLine(); 
    if (text==null){
      out.write("First Name" + ',');
      out.write("Last Name" + ',');
      out.write("City" + ',');
      out.write("Postal Code" + ',');
      out.write("Credit Card" + ',');
      out.write("ID"); 
    }
    br.close();    
    out.close();
  }
        
        /* Method to verify first 3 digits of inputted postal code matches with 
         * one of the codes on the given list
         */
        public static boolean postalverify (String postalcode)throws Exception{
          boolean verify=false;
          File file = new File("D:\\postal_codes.csv"); 
          BufferedReader reader = new BufferedReader(new FileReader(file));
          String st;
          while ((st=reader.readLine())!=null){
            st=st.substring(0,3);
            //Takes only first 3 characters user has types for verifacation
            if (st.equals(postalcode.substring(0,3).toUpperCase())){
              verify=true;
              break;
            }
          }
          reader.close();
          return verify;
        }
        //Reverses a given integer array (used for luhn algorithm)
        public static void reverse (int[] numbers){
          int temp;
          for (int left = 0, right = numbers.length - 1; left < right; left++, right--) {
           temp = numbers[left];
           numbers[left]  = numbers[right];
           numbers[right] = temp;
          }
          
        }
        
       
        //Method for application of Bendford's Law
        public static boolean creditvalidation (String word){
           int numbers[]=new int [word.length()];
          for (int i=0;i<numbers.length;i++){
            numbers[i]=Character.getNumericValue(word.charAt(i));
          }
          //Using reverse method for creditcard digits
          reverse(numbers);
          int sum=0;
         for (int i =0 ; i<numbers.length;i=i+2){
          sum= sum+numbers[i];
         }
         for (int i=1; i<numbers.length;i=i+2){
           if (numbers[i]*2>9){
            numbers[i]=(numbers[i]*2)-9; 
            sum=sum+numbers[i]; 
           }else{
            sum=sum+numbers[i]*2;
           }
         }
         /*Since this method returns a boolean, I saved memory by simply giving a statement 
          * and lettign the program decide if it was true, rather than setting a boolean variable
          * and using an if statement
          */
         return (sum%10==0);
        }
}
 
        