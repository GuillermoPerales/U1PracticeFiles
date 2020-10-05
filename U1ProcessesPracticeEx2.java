
package empar.psp;

import java.io.*;

/**
 *
 * @author empar
 */
public class U1ProcessesPracticeEx2 {

    /**
     * @param args the command line arguments
     * @throws  java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        final String ex2Random10JarPath= "out/artifacts/Ex2Random10_jar/Ex2Random10.jar";
        //final String ex2Random10JarPath= "Ex2Random10.jar";
        //final String ex2Random10JarPath = args[0];

        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        FileWriter outputFile = null;
        Process childProcess;
        int childExitValue = -1;

        try {
            // start new child childProcess for Random10.jar
            childProcess = new ProcessBuilder("java", "-jar", ex2Random10JarPath).start();



            // create a new file to save generated numbers
            outputFile = new FileWriter("randoms.txt");

            // output data stream to write data to childProcess
            OutputStream outStream = childProcess.getOutputStream();
            OutputStreamWriter outStrWriter = new OutputStreamWriter(outStream);
            bufWriter = new BufferedWriter(outStrWriter);

            // input stream to read from child childProcess
            InputStream inStream = childProcess.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            bufReader = new BufferedReader(inputStreamReader);

            // Reading from command line, alternative to Scanner class
            BufferedReader bufReadCommLine = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("======== WRITE A WORD AND YOU'LL GET A RANDOM NUMBER ! =========");

            String word;
            do {
                System.out.print("Write a word ('stop' to finish): ");
                word = bufReadCommLine.readLine();
                // send word to childProcess
                bufWriter.write(word);
                bufWriter.newLine();

                // flush buffer, this makes word arrive immediately to the child childProcess
                bufWriter.flush();

                // read and print the random number generated by the subprocess
                if (!word.equalsIgnoreCase("stop")) {
                    String number = bufReader.readLine();
                    System.out.println("Your random number: " + number);
                    // write random number to file
                    outputFile.write(number + System.lineSeparator());
                }
            } while (!word.equalsIgnoreCase("stop"));

            // wait until child finishes
            childExitValue = childProcess.waitFor();

        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
            System.exit(1);
        } catch (InterruptedException ex) {
            System.out.println("EXCEPTION: Current Program was interrupted while waiting for child termination");
            System.exit(2);
        } catch (Exception ex) {
            System.out.println("GENERAL EXCEPTION: " + ex.getMessage());
            System.exit(3);
        } finally {
            // close childProcess data streams
            if (bufWriter != null) {
                bufWriter.close();
            }
            if (bufReader != null) {
                bufReader.close();
            }
            if (outputFile != null) {
                outputFile.close();
            }

            System.out.println(System.lineSeparator() + "Child process Exit value: " + childExitValue);
            if (childExitValue == 0) {
                System.out.println("RANDOM NUMBERS SAVED IN FILE: randoms.txt");
            } else {
                System.out.println("THERE WAS AN ERROR WHILE EXECUTING CHILD: " + childExitValue);
            }


        }
    }
}