package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.PersistentCollectionHandler;

public class FakePersistentCollectionHandler implements PersistentCollectionHandler {
    @Override
    public Collection read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Collection Collection) {
        return null;
    }

    @Override
    public Collection getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Collection generateArchetype(String valueType) throws IllegalArgumentException {

        int openingCaret = valueType.indexOf("<");
        int closingCaret = valueType.lastIndexOf(">");
        if (!valueType.substring(0, openingCaret).equals(Collection.class.getCanonicalName())) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.generateArchetype: valueType is not a String representation of a Collection");
        }
        String innerType = valueType.substring(openingCaret + 1, closingCaret);

        return new FakeCollectionFactory().make(new FakePersistentValuesHandler()
                .generateArchetype(innerType));
    }
}
