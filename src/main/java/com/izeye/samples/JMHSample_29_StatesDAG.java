package com.izeye.samples;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_29_StatesDAG.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class JMHSample_29_StatesDAG {

	public static class Counter {
		int x;

		public int inc() {
			return this.x++;
		}

		public void dispose() {
		}
	}

	@State(Scope.Benchmark)
	public static class Shared {
		List<Counter> all;
		Queue<Counter> available;

		@Setup
		public synchronized void setup() {
			this.all = new ArrayList<>();
			for (int c = 0; c < 10; c++) {
				this.all.add(new Counter());
			}

			this.available = new LinkedList<>();
			this.available.addAll(this.all);
		}

		@TearDown
		public synchronized void tearDown() {
			for (Counter c : this.all) {
				c.dispose();
			}
		}

		public synchronized Counter getMine() {
			return this.available.poll();
		}
	}

	@State(Scope.Thread)
	public static class Local {
		Counter cnt;

		@Setup
		public void setup(Shared shared) {
			this.cnt = shared.getMine();
		}
	}

	@Benchmark
	public int test(Local local) {
		return local.cnt.inc();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_29_StatesDAG.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}

}
