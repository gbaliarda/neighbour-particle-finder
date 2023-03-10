from manim import *

from parse_inputs import parse_static_inputs, parse_dynamic_inputs, parse_simulation_output

# Input files
STATIC_FILE = "./static.txt"
DYNAMIC_FILE = "./dynamic.txt"
OUTPUT_FILE = "./output.txt"
TARGET_PARTICLE_ID = 4

# Read the static inputs
_, L, _, Rc, _ = parse_static_inputs(STATIC_FILE)

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
      dot = Dot()

      if particle_id == TARGET_PARTICLE_ID:
        dot.set_color(RED)
      elif particle_id in neighbours[TARGET_PARTICLE_ID]:
        dot.set_color(GREEN)
      else:
        dot.set_opacity(0.3)

      dot.move_to(plane.coords_to_point(*position))
      dots.add(dot)

      if particle_id == TARGET_PARTICLE_ID:
        # The radius needs to be scaled to the axes, so we divide by PI for some reason
        circle = Circle(radius=Rc*grid_scale, color=RED, stroke_width=2)
        circle.move_to(dot.get_center())
        self.add(circle)

    # Add all the elements to the scene
    self.add(plane, dots)
