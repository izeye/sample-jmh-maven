package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_17_SyncIterations.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHSample_17_SyncIterations {

	private double src;

	@Benchmark
	public double test() {
		double s = this.src;
		for (int i = 0; i < 1000; i++) {
			s = Math.sin(s);
		}
		return s;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_17_SyncIterations.class.getSimpleName())
				.warmupIterations(1)
				.measurementIterations(20)
				.threads(Runtime.getRuntime().availableProcessors() * 16)
				.forks(1)
				.syncIterations(true)
				.build();

		new Runner(opt).run();
	}

}
