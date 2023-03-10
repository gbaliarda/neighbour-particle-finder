import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileParser {
    private static Input input;

    public static Input parseFiles(String staticFile, String dynamicFile) throws FileNotFoundException {
        input = new Input();
        parseStaticFile(staticFile);
        parseDynamicFile(dynamicFile);
        return input;
    }

    private static void parseStaticFile(String staticFile) throws FileNotFoundException {
        File file = new File(staticFile);
        Scanner scanner = new Scanner(file).useLocale(Locale.US);

        input.setAmountParticles(scanner.nextInt());
        input.setLengthMatrix(scanner.nextDouble());
        input.setAmountCells(scanner.nextInt());
        input.setInteractionRadius(scanner.nextDouble());

        input.setParticles(new ArrayList<>());
        for (int i = 0; i < input.getAmountParticles(); i++) {
            double radius = scanner.nextDouble();
            double property = scanner.nextDouble();
            Particle p = new Particle(i + 1, radius, property);
            input.addParticle(p);
        }
        scanner.close();
    }

    private static void parseDynamicFile(String dynamicFile) throws FileNotFoundException {
        File file = new File(dynamicFile);
        Scanner scanner = new Scanner(file).useLocale(Locale.US);

        scanner.nextInt();

        input.getParticles().forEach(p -> {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();

            p.setPosition(x, y);
            scanner.nextDouble();
            scanner.nextDouble();
        });
        scanner.close();
    }
}
