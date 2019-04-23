#!/bin/bash
spark-submit --class "WordCount" --master local[*] target/scala-2.11/word-count_2.11-1.0.0.jar 76-0.txt 2> /dev/null

