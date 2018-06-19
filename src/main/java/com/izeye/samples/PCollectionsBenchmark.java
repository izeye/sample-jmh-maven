package com.izeye.samples;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PCollectionsBenchmark {

	@Param({"1000", "10000", "100000"})
	int size;

	@Benchmark
	public Map<String, String> hashMap() {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < this.size; i++) {
			map = put(map, "key" + i, "value" + i);
		}
		return map;
	}

	@Benchmark
	public Map<String, String> hashTreePMap() {
		PMap<String, String> map = HashTreePMap.empty();
		for (int i = 0; i < this.size; i++) {
			map = map.plus("key" + i, "value" + i);
		}
		return map;
	}

	private Map<String, String> put(Map<String, String> map, String key, String value) {
		Map<String, String> newMap = new HashMap<>(map);
		newMap.put(key, value);
		return newMap;
	}

}
