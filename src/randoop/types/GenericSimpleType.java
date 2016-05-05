package randoop.types;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a type variable used by itself as a type. Could occur as a return
 * type, a method/constructor parameter type, a field type, or the type of an
 * array.
 */
class GenericSimpleType extends GenericType {

	/** the type parameter of the simple type */
	private TypeVariable<?> parameter;

	/** the (upper) bound on the type parameter */
	private TypeBound bound;

	/**
	 * Create a {@code GenericSimpleType} for the given type parameter.
	 *
	 * @param parameter
	 *            the type parameter
	 */
	GenericSimpleType(TypeVariable<?> parameter) throws RandoopTypeException {
		this.parameter = parameter;
		this.bound = TypeBound.fromTypes(new SupertypeOrdering(), parameter.getBounds());
	}

	/**
	 * {@inheritDoc} Checks that the type parameter is equal. This may be more
	 * restrictive than desired because equivalent TypeVariable objects from
	 * different instances of the same type may be distinct.
	 * 
	 * @return true if the type parameters are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GenericSimpleType)) {
			return false;
		}
		GenericSimpleType t = (GenericSimpleType) obj;
		return parameter.equals(t.parameter);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parameter, bound);
	}

	@Override
	public String toString() {
		return parameter.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return runtime class of the bound of this type
	 */
	@Override
	public Class<?> getRuntimeClass() {
		return bound.getRuntimeClass();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return name of type parameter of this type
	 */
	@Override
	public String getName() {
		return parameter.getName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the singleton list with type parameter bound for this type
	 */
	@Override
	public List<TypeBound> getBounds() {
		List<TypeBound> l = new ArrayList<>();
		l.add(bound);
		return l;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the type argument if it satisfies the type parameter bound
	 * @throws IllegalArgumentException
	 *             if type arguments are null, the number of arguments is not
	 *             null, or the type argument does not match the parameter bound
	 */
	@Override
	public ConcreteType instantiate(ConcreteType... typeArguments) throws RandoopTypeException {
		if (typeArguments == null) {
			throw new IllegalArgumentException("type arguments must be non-null");
		}
		if (typeArguments.length != 1) {
			throw new IllegalArgumentException("only one type argument expected");
		}
		List<TypeParameter> parameters = new ArrayList<>();
		parameters.add(new TypeParameter(parameter, bound));
		Substitution substitution = Substitution.forArgs(parameters, typeArguments);
		if (!bound.isSatisfiedBy(typeArguments[0], substitution)) {
			throw new IllegalArgumentException("type argument does not match parameter bound");
		}
		return typeArguments[0];
	}

	/**
	 * Instantiates this generic type with the substitution.
	 *
	 * @param substitution
	 *            the type substitution
	 * @return the {@code ConcreteType} for the type parameter
	 * @throws IllegalArgumentException
	 *             if the substitution is null, or the type argument does not
	 *             match the parameter bound.
	 */
	@Override
	public GeneralType apply(Substitution substitution) throws RandoopTypeException {
		if (substitution == null) {
			throw new IllegalArgumentException("substitution must be non-null");
		}
		ConcreteType concreteType = substitution.get(parameter);
		if (concreteType == null) {
			return this;
		}
		if (!bound.isSatisfiedBy(concreteType, substitution)) {
			throw new IllegalArgumentException("type argument from substitution does not match parameter bound");
		}
		return concreteType;
	}

}
