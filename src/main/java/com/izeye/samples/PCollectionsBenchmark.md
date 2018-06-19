# Instructions and results

```
$ mvn clean install
...
$ java -jar target/benchmarks.jar PCollectionsBenchmark
...
Benchmark                                (size)  Mode  Cnt       Score   Error  Units
PCollectionsBenchmark.concurrentHashMap    1000  avgt    2       0.105          ms/op
PCollectionsBenchmark.concurrentHashMap   10000  avgt    2       1.161          ms/op
PCollectionsBenchmark.concurrentHashMap  100000  avgt    2      18.091          ms/op
PCollectionsBenchmark.hashMap              1000  avgt    2       8.469          ms/op
PCollectionsBenchmark.hashMap             10000  avgt    2    1216.814          ms/op
PCollectionsBenchmark.hashMap            100000  avgt    2  130917.445          ms/op
PCollectionsBenchmark.hashTreePMap         1000  avgt    2       0.298          ms/op
PCollectionsBenchmark.hashTreePMap        10000  avgt    2       4.178          ms/op
PCollectionsBenchmark.hashTreePMap       100000  avgt    2      54.630          ms/op
```
