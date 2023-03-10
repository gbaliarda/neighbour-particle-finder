def parse_static_inputs(static_file):
  with open(static_file, "r") as f:
    particle_count = int(f.readline().rstrip("\n"))
    area_length = float(f.readline().rstrip("\n"))
    matrix_size = int(f.readline().rstrip("\n"))
    interaction_radius = float(f.readline().rstrip("\n"))

    static_properties = {}
    for index in range(0, particle_count):
      static_properties[index] = [ float(x) for x in f.readline().rstrip("\n").split(" ")]
    
    return particle_count, area_length, matrix_size, interaction_radius, static_properties


def parse_dynamic_inputs(dynamic_file):
  with open(dynamic_file, "r") as f:
    f.readline().rstrip("\n") # skip the time indicator line

    particles: dict[int, tuple[float, float]] = {}

    index = 0
    for line in f:
      particles[index] = [float(x) for x in line.rstrip("\n").split(" ")]
      index += 1

    return particles


def parse_simulation_output(output_file):
  with open(output_file, "r") as f:
    neighbours: dict[int, list[int]] = {}

    index = 0
    for line in f:
      particles = [int(x) for x in line.rstrip("\n").split(" ")]
      neighbours[particles[0]] = [x for x in particles[1:]]
      index += 1

    return neighbours
