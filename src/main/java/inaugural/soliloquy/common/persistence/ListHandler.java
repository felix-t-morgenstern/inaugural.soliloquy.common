package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeWithOneGenericParamHandler;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.Cloneable;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

@SuppressWarnings("rawtypes")
public class ListHandler
        extends AbstractTypeWithOneGenericParamHandler<List>
        implements TypeHandler<List> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final ListFactory LIST_FACTORY;

    public ListHandler(PersistentValuesHandler persistentValuesHandler,
                       ListFactory listFactory) {
        super(
                new ListArchetype(),
                persistentValuesHandler,
                archetype -> Check.ifNull(listFactory, "listFactory").make(archetype)
        );
        PERSISTENT_VALUES_HANDLER = Check.ifNull(persistentValuesHandler,
                "persistentValuesHandler");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public List read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);
        var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.type);
        var list = LIST_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.type));
        for (var i = 0; i < dto.values.length; i++) {
            list.add(handler.read(dto.values[i]));
        }
        return list;
    }

    @Override
    public String write(List list) {
        Check.ifNull(list, "list");
        var internalType = getProperTypeName(list.archetype());
        var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(internalType);
        var dto = new DTO();
        dto.type = internalType;
        var serializedValues = new String[list.size()];
        for (var i = 0; i < list.size(); i++) {
            serializedValues[i] = handler.write(list.get(i));
        }
        dto.values = serializedValues;
        return JSON.toJson(dto);
    }

    // TODO: Abbreviate DTO param names
    @SuppressWarnings("InnerClassMayBeStatic")
    private class DTO {
        String type;
        String[] values;
    }

    private static class ListArchetype implements List {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean addAll(Collection collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, Collection collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Object get(int i) {
            return null;
        }

        @Override
        public Object set(int i, Object o) {
            return null;
        }

        @Override
        public void add(int i, Object o) {

        }

        @Override
        public Object remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator listIterator() {
            return null;
        }

        @Override
        public ListIterator listIterator(int i) {
            return null;
        }

        @Override
        public java.util.List subList(int i, int i1) {
            return null;
        }

        @Override
        public boolean retainAll(Collection collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection collection) {
            return false;
        }

        @Override
        public boolean containsAll(Collection collection) {
            return false;
        }

        @Override
        public Object[] toArray(Object[] objects) {
            return new Object[0];
        }

        @Override
        public Cloneable makeClone() {
            return null;
        }

        @Override
        public Object archetype() {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return List.class.getCanonicalName();
        }
    }
}
