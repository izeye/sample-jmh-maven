package com.izeye.samples;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_06_FixtureLevel.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
public class JMHSample_06_FixtureLevel {

	double x;

	@TearDown(Level.Iteration)
	public void check() {
		assert x > Math.PI : "Nothing changed?";
	}

	@Benchmark
	public void measureRight() {
		x++;
	}

	@Benchmark
	public void measureWrong() {
		double x = 0;
		x++;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_06_FixtureLevel.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.jvmArgs("-ea")
				.shouldFailOnError(false)
				.build();

		new Runner(opt).run();
	}

}
