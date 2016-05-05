package randoop.contract;

import randoop.Globals;

/**
 * Checks that calling toString() on an object does not throw an exception.
 */
public final class ToStringReturnsNormally implements ObjectContract {

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		return o instanceof ToStringReturnsNormally;
	}

	@Override
	public int hashCode() {
		int h = 51;
		return h; // no state to compare.
	}

	@Override
	public boolean evaluate(Object... objects) {
		assert objects != null && objects.length == 1;
		Object o = objects[0];
		assert o != null;
		o.toString();
		return true;
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public String toCommentString() {
		return "x0.toString() throws no Exception.";
	}

	@Override
	public String get_observer_str() {
		return "toString throws no Exception";
	}

	@Override
	public boolean evalExceptionMeansFailure() {
		return true;
	}

	@Override
	public String toCodeString() {
		StringBuilder b = new StringBuilder();
		b.append(Globals.lineSep);
		b.append("// Checks the contract: ");
		b.append(" " + toCommentString() + Globals.lineSep);
		b.append("org.junit.Assert.assertTrue(");
		b.append("\"Contract failed: " + toCommentString() + "\", ");
		b.append("x0.toString()");
		b.append(");");
		return b.toString();
	}
}
