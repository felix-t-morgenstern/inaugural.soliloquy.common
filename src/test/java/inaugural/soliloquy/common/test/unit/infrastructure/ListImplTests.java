package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.ListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.List;

import java.util.ArrayList;
import java.util.Objects;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

public class ListImplTests {
    private final int ARCHETYPE = randomInt();

    private List<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new ListImpl<>(ARCHETYPE);
    }

    // TODO: Add other conditions
    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ListImpl<>(null));

        assertThrows(IllegalArgumentException.class,
                () -> new ListImpl<>(listOf(), null));
    }

    @Test
    public void testExtendsArrayList() {
        assertTrue(list instanceof ArrayList);
    }

    @Test
    public void testMakeClone() {
        var newCollection = list.makeClone();

        assertEquals(list, newCollection);
        assertNotNull(newCollection.archetype());
    }

    @Test
    public void testReadOnlyRepresentation() {
        list.add(randomInt());
        list.add(randomInt());
        list.add(randomInt());
        var clonedList = list.makeClone();

        assertNotNull(clonedList);
        assertNotSame(list, clonedList);
        assertEquals(list, clonedList);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    public void testGetParameterizedClassName() {
        var strings = new ListImpl<>("");
        assertEquals(List.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">",
                strings.getInterfaceName());

        var groupsOfGroupsOfStrings =
                new ListImpl<>(new ListImpl<>(new ListImpl<>("")));
        assertEquals(List.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">>>",
                groupsOfGroupsOfStrings.getInterfaceName());
    }

    @Test
    public void testArchetype() {
        assertEquals(ARCHETYPE, (int) list.archetype());
    }

    @Test
    public void testHashCode() {
        final String string1 = randomString();
        final String string2 = randomString();
        final String string3 = randomString();

        var strings = new ListImpl<>("");

        strings.add(string1);
        strings.add(string2);
        strings.add(string3);

        assertEquals(Objects.hash(string1, string2, string3), strings.hashCode());
    }

    @Test
    public void testEquals() {
        var item1 = randomInt();
        var item2 = randomInt();
        var item3 = randomInt();
        var newCollection = new ListImpl<>(randomInt());

        list.add(item1);
        assertNotEquals(list, newCollection);

        newCollection.add(item1);
        assertEquals(list, newCollection);

        list.add(item2);
        assertNotEquals(list, newCollection);

        newCollection.add(item3);
        assertNotEquals(list, newCollection);
    }

    @Test
    public void testToString() {
        assertThrows(UnsupportedOperationException.class, list::toString);
    }
}
