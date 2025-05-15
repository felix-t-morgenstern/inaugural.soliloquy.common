package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.EntityGroupImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.EntityGroup;
import soliloquy.specs.common.shared.HasId;

import static inaugural.soliloquy.common.infrastructure.EntityGroupImpl.EntryImpl.entityOf;
import static inaugural.soliloquy.common.infrastructure.EntityGroupImpl.EntryImpl.groupOf;
import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockWithId;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.infrastructure.EntityGroup.Entry;
import static soliloquy.specs.common.infrastructure.EntityGroup.Entry.Type.ENTITY;
import static soliloquy.specs.common.infrastructure.EntityGroup.Entry.Type.GROUP;

public class EntityGroupImplTests {
    private final String ENTITY_0_ID = randomString();
    private final String SUBGROUP_1_ID = randomString();
    private final String ENTITY_1_0_ID = randomString();
    private final String SUBGROUP_2_ID = randomString();
    private final String SUBGROUP_2_0_ID = randomString();
    private final String ENTITY_2_0_0_ID = randomString();
    private final String SUBGROUP_2_1_ID = randomString();
    private final String ENTITY_2_1_0_ID = randomString();
    private final String ENTITY_2_1_1_ID = randomString();

    private final Entry<HasId>[] ENTRIES = arrayOf(
            entityOf(mockEntity(ENTITY_0_ID)),
            groupOf(SUBGROUP_1_ID,
                    entityOf(mockEntity(ENTITY_1_0_ID))
            ),
            groupOf(SUBGROUP_2_ID,
                    groupOf(SUBGROUP_2_0_ID,
                            entityOf(mockEntity(ENTITY_2_0_0_ID))
                    ),
                    groupOf(SUBGROUP_2_1_ID,
                            entityOf(mockEntity(ENTITY_2_1_0_ID)),
                            entityOf(mockEntity(ENTITY_2_1_1_ID))
                    )
            )
    );

    private EntityGroup<HasId> entityGroup;

    @BeforeEach
    public void setUp() {
        entityGroup = new EntityGroupImpl<>(ENTRIES);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new EntityGroupImpl<>((Entry<HasId>[]) null));
        assertThrows(IllegalArgumentException.class,
                () -> new EntityGroupImpl<>((Entry<HasId>) null));
    }

    @Test
    public void testEntityOfType() {
        var entityOf = entityOf(mockEntity(randomString()));

        assertEquals(ENTITY, entityOf.type());
    }

    @Test
    public void testEntityOfEntity() {
        var entity = mockEntity(randomString());

        var entityOf = entityOf(entity);

        assertSame(entity, entityOf.entity());
    }

    @Test
    public void testEntityOfGroup() {
        var entityOf = entityOf(mockEntity(randomString()));

        assertThrows(UnsupportedOperationException.class, entityOf::group);
    }

    @Test
    public void testGroupOfType() {
        var groupOf = groupOf(randomString());

        assertEquals(GROUP, groupOf.type());
    }

    @Test
    public void testGroupOfGroup() {
        var id = randomString();

        var groupOf = groupOf(id);

        assertEquals(id, groupOf.group().id());
    }

    @Test
    public void testGroupOfEntity() {
        var groupOf = groupOf(randomString());

        assertThrows(UnsupportedOperationException.class, groupOf::entity);
    }

    @Test
    public void testEntityOfWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> entityOf(null));
    }

    @Test
    public void testGroupOfWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> groupOf(null));
        assertThrows(IllegalArgumentException.class, () -> groupOf(""));
        assertThrows(IllegalArgumentException.class, () -> groupOf(randomString(),
                (Entry<HasId>[]) null));
        assertThrows(IllegalArgumentException.class, () -> groupOf(randomString(),
                (Entry<HasId>) null));
    }

    @Test
    public void testSize() {
        assertEquals(ENTRIES.length, entityGroup.size());
    }

    @Test
    public void testGetEntryByOrder() {
        for (var i = 0; i < ENTRIES.length; i++) {
            assertSame(ENTRIES[i], entityGroup.getEntryByOrder(i));
        }
    }

    @Test
    public void testGetEntryByOrderWithInvalidArgs() {
        assertThrows(IndexOutOfBoundsException.class, () -> entityGroup.getEntryByOrder(-1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> entityGroup.getEntryByOrder(ENTRIES.length + 1));
    }

    @Test
    public void testGetAllGrouped() {
        var allGrouped = entityGroup.getAllGrouped();
        var duplicate = entityGroup.getAllGrouped();

        assertNotNull(allGrouped);
        assertNotSame(duplicate, allGrouped);
        assertEquals(ENTRIES.length, allGrouped.size());
        for (var i = 0; i < ENTRIES.length; i++) {
            assertSame(ENTRIES[i], allGrouped.get(i));
        }
    }

    @Test
    public void testAllEntities() {
        var expectedEntities = new HasId[]{
                ENTRIES[0].entity(),
                ENTRIES[1].group().getEntryByOrder(0).entity(),
                ENTRIES[2].group().getEntryByOrder(0).group().getEntryByOrder(0).entity(),
                ENTRIES[2].group().getEntryByOrder(1).group().getEntryByOrder(0).entity(),
                ENTRIES[2].group().getEntryByOrder(1).group().getEntryByOrder(1).entity(),
                };

        var allEntities = entityGroup.allEntities();

        assertNotNull(allEntities);
        assertEquals(expectedEntities.length, allEntities.size());

    }

    @Test
    public void testGetById() {
        var byId = entityGroup.getById(ENTITY_2_1_0_ID);

        assertNotNull(byId);
        assertSame(ENTRIES[2].group().getEntryByOrder(1).group().getEntryByOrder(0).entity(), byId);
    }

    @Test
    public void testGetByIdWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> entityGroup.getById(null));
        assertThrows(IllegalArgumentException.class, () -> entityGroup.getById(""));
    }

    private HasId mockEntity(String id) {
        return generateMockWithId(HasId.class, id);
    }
}
