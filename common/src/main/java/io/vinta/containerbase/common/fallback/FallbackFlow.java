package io.vinta.containerbase.common.fallback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Getter;

@Getter
public class FallbackFlow<T, R> {
	private final List<FallbackStep<T, R>> fallbackSteps;

	private FallbackFlow(List<FallbackStep<T, R>> fallbackSteps) {
		this.fallbackSteps = fallbackSteps != null ? fallbackSteps : List.of();
	}

	private R execute(T param) {
		return fallbackSteps.stream()
				.filter(step -> step.condition()
						.test(param))
				.findFirst()
				.map(step -> step.supplier()
						.apply(param))
				.orElse(null);
	}

	public static <T, R> Builder<T, R> builder() {
		return new Builder<>();
	}

	public static class Builder<T, R> {

		private final List<FallbackStep<T, R>> fallbackSteps = new ArrayList<>();

		public Builder<T, R> addFallBack(Predicate<T> condition, Function<T, R> supplier) {
			fallbackSteps.add(new FallbackStep<>(condition, supplier));
			return this;
		}

		public R execute(T param) {
			return (new FallbackFlow<>(fallbackSteps)).execute(param);
		}
	}

	private record FallbackStep<T, R>(Predicate<T> condition,
			Function<T, R> supplier) {
	}
}
