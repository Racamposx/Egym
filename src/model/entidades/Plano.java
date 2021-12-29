package model.entidades;

public class Plano {
	private Integer id;
	private String nome;
	private double preco;
	private String descricao;
	
	public Plano() {
		
	}
	
	
	public Plano(String nome, double preco, String descricao) {
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
	}
	
	public void atualizaPlano(String nome, double preco, String descricao) {
		setNome(nome);
		setPreco(preco);
		setDescricao(descricao);
	}
	
	
	@Override
	public String toString() {
		return this.nome;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
