package com.izeye.samples;

import java.util.LinkedList;
import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_26_BatchSize.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
public class JMHSample_26_BatchSize {

	List<String> list = new LinkedList<>();

	@Benchmark
	@Warmup(iterations = 5, time = 1)
	@Measurement(iterations = 5, time = 1)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> measureWrong_1() {
		this.list.add(this.list.size() / 2, "something");
		return this.list;
	}

	@Benchmark
	@Warmup(iterations = 5, time = 5)
	@Measurement(iterations = 5, time = 5)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> measureWrong_5() {
		this.list.add(this.list.size() / 2, "something");
		return this.list;
	}

	@Benchmark
	@Warmup(iterations = 5, batchSize = 5000)
	@Measurement(iterations = 5, batchSize = 5000)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> measureRight() {
		this.list.add(this.list.size() / 2, "something");
		return this.list;
	}

	@Setup(Level.Iteration)
	public void setup() {
		this.list.clear();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_26_BatchSize.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
