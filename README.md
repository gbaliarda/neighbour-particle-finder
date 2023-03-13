# Input Generation

In order to generate both static and dynamic input files run the following command:
```shell
$> ./generate.sh $1 $2 $3 $4
```
where:
- $1 is the interaction radius
- $2 is L
- $3 is M
- $4 is the amount of particles
Make sure `generate.sh` has executable permissions
```shell
$> chmod u+x ./generate.sh
```

The structure of `static.txt` is:
```
amount_particles
map_length
amount_cells
interaction_radius
radius_1 property_1
...
radius_N property_N
```
The structure of `dynamic.txt` is:
```
iteration
pos_x1 pos_y1 vel_x1 vel_y1
...
pos_xN pos_yN vel_xN vel_yN
```


# Simulation

To generate the .jar file run the following command:
```shell  
$> mvn clean package  
```

In order to run the algorithm run:
```shell
$> java -jar ./target/neighbour-particle-finder-1.0-SNAPSHOT-jar-with-dependencies.jar
```

This will generate a file called output.txt with each particle and its neighbours. It will also print how long both brute force and cell index method took to execute.

## Taking into account particles with radius
The `L/M > Rc` criteria when working with non-punctual particles (r > 0) changes to `L / M > Rc + maxR`, where `maxR` is the maximum radius among all the particles in the simulation.

This is to prevent the case where a particle is at a given cell but its border is in a neighbor cell.

## Optimal M

The optimal M should be the larger value possible that satifies the working condition described in the previous point.

# Run visuals

## Manim
[How to install Manim](https://docs.manim.community/en/stable/installation.html).

Also, [Latex](https://docs.manim.community/en/stable/installation/windows.html#optional-dependencies) is needed to run the animations.
```
manim -pqh --disable_caching --flush_cache visuals/visualize.py
```

# Run statistics

In order to run statistics make sure `run_stats` is set to true in `config.toml` and run:
```shell
$> ./statistics.sh
```

To visualize the statistics, run:
```shell
python visuals/statistics.py
```
