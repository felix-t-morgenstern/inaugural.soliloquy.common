package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;

public class PersistentVariableCachePersistenceHandler
        extends PersistentTypeHandler<IPersistentVariableCache>
        implements IPersistentValueTypeHandler<IPersistentVariableCache> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final IPersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;
    private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY;
    private final IPersistentVariableCache ARCHETYPE;

    private final String DELIMITER_OUTER = "\u000e";
    private final String DELIMITER_INNER = "\u000f";

    public PersistentVariableCachePersistenceHandler(
            IPersistentValuesHandler persistentValuesHandler,
            IPersistentVariableCacheFactory persistentVariableCacheFactory,
            IPersistentVariableFactory persistentVariableFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        PERSISTENT_VARIABLE_CACHE_FACTORY = persistentVariableCacheFactory;
        PERSISTENT_VARIABLE_FACTORY = persistentVariableFactory;
        ARCHETYPE = PERSISTENT_VARIABLE_CACHE_FACTORY.make();
    }

    @Override
    public IPersistentVariableCache getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IPersistentVariableCache read(String data) throws IllegalArgumentException {
        IPersistentVariableCache persistentVariableCache =
                PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        String[] pVarValueStrings = data.split(DELIMITER_OUTER);
        for(String pVarValueString : pVarValueStrings) {
            if (pVarValueString.equals("")) {
                continue;
            }
            String[] pVarComponents = pVarValueString.split(DELIMITER_INNER);
            if (pVarComponents.length != 3) {
                throw new IllegalArgumentException(
                        "PersistentVariableCachePersistenceHandler.read: Value string (" +
                                pVarValueString + ") has invalid number of delimiters");
            }
            String name = pVarComponents[0];
            String typeName = pVarComponents[1];
            String valueString = pVarComponents[2];
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            Object value = handler.read(valueString);
            IPersistentVariable pVar = PERSISTENT_VARIABLE_FACTORY.make(name, value);
            persistentVariableCache.put(pVar);
        }
        return persistentVariableCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IPersistentVariableCache persistentVariableCache) {
        if (persistentVariableCache == null) {
            throw new IllegalArgumentException(
                    "PersistentVariableCachePersistenceHandler.write: persistentVariableCache " +
                            "must be non-null");
        }
        ICollection<IPersistentVariable> pVars =
                persistentVariableCache.getVariablesRepresentation();
        StringBuilder writer = new StringBuilder();
        boolean isFirstPVar = true;
        for(IPersistentVariable pVar : pVars) {
            if (!isFirstPVar) {
                writer.append(DELIMITER_OUTER);
            } else {
                isFirstPVar = false;
            }
            String typeName = getProperTypeName(pVar.getValue());
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(typeName);
            String valueString = handler.write(pVar.getValue());
            writer.append(pVar.getName());
            writer.append(DELIMITER_INNER);
            writer.append(typeName);
            writer.append(DELIMITER_INNER);
            writer.append(valueString);
        }
        return writer.toString();
    }
}
