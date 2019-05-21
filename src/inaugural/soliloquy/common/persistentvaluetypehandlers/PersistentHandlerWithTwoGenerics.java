package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.IPersistentValuesHandler;

public abstract class PersistentHandlerWithTwoGenerics<T> extends PersistentTypeHandler<T> {
    protected final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    protected PersistentHandlerWithTwoGenerics(IPersistentValuesHandler persistentValuesHandler) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
    }

    protected abstract Object generateTypeFromFactory(Object archetype1, Object archetype2);

    protected Object generateTypeFromGenericParameterNames(String genericParameters) {
        int caretLevel = 0;
        int substringSeparator = -1;
        for (int i = 0; i < genericParameters.length(); i++) {
            char c = genericParameters.charAt(i);
            if (c == ',' && caretLevel == 0) {
                substringSeparator = i;
                break;
            } else if (c == '<') {
                caretLevel++;
            } else if (c == '>') {
                caretLevel--;
            }
        }
        String genericParameter1 = genericParameters.substring(0,substringSeparator);
        String genericParameter2 = genericParameters.substring(substringSeparator + 1);

        return generateTypeFromFactory(
                PERSISTENT_VALUES_HANDLER.generateArchetype(genericParameter1),
                PERSISTENT_VALUES_HANDLER.generateArchetype(genericParameter2));
    }
}
