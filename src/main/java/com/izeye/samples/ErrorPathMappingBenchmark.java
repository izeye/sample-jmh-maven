package com.izeye.samples;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.UnsatisfiedDependencyException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * A sample benchmark from:
 * https://github.com/spring-projects/spring-boot/pull/7010#issuecomment-254811986
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ErrorPathMappingBenchmark {

	@State(Scope.Benchmark)
	public static class BenchmarkState {

		private final Map<Class<?>, String> exceptions = new HashMap<>();
		private final Map<Class<?>, Class<?>> superTypes = new HashMap<>();

		public BenchmarkState() {
			this.exceptions.put(Exception.class, "/500");
			this.superTypes.put(UnsatisfiedDependencyException.class, Exception.class);
		}
	}

	@Benchmark
	public String deepException(BenchmarkState state) {
		Class<?> type = UnsatisfiedDependencyException.class;
		return getErrorPath(state, type);
	}

	@Benchmark
	public String deepExceptionUsingSuperTypes(BenchmarkState state) {
		Class<?> type = UnsatisfiedDependencyException.class;
		return getErrorPathUsingSuperTypes(state, type);
	}

	@Benchmark
	public String shallowException(BenchmarkState state) {
		Class<?> type = Exception.class;
		return getErrorPath(state, type);
	}

	private String getErrorPathUsingSuperTypes(BenchmarkState state, Class<?> type) {
		Class<?> superType = state.superTypes.get(type);
		if (superType != null) {
			return state.exceptions.get(superType);
		}
		while (type != Object.class) {
			String path = state.exceptions.get(type);
			if (path != null) {
				return path;
			}
			type = type.getSuperclass();
		}
		return "global";
	}

	private String getErrorPath(BenchmarkState state, Class<?> type) {
		while (type != Object.class) {
			String path = state.exceptions.get(type);
			if (path != null) {
				return path;
			}
			type = type.getSuperclass();
		}
		return "global";
	}

}
