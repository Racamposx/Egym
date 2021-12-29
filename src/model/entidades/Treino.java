package model.entidades;

public class Treino {
	private Integer id;
	private Ficha fichaTreino;
	private String nomeTreino;
	private String tipo;
	
	public Treino() {
		
	}

	public Treino(Integer id, Ficha fichaTreino, String nomeTreino, String tipo) {
		this.id = id;
		this.fichaTreino = fichaTreino;
		this.nomeTreino = nomeTreino;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Ficha getFichaTreino() {
		return fichaTreino;
	}

	public void setFichaTreino(Ficha fichaTreino) {
		this.fichaTreino = fichaTreino;
	}

	public String getNomeTreino() {
		return nomeTreino;
	}

	public void setNomeTreino(String nomeTreino) {
		this.nomeTreino = nomeTreino;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Treino [id=" + id + ", fichaTreino=" + fichaTreino + ", nomeTreino=" + nomeTreino + ", tipo=" + tipo
				+ "]";
	}
	
	
	
	
}
