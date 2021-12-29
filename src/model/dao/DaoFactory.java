package model.dao;

import db_configs.DB;
import model.dao.implementacao.ClienteDaoJDBC;
import model.dao.implementacao.FichaDaoJDBC;
import model.dao.implementacao.PlanoDaoJDBC;
import model.dao.implementacao.ExercicioDaoJDBC;
import model.dao.interfaces.ClienteDao;
import model.dao.interfaces.ExercicioDao;
import model.dao.interfaces.FichaDao;
import model.dao.interfaces.PlanoDao;

public class DaoFactory {
	
	public static PlanoDao createPlanoDao() {
		return new PlanoDaoJDBC(DB.getConnection());
	}
	
	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static FichaDao createFichaDao() {
		return new FichaDaoJDBC(DB.getConnection());
	}
		
	public static ExercicioDao createExercicioDao() {
		return new ExercicioDaoJDBC(DB.getConnection());
	}
	
}
