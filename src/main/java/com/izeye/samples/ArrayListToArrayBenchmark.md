# Instructions and results

```
$ mvn clean install
...
$ java -jar target/benchmarks.jar ArrayListToArrayBenchmark
...
Benchmark                         (size)  Mode  Cnt     Score    Error  Units
ArrayListToArrayBenchmark.simple       0  avgt   15    21.650 ±  0.599  ns/op
ArrayListToArrayBenchmark.simple       1  avgt   15     8.532 ±  0.244  ns/op
ArrayListToArrayBenchmark.simple      10  avgt   15    10.554 ±  0.240  ns/op
ArrayListToArrayBenchmark.simple     100  avgt   15    51.420 ±  0.727  ns/op
ArrayListToArrayBenchmark.simple    1000  avgt   15   499.350 ± 17.533  ns/op
ArrayListToArrayBenchmark.sized        0  avgt   15    20.592 ±  0.916  ns/op
ArrayListToArrayBenchmark.sized        1  avgt   15    20.049 ±  0.659  ns/op
ArrayListToArrayBenchmark.sized       10  avgt   15    28.537 ±  0.671  ns/op
ArrayListToArrayBenchmark.sized      100  avgt   15   140.234 ±  6.641  ns/op
ArrayListToArrayBenchmark.sized     1000  avgt   15  1567.469 ± 73.761  ns/op
ArrayListToArrayBenchmark.zero         0  avgt   15     4.635 ±  0.168  ns/op
ArrayListToArrayBenchmark.zero         1  avgt   15    12.251 ±  0.675  ns/op
ArrayListToArrayBenchmark.zero        10  avgt   15    21.328 ±  0.551  ns/op
ArrayListToArrayBenchmark.zero       100  avgt   15   129.701 ±  2.951  ns/op
ArrayListToArrayBenchmark.zero      1000  avgt   15  1433.015 ± 55.105  ns/op
```
