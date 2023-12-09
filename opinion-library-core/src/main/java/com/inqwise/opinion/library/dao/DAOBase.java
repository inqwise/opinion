package com.inqwise.opinion.library.dao;

public abstract class DAOBase {

	public static final String ERROR_CODE = "error_code";
	
	protected static <T> Object GenerateSQLValue(T param){
		if(null == param){
			return "[:skip:]";
		}
		
		return param;
	}
	
	/*protected static void update(String databaseName, String sql, Object... values) throws DAOException {
    	Connection connection = null;
		CallableStatement callableStatement = null;
    	
    	try {
    		Database factory = DAOFactory.getInstance(databaseName);
        	connection = factory.getConnection();
            callableStatement = connection.prepareCall(sql);
            DAOUtil.setValues(callableStatement, values);
            int affectedRows = callableStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(callableStatement);
            DAOUtil.close(connection);
        }
    }
    
    
	
    protected static <T> Object GenerateSQLValue(Skipable<T> param){
		if(null == param){
			return "[:skip:]";
		}
		
		return param.getValue();
	}
	*/
    
}
