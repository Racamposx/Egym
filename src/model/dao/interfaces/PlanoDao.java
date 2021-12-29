package model.dao.interfaces;

import java.util.List;

import model.entidades.Plano;

public interface PlanoDao {
	public void criaPlano(Plano plano);
	public void atualizaPlano(Plano plano);
	public void deletaPlanoId(Integer id);
	public Plano encontraPlanoId(Integer id);  
	public List<Plano> listaPlanos();
	
}
