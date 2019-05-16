package inaugural.soliloquy.common.test.stubs;

import java.util.ArrayList;
import java.util.Collection;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

public class PersistentValuesHandlerTestsReadAction implements IAction<IPersistentValueToWrite> {
	public static Collection<IPersistentValueToWrite<?>> _results = new ArrayList<IPersistentValueToWrite<?>>();

	@Override
	public String id() {
		// Not needed for test stub
		return null;
	}

	@Override
	public void run(IPersistentValueToWrite input) throws IllegalArgumentException {
		_results.add(input);
	}

	@Override
	public String getInterfaceName() {
		// Not needed for test stub
		return null;
	}

	@Override
	public IPersistentValueToWrite getArchetype() {
		// Not needed for test stub
		return null;
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		// Not needed for test stub
		return null;
	}

	@Override
	public IGame game() {
		// Not needed for test stub
		return null;
	}

	@Override
	public ILogger logger() {
		// Not needed for test stub
		return null;
	}

}
