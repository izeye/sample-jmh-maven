package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.WarmupMode;

/**
 * JMHSample_32_BulkWarmup.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_32_BulkWarmup {

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

	Counter c1 = new Counter1();
	Counter c2 = new Counter2();

	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	public int measure(Counter c) {
		int s = 0;
		for (int i = 0; i < 10; i++) {
			s += c.inc();
		}
		return s;
	}

	@Benchmark
	public int measure_c1() {
		return measure(this.c1);
	}

	@Benchmark
	public int measure_c2() {
		return measure(this.c2);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_32_BulkWarmup.class.getSimpleName())
				.warmupMode(WarmupMode.BULK)
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
