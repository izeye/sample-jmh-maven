package com.izeye.samples;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * Benchmark {@link SimpleDateFormat} vs. {@link DateTimeFormatter}.
 *
 * @author Johnny Lim
 */
public class DateTimeFormatterBenchmark {

	@State(Scope.Benchmark)
	public static class SimpleDateFormatState {

		private final Date dateTime;
		private final DateFormat formatter;

		public SimpleDateFormatState() {
			this.dateTime = new Date();
			this.formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		}

	}

	@State(Scope.Benchmark)
	public static class DateTimeFormatterState {

		private final LocalDateTime dateTime;
		private final DateTimeFormatter formatter;

		public DateTimeFormatterState() {
			this.dateTime = LocalDateTime.now();
			this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		}

	}

	@State(Scope.Benchmark)
	public static class DateTimeFormatterIsoDateTimeState {

		private final LocalDateTime dateTime;
		private final DateTimeFormatter formatter;

		public DateTimeFormatterIsoDateTimeState() {
			this.dateTime = LocalDateTime.now();
			this.formatter = DateTimeFormatter.ISO_DATE_TIME;
		}

	}

	@Benchmark
	public void benchmarkSimpleDateFormat(SimpleDateFormatState state) {
		state.formatter.format(state.dateTime);
	}

	@Benchmark
	public void benchmarkDateTimeFormatter(DateTimeFormatterState state) {
		state.formatter.format(state.dateTime);
	}

	@Benchmark
	public void benchmarkDateTimeFormatterIsoDateTime(DateTimeFormatterIsoDateTimeState state) {
		state.formatter.format(state.dateTime);
	}

}
