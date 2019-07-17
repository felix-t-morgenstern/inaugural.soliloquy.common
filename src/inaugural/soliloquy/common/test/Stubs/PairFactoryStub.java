package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;

public class PairFactoryStub implements PairFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T1, T2> Pair<T1, T2> make(T1 item1, T2 item2) throws IllegalArgumentException {
		return new PairStub(item1, item2);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T1, T2> Pair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
			throws IllegalArgumentException {
		return new PairStub(item1, item2, archetype1, archetype2);
	}

	@Override
	public String getInterfaceName() {
		// Stub method; unimplemented
		throw new UnsupportedOperationException();
	}
}
