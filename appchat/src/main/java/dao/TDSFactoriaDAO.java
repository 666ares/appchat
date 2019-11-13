package dao;

public class TDSFactoriaDAO extends FactoriaDAO {

	public TDSFactoriaDAO() { }
	
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {
		return new TDSUsuarioDAO();
	}
}
