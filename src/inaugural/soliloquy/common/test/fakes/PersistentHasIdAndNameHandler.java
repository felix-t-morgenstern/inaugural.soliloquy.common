package inaugural.soliloquy.common.test.fakes;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;

// TODO: This is WAY too much of an implementation; refactor this away!
public class PersistentHasIdAndNameHandler extends AbstractTypeHandler<FakeHasIdAndName>
        implements TypeHandler<FakeHasIdAndName> {
    public final static String ARCHETYPE_ID = "ArchetypeId";
    public final static String ARCHETYPE_NAME = "ArchetypeName";
    public final static FakeHasIdAndName ARCHETYPE =
            new FakeHasIdAndName(ARCHETYPE_ID, ARCHETYPE_NAME);

    public PersistentHasIdAndNameHandler() {
        super(ARCHETYPE);
    }

    @Override
    public FakeHasIdAndName read(String s) throws IllegalArgumentException {
        HasIdAndNameStubDTO dto = JSON.fromJson(s, HasIdAndNameStubDTO.class);
        return new FakeHasIdAndName(dto.id, dto.name);
    }

    @Override
    public String write(FakeHasIdAndName hasIdAndNameStub) {
        HasIdAndNameStubDTO dto = new HasIdAndNameStubDTO();
        dto.id = hasIdAndNameStub.id();
        dto.name = hasIdAndNameStub.getName();
        return JSON.toJson(dto);
    }

    @Override
    public FakeHasIdAndName getArchetype() {
        return ARCHETYPE;
    }

    private static class HasIdAndNameStubDTO {
        String id;
        String name;
    }
}
