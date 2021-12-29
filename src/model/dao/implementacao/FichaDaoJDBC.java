package model.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import db_configs.DB;
import db_configs.DbException;
import model.dao.interfaces.FichaDao;
import model.entidades.Cliente;
import model.entidades.Ficha;

public class FichaDaoJDBC implements FichaDao{
	
	private Connection conn;
	
	public FichaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public FichaDaoJDBC() {
		
	}


	@Override
	public void criaFicha(Ficha ficha) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO ficha "
					+ "(id_cliente, data_inicio, peso_cliente) "
					+ "VALUES " + "(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, ficha.getClienteFicha().getId());
			st.setDate(2, new Date(ficha.getDataInicio().getTime()));
			st.setDouble(3, ficha.getPesoCliente());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					ficha.setId(id);
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
	public void atualizaFicha(Ficha ficha) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ficha "
			+ "SET data_inicio = ?, peso_cliente = ? "
			+ "WHERE id_ficha = ? AND id_cliente = ?;");

			st.setDate(1, new Date(ficha.getDataInicio().getTime()));
			st.setDouble(2, ficha.getPesoCliente());
			st.setInt(3, ficha.getId());
			st.setInt(4, ficha.getClienteFicha().getId());
			
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
	public void deletaFichaId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM ficha WHERE id_ficha = ?");
			
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
	public Ficha encontraFichaId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ficha.*, cliente.*"
					+ "FROM ficha INNER JOIN cliente "
					+ "ON ficha.id_cliente = cliente.id_cliente "
					+ "WHERE id_ficha = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Cliente cliente = instanciaCliente(rs);
				Ficha ficha = instanciaFicha(rs, cliente);
				return ficha;
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
	public List<Ficha> listaFichas() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ficha.*, cliente.*"
					+ "FROM ficha INNER JOIN cliente "
					+ "ON ficha.id_cliente = cliente.id_cliente "
					+ "ORDER BY primeiro_nome");
			
			rs = st.executeQuery();
			List<Ficha> fichas = new ArrayList<>();
			
			while(rs.next()) {
				Cliente cliente = instanciaCliente(rs);
				Ficha ficha = instanciaFicha(rs, cliente);
				fichas.add(ficha);
			}
			return fichas;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
	private Ficha instanciaFicha(ResultSet rs, Cliente cliente) throws SQLException {
		Ficha ficha = new Ficha();
		ficha.setId(rs.getInt("id_ficha"));
		ficha.setClienteFicha(cliente);
		ficha.setDataInicio(rs.getDate("data_inicio"));
		ficha.setPesoCliente(rs.getDouble("peso_cliente"));
		return ficha;
	}
	
	
	private Cliente instanciaCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id_cliente"));
		cliente.setPrimeiroNome(rs.getString("primeiro_nome"));
		cliente.setNomeMeio(rs.getString("nome_meio"));
		cliente.setUltimoNome(rs.getString("ultimo_nome"));
		cliente.setCpf(rs.getString("cpf"));
		cliente.setDataNasc(rs.getDate("data_nascimento"));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setPlanoCliente(null);
		return cliente;
	}
	

}
