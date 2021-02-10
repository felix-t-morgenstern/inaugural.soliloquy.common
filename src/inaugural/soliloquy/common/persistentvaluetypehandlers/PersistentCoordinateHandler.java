package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.persistentvaluetypehandlers.PersistentTypeHandler;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;

public class PersistentCoordinateHandler extends PersistentTypeHandler<Coordinate>
        implements PersistentValueTypeHandler<Coordinate> {
    private final Coordinate ARCHETYPE;
    private final CoordinateFactory COORDINATE_FACTORY;

    private static final String NULL = "NULL";

    public PersistentCoordinateHandler(CoordinateFactory coordinateFactory) {
        ARCHETYPE = coordinateFactory.make(0,0);
        COORDINATE_FACTORY = coordinateFactory;
    }

    @Override
    public Coordinate getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Coordinate read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "PersistentCoordinateHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentCoordinateHandler.read: serializedValue must be non-empty");
        }
        if (serializedValue.equals(NULL)){
            return null;
        } else {
            CoordinateDTO dto = new Gson().fromJson(serializedValue, CoordinateDTO.class);
            return COORDINATE_FACTORY.make(dto.x, dto.y);
        }
    }

    @Override
    public String write(Coordinate coordinate) {
        if (coordinate == null) {
            return NULL;
        } else {
            CoordinateDTO dto = new CoordinateDTO();
            dto.x = coordinate.getX();
            dto.y = coordinate.getY();
            return new Gson().toJson(dto);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CoordinateDTO {
        int x;
        int y;
    }
}
