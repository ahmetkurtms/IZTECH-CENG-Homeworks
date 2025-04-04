#!/bin/bash

# Benchmarks that I select
BENCHMARKS=("2mm" "3mm" "atax" "bicg" "doitgen")

# Output file and clear previous
OUTPUT="output_shell.txt"
> $OUTPUT

# 10 times run
for benchmark in "${BENCHMARKS[@]}"; do
    echo "Running $benchmark" >> $OUTPUT #output to the file
    for i in {1..10}; do
        /usr/bin/time -f "real %e" ./$benchmark 2>> $OUTPUT
    done
done

echo "Execution finished and saved as $OUTPUT."
