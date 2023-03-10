#!/bin/bash

particle_radius="0.25";
interaction_radius="1.0";
property="1.000";
map_length="20";
amount_cells="5";
amount_particles="10000";

printf "$amount_particles
$map_length
$amount_cells
$interaction_radius
" > static.txt

printf "0\n" > dynamic.txt

for ((i=1; i<=$amount_particles; i++))
do
    echo "$particle_radius $property" >> static.txt
done

for ((i=1; i<=$amount_particles; i++))
do
    printf "%.2f %.2f %.2f %.2f\n" "$(($RANDOM % ($map_length * 100)))e-2" "$(($RANDOM % ($map_length * 100)))e-2" "0.00" "0.00" >> dynamic.txt
done