package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;

public class PersistentGenericParamsSetHandler extends PersistentTypeHandler<IGenericParamsSet>
        implements IPersistentValueTypeHandler<IGenericParamsSet> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final IGenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;
    private final IGenericParamsSet ARCHETYPE;

    private final String DELIMITER_OUTER = "\u0095";
    private final String DELIMITER_TYPE = "\u0093";
    private final String DELIMITER_ITEM = "\u0096";
    private final String DELIMITER_INNER = "\u0097";

    public PersistentGenericParamsSetHandler(IPersistentValuesHandler persistentValuesHandler,
                                             IGenericParamsSetFactory genericParamsSetFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        GENERIC_PARAMS_SET_FACTORY = genericParamsSetFactory;
        ARCHETYPE = genericParamsSetFactory.make();
    }

    @Override
    public IGenericParamsSet getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IGenericParamsSet read(String valueString) throws IllegalArgumentException {
        IGenericParamsSet genericParamsSet = GENERIC_PARAMS_SET_FACTORY.make();
        String[] paramSetStrings = valueString.split(DELIMITER_OUTER);
        for(String paramSetString : paramSetStrings) {
            String[] paramSetStringComponents = paramSetString.split(DELIMITER_TYPE);
            if (paramSetStringComponents.length != 2) {
                throw new IllegalArgumentException(
                        "PersistentGenericParamsSetHandler.read: paramString has invalid number of " +
                                "fields");
            }
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER
                            .getPersistentValueTypeHandler(paramSetStringComponents[0]);
            String[] paramStrings = paramSetStringComponents[1].split(DELIMITER_ITEM);
            for(String paramString : paramStrings) {
                String[] paramStringComponents = paramString.split(DELIMITER_INNER);
                if (paramStringComponents.length != 2) {
                    throw new IllegalArgumentException(
                            "PersistentGenericParamsSetHandler.read: paramString has invalid number" +
                                    "of fields");
                }
                String paramName = paramStringComponents[0];
                String paramValueString = paramStringComponents[1];
                genericParamsSet.addParam(paramName, handler.read(paramValueString));
            }
        }
        return genericParamsSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IGenericParamsSet genericParamsSet) {
        if (genericParamsSet == null) {
            throw new IllegalArgumentException(
                    "PersistentGenericParamsSetHandler.write: genericParamsSet must be non-null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        ICollection<String> paramTypes = genericParamsSet.paramTypes();
        boolean isFirstParamType = true;
        for(String paramType : paramTypes) {
            if (isFirstParamType) {
                isFirstParamType = false;
            } else {
                stringBuilder.append(DELIMITER_OUTER);
            }
            stringBuilder.append(paramType);
            stringBuilder.append(DELIMITER_TYPE);
            IMap<String,Object> params = genericParamsSet.getParamsSet(paramType);
            IPersistentValueTypeHandler handler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(paramType);
            boolean isFirstParam = true;
            for(IPair<String,Object> param : params) {
                if (isFirstParam) {
                    isFirstParam = false;
                } else {
                    stringBuilder.append(DELIMITER_ITEM);
                }
                stringBuilder.append(param.getItem1());
                stringBuilder.append(DELIMITER_INNER);
                stringBuilder.append(handler.write(param.getItem2()));
            }
        }
        return stringBuilder.toString();
    }
}
