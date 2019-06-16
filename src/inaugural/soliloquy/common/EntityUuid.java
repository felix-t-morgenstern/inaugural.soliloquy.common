package inaugural.soliloquy.common;

import soliloquy.specs.common.valueobjects.IEntityUuid;

import java.util.UUID;

public class EntityUuid implements IEntityUuid {
	private UUID _uuid;

	public EntityUuid(long mostSignificantBits, long leastSignificantBits) {
		_uuid = new UUID(mostSignificantBits, leastSignificantBits);
	}

	public EntityUuid(String uuid) {
		_uuid = UUID.fromString(uuid);
	}

	public EntityUuid(){
		_uuid = UUID.randomUUID();
	}

	@Override
	public long getMostSignificantBits() {
		return _uuid.getMostSignificantBits();
	}

	@Override
	public long getLeastSignificantBits() {
		return _uuid.getLeastSignificantBits();
	}
	
	public String toString() {
		return _uuid.toString();
	}

	@Override
	public boolean equals(Object comparand) {
		return comparand != null 
				&& _uuid.getMostSignificantBits() == ((IEntityUuid)comparand).getMostSignificantBits() 
				&& _uuid.getLeastSignificantBits() == ((IEntityUuid)comparand).getLeastSignificantBits();
	}

	@Override
	public String getInterfaceName() {
		return IEntityUuid.class.getCanonicalName();
	}
}
