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
import db_configs.DbIntegrityException;
import model.dao.interfaces.PlanoDao;
import model.entidades.Plano;

public class PlanoDaoJDBC implements PlanoDao {

	private Connection conn;

	public PlanoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public PlanoDaoJDBC() {
		
	}

	@Override
	public void criaPlano(Plano plano) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO plano "
			+ "(nome, preco, descricao) "
			+ "VALUES " + "(?,?,?)",Statement.RETURN_GENERATED_KEYS);

			st.setString(1, plano.getNome());
			st.setDouble(2, plano.getPreco());
			st.setString(3, plano.getDescricao());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					plano.setId(id);
				}
				
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
			}
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void atualizaPlano(Plano plano) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE plano "
			+ "SET nome = ?, preco = ?, descricao = ? "
			+ "WHERE plano_id = ?");

			st.setString(1, plano.getNome());
			st.setDouble(2, plano.getPreco());
			st.setString(3, plano.getDescricao());
			st.setInt(4, plano.getId());
			
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deletaPlanoId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM plano WHERE plano_id = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Plano encontraPlanoId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT plano.* FROM plano WHERE plano_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Plano plano = instanciaPlano(rs);
				return plano;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}

	@Override
	public List<Plano> listaPlanos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT plano.* FROM plano ORDER BY nome");
			
			rs = st.executeQuery();
			List<Plano> planos = new ArrayList<>();
			
			while(rs.next()) {
				Plano plano = instanciaPlano(rs);
				planos.add(plano);
			}
			return planos;
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	private Plano instanciaPlano(ResultSet rs) throws SQLException {
		Plano plano = new Plano();
		plano.setId(rs.getInt("plano_id"));
		plano.setNome(rs.getString("nome"));
		plano.setPreco(rs.getDouble("preco"));
		plano.setDescricao(rs.getString("descricao"));
		return plano;
	}

}
