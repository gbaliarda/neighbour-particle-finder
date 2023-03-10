# neighbour-particle-finder
## Input Generation

In order to generate both static and dynamic input files run the following command:
```shell
$> ./generate.sh
```
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


## Simulation

To generate the .jar file run the following command:
```shell  
$> mvn clean package  
```

In order to run the algorithm run:
```shell
$> java -jar ./target/neighbour-particle-finder-1.0-SNAPSHOT-jar-with-dependencies.jar {staticFile} {dynamicFile} {isPeriodic}
```
where:
- staticFile: relative path to the static file which contains algorithm headers, and each particle radius and property.
- dynamicFile: relative path to the dynamic file which contains particles position
- isPeriodic: which can be true or false

This will generate a file called output.txt with each particle and its neighbours. It will also print how long both brute force and cell index method took to execute.