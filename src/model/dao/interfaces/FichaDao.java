package model.dao.interfaces;

import java.util.List;

import model.entidades.Ficha;

public interface FichaDao {
	public void criaFicha(Ficha ficha);
	public void atualizaFicha(Ficha ficha);
	public void deletaFichaId(Integer id);
	public Ficha encontraFichaId(Integer id);  
	public List<Ficha> listaFichas();
}
