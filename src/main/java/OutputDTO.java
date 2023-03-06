import java.util.List;
import java.util.Map;

public class OutputDTO {
    private Map<Particle, List<Particle>> neighbours;
    private double executeTime;

    public OutputDTO(Map<Particle, List<Particle>> neighbours, double executeTime) {
        this.neighbours = neighbours;
        this.executeTime = executeTime;
    }

    public Map<Particle, List<Particle>> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<Particle, List<Particle>> neighbours) {
        this.neighbours = neighbours;
    }

    public double getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(double executeTime) {
        this.executeTime = executeTime;
    }
}
