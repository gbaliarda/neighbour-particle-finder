import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Particle p1 = new Particle(1, 1.8, 1.9, 0);
        Particle p2 = new Particle(2, 3.8, 1.9, 0);
        Particle p3 = new Particle(3, 2.5, 2.9, 0);
        Particle p4 = new Particle(4, 1.2, 2.9, 0);
        Particle p5 = new Particle(5, 0.7, 3.8, 0);
        Particle p6 = new Particle(6, 1.7, 3.8, 0);
        Particle p7 = new Particle(7, 3.1, 3.5, 0);
        Particle p8 = new Particle(8, 4.1, 3.7, 0);
        Particle p9 = new Particle(9, 0.2, 4.2, 0);
        Particle p10 = new Particle(10, 0.6, 4.2, 0);
        Particle p11 = new Particle(11, 2.8, 4.2, 0);
        List<Particle> particles = new LinkedList<>();
        particles.add(p1);
        particles.add(p2);
        particles.add(p3);
        particles.add(p4);
        particles.add(p5);
        particles.add(p6);
        particles.add(p7);
        particles.add(p8);
        particles.add(p9);
        particles.add(p10);
        particles.add(p11);

        try {
            CellIndexMethod t = new CellIndexMethod(particles, 11, 5.1, 5, 0.5, false);
            OutputDTO output = t.calculateAllDistances();
            output.getNeighbours().forEach((particle, neighbours) -> {
                System.out.printf("Particle with id %d is neighbour of: ", particle.getId());
                neighbours.forEach(neighbour -> {
                    System.out.printf(" particle %d ", neighbour.getId());
                });
                System.out.println();
            });
            System.out.printf("Execution time: %f", output.getExecuteTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
