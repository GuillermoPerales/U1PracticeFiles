import java.io.*;

/**
 * @author empar
 */
public class U1ProcessesPracticeEx3 {

    /**
     * @param args the command line arguments
     * @throws IOException          Problem with Child Process
     * @throws InterruptedException Parent is interrupted while waiting for child to finish
     */

    public static void main(String[] args) throws IOException, InterruptedException {
        final String ex3UpperJarPath = "out/artifacts/Ex3Upper_jar/Ex3Upper.jar";
        // final String ex3UpperJarPath = args;

        // start new childProcess 
        Process childProcess = new ProcessBuilder("java", "-jar", ex3UpperJarPath).start();

        // output data stream to write data to childProcess               
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(childProcess.getOutputStream());
        InputStreamReader inputStreamReader = new InputStreamReader(childProcess.getInputStream());

        // input streams to read/write from child childProcess
        try (BufferedWriter bufWriter = new BufferedWriter(outputStreamWriter);
             BufferedReader bufReader = new BufferedReader(inputStreamReader)) {

            // Read from command line
            BufferedReader bufReadCommLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("=========== CONVERTING TEXT TO CAPITAL LETTERS ==========");

            String line;
            boolean goOn;
            do {
                System.out.print("Write a line of words (write 'finish' to Stop): ");
                line = bufReadCommLine.readLine();
                // send line read to childProcess
                bufWriter.write(line);
                bufWriter.newLine();

                // flush buffer to make line arrive immediately to the child childProcess
                bufWriter.flush();

                goOn = !line.equalsIgnoreCase("finish");
                // if not finished, read from the child childProcess (line converted to uppercase)
                if (goOn) {
                    System.out.println(line + " ---> " + bufReader.readLine());
                }
            } while (goOn);

        } finally {
            // wait for child process to finish
            int exitval = childProcess.waitFor();

            System.out.println("\nFinished converting text to UpperCase. Child process Exit value is: " + exitval);
            if (exitval != 0) {
                System.out.println(System.lineSeparator() + "ATTENTION: ERROR IN CHILD PROCESS FOUND -->");
                printChildErr(childProcess.getErrorStream());
            }
        }
    }


    private static void printChildErr(InputStream errorStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
        String line;
        int cont = 0;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
