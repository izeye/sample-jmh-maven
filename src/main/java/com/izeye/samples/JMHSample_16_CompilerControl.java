package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_16_CompilerControl.
 *
 * @author Johnny Lim
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_16_CompilerControl {

	public void target_blank() {
	}

	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	public void target_dontInline() {
	}

	@CompilerControl(CompilerControl.Mode.INLINE)
	public void target_inline() {
	}

	@CompilerControl(CompilerControl.Mode.EXCLUDE)
	public void target_exclude() {
	}

	@Benchmark
	public void baseline() {
	}

	@Benchmark
	public void blank() {
		target_blank();
	}

	@Benchmark
	public void dontInline() {
		target_dontInline();
	}

	@Benchmark
	public void inline() {
		target_inline();
	}

	@Benchmark
	public void exclude() {
		target_exclude();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_16_CompilerControl.class.getSimpleName())
				.warmupIterations(1)
				.measurementIterations(3)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
