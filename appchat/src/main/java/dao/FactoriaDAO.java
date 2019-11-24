package dao;

public abstract class FactoriaDAO {

	public static final String DAO_TDS = "dao.TDSFactoriaDAO";
	
	private static FactoriaDAO unicaInstancia;
	
	public static FactoriaDAO getInstancia(String tipo) throws DAOException {
		if (unicaInstancia == null)
			try {
				unicaInstancia = (FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		return unicaInstancia;
	}
	
	public static FactoriaDAO getInstancia() throws DAOException {
		return getInstancia(FactoriaDAO.DAO_TDS);
	}
	
	protected FactoriaDAO() { }
	
	public abstract UsuarioDAO 			getUsuarioDAO();
	public abstract ContactoIndividualDAO 	getContactoIndividualDAO();
	public abstract GrupoDAO 				getGrupoDAO();
	public abstract MensajeDAO 			getMensajeDAO();
}
