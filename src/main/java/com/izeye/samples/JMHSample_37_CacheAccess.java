package com.izeye.samples;

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
 * JMHSample_37_CacheAccess.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Benchmark)
public class JMHSample_37_CacheAccess {

	private final static int COUNT = 4096;
	private final static int MATRIX_SIZE = COUNT * COUNT;

	private int[][] matrix;

	@Setup
	public void setup() {
		this.matrix = new int[COUNT][COUNT];
		Random random = new Random(1234);
		for (int i = 0; i < COUNT; i++) {
			for (int j = 0; j < COUNT; j++) {
				this.matrix[i][j] = random.nextInt();
			}
		}
	}

	@Benchmark
	@OperationsPerInvocation(MATRIX_SIZE)
	public void colFirst(Blackhole bh) {
		for (int c = 0; c < COUNT; c++) {
			for (int r = 0; r < COUNT; r++) {
				bh.consume(matrix[r][c]);
			}
		}
	}

	@Benchmark
	@OperationsPerInvocation(MATRIX_SIZE)
	public void rowFirst(Blackhole bh) {
		for (int r = 0; r < COUNT; r++) {
			for (int c = 0; c < COUNT; c++) {
				bh.consume(matrix[r][c]);
			}
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_37_CacheAccess.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}

}
