package model.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ficha {
	private Integer id;
	private Cliente clienteFicha;
	private Date dataInicio;
	private Double pesoCliente;
	private List<Treino> treinos = new ArrayList<>();
	
	public Ficha() {
		
	}


	public Ficha(Cliente clienteFicha, Date dataInicio, Double pesoCliente) {
		this.clienteFicha = clienteFicha;
		this.dataInicio = dataInicio;
		this.pesoCliente = pesoCliente;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Cliente getClienteFicha() {
		return clienteFicha;
	}


	public void setClienteFicha(Cliente clienteFicha) {
		this.clienteFicha = clienteFicha;
	}


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Double getPesoCliente() {
		return pesoCliente;
	}


	public void setPesoCliente(Double pesoCliente) {
		this.pesoCliente = pesoCliente;
	}
	

	public List<Treino> getTreinos() {
		return treinos;
	}


	public void setTreinos(List<Treino> treinos) {
		this.treinos = treinos;
	}


	@Override
	public String toString() {
		return "Ficha [id=" + id + ", clienteFicha=" + clienteFicha + ", dataInicio=" + dataInicio + ", pesoCliente="
				+ pesoCliente + "]";
	}
	
	
}
