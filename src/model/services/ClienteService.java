package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.interfaces.ClienteDao;
import model.entidades.Cliente;

public class ClienteService {
	
	private ClienteDao dao = DaoFactory.createClienteDao();
	
	public List<Cliente> findAll(){
		return dao.listaClientes();
	}
	
	public void saveOrUpdate(Cliente cliente) {
		if(cliente.getId() == null) {
			dao.criaCliente(cliente);
		}
		else {
			dao.atualizaCliente(cliente);
		}
	}
	
	public void remove(Cliente cliente) {
		dao.deletaClienteId(cliente.getId());
	}
	
	
}
