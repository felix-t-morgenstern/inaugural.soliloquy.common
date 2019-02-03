package inaugural.soliloquy.common.test.java.Stubs;

import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IPair;

public class MapValidatorStub implements IFunction<IPair<String,String>,String> {
	@Override
	public String id() {
		// Ignore; stub class
		return null;
	}

	@Override
	public String run(IPair<String, String> input) throws IllegalArgumentException {
		if (input.getItem1() == "Key1") return null;
		return "Input key (" + input.getItem1() + ") not equal to Key1";
	}

	@Override
	public String getParameterizedClassName() {
		// Ignore; stub class
		return null;
	}

	@Override
	public IPair<String, String> getFirstArchetype() {
		// Ignore; stub class
		return null;
	}

	@Override
	public String getSecondArchetype() {
		// Ignore; stub class
		return null;
	}
}
