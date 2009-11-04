package net.woodstock.rockframework.domain;

import net.woodstock.rockframework.utils.ObjectUtils;

public class ValueObject<T> implements Pojo {

	private static final long	serialVersionUID	= 5096193480689528838L;

	private T					value;

	public ValueObject(T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	// Object
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof ValueObject<?>) {
			return false;
		}

		ValueObject<?> other = (ValueObject<?>) obj;
		if ((this.value == null) && (other.value != null)) {
			return false;
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = ObjectUtils.HASH_PRIME;
		int result = 1;
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValueObject[");
		builder.append(this.value);
		builder.append("]");
		return builder.toString();
	}

}
