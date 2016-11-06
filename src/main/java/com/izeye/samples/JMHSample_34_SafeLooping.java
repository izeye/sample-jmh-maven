package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_34_SafeLooping.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_34_SafeLooping {

	static final int BASE = 42;

	static int work(int x) {
		return BASE + x;
	}

	@Param({"1", "10", "100", "1000"})
	int size;

	int[] xs;

	@Setup
	public void setup() {
		this.xs = new int[this.size];
		for (int c = 0; c < this.size; c++) {
			this.xs[c] = c;
		}
	}

	@Benchmark
	public int measureWrong_1() {
		int acc = 0;
		for (int x : this.xs) {
			acc = work(x);
		}
		return acc;
	}

	@Benchmark
	public int measureWrong_2() {
		int acc = 0;
		for (int x : this.xs) {
			acc += work(x);
		}
		return acc;
	}

	@Benchmark
	public void measureRight_1(Blackhole bh) {
		for (int x : this.xs) {
			bh.consume(work(x));
		}
	}

	@Benchmark
	public void measureRight_2() {
		for (int x : this.xs) {
			sink(work(x));
		}
	}

	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	private static void sink(int work) {
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_34_SafeLooping.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}

}
