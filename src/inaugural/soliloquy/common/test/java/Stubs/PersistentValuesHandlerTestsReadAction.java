package inaugural.soliloquy.common.test.java.Stubs;

import java.util.ArrayList;
import java.util.Collection;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentValueToWrite;

public class PersistentValuesHandlerTestsReadAction implements IAction<IPair<IPersistentValueToWrite<?>, Boolean>> {
	public static Collection<IPersistentValueToWrite<?>> _results = new ArrayList<IPersistentValueToWrite<?>>();
	public static Boolean _overrideData;

	@Override
	public String id() {
		// Not needed for test stub
		return null;
	}

	@Override
	public void run(IPair<IPersistentValueToWrite<?>, Boolean> input) throws IllegalArgumentException {
		_overrideData = input.getItem2();
		_results.add(input.getItem1());
	}

	@Override
	public String getInterfaceName() {
		// Not needed for test stub
		return null;
	}

	@Override
	public IPair<IPersistentValueToWrite<?>, Boolean> getArchetype() {
		// Not needed for test stub
		return null;
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		// Not needed for test stub
		return null;
	}

}
