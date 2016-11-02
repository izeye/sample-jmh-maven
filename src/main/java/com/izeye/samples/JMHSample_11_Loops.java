package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_11_Loops.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_11_Loops {

	int x = 1;
	int y = 2;

	@Benchmark
	public int measureRight() {
		return this.x + this.y;
	}

	private int reps(int reps) {
		int s = 0;
		for (int i = 0; i < reps; i++) {
			s += this.x + this.y;
		}
		return s;
	}

	@Benchmark
	@OperationsPerInvocation(1)
	public int measureWrong_1() {
		return reps(1);
	}

	@Benchmark
	@OperationsPerInvocation(10)
	public int measureWrong_10() {
		return reps(10);
	}

	@Benchmark
	@OperationsPerInvocation(100)
	public int measureWrong_100() {
		return reps(100);
	}

	@Benchmark
	@OperationsPerInvocation(1000)
	public int measureWrong_1000() {
		return reps(1000);
	}

	@Benchmark
	@OperationsPerInvocation(10000)
	public int measureWrong_10000() {
		return reps(10000);
	}

	@Benchmark
	@OperationsPerInvocation(100000)
	public int measureWrong_100000() {
		return reps(100000);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_11_Loops.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
