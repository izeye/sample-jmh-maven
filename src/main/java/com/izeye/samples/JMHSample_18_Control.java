package com.izeye.samples;

import java.util.concurrent.atomic.AtomicBoolean;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Control;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_18_Control.
 *
 * @author Johnny Lim
 */
@State(Scope.Group)
public class JMHSample_18_Control {

	public final AtomicBoolean flag = new AtomicBoolean();

	@Benchmark
	@Group("pingpong")
	public void ping(Control cnt) {
		while (!cnt.stopMeasurement && !this.flag.compareAndSet(false, true)) {
		}
	}

	@Benchmark
	@Group("pingpong")
	public void pong(Control cnt) {
		while (!cnt.stopMeasurement && !this.flag.compareAndSet(true, false)) {
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_18_Control.class.getSimpleName())
				.warmupIterations(1)
				.measurementIterations(5)
				.threads(2)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
