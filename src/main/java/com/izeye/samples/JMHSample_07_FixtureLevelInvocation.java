package com.izeye.samples;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_07_FixtureLevelInvocation.
 *
 * @author Johnny Lim
 */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JMHSample_07_FixtureLevelInvocation {

	@State(Scope.Benchmark)
	public static class NormalState {
		ExecutorService service;

		@Setup(Level.Trial)
		public void up() {
			this.service = Executors.newCachedThreadPool();
		}

		@TearDown(Level.Trial)
		public void down() {
			this.service.shutdown();
		}
	}

	public static class LaggingState extends NormalState {
		public static final int SLEEP_TIME = Integer.getInteger("sleepTime", 10);

		@Setup(Level.Invocation)
		public void lag() throws InterruptedException {
			TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
		}
	}

	@State(Scope.Thread)
	public static class Scratch {
		private double p;

		public double doWork() {
			this.p = Math.log(this.p);
			return this.p;
		}
	}

	public static class Task implements Callable<Double> {
		private Scratch s;

		public Task(Scratch s) {
			this.s = s;
		}

		@Override
		public Double call() throws Exception {
			return this.s.doWork();
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public double measureHot(NormalState e, final Scratch s) throws ExecutionException, InterruptedException {
		return e.service.submit(new Task(s)).get();
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public double measureCold(LaggingState e, final Scratch s) throws ExecutionException, InterruptedException {
		return e.service.submit(new Task(s)).get();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_07_FixtureLevelInvocation.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
