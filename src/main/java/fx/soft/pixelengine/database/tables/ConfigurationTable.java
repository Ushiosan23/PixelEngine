package fx.soft.pixelengine.database.tables;

/**
 * Table configuration class
 */
final class ConfigurationTable extends BaseTable<ConfigurationTable> {
	
	/**
	 * Get configuration table name
	 *
	 * @return get table name
	 */
	@Override
	public String getTableName() {
		return "app_configuration";
	}
	
	/**
	 * Get configuration table query creation
	 *
	 * @return {@link String} with query creation
	 */
	@Override
	public String getTableCreationQuery() {
		return "" +
			"CREATE TABLE IF NOT EXISTS '" + getTableName() + "' (" +
			"'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"'name' TEXT NOT NULL UNIQUE," +
			"'value' TEXT NOT NULL," +
			"'type' TEXT NOT NULL DEFAULT 'text'" +
			")";
	}
	
}
