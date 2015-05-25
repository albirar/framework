/*
 * This file is part of "albirar-framework".
 * 
 * "albirar-framework" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar-framework" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés ofornes@albirar.cat
 */

package cat.albirar.framework.dynabean.visitor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A {@link IDynaBeanVisitor} implementation for chaining visitors as a {@link List}.
 * The list is executed entirely, no control of returned values are made.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DynaBeanVisitorListDefault<C extends IDynaBeanVisitor> implements IDynaBeanVisitor, List<C> {
	private List<C> visitors;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object eventGet(String name, Object value) {
		if(visitors != null) {
			for(C visitor: visitors) {
				value = visitor.eventGet(name, value);
			}
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object eventSet(String name, Object value) {
		if(visitors != null) {
			for(C visitor: visitors) {
				value = visitor.eventSet(name, value);
			}
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return visitors.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return visitors.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(Object o) {
		return visitors.contains(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<C> iterator() {
		return visitors.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		return visitors.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T[] toArray(T[] a) {
		return visitors.toArray(a);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean add(C e) {
		return visitors.add(e);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(Object o) {
		return visitors.remove(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsAll(Collection<?> c) {
		return visitors.containsAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(Collection<? extends C> c) {
		return visitors.addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(int index, Collection<? extends C> c) {
		return visitors.addAll(index, c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeAll(Collection<?> c) {
		return visitors.removeAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean retainAll(Collection<?> c) {
		return visitors.retainAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		visitors.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		return visitors.equals(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return visitors.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public C get(int index) {
		return visitors.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public C set(int index, C element) {
		return visitors.set(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(int index, C element) {
		visitors.add(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	public C remove(int index) {
		return visitors.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public int indexOf(Object o) {
		return visitors.indexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public int lastIndexOf(Object o) {
		return visitors.lastIndexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public ListIterator<C> listIterator() {
		return visitors.listIterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public ListIterator<C> listIterator(int index) {
		return visitors.listIterator(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<C> subList(int fromIndex, int toIndex) {
		return visitors.subList(fromIndex, toIndex);
	}
}
