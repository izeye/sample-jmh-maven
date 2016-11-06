package com.izeye.samples;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
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
 * JMHSample_36_BranchPrediction.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Benchmark)
public class JMHSample_36_BranchPrediction {

	private static final int COUNT = 1024 * 1024;

	private byte[] sorted;
	private byte[] unsorted;

	@Setup
	public void setup() {
		this.sorted = new byte[COUNT];
		this.unsorted = new byte[COUNT];
		Random random = new Random(1234);
		random.nextBytes(this.sorted);
		random.nextBytes(this.unsorted);
		Arrays.sort(this.sorted);
	}

	@Benchmark
	@OperationsPerInvocation(COUNT)
	public void sorted(Blackhole bh1, Blackhole bh2) {
		for (byte v : this.sorted) {
			if (v > 0) {
				bh1.consume(v);
			}
			else {
				bh2.consume(v);
			}
		}
	}

	@Benchmark
	@OperationsPerInvocation(COUNT)
	public void unsorted(Blackhole bh1, Blackhole bh2) {
		for (byte v : this.unsorted) {
			if (v > 0) {
				bh1.consume(v);
			}
			else {
				bh2.consume(v);
			}
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_36_BranchPrediction.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}

}
