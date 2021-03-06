package randoop.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class OneMoreElementList<T> extends SimpleList<T> implements Serializable {

	private static final long serialVersionUID = 1332963552183905833L;

	public final T lastElement;
	public final SimpleList<T> list;
	public final int size;

	public OneMoreElementList(SimpleList<T> list, T extraElement) {
		this.list = list;
		this.lastElement = extraElement;
		this.size = list.size() + 1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public T get(int index) {
		if (index < size - 1)
			return list.get(index);
		if (index == size - 1)
			return lastElement;
		throw new IndexOutOfBoundsException("No such element: " + index);
	}

	@Override
	public SimpleList<T> getSublist(int index) {
		if (index == size - 1) { // is lastElement
			return this;
		}
		if (index < size - 1) {
			return list.getSublist(index);
		}
		throw new IndexOutOfBoundsException("No such index: " + index);
	}

	@Override
	public List<T> toJDKList() {
		List<T> result = new ArrayList<T>();
		result.addAll(list.toJDKList());
		result.add(lastElement);
		return result;
	}

	@Override
	public String toString() {
		return toJDKList().toString();
	}
}
