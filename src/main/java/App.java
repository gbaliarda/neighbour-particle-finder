import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

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
            output.getNeighbours().forEach((particle, neighbours) -> {
                System.out.printf("Particle with id %d is neighbour of: ", particle.getId());
                neighbours.forEach(neighbour -> {
                    System.out.printf(" particle %d ", neighbour.getId());
                });
                System.out.println();
            });
            System.out.printf("Execution time: %f", output.getExecuteTime());
        } catch (FileNotFoundException e) {
            System.out.println("Error while parsing files");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
