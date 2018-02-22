package com.izeye.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgsAppend = {"-XX:+UseParallelGC", "-Xms1g", "-Xmx1g"})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class ArrayListToArrayBenchmark {

	@Param({"0", "1", "10", "100", "1000"})
	int size;

	List<String> list;

	@Setup
	public void setup() {
		this.list = new ArrayList<>();
		for (int i = 0; i < this.size; i++) {
			this.list.add(String.valueOf(i));
		}
	}

	@Benchmark
	public Object[] simple() {
		return this.list.toArray();
	}

	@Benchmark
	public String[] zero() {
		return this.list.toArray(new String[0]);
	}

	@Benchmark
	public String[] sized() {
		return this.list.toArray(new String[this.size]);
	}

}
