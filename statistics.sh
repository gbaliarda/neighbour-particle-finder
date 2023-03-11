#!/bin/bash

./generate.sh "1.0" 20 10 100
for i in {1..10}
do
  java -jar ./target/neighbour-particle-finder-1.0-SNAPSHOT-jar-with-dependencies.jar
done

amount_particles=1000
L=20
interaction_radius="1.0"
property="1.000"
particle_radius="0.25"
minimum_M=-1
minimum_time=-1

./generate.sh $interaction_radius $L 1 $amount_particles
for ((M=2; M<L;M++))
do
  printf "$amount_particles
  $L
  $M
  $interaction_radius
  " > static.txt
  for ((i=1; i<=$amount_particles; i++))
  do
      echo "$particle_radius $property" >> static.txt
  done

  java -jar ./target/neighbour-particle-finder-1.0-SNAPSHOT-jar-with-dependencies.jar
  last_time=$(tail -n 1 times.txt | grep -Eo "^[^( |.)]+")
  if [ "$minimum_M" -lt 0 ] || [ "$last_time" -lt "$minimum_time" ]
  then
    minimum_M=$M
    minimum_time=$(tail -n 1 times.txt | grep -Eo "^[^( |.)]+")
  fi
done

printf "Optimal M is %d with time %d \n" "$minimum_M" "$minimum_time";