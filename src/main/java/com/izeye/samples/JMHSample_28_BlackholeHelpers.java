package com.izeye.samples;

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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_28_BlackholeHelpers.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class JMHSample_28_BlackholeHelpers {

	public interface Worker {
		void work();
	}

	private Worker workerBaseline;
	private Worker workerRight;
	private Worker workerWrong;

	@Setup
	public void setup(final Blackhole bh) {
		this.workerBaseline = new Worker() {
			double x;

			@Override
			public void work() {
			}
		};

		this.workerWrong = new Worker() {
			double x;

			@Override
			public void work() {
				Math.log(this.x);
			}
		};

		this.workerRight = new Worker() {
			double x;

			@Override
			public void work() {
				bh.consume(Math.log(this.x));
			}
		};
	}

	@Benchmark
	public void baseline() {
		this.workerBaseline.work();
	}

	@Benchmark
	public void measureWrong() {
		this.workerWrong.work();
	}

	@Benchmark
	public void measureRight() {
		this.workerRight.work();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_28_BlackholeHelpers.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}

}
