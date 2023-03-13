from manim import *
import tomllib

from parse_inputs import parse_static_inputs, parse_dynamic_inputs, parse_simulation_output

# Configuration
with open("config.toml", "rb") as f:
  config = tomllib.load(f)
  TARGET_PARTICLE_ID = config["visuals"]["target_particle_id"]
  PERIODIC_BOUNDARY_CONDITIONS = config["CIM"]["periodic_boundary"]
  STATIC_FILE = config["files"]["static_input"]
  DYNAMIC_FILE = config["files"]["dynamic_input"]
  OUTPUT_FILE = config["files"]["output"]

print("Target Particle ID:", TARGET_PARTICLE_ID)
print("Periodic Boundary Conditions:", PERIODIC_BOUNDARY_CONDITIONS)

# Read the static inputs
_, L, _, Rc, properties = parse_static_inputs(STATIC_FILE)

print("L:", L)
print("Rc:", Rc)

# Read the dynamic inputs: particle id to [x, y] position dictionary
particles = parse_dynamic_inputs(DYNAMIC_FILE)

# Read the simulation output: particle id to list of neighbour ids dictionary
neighbours = parse_simulation_output(OUTPUT_FILE)

# Animation
class Particle(Scene):  
  def construct(self):
    # Setup the axes
    x_range=[0, L, L/5]
    y_range=[0, L, L/5]
    scale=6

    # Create the Cartesian plane
    plane = NumberPlane(
      background_line_style={
        "stroke_width": 1,
        "stroke_opacity": 0.4
      },
      x_range=x_range,
      y_range=y_range,
      x_length=scale,
      y_length=scale,
    ).add_coordinates()

    grid_scale = plane.c2p(1,0)[0] - plane.c2p(0,0)[0]

    # Create the dots for each particle
    dots = VGroup()
    for particle_id, position in particles.items():
      particle_radius = properties[particle_id][0]
      dot = Dot(radius=particle_radius*grid_scale)

      if particle_id == TARGET_PARTICLE_ID:
        dot.set_color(RED)

        if PERIODIC_BOUNDARY_CONDITIONS:
          reflected_x = position[0]
          reflected_y = position[1]

          if position[0] - Rc < 0:
            reflected_x = position[0] + L
          elif position[0] + Rc > L:
            reflected_x = position[0] - L

          if position[1] - Rc < 0:
            reflected_y = position[1] + L
          elif position[1] + Rc > L:
            reflected_y = position[1] - L
 
          if reflected_x != position[0] or reflected_y != position[1]:
            # FIXME: when the `reflected_dot` is in a corner, it should be reflected in all other corners
            reflected_dot = Dot(radius=particle_radius*grid_scale, color=RED)
            reflected_dot.move_to(plane.c2p(reflected_x, reflected_y))
            circle = Circle(radius=(Rc + particle_radius)*grid_scale, color=RED, stroke_width=2)
            circle.move_to(reflected_dot.get_center())
            self.add(reflected_dot, circle)

      elif particle_id in neighbours[TARGET_PARTICLE_ID]:
        dot.set_color(GREEN)
      else:
        dot.set_opacity(0.3)

      dot.move_to(plane.c2p(*position))
      dots.add(dot)

      if particle_id == TARGET_PARTICLE_ID:
        # The radius needs to be scaled to the axes, so we divide by PI for some reason
        circle = Circle(radius=(Rc + particle_radius)*grid_scale, color=RED, stroke_width=2)
        circle.move_to(dot.get_center())
        self.add(circle)

    # Add all the elements to the scene
    self.add(plane, dots)
