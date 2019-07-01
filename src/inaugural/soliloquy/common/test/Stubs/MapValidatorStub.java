package inaugural.soliloquy.common.test.stubs;
import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.game.IGame;
import soliloquy.specs.logger.ILogger;

public class MapValidatorStub implements IFunction<IPair<String,String>,String> {
	@Override
	public String id() {
		// Ignore; stub class
		return null;
	}

	@Override
	public String run(IPair<String, String> input) throws IllegalArgumentException {
		if (input.getItem1().equals("Key1")) return null;
		return "Input key (" + input.getItem1() + ") not equal to Key1";
	}

	@Override
	public String getInterfaceName() {
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

	@Override
	public String getUnparameterizedInterfaceName() {
		// Ignore; stub class
		return null;
	}

	@Override
	public IGame game() {
		// Ignore; stub class
		return null;
	}

	@Override
	public ILogger logger() {
		// Ignore; stub class
		return null;
	}
}
