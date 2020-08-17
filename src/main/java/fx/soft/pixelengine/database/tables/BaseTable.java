package fx.soft.pixelengine.database.tables;

import java.util.Arrays;
import java.util.List;

/**
 * Class model for Tables
 */
public abstract class BaseTable<T extends BaseTable<T>> {
	
	/**
	 * Default constructor
	 */
	public BaseTable() {
	}
	
	/**
	 * Get table name
	 *
	 * @return {@link String} with table name
	 */
	public abstract String getTableName();
	
	/**
	 * Get all query string creation
	 *
	 * @return {@link String} with all creation query
	 */
	public abstract String getTableCreationQuery();
	
	/**
	 * Get table instance
	 *
	 * @param <T> Target table class
	 *
	 * @return {@link BaseTable} instance
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseTable<?>> T getInstance(Class<T> target) {
		try {
			return (T) target.getDeclaredConstructors()[0].newInstance();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get all tables list
	 *
	 * @return {@link List} with all {@link BaseTable} classes
	 */
	public static List<Class<?>> getAllTables() {
		return Arrays.asList(
			ConfigurationTable.class,
			ProjectTable.class
		);
	}
	
}
