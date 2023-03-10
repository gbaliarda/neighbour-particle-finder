import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CellIndexMethod {
    private final Map<Long, List<Particle>> map;
    private final List<Particle> particles;
    private final long cellAmount;
    private final double mapLength;
    private final double interactionRadius;
    private final boolean isPeriodic;

    /*
    * M: map MxM
    * L: total length map
    * N: total particles
    * r: interaction radius
     */
    public CellIndexMethod(List<Particle> particles, long N, double L, long M, double r, boolean isPeriodic) throws Exception {
        if (L/M <= r)
            throw new Exception(); // make new checked exception
        this.map = new HashMap<>();
        this.particles = particles;
        this.cellAmount = M;
        this.mapLength = L;
        this.interactionRadius = r;
        this.isPeriodic = isPeriodic;
        double cellSize = L/M;
        particles.forEach((p) -> {
            long xCellNumber = Math.round(p.getX() % cellSize) + 1;
            long yCellNumber = Math.round(p.getY() % cellSize);
            long cellNumber = xCellNumber + yCellNumber * M;
            map.putIfAbsent(cellNumber, new LinkedList<>());
            map.get(cellNumber).add(p);
        });
    }

    private double calculateDistance(Particle p1, Particle p2) {
        double xDistance = Math.abs(p2.getX() - p1.getX());
        double yDistance = Math.abs(p2.getY() - p1.getY());

        if (isPeriodic) {
            if (xDistance * 2 > mapLength)
                xDistance = mapLength - xDistance;
            if (yDistance * 2 > mapLength)
                yDistance = mapLength - yDistance;
        }
        return Math.sqrt(xDistance*xDistance + yDistance*yDistance) - p1.getRadius() - p2.getRadius(); // What happens if two circles are concentric?
    }

    private boolean particlesAreNeighbours(Particle p1, Particle p2) {
        return p1.getId() != p2.getId() && calculateDistance(p1, p2) < interactionRadius;
    }

    private void addNeighboursDistanceBetweenCells(Map<Particle, List<Particle>> neighbours, long currentCell, long otherCell) {
        if (!map.containsKey(currentCell) || !map.containsKey(otherCell))
            return;
        map.get(currentCell).forEach((currentParticle) -> {
            map.get(otherCell).forEach((otherParticle) -> {
                neighbours.putIfAbsent(currentParticle, new LinkedList<>());
                if (particlesAreNeighbours(currentParticle, otherParticle)) {
                    neighbours.get(currentParticle).add(otherParticle);
                    if (currentCell != otherCell) {
                        neighbours.putIfAbsent(otherParticle, new LinkedList<>());
                        neighbours.get(otherParticle).add(currentParticle);
                    }
                }
            });
        });
    }

    public Output calculateAllDistances() {
        long startTime = System.currentTimeMillis();
        Map<Particle, List<Particle>> neighbours = new HashMap<>();
        for (int x = 1; x <= cellAmount; x++) {
            for (int y = 0 ; y < cellAmount; y++) {
                long currentCell = x + y * cellAmount;
                addNeighboursDistanceBetweenCells(neighbours, currentCell, currentCell);
                if (y > 0 || isPeriodic) {
                    long northCell = y > 0 ? x + (y - 1) * cellAmount : x + (cellAmount - 1) * cellAmount;
                    addNeighboursDistanceBetweenCells(neighbours, currentCell, northCell);
                }
                if (x < cellAmount || isPeriodic) {
                    long eastCell = ((x + 1) % cellAmount) + y * cellAmount;
                    long northEastCell = y > 0 ? ((x + 1) % cellAmount) + (y - 1) * cellAmount : ((x + 1) % cellAmount) + (cellAmount - 1) * cellAmount;
                    long southEastCell = y < cellAmount - 1 ? ((x + 1) % cellAmount) + (y + 1) * cellAmount : (x + 1) % cellAmount;
                    addNeighboursDistanceBetweenCells(neighbours, currentCell, eastCell);
                    if (y > 0 || isPeriodic)
                        addNeighboursDistanceBetweenCells(neighbours, currentCell, northEastCell);
                    if (y < cellAmount - 1 || isPeriodic)
                        addNeighboursDistanceBetweenCells(neighbours, currentCell, southEastCell);
                }
            }
        }
        return new Output(neighbours, System.currentTimeMillis() - startTime);
    }

    public Output calculateAllDistancesBruteForce() {
        long startTime = System.currentTimeMillis();
        Map<Particle, List<Particle>> neighbours = new HashMap<>();
        particles.forEach(p -> neighbours.putIfAbsent(p, particles.stream().filter(otherParticle -> otherParticle.getId() != p.getId() && particlesAreNeighbours(p, otherParticle)).collect(Collectors.toList())));
        return new Output(neighbours, System.currentTimeMillis() - startTime);
    }
}
