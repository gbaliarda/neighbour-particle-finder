#!/bin/bash

if [ $# -lt 4 ]
then
  echo "Invalid arguments"
  exit
fi

particle_radius="0.25";
property="1.000";
interaction_radius=$1;
L=$2;
M=$3;
amount_particles=$4;

printf "$amount_particles
$L
$M
$interaction_radius
" > static.txt

printf "0\n" > dynamic.txt

for ((i=1; i<=$amount_particles; i++))
do
    echo "$particle_radius $property" >> static.txt
    printf "%.2f %.2f %.2f %.2f\n" "$(($RANDOM % ($L * 100)))e-2" "$(($RANDOM % ($L * 100)))e-2" "0.00" "0.00" >> dynamic.txt
done