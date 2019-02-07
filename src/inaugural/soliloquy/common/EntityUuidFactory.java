package inaugural.soliloquy.common;

import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IEntityUuidFactory;

public class EntityUuidFactory implements IEntityUuidFactory {

	@Override
	public IEntityUuid createFromLongs(long mostSignificantBits, long leastSignificantBits) {
		return new EntityUuid(mostSignificantBits, leastSignificantBits);
	}

	@Override
	public IEntityUuid createFromString(String uuidString) throws IllegalArgumentException {
		return new EntityUuid(uuidString);
	}

	@Override
	public IEntityUuid createRandomEntityUuid() {
		return new EntityUuid();
	}

	@Override
	public String getInterfaceName() {
		return IEntityUuidFactory.class.getCanonicalName();
	}
}
