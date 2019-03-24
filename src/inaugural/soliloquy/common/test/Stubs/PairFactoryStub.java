package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;

public class PairFactoryStub implements IPairFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2) throws IllegalArgumentException {
		return new PairStub(item1, item2);
	}

	@Override
	public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
			throws IllegalArgumentException {
		// Stub class; not implemented
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}
