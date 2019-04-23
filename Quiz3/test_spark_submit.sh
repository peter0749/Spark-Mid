#!/bin/bash
spark-submit --class "MM" --master local[*] target/scala-2.10/mm_2.10-1.0.0.jar Trans.txt 2> /dev/null

