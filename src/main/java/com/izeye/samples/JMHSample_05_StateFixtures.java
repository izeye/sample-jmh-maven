package com.izeye.samples;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_05_StateFixtures.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
public class JMHSample_05_StateFixtures {

	double x;

	@Setup
	public void prepare() {
		x = Math.PI;
	}

	@TearDown
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
				.include(JMHSample_05_StateFixtures.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.jvmArgs("-ea")
				.build();

		new Runner(opt).run();
	}

}
