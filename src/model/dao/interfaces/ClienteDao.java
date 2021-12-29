package model.dao.interfaces;

import java.util.List;

import model.entidades.Cliente;

public interface ClienteDao {
	public void criaCliente(Cliente cliente);
	public void atualizaCliente(Cliente cliente);
	public void deletaClienteId(Integer id);
	public Cliente encontraClienteId(Integer id);
	public List<Cliente> listaClientes();
}
