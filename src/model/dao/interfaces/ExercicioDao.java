package model.dao.interfaces;

import java.util.List;

import model.entidades.Exercicio;

public interface ExercicioDao {
	public void criaExercicio(Exercicio cliente);
	public void atualizaExercicio(Exercicio cliente);
	public void deletaExercicioId(Integer id);
	public Exercicio encontraExercicioId(Integer id);
	public List<Exercicio> listaExercicio();
}
