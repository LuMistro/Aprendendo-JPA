package br.senac.sc.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // essa classe é uma tabela do banco
@Table(name = "funcionario") // @Table dita as configurações da tabela no banco, nesse caso, o nome
public class Funcionario {

	@Id // indica que é a PK no banco
	@GeneratedValue(strategy = GenerationType.IDENTITY) // este campo é configurado(preenchido) automaticamente pelo
														// banco
	private Long codigo;

	@Column(length = 100, nullable = false) // é uma coluna no banco, tamanho 100 e not null;
	private String nome;

	@Column(length = 100, nullable = false) // é uma coluna no banco, tamanho 100 e not null;
	private String email;

	@OneToOne(cascade = CascadeType.ALL) // Sem o cascade, é usado para persistir os dois, em cascata (ele faz o crud todo em cascata)
	@JoinColumn(name = "idEndereco")
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "idDepartamento")
	private Departamento departamento;

	public Funcionario(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	public Funcionario() {
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Funcionario [codigo=" + codigo + ", nome=" + nome + ", email=" + email + ", endereco=" + endereco
				+ ", departamento=" + departamento + "]";
	}

}
