package randoop.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import randoop.BugInRandoopException;
import randoop.reflection.ReflectionPredicate;
import randoop.sequence.Variable;
import randoop.types.GeneralType;

/**
 * AccessibleField represents an accessible field of a class object, which can
 * be an instance field, a static field, or a static final field. Meant to be
 * adapted by either {@link randoop.operation.FieldSet FieldSet} or
 * {@link randoop.operation.FieldGet FieldGet} for use as a
 * {@link randoop.operation.Operation Operation}.
 */
public class AccessibleField {

	private Field field;
	private final GeneralType declaringType;
	private boolean isFinal;
	private boolean isStatic;

	/**
	 * Create the public field object for the given {@code Field}.
	 *
	 * @param field
	 *            the field.
	 */
	public AccessibleField(Field field, GeneralType declaringType) {
		this.field = field;
		this.field.setAccessible(true);
		int mods = field.getModifiers() & Modifier.fieldModifiers();
		this.isFinal = Modifier.isFinal(mods);
		this.isStatic = Modifier.isStatic(mods);
		this.declaringType = declaringType;
	}

	/**
	 * Returns the declared name of the field.
	 *
	 * @return unqualified name of the field.
	 */
	public String getName() {
		return field.getName();
	}

	/**
	 * Translates field into a string representing fully qualified name.
	 *
	 * @param declaringType
	 *            the declaring type for this field
	 * @param inputVars
	 *            list of input variables
	 * @return string representing code representation of field.
	 */
	public String toCode(GeneralType declaringType, List<Variable> inputVars) {
		StringBuilder sb = new StringBuilder();
		if (isStatic) {
			sb.append(declaringType.getName());
		} else {
			sb.append(inputVars.get(0).getName());
		}
		return sb.append(".").append(getName()).toString();
	}

	/**
	 * Returns a string descriptor of a field that can be parsed by
	 * {@link FieldParser#parse(String, String, String)}.
	 *
	 * @return String for type-field pair describing field.
	 */
	public String toParseableString(GeneralType declaringType) {
		return declaringType.getName() + "." + field.getName();
	}

	/**
	 * Returns string representation of underlying
	 * {@link java.lang.reflect.Field} object.
	 */
	@Override
	public String toString() {
		return field.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AccessibleField) {
			AccessibleField f = (AccessibleField) obj;
			return this.field.equals(f.field);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return field.hashCode();
	}

	/**
	 * Uses reflection to return the value of the field for the given object.
	 * Suppresses exceptions that occur because PublicField was not correctly
	 * initialized.
	 *
	 * @param object
	 *            - instance to which field belongs, or null if field is static.
	 * @return reference to value of field.
	 * @throws BugInRandoopException
	 *             if field access throws {@link IllegalArgumentException} or
	 *             {@link IllegalAccessException}.
	 */
	public Object getValue(Object object) {
		Object ret;
		try {
			ret = field.get(object);
		} catch (IllegalArgumentException e) {
			throw new BugInRandoopException("Field access to object of wrong type: " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BugInRandoopException(
					"Access control violation for field: " + field.getName() + "; " + e.getMessage());
		}
		return ret;
	}

	/**
	 * Uses reflection to set the value of the field for the given object.
	 * Suppresses exceptions that occur because setup was incorrect.
	 *
	 * @param object
	 *            - instance to which field belongs, or null if static.
	 * @param value
	 *            - new value to assign to field
	 * @throws BugInRandoopException
	 *             if field access throws {@link IllegalArgumentException} or
	 *             {@link IllegalAccessException}.
	 */
	public void setValue(Object object, Object value) {
		assert !isFinal : "cannot set a final field";
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new BugInRandoopException("Field set to object of wrong type: " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BugInRandoopException("Access control violation for field: " + e.getMessage());
		}
	}

	/**
	 * isStatic returns the default that a field is not static.
	 *
	 * @return false (default for a field).
	 */
	public boolean isStatic() {
		return isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * satisfies checks whether the enclosed {@link Field} object satisfies the
	 * given predicate.
	 *
	 * @param predicate
	 *            the {@link ReflectionPredicate} to check this.field against.
	 * @return true if this.field satisfies predicate.canUse(field).
	 */
	public boolean satisfies(ReflectionPredicate predicate) {
		return predicate.test(field);
	}

	public Field getRawField() {
		return field;
	}

	public GeneralType getDeclaringType() {
		return declaringType;
	}
}
