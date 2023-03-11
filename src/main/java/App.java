import com.moandjiezana.toml.Toml;

import java.io.*;

public class App {
    private static final String OUTPUT_FILE = "output.txt";
    private static final String NO_VALUE_FOUND = "NO_VALUE_FOUND";

    public static void main( String[] args ) {
        String staticFile, dynamicFile, outputFile;
        boolean isPeriodic;

        try {
            InputStream inputStream = new FileInputStream("config.toml");
            Toml toml = new Toml().read(inputStream);
            staticFile = toml.getString("files.static_input", NO_VALUE_FOUND);
            dynamicFile = toml.getString("files.dynamic_input", NO_VALUE_FOUND);
            outputFile = toml.getString("files.output", OUTPUT_FILE);
            isPeriodic = toml.getBoolean("CIM.periodic_boundary", false);

            if (staticFile.equals(NO_VALUE_FOUND) || dynamicFile.equals(NO_VALUE_FOUND))
                throw new Exception("Invalid static or dynamic file");
        } catch (Exception e) {
            System.out.println("Error while reading configuration file");
            return;
        }

        try {
            Input input = FileParser.parseFiles(staticFile, dynamicFile);
            CellIndexMethod t = new CellIndexMethod(input.getParticles(), input.getAmountParticles(), input.getLengthMatrix(), input.getAmountCells(),  input.getInteractionRadius(), isPeriodic);
            Output output = t.calculateAllDistances();
            System.out.printf("CIM Execution time: %f\n", output.getExecuteTime());
            generateOutputFile(outputFile, output);
            Output outputBruteForce = t.calculateAllDistancesBruteForce();
            System.out.printf("BF Execution time: %f\n", outputBruteForce.getExecuteTime());
        } catch (FileNotFoundException e) {
            System.out.println("Error while parsing files");
        } catch (IOException e) {
            System.out.println("Error while generating output file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateOutputFile(String fileName, Output output) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);

        StringBuilder stringBuilder = new StringBuilder();
        output.getNeighbours().forEach((particle, neighbours) -> {
            stringBuilder.append(particle.getId());
            neighbours.forEach(neighbour -> {
                stringBuilder.append(" ").append(neighbour.getId());
            });
            stringBuilder.append("\n");
        });
        fileWriter.write(stringBuilder.toString());
        fileWriter.close();
    }
}
