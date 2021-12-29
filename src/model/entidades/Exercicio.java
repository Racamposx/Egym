package model.entidades;

public class Exercicio {
	private String nome;
	private Integer id;
	
	
	public Exercicio() {
		
	}
	
	public Exercicio(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Exercicio [nome=" + nome + ", id=" + id + "]";
	}
	
	
	
}
