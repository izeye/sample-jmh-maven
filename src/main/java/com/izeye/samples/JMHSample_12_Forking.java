package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_12_Forking.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_12_Forking {

	public interface Counter {
		int inc();
	}

	public class Counter1 implements Counter {
		private int x;

		@Override
		public int inc() {
			return this.x++;
		}
	}

	public class Counter2 implements Counter {
		private int x;

		@Override
		public int inc() {
			return this.x++;
		}
	}

	public int measure(Counter c) {
		int s = 0;
		for (int i = 0; i < 10; i++) {
			s += c.inc();
		}
		return s;
	}

	Counter c1 = new Counter1();
	Counter c2 = new Counter2();

	@Benchmark
	@Fork(0)
	public int measure_1_c1() {
		return measure(this.c1);
	}

	@Benchmark
	@Fork(0)
	public int measure_2_c2() {
		return measure(this.c2);
	}

	@Benchmark
	@Fork(0)
	public int measure_3_c1_again() {
		return measure(this.c1);
	}

	@Benchmark
	@Fork(1)
	public int measure_4_c1() {
		return measure(this.c1);
	}

	@Benchmark
	@Fork(1)
	public int measure_5_c2() {
		return measure(this.c1);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_12_Forking.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.build();

		new Runner(opt).run();
	}

}
