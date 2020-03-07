package inaugural.soliloquy.common.test.fakes;

import com.google.gson.Gson;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class PersistentHasIdAndNameHandler extends PersistentTypeHandler<FakeHasIdAndName>
        implements PersistentValueTypeHandler<FakeHasIdAndName> {
    public final static String ARCHETYPE_ID = "ArchetypeId";
    public final static String ARCHETYPE_NAME = "ArchetypeName";
    public final static FakeHasIdAndName ARCHETYPE = new FakeHasIdAndName(ARCHETYPE_ID, ARCHETYPE_NAME);

    @Override
    public FakeHasIdAndName read(String s) throws IllegalArgumentException {
        HasIdAndNameStubDTO dto = new Gson().fromJson(s, HasIdAndNameStubDTO.class);
        return new FakeHasIdAndName(dto.id, dto.name);
    }

    @Override
    public String write(FakeHasIdAndName hasIdAndNameStub) {
        HasIdAndNameStubDTO dto = new HasIdAndNameStubDTO();
        dto.id = hasIdAndNameStub.id();
        dto.name = hasIdAndNameStub.getName();
        return new Gson().toJson(dto);
    }

    @Override
    public FakeHasIdAndName getArchetype() {
        return ARCHETYPE;
    }

    private class HasIdAndNameStubDTO
    {
        String id;
        String name;
    }
}
