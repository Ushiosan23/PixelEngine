package fx.soft.pixelengine.database.tables;

/**
 * Table projects class
 */
final class ProjectTable extends BaseTable<ProjectTable> {
	
	/**
	 * Get projects table name
	 *
	 * @return get table name
	 */
	@Override
	public String getTableName() {
		return "app_project";
	}
	
	/**
	 * Get project table query creation
	 *
	 * @return {@link String} with query creation
	 */
	@Override
	public String getTableCreationQuery() {
		return "" +
			"CREATE TABLE IF NOT EXISTS '" + getTableName() + "' (" +
			"'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"'location' TEXT NOT NULL UNIQUE" +
			")";
	}
	
}
