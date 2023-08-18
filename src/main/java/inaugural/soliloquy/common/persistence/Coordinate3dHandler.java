package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate3d;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class Coordinate3dHandler extends AbstractTypeHandler<Coordinate3d> {
    public Coordinate3dHandler() {
        super(generateSimpleArchetype(Coordinate3d.class));
    }

    @Override
    public Coordinate3d read(String serializedValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(serializedValue, "serializedValue");
        var dto = JSON.fromJson(serializedValue, DTO.class);
        return Coordinate3d.of(dto.x, dto.y, dto.z);
    }

    @Override
    public String write(Coordinate3d coordinate) {
        Check.ifNull(coordinate, "coordinate");
        var dto = new DTO();
        dto.x = coordinate.X;
        dto.y = coordinate.Y;
        dto.z = coordinate.Z;
        return JSON.toJson(dto);
    }

    private static class DTO {
        int x;
        int y;
        int z;
    }
}
