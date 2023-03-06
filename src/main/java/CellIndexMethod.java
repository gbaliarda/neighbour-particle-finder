import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CellIndexMethod {
    private final Map<Long, List<Particle>> map;
    private final long cellAmount;
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
        this.cellAmount = M;
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
        return Math.sqrt(xDistance*xDistance + yDistance*yDistance) - p1.getRadius() - p2.getRadius(); // What happens if two circles are concentric?
    }

    private void addNeighboursDistanceBetweenCells(Map<Particle, List<Particle>> neighbours, long currentCell, long otherCell) {
        if (!map.containsKey(currentCell) || !map.containsKey(otherCell))
            return;
        map.get(currentCell).forEach((currentParticle) -> {
            map.get(otherCell).forEach((otherParticle) -> {
                neighbours.putIfAbsent(currentParticle, new LinkedList<>());
                if (calculateDistance(currentParticle, otherParticle) < interactionRadius) {
                    neighbours.get(currentParticle).add(otherParticle);
                    neighbours.putIfAbsent(otherParticle, new LinkedList<>());
                    neighbours.get(otherParticle).add(currentParticle);
                }
            });
        });
    }
    public OutputDTO calculateAllDistances() {
        long startTime = System.currentTimeMillis();
        Map<Particle, List<Particle>> neighbours = new HashMap<>();
        for (int x = 1; x <= cellAmount; x++) {
            for (int y = 0 ; y < cellAmount; y++) {
                long currentCell = x + y * cellAmount;
                if (y > 0) {
                    addNeighboursDistanceBetweenCells(neighbours, currentCell, x + (y - 1) * cellAmount);
                    if (x < cellAmount)
                        addNeighboursDistanceBetweenCells(neighbours, currentCell, (x + 1) + (y - 1) * cellAmount);
                }
                if (x < cellAmount) {
                    addNeighboursDistanceBetweenCells(neighbours, currentCell, (x + 1) + y * cellAmount);
                    if (y < cellAmount - 1)
                        addNeighboursDistanceBetweenCells(neighbours, currentCell, (x + 1) + (y + 1) * cellAmount);
                }
            }
        }
        return new OutputDTO(neighbours, System.currentTimeMillis() - startTime);
    }
}
