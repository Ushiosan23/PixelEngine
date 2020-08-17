package fx.soft.pixelengine.database;

import fx.soft.pixelengine.database.tables.BaseTable;
import fx.soft.pixelengine.utils.system.PathUtils;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.InputStream;
import java.sql.*;

public final class DatabaseAccess {
	
	/**
	 * Database connection sqlite
	 */
	public final Connection connection;
	
	/**
	 * Current database instance
	 */
	public static DatabaseAccess instance;
	
	/**
	 * Database access connection
	 *
	 * @throws SQLException database connection error
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	private DatabaseAccess() throws SQLException {
		String homePath = PathUtils.join(System.getProperty("user.home"), ".pixelEngine", "config.db");
		File locationFile = new File(homePath).getParentFile();
		
		if (!locationFile.exists())
			locationFile.mkdir();
		
		connection = DriverManager.getConnection("jdbc:sqlite:" + homePath);
	}
	
	/**
	 * Initialize database connection access.
	 */
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public static void initializeDatabase() throws SQLException {
		if (instance != null)
			return;
		instance = new DatabaseAccess();
		
		for (Class<?> objTable : BaseTable.getAllTables()) {
			BaseTable<?> table = BaseTable.getInstance((Class<? extends BaseTable<?>>) objTable);
			
			// Create table statement
			Statement statement = instance.createStatement();
			statement.execute(table.getTableCreationQuery());
			statement.close();
		}
	}
	
	/**
	 * Create sql statement
	 *
	 * @return {@link Statement} connection
	 */
	public Statement createStatement() {
		try {
			return connection.createStatement();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Create and prepare statement
	 *
	 * @param query target query to prepare
	 * @param args  target arguments to attach in query
	 *
	 * @return prepared {@link Statement}
	 */
	public PreparedStatement createStatement(String query, Object... args) {
		try {
			PreparedStatement prepared = connection.prepareStatement(query);
			for (int i = 0; i < args.length; i++) {
				setStatementArgument(prepared, i + 1, args[i]);
			}
			return prepared;
		} catch (SQLException err) {
			err.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Set statement values
	 *
	 * @param statement target statement
	 * @param index     statement index to set value
	 * @param value     target value to set
	 */
	private void setStatementArgument(PreparedStatement statement, int index, Object value) throws SQLException {
		if (value == null)
			return;
		
		if (value instanceof String) {
			statement.setString(index, (String) value);
		} else if (value instanceof Boolean) {
			statement.setBoolean(index, (Boolean) value);
		} else if (value instanceof Byte) {
			statement.setByte(index, (Byte) value);
		} else if (value instanceof byte[]) {
			statement.setBytes(index, (byte[]) value);
		} else if (value instanceof Integer) {
			statement.setInt(index, (int) value);
		} else if (value instanceof Blob) {
			statement.setBlob(index, (Blob) value);
		} else if (value instanceof InputStream) {
			statement.setBlob(index, (InputStream) value);
		} else {
			statement.setObject(index, value);
		}
	}
	
}
