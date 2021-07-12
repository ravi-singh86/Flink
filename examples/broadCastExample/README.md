```
How to Use this Example
1. Compile code using mnv packege
2. start flink cluster by using installed version of flink by using {PATHTOFLINK}/bin/start-cluster.sh
3. start in different terminal nc -lk 9991 // for rule
4. start in different terminal nc -lk 9992 // for event
4. submit Job using {PATHTOFLINK}/bin/flink run -c test.RulePipeline target/wordcount-0.1.jar
```
