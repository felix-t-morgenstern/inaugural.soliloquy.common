package inaugural.soliloquy.common.persistentvaluetypehandlers;

abstract class PersistentDataStructureWithOneGenericParamHandler<T>
        extends PersistentTypeHandler<T> {
    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getArchetype() {
        // NB: The PersistentTypeHandler for this data structure should be selected by the
        // PersistentValuesHandler through specific, manually-defined String pattern recognition,
        // rather than via getArchetype.
        return null;
    }

    String getInnerType(String valueType, Class<T> dataStructureClass, String className) {
        if(valueType == null) {
            throw new IllegalArgumentException(
                    className + ".generateArchetype: valueType must be non-null");
        }
        if(valueType.equals("")) {
            throw new IllegalArgumentException(
                    className + ".generateArchetype: valueType must be non-empty");
        }

        int openingCaret = valueType.indexOf("<");
        int closingCaret = valueType.lastIndexOf(">");
        if (!valueType.substring(0, openingCaret).equals(dataStructureClass.getCanonicalName())) {
            throw new IllegalArgumentException(
                    className +
                            ".generateArchetype: valueType is not a String representation of a" +
                            dataStructureClass.getName());
        }
        return valueType.substring(openingCaret + 1, closingCaret);
    }
}
