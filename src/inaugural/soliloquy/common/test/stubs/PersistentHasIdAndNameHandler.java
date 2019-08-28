package inaugural.soliloquy.common.test.stubs;

import com.google.gson.Gson;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class PersistentHasIdAndNameHandler extends PersistentTypeHandler<HasIdAndNameStub>
        implements PersistentValueTypeHandler<HasIdAndNameStub> {
    public final static String ARCHETYPE_ID = "ArchetypeId";
    public final static String ARCHETYPE_NAME = "ArchetypeName";
    public final static HasIdAndNameStub ARCHETYPE = new HasIdAndNameStub(ARCHETYPE_ID, ARCHETYPE_NAME);

    @Override
    public HasIdAndNameStub read(String s) throws IllegalArgumentException {
        HasIdAndNameStubDTO dto = new Gson().fromJson(s, HasIdAndNameStubDTO.class);
        return new HasIdAndNameStub(dto.id, dto.name);
    }

    @Override
    public String write(HasIdAndNameStub hasIdAndNameStub) {
        HasIdAndNameStubDTO dto = new HasIdAndNameStubDTO();
        dto.id = hasIdAndNameStub.id();
        dto.name = hasIdAndNameStub.getName();
        return new Gson().toJson(dto);
    }

    @Override
    public HasIdAndNameStub getArchetype() {
        return ARCHETYPE;
    }

    private class HasIdAndNameStubDTO
    {
        String id;
        String name;
    }
}
