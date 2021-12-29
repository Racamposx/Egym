package model.dao.implementacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_configs.DB;
import db_configs.DbException;
import model.dao.interfaces.ClienteDao;
import model.entidades.Cliente;
import model.entidades.Plano;

public class ClienteDaoJDBC implements ClienteDao{
	
	private Connection conn;
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public ClienteDaoJDBC() {
		
	}

	@Override
	public void criaCliente(Cliente cliente) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO cliente "
			+ "(primeiro_nome, nome_meio, ultimo_nome, cpf, data_nascimento, telefone, plano_id) "
			+ "VALUES " + "(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

			st.setString(1, cliente.getPrimeiroNome());
			st.setString(2, cliente.getNomeMeio());
			st.setString(3, cliente.getUltimoNome());
			st.setString(4, cliente.getCpf());
			st.setDate(5, new Date(cliente.getDataNasc().getTime()));
			st.setString(6, cliente.getTelefone());
			st.setInt(7, cliente.getPlanoCliente().getId());
			
			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					cliente.setId(id);
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
	public void atualizaCliente(Cliente cliente) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE cliente "
			+ "SET primeiro_nome = ?, nome_meio = ?, ultimo_nome = ?, "
			+ "cpf = ?, data_nascimento = ?, telefone = ?, plano_id = ? "
			+ "WHERE id_cliente = ?");

			st.setString(1, cliente.getPrimeiroNome());
			st.setString(2, cliente.getNomeMeio());
			st.setString(3, cliente.getUltimoNome());
			st.setString(4, cliente.getCpf());
			st.setDate(5, new Date(cliente.getDataNasc().getTime()));
			st.setString(6, cliente.getTelefone());
			st.setInt(7, cliente.getPlanoCliente().getId());
			st.setInt(8, cliente.getId());
			
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
	public void deletaClienteId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM cliente WHERE id_cliente = ?");
			
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
	public Cliente encontraClienteId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT cliente.*, plano.nome, plano.descricao, plano.preco "
					+ "FROM cliente INNER JOIN plano "
					+ "ON cliente.plano_id = plano.plano_id "
					+ "WHERE cliente.id_cliente = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Plano plano = instanciaPlano(rs);
				Cliente cliente = instanciaCliente(rs, plano);
				return cliente;
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
	public List<Cliente> listaClientes() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT cliente.*, plano.nome, plano.descricao, plano.preco "
					+ "FROM cliente INNER JOIN plano "
					+ "ON cliente.plano_id = plano.plano_id "
					+ "ORDER BY primeiro_nome;");
			
			
			rs = st.executeQuery();
			List<Cliente> clientes = new ArrayList<>();
			
			while(rs.next()) {
				Plano plano = instanciaPlano(rs);
				Cliente cliente = instanciaCliente(rs, plano);
				clientes.add(cliente);
			}
			return clientes;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Plano instanciaPlano(ResultSet rs) throws SQLException{
		Plano plano = new Plano();
		plano.setId(rs.getInt("plano_id"));
		plano.setNome(rs.getString("nome"));
		plano.setPreco(rs.getDouble("preco"));
		plano.setDescricao(rs.getString("descricao"));
		return plano;
	}
	
	private Cliente instanciaCliente(ResultSet rs, Plano plano) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id_cliente"));
		cliente.setPrimeiroNome(rs.getString("primeiro_nome"));
		cliente.setNomeMeio(rs.getString("nome_meio"));
		cliente.setUltimoNome(rs.getString("ultimo_nome"));
		cliente.setCpf(rs.getString("cpf"));
		cliente.setDataNasc(new java.util.Date(rs.getTimestamp("data_nascimento").getTime()));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setPlanoCliente(plano);
		return cliente;
	}
	
	
}
