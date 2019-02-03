package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentIntegersHandler extends PersistentTypeHandler<ICollection<Integer>> implements IPersistentValueTypeHandler<ICollection<Integer>>
{
	private final ICollectionFactory COLLECTION_FACTORY;
	
	public PersistentIntegersHandler(ICollectionFactory collectionFactory)
	{
		COLLECTION_FACTORY = collectionFactory;
	}

	@Override
	public ICollection<Integer> read(String valueString)
	{
		ICollection<Integer> readValues = COLLECTION_FACTORY.make(0);
		if (valueString == null)
		{
			throw new IllegalArgumentException("valueString cannot be null");
		}
		if (valueString.equals(""))
		{
			return readValues;
		}
		String[] valuesToRead = valueString.split(",");
		for(String valueToRead : valuesToRead)
		{
			readValues.add(Integer.parseInt(valueToRead));
		}
		return readValues;
	}

	@Override
	public String write(ICollection<Integer> values)
	{
		String writtenValue = "";
		Boolean firstValue = true;
		for(Integer value : values)
		{
			if (firstValue)
			{
				firstValue = false;
			}
			else
			{
				writtenValue += ",";
			}
			writtenValue += value.toString();
		}
		return writtenValue;
	}

	@Override
	public ICollection<Integer> getArchetype()
	{
		return COLLECTION_FACTORY.make(0);
	}

}
