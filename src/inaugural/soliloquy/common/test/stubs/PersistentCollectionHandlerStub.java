package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.IPersistentCollectionHandler;
import soliloquy.specs.common.valueobjects.ICollection;

public class PersistentCollectionHandlerStub implements IPersistentCollectionHandler {
    @Override
    public ICollection read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(ICollection iCollection) {
        return null;
    }

    @Override
    public ICollection getArchetype() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public ICollection generateArchetype(String valueType) throws IllegalArgumentException {

        int openingCaret = valueType.indexOf("<");
        int closingCaret = valueType.lastIndexOf(">");
        if (!valueType.substring(0, openingCaret).equals(ICollection.class.getCanonicalName())) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.generateArchetype: valueType is not a String representation of a Collection");
        }
        String innerType = valueType.substring(openingCaret + 1, closingCaret);

        return new CollectionFactoryStub().make(new PersistentValuesHandlerStub()
                .generateArchetype(innerType));
    }
}
