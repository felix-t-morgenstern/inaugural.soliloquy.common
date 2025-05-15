package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

@SuppressWarnings("rawtypes")
public class MapHandler extends AbstractTypeHandler<Map<?,?>> implements TypeHandler<Map<?,?>> {
    private final PersistenceHandler PERSISTENCE_HANDLER;

    @Override
    public String typeHandled() {
        return Map.class.getCanonicalName();
    }

    public MapHandler(PersistenceHandler persistenceHandler) {
        PERSISTENCE_HANDLER = Check.ifNull(persistenceHandler, "persistenceHandler");
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <M extends Map<?,?>> M read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);
        var keyHandler = PERSISTENCE_HANDLER.getTypeHandler(dto.keyType);
        var valueHandler = PERSISTENCE_HANDLER.getTypeHandler(dto.valueType);
        var map = (M) mapOf();
        for (int i = 0; i < dto.keys.length; i++) {
            map.put(
                    dto.keys[i] == null ? null : keyHandler.read(dto.keys[i]),
                    dto.values[i] == null ? null : valueHandler.read(dto.values[i])
            );
        }
        return map;
    }

    @Override
    public String write(Map<?,?> map) {
        Check.ifNull(map, "map");
        var dto = new DTO();
        if (!map.isEmpty()) {
            TypeHandler keyHandler = null;
            String keyType = null;
            var keyEntry = map.entrySet().stream().filter(e -> e.getKey() != null).findFirst();
            if (keyEntry.isPresent()) {
                keyType = keyEntry.get().getKey().getClass().getCanonicalName();
                keyHandler = PERSISTENCE_HANDLER.getTypeHandler(keyType);
            }

            TypeHandler valueHandler = null;
            String valueType = null;
            var valueEntry = map.entrySet().stream().filter(e -> e.getValue() != null).findFirst();
            if (valueEntry.isPresent()) {
                valueType = valueEntry.get().getValue().getClass().getCanonicalName();
                valueHandler = PERSISTENCE_HANDLER.getTypeHandler(valueType);
            }

            dto.keyType = keyType;
            dto.valueType = valueType;
            dto.keys = new String[map.size()];
            dto.values = new String[map.size()];
            var indexCounter = 0;
            for (var key : map.keySet()) {
                if (keyHandler != null && key != null) {
                    //noinspection unchecked
                    dto.keys[indexCounter] = keyHandler.write(key);
                }
                else {
                    dto.keys[indexCounter] = null;
                }

                if (valueHandler != null && map.get(key) != null) {
                    //noinspection unchecked
                    dto.values[indexCounter] = valueHandler.write(map.get(key));
                }
                else {
                    dto.values[indexCounter] = null;
                }

                indexCounter++;
            }
        }
        return JSON.toJson(dto);
    }

    private static class DTO {
        String keyType;
        String valueType;
        String[] keys;
        String[] values;
    }
}
