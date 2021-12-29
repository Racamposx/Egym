package model.entidades;

import java.util.Date;

public class Cliente {
	private Integer id;
	private String primeiroNome;
	private String nomeMeio;
	private	String ultimoNome;
	private String cpf;
	private Date dataNasc;
	private String telefone;
	private Plano planoCliente;	
	
	public Cliente() {
		
	}
	
	public Cliente(String primeiroNome, String nomeMeio, String ultimoNome, String cpf, Date dataNasc,
			String telefone, Plano planoCliente) {
		this.primeiroNome = primeiroNome;
		this.nomeMeio = nomeMeio;
		this.ultimoNome = ultimoNome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.planoCliente = planoCliente;
	}
	
	public void atualizaClienteSetters(String primeiroNome, String nomeMeio, String ultimoNome, String cpf, Date dataNasc,
	String telefone, Plano planoCliente) {
		setPrimeiroNome(primeiroNome);
		setNomeMeio(nomeMeio);
		setUltimoNome(ultimoNome);
		setCpf(cpf);
		setDataNasc(dataNasc);
		setTelefone(telefone);
		setPlanoCliente(planoCliente);
	}
	
	
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", primeiroNome=" + primeiroNome + ", nomeMeio=" + nomeMeio + ", ultimoNome="
				+ ultimoNome + ", cpf=" + cpf + ", dataNasc=" + dataNasc + ", telefone=" + telefone + ", planoCliente="
				+ planoCliente + "]";
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getNomeMeio() {
		return nomeMeio;
	}

	public void setNomeMeio(String nomeMeio) {
		this.nomeMeio = nomeMeio;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Plano getPlanoCliente() {
		return planoCliente;
	}

	public void setPlanoCliente(Plano planoCliente) {
		this.planoCliente = planoCliente;
	}
	
	
}
