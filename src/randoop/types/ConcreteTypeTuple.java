package randoop.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import plume.UtilMDE;

/**
 * {@code ConcreteTypeTuple} represents an ordered tuple of {@link ConcreteType}
 * objects, primarily as the input types of
 * {@link randoop.operation.ConcreteOperation #ConcreteOperation} objects.
 */
public class ConcreteTypeTuple implements GeneralTypeTuple {

	/** The ordered sequence of {@link randoop.types.ConcreteType} objects */
	private ArrayList<ConcreteType> list;

	/**
	 * Creates a tuple of concrete types from the given list with the same order
	 * and cardinality.
	 *
	 * @param list
	 *            the list of {@link ConcreteType} objects
	 */
	public ConcreteTypeTuple(List<ConcreteType> list) {
		this.list = new ArrayList<>(list);
	}

	/**
	 * Creates an empty tuple.
	 */
	public ConcreteTypeTuple() {
		list = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConcreteTypeTuple)) {
			return false;
		}
		ConcreteTypeTuple tuple = (ConcreteTypeTuple) obj;
		return list.equals(tuple.list);
	}

	@Override
	public int hashCode() {
		return Objects.hash(list);
	}

	/**
	 * Returns the arity of the tuple.
	 *
	 * @return the arity of this tuple
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Returns the ith component of this tuple.
	 *
	 * @param i
	 *            the component index
	 * @return the {@link ConcreteType} at index i
	 */
	@Override
	public ConcreteType get(int i) {
		assert 0 <= i && i < list.size();
		return list.get(i);
	}

	/**
	 * Indicates whether this is the empty tuple.
	 *
	 * @return true if the tuple is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isGeneric() {
		return false;
	}

	@Override
	public GeneralTypeTuple apply(Substitution substitution) {
		return this;
	}

	@Override
	public String toString() {
		return "(" + UtilMDE.join(list, ", ") + ")";
	}
}
