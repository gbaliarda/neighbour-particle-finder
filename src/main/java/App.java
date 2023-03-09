import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static final String OUTPUT_FILE = "output.txt";

    public static void main( String[] args ) {
        if (args.length < 2) {
            System.out.println("Invalid amount of arguments");
            return;
        }
        String staticFile = args[0];
        String dynamicFile = args[1];
        String isPeriodic = args.length > 2 ? args[2] : "false";

        try {
            Input input = FileParser.parseFiles(staticFile, dynamicFile);
            CellIndexMethod t = new CellIndexMethod(input.getParticles(), input.getAmountParticles(), input.getLengthMatrix(), input.getAmountCells(),  input.getInteractionRadius(), Boolean.parseBoolean(isPeriodic));
            Output output = t.calculateAllDistances();
            System.out.printf("Execution time: %f", output.getExecuteTime());
            generateOutputFile(output);
        } catch (FileNotFoundException e) {
            System.out.println("Error while parsing files");
        } catch (IOException e) {
            System.out.println("Error while generating output file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateOutputFile(Output output) throws IOException {
        File file = new File(OUTPUT_FILE);
        FileWriter fileWriter = new FileWriter(file);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(output.getExecuteTime()).append("\n");
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
