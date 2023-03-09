import java.util.ArrayList;
import java.util.List;

public class Input {
    private long amountParticles, amountCells;
    private double lengthMatrix, interactionRadius;
    private List<Particle> particles;

    public void setParticles(List<Particle> particles) {
        this.particles = particles;
    }

    public Input() {}

    public Input(long amountParticles, long amountCells, double lengthMatrix, double interactionRadius) {
        this.amountParticles = amountParticles;
        this.amountCells = amountCells;
        this.lengthMatrix = lengthMatrix;
        this.interactionRadius = interactionRadius;
        this.particles = new ArrayList<>();
    }

    public long getAmountParticles() {
        return amountParticles;
    }

    public long getAmountCells() {
        return amountCells;
    }

    public double getLengthMatrix() {
        return lengthMatrix;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void setAmountParticles(long amountParticles) {
        this.amountParticles = amountParticles;
    }

    public void setAmountCells(long amountCells) {
        this.amountCells = amountCells;
    }

    public void setLengthMatrix(double lengthMatrix) {
        this.lengthMatrix = lengthMatrix;
    }

    public void setInteractionRadius(double interactionRadius) {
        this.interactionRadius = interactionRadius;
    }

    public List<Particle> addParticle(Particle particle) {
        particles.add(particle);
        return particles;
    }
}
