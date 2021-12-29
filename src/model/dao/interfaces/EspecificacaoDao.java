package model.dao.interfaces;

import java.util.List;

import model.entidades.Especificacao;



public interface EspecificacaoDao {
	public void criaEspecificacao(Especificacao cliente);
	public void atualizaEspecificacao(Especificacao cliente);
	public void deletaEspecificacaoId(Integer id);
	public Especificacao encontraEspecificacaoId(Integer id);
	public List<Especificacao> listaEspecificacao();
}
