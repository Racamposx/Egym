package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.interfaces.PlanoDao;
import model.entidades.Plano;

public class PlanoService {
	
	private PlanoDao dao = DaoFactory.createPlanoDao();
	
	public List<Plano> findAll(){
		return dao.listaPlanos();
	}
	
	public void saveOrUpdate(Plano plano) {
		if(plano.getId() == null) {
			dao.criaPlano(plano);
		}
		else {
			dao.atualizaPlano(plano);
		}
	}
	
	public void remove(Plano plano) {
		dao.deletaPlanoId(plano.getId());
	}
	
	
}
