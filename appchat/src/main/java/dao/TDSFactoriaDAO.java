package dao;

public class TDSFactoriaDAO extends FactoriaDAO {

	public TDSFactoriaDAO() { }
	
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {
		return new TDSUsuarioDAO();
	}
	
	@Override
	public TDSContactoIndividualDAO getContactoIndividualDAO() {
		return new TDSContactoIndividualDAO();
	}

	@Override
	public TDSGrupoDAO getGrupoDAO() {
		return new TDSGrupoDAO();
	}

	@Override
	public TDSMensajeDAO getMensajeDAO() {
		return new TDSMensajeDAO();
	}
}
