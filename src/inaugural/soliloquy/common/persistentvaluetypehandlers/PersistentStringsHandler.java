package inaugural.soliloquy.common.persistentvaluetypehandlers;

import inaugural.soliloquy.common.Collection;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentStringsHandler extends PersistentTypeHandler<ICollection<String>> implements IPersistentValueTypeHandler<ICollection<String>> {
	private final String DELIMITER = "\u001f";
	
	private final ICollectionFactory COLLECTION_FACTORY;
	
	public PersistentStringsHandler(ICollectionFactory collectionFactory)
	{
		COLLECTION_FACTORY = collectionFactory;
	}
	
	@Override
	public ICollection<String> read(String valueString) throws IllegalArgumentException {
		return new Collection<String>(valueString.split(DELIMITER),"");
	}

	@Override
	public String write(ICollection<String> value) {
		String result = "";
		boolean firstItem = true;
		for (int i = 0; i < value.size(); i++)
		{
			if (firstItem) firstItem = false;
			else result += DELIMITER;
			result += value.get(i);
		}
		return result;
	}

	@Override
	public ICollection<String> getArchetype() {
		return COLLECTION_FACTORY.make("");
	}

}
