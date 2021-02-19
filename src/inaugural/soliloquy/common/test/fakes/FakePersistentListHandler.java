package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.PersistentListHandler;

public class FakePersistentListHandler implements PersistentListHandler {
    @Override
    public List read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(List list) {
        return null;
    }

    @Override
    public List getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public List generateArchetype(String valueType) throws IllegalArgumentException {

        int openingCaret = valueType.indexOf("<");
        int closingCaret = valueType.lastIndexOf(">");
        if (!valueType.substring(0, openingCaret).equals(List.class.getCanonicalName())) {
            throw new IllegalArgumentException(
                    "PersistentListHandler.generateArchetype: valueType is not a String representation of a Collection");
        }
        String innerType = valueType.substring(openingCaret + 1, closingCaret);

        return new FakeListFactory().make(new FakePersistentValuesHandler()
                .generateArchetype(innerType));
    }
}
