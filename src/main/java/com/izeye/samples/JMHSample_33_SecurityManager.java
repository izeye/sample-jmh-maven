package com.izeye.samples;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.Policy;
import java.security.URIParameter;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMHSample_33_SecurityManager.
 *
 * @author Johnny Lim
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_33_SecurityManager {

	@State(Scope.Benchmark)
	public static class SecurityManagerInstalled {
		@Setup
		public void setup() throws URISyntaxException, NoSuchAlgorithmException {
			URI policyFile = getClass().getResource("/jmh-security.policy").toURI();
			Policy.setPolicy(Policy.getInstance("JavaPolicy", new URIParameter(policyFile)));
			System.setSecurityManager(new SecurityManager());
		}

		@TearDown
		public void tearDown() {
			System.setSecurityManager(null);
		}
	}

	@State(Scope.Benchmark)
	public static class SecurityManagerEmpty {
		@Setup
		public void setup() {
			System.setSecurityManager(null);
		}
	}

	@Benchmark
	public String testWithSM(SecurityManagerInstalled s) {
		return System.getProperty("java.home");
	}

	@Benchmark
	public String testWithoutSM(SecurityManagerEmpty s) {
		return System.getProperty("java.home");
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHSample_33_SecurityManager.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
