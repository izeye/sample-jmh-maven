package com.izeye.samples;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_01_HelloWorld.
 *
 * @author Johnny Lim
 */
public class JMHSample_01_HelloWorld {

	@Benchmark
	public void wellHelloThere() {
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_01_HelloWorld.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
