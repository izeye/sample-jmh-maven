package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_13_RunToRun.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHSample_13_RunToRun {

	@State(Scope.Thread)
	public static class SleepyState {
		public long sleepTime;

		@Setup
		public void setup() {
			this.sleepTime = (long) (Math.random() * 1000);
		}
	}

	@Benchmark
	@Fork(1)
	public void baseline(SleepyState s) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(s.sleepTime);
	}

	@Benchmark
	@Fork(5)
	public void fork_1(SleepyState s) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(s.sleepTime);
	}

	@Benchmark
	@Fork(20)
	public void fork_2(SleepyState s) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(s.sleepTime);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_13_RunToRun.class.getSimpleName())
				.warmupIterations(0)
				.measurementIterations(3)
				.build();

		new Runner(opt).run();
	}

}
