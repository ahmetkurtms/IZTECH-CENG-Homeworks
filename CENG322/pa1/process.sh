#!/bin/bash

INPUT="output_shell.txt"
OUTPUT="output_times.txt"

> $OUTPUT # Clear previous data

benchmark_name=""
total=0
entries=0

while IFS= read -r line; do
    if [[ $line == "Running "* ]]; then
      # If there are pre-entries, calc. and write avg.
        if [[ $entries -gt 0 ]]; then
            average_time=$(awk "BEGIN {print $total / $entries}")
            echo "Benchmark: $benchmark_name - Avg Time: $average_time sec" >> $OUTPUT
        fi
        benchmark_name=$(echo $line | cut -d' ' -f2)
        total=0
        entries=0
    elif [[ $line == real* ]]; then
        time_value=$(echo $line | awk '{print $2}')
        total=$(awk "BEGIN {print $total + $time_value}")
        entries=$((entries + 1))
    fi
done < "$INPUT"

if [[ $entries -gt 0 ]]; then
    average_time=$(awk "BEGIN {print $total / $entries}")
    echo "Benchmark: $benchmark_name - Avg Time: $average_time sec" >> $OUTPUT
fi

echo "All average execution times saved as $OUTPUT."
