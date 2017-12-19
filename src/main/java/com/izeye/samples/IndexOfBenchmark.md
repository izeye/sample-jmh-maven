# Instructions and results

```
$ mvn clean install
...
$ java -jar target/benchmarks.jar IndexOfBenchmark
...
Benchmark                                   Mode  Cnt         Score        Error  Units
IndexOfBenchmark.indexOfChar               thrpt  200  65880319.623 ± 398528.501  ops/s
IndexOfBenchmark.indexOfCharViaVariable    thrpt  200  66243012.226 ± 499424.944  ops/s
IndexOfBenchmark.indexOfString             thrpt  200  59644677.096 ± 369317.817  ops/s
IndexOfBenchmark.indexOfStringViaVariable  thrpt  200  56771164.906 ± 326952.665  ops/s
```
