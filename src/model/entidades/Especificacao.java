package model.entidades;

public class Especificacao {
	private Integer id;
	private Integer repeticao;
	private Integer carga;
	private Integer series;
	private String descricao;
	private Exercicio exercicio;
	private Treino treino;
	
	public Especificacao() {
		
	}
	
	public Especificacao(Integer repeticao, Integer carga, Integer series, String descricao) {
		this.repeticao = repeticao;
		this.carga = carga;
		this.series = series;
		this.descricao = descricao;
	}

		
	@Override
	public String toString() {
		return "Especificacao [id=" + id + ", repeticao=" + repeticao + ", carga=" + carga + ", series=" + series
				+ ", descricao=" + descricao + "]";
	}
	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRepeticao() {
		return repeticao;
	}
	
	public void setRepeticao(Integer repeticao) {
		this.repeticao = repeticao;
	}
	
	public Integer getCarga() {
		return carga;
	}
	
	public void setCarga(Integer carga) {
		this.carga = carga;
	}
	
	public Integer getSeries() {
		return series;
	}
	
	public void setSeries(Integer series) {
		this.series = series;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Exercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}
	
	
}
