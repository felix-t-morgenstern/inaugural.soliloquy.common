package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.ICoordinateFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentCoordinateHandler extends PersistentTypeHandler<ICoordinate>
        implements IPersistentValueTypeHandler<ICoordinate> {
    private final ICoordinate ARCHETYPE;
    private final ICoordinateFactory COORDINATE_FACTORY;

    private static final String NULL = "NULL";
    private static final String DELIMITER = "\u0080";

    public PersistentCoordinateHandler(ICoordinateFactory coordinateFactory) {
        ARCHETYPE = coordinateFactory.make(0,0);
        COORDINATE_FACTORY = coordinateFactory;
    }

    @Override
    public ICoordinate getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public ICoordinate read(String valueString) throws IllegalArgumentException {
        if (valueString.equals(NULL)){
            return null;
        } else {
            try {
                String[] components = valueString.split(DELIMITER);
                if (components.length > 2) {
                    throw new Exception();
                }
                int x = Integer.parseInt(components[0]);
                int y = Integer.parseInt(components[1]);
                return COORDINATE_FACTORY.make(x, y);
            }
            catch (Exception e) {
                throw new IllegalArgumentException(
                        String.format(
                                "PersistentCoordinateHandler.read: Could not process valueString (%s)",
                                valueString));
            }
        }
    }

    @Override
    public String write(ICoordinate coordinate) {
        if (coordinate == null) {
            return NULL;
        } else {
            return String.format("%d%s%d", coordinate.getX(), DELIMITER, coordinate.getY());
        }
    }
}
