# Instructions and results

```
$ mvn clean install
...
$ java -jar target/benchmarks.jar PCollectionsBenchmark
...
Benchmark                           (size)  Mode  Cnt       Score   Error  Units
PCollectionsBenchmark.hashMap         1000  avgt    2       7.892          ms/op
PCollectionsBenchmark.hashMap        10000  avgt    2    1217.640          ms/op
PCollectionsBenchmark.hashMap       100000  avgt    2  129473.325          ms/op
PCollectionsBenchmark.hashTreePMap    1000  avgt    2       0.282          ms/op
PCollectionsBenchmark.hashTreePMap   10000  avgt    2       4.109          ms/op
PCollectionsBenchmark.hashTreePMap  100000  avgt    2      52.252          ms/op
```
