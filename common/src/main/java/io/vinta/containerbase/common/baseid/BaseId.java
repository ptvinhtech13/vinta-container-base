package io.vinta.containerbase.common.baseid;

import java.util.Objects;

public abstract class BaseId<T> {

	private final T value;

	protected BaseId(T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseId<?> other = (BaseId<?>) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Id [value=" + value
				+ "]";
	}

}
