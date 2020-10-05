
package empar.psp;

/**
 *
 * @author empar
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class U1ProcessesPracticeEx2Random10 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        Random r = new Random();
        int number;
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));
        //Scanner bufReader = new Scanner(System.in);

        String words;
        boolean goOn = true;
        while (goOn && (words = bufReader.readLine()) != null) {
            if (words.equalsIgnoreCase("stop")){
                goOn = false;
            }
            else { // generate another random number
                number = r.nextInt(11);            
                System.out.println(number);
            }
        }
        bufReader.close();

    }
}
