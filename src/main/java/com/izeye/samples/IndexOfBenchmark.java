package com.izeye.samples;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * Benchmark for {@link String#indexOf(int)} vs. {@link String#indexOf(String)}.
 *
 * @author Johnny Lim
 */
@State(Scope.Benchmark)
public class IndexOfBenchmark {

	private String string;
	private char c;
	private String s;

	@Setup
	public void setup() {
		StringBuilder sb = new StringBuilder();
		for (char c = 'A'; c <= 'Z'; c++) {
			sb.append(c);
		}
		for (char c = 'a'; c <= 'z'; c++) {
			sb.append(c);
		}
		this.string = sb.toString();
		this.c = 'a';
		this.s = "a";
	}

	@Benchmark
	public int indexOfChar() {
		return string.indexOf('a');
	}

	@Benchmark
	public int indexOfString() {
		return string.indexOf("a");
	}

	@Benchmark
	public int indexOfCharViaVariable() {
		return string.indexOf(c);
	}

	@Benchmark
	public int indexOfStringViaVariable() {
		return string.indexOf(s);
	}

}
