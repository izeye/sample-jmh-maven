package com.izeye.samples;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.AuxCounters;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_23_AuxCounters.
 *
 * @author Johnny Lim
 */
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JMHSample_23_AuxCounters {

	@State(Scope.Thread)
	@AuxCounters(AuxCounters.Type.OPERATIONS)
	public static class OpCounters {
		public int case1;
		public int case2;

		public int total() {
			return this.case1 + this.case2;
		}
	}

	@State(Scope.Thread)
	@AuxCounters(AuxCounters.Type.EVENTS)
	public static class EventCounters {
		public int wows;
	}

	@Benchmark
	public void splitBranch(OpCounters counters) {
		if (Math.random() < 0.1) {
			counters.case1++;
		}
		else {
			counters.case2++;
		}
	}

	@Benchmark
	public void runSETI(EventCounters counters) {
		float random = (float) Math.random();
		float wowSignal = (float) (Math.PI / 4);
		if (random == wowSignal) {
			counters.wows++;
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_23_AuxCounters.class.getSimpleName())
				.build();

		new Runner(opt).run();
	}

}
