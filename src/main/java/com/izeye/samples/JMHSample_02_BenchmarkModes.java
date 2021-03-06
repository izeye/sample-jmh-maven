package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_02_BenchmarkModes.
 *
 * @author Johnny Lim
 */
public class JMHSample_02_BenchmarkModes {

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void measureThroughput() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureAverageTime() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureSampleTime() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureSingleShotTime() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	@Benchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureMultiple() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	@Benchmark
	@BenchmarkMode(Mode.All)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureAll() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_02_BenchmarkModes.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
