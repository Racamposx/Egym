package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.interfaces.ExercicioDao;
import model.entidades.Exercicio;

public class ExercicioService {
	
	private ExercicioDao dao = DaoFactory.createExercicioDao();
	
	public List<Exercicio> findAll(){
		return dao.listaExercicio();
	}
	
	public void saveOrUpdate(Exercicio exercicio) {
		if(exercicio.getId() == null) {
			dao.criaExercicio(exercicio);
		}
		else {
			dao.atualizaExercicio(exercicio);
		}
	}
	
	public void remove(Exercicio exercicio) {
		dao.deletaExercicioId(exercicio.getId());
	}
	
	
}
