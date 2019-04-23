#!/bin/bash
spark-submit --class "MM" --master local[*] target/scala-2.10/mm_2.10-1.0.0.jar  A.txt B.txt output.txt 2> /dev/null

