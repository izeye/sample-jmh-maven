package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_09_Blackholes.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class JMHSample_09_Blackholes {

	double x1 = Math.PI;
	double x2 = Math.PI * 2;

	@Benchmark
	public double baseline() {
		return Math.log(this.x1);
	}

	@Benchmark
	public double measureWrong() {
		Math.log(this.x1);
		return Math.log(this.x2);
	}

	@Benchmark
	public double measureRight_1() {
		return Math.log(this.x1) + Math.log(this.x2);
	}

	@Benchmark
	public void measureRight_2(Blackhole bh) {
		bh.consume(Math.log(this.x1));
		bh.consume(Math.log(this.x2));
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_09_Blackholes.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
