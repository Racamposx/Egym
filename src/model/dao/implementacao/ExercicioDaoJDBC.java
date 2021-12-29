package model.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_configs.DB;
import db_configs.DbException;
import model.dao.interfaces.ExercicioDao;
import model.entidades.Exercicio;


public class ExercicioDaoJDBC implements ExercicioDao{
	
	private Connection conn;
	
	
	public ExercicioDaoJDBC() {
		
	}
	
	public ExercicioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void criaExercicio(Exercicio exercicio) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO exercicio "
					+ "(nome) "
					+ "VALUES " + "(?)",Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, exercicio.getNome());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					exercicio.setId(id);
				}
				
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi afetada"); 
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
		

	@Override
	public void atualizaExercicio(Exercicio exercicio) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE exercicio "
			+ "SET nome = ?"
			+ "WHERE id_exercicio = ?");

			st.setString(1, exercicio.getNome());
			st.setInt(2, exercicio.getId());
			
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deletaExercicioId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM exercicio WHERE id_exercicio = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Exercicio encontraExercicioId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT exercicio.* FROM exercicio "
					+ "WHERE id_exercicio = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Exercicio exercicio = instanciaExercicio(rs);
				return exercicio;
			}
			return null;
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Exercicio> listaExercicio() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT exercicio.* FROM exercicio ORDER BY nome");
			
			rs = st.executeQuery();
			List<Exercicio> exercicios = new ArrayList<>();
			
			while(rs.next()) {
				Exercicio exercicio = instanciaExercicio(rs);
				exercicios.add(exercicio);
			}
			return exercicios;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
	private Exercicio instanciaExercicio(ResultSet rs) throws SQLException {
		Exercicio exercicio = new Exercicio();
		exercicio.setNome(rs.getString("nome"));
		exercicio.setId(rs.getInt("id_exercicio"));
		return exercicio;
	}
	
}
