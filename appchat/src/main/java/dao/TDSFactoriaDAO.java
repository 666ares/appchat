package dao;

public class TDSFactoriaDAO extends FactoriaDAO {

	public TDSFactoriaDAO() { }
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new TDSUsuarioDAO();
	}
	
	@Override
	public ContactoIndividualDAO getContactoIndividualDAO() {
		return new TDSContactoIndividualDAO();
	}

	@Override
	public GrupoDAO getGrupoDAO() {
		return new TDSGrupoDAO();
	}

	@Override
	public MensajeDAO getMensajeDAO() {
		return new TDSMensajeDAO();
	}
}
