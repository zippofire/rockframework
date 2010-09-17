/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.collection;

import java.util.Queue;

public class DelegateQueue<E> extends DelegateCollection<E> implements Queue<E> {

	private Queue<E>	queue;

	public DelegateQueue(final Queue<E> queue) {
		super(queue);
		this.queue = queue;
	}

	@Override
	public E element() {
		return this.queue.element();
	}

	@Override
	public boolean equals(final Object o) {
		return this.queue.equals(o);
	}

	@Override
	public int hashCode() {
		return this.queue.hashCode();
	}

	@Override
	public boolean offer(final E e) {
		return this.queue.offer(e);
	}

	@Override
	public E peek() {
		return this.queue.peek();
	}

	@Override
	public E poll() {
		return this.queue.poll();
	}

	@Override
	public E remove() {
		return this.queue.remove();
	}

}
