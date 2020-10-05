package empar.psp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author empar
 */
public class U1ProcessesPracticeEx3Upper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
           
            while ((line = bufReader.readLine()) != null && !line.equalsIgnoreCase("Finish")) {
                System.out.println(line.toUpperCase());      
                //bufReader.close();  // to introduce intentioned error
            }
        } catch (Exception ex) {
            System.err.println("Exception in Ex3Upper Program: " + ex.getMessage());
            System.err.println("Ex3_Upper StackTrace ===>>");                    
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

}
