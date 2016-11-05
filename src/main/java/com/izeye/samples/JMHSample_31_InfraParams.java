package com.izeye.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.ThreadParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_31_InfraParams.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JMHSample_31_InfraParams {

	static final int THREAD_SLICE = 1000;

	private ConcurrentHashMap<String, String> mapSingle;
	private ConcurrentHashMap<String, String> mapFollowThreads;

	@Setup
	public void setup(BenchmarkParams params) {
		int capacity = 16 * THREAD_SLICE * params.getThreads();
		this.mapSingle = new ConcurrentHashMap<>(capacity, 0.75f, 1);
		this.mapFollowThreads = new ConcurrentHashMap<>(capacity, 0.75f, params.getThreads());
	}

	@State(Scope.Thread)
	public static class Ids {
		private List<String> ids;

		@Setup
		public void setup(ThreadParams params) {
			this.ids = new ArrayList<>();
			for (int c = 0; c < THREAD_SLICE; c++) {
				this.ids.add("ID" + (THREAD_SLICE * params.getThreadCount() + c));
			}
		}
	}

	@Benchmark
	public void measureDefault(Ids ids) {
		for (String s : ids.ids) {
			this.mapSingle.remove(s);
			this.mapSingle.put(s, s);
		}
	}

	@Benchmark
	public void measureFollowThreads(Ids ids) {
		for (String s : ids.ids) {
			this.mapFollowThreads.remove(s);
			this.mapFollowThreads.put(s, s);
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_30_Interrupts.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.threads(4)
				.forks(5)
				.build();

		new Runner(opt).run();
	}

}
