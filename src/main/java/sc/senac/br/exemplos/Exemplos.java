package sc.senac.br.exemplos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.senac.sc.model.Funcionario;
import br.senac.sc.model.Departamento;
import br.senac.sc.model.Endereco;

public class Exemplos {

	public static void main(String[] args) {

		// Essas duas linhas estabelecem conexão com o banco
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Funcionarios-PU");
		EntityManager entityManager = factory.createEntityManager();

//		 salva(entityManager);
//		altera(entityManager);
//		remove(entityManager);
//		buscaPorId(entityManager);
//		buscaTodosOrderNome(entityManager);
//		buscaPorNome(entityManager);
//		testaRelacionamentoOneToOne(entityManager);
//		testaRelacionamentoManyToOne(entityManager);
		testaRelacionamentoOneToMany(entityManager);
	}

	private static void salva(EntityManager entityManager) {
		Funcionario f = new Funcionario("Daniela Mistro", "Danimistro@gmil.com");
		entityManager.getTransaction().begin(); // inicia transação com o banco(abre conexão) (OBRIGATÓRIO)
		entityManager.persist(f); // insere registro (não insere na hora) -é um objeto persistente-
		entityManager.getTransaction().commit(); // Garante que o objeto vai pro banco na hora que executado
		entityManager.close(); // Fecha conexão com o banco
	}

	private static void altera(EntityManager entityManager) {
		Funcionario f = new Funcionario();
		f.setCodigo(1L);
		f.setNome("Luiza Mistro");
		f.setEmail("Luizamistro@gmail.com");

		entityManager.getTransaction().begin(); // inicia transação com o banco(abre conexão) (OBRIGATÓRIO)
		entityManager.merge(f); // atualiza registro (não att na hora)
		entityManager.getTransaction().commit(); // Garante que o objeto vai pro banco na hora que executado
		entityManager.close(); // Fecha conexão com o banco

	}

	private static void remove(EntityManager entityManager) {
		Funcionario f = new Funcionario();
		f.setCodigo(2L);
		entityManager.getTransaction().begin(); // inicia transação com o banco(abre conexão) (OBRIGATÓRIO)
		f = entityManager.find(Funcionario.class, f.getCodigo()); // procura o funcionario no banco para excluir
		entityManager.remove(f); // exclui do BD
		entityManager.getTransaction().commit(); // Garante que o objeto vai pro banco na hora que executado
		entityManager.close(); // Fecha conexão com o banco
	}

	private static void buscaPorId(EntityManager entityManager) {
		Funcionario f = new Funcionario();
		f.setCodigo(2L);
		f = entityManager.find(Funcionario.class, f.getCodigo()); // procura o funcionario no banco para excluir
		System.out.println(f);
		entityManager.close();
	}

	private static void buscaTodos(EntityManager entityManager) {
		// os jpql's equivalem ao sql n4esse contexto
		// Select * from funcionario - sql
		// Select f from Funcionario f - jpql
		// from funcionario - jpql
		TypedQuery<Funcionario> query = entityManager.createQuery("from Funcionario ", Funcionario.class);
		List<Funcionario> fs = query.getResultList();
		entityManager.close();
		for (Funcionario f : fs) {
			System.out.println(f);
		}
	}

	private static void buscaTodosOrderNome(EntityManager entityManager) {
		String jpql = "Select f from Funcionario f order by f.nome"; // Esse f.nome não se refere a coluna do banco e
																		// sim ao atributo da classe funcionario
		TypedQuery<Funcionario> query = entityManager.createQuery(jpql, Funcionario.class);
		List<Funcionario> fs = query.getResultList();
		entityManager.close();
		for (Funcionario f : fs) {
			System.out.println(f);
		}
	}

	private static void buscaPorNome(EntityManager entityManager) {
		String jpql = "Select f from Funcionario f where f.nome = :batata"; // o :batata equivale ao "?" (pode ser
																			// qualquer coisa)
		TypedQuery<Funcionario> query = entityManager.createQuery(jpql, Funcionario.class);
		query.setParameter("batata", "Luiza Maria");

		List<Funcionario> fs = query.getResultList();
		entityManager.close();

		for (Funcionario f : fs) {
			System.out.println(f);
		}
	}

	private static void buscaPorNomeLike(EntityManager entityManager) {
		String jpql = "Select f from Funcionario f where f.nome like concat('%', :batata\", '%')"; // o :batata equivale
																									// ao "?" (pode ser
																									// qualquer coisa)
//		String jpql = "Select f from Funcionario f where f.nome like :batata; // alternativo (teria que ser junto com o %Luiza%)
		TypedQuery<Funcionario> query = entityManager.createQuery(jpql, Funcionario.class);
		query.setParameter("batata", "Luiza");
//		query.setParameter("batata", "Luiza");// Alternativo
		List<Funcionario> fs = query.getResultList();
		entityManager.close();

		for (Funcionario f : fs) {
			System.out.println(f);
		}
	}

	private static void testaRelacionamentoOneToOne(EntityManager entityManager) {
		Endereco endereco = new Endereco();
		endereco.setNumero(123);
		endereco.setCep("88130-400");
		endereco.setRua("jose frederico goedert");

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Luiza");
		funcionario.setEmail("luiza@gmail.com");
		funcionario.setEndereco(endereco);

		entityManager.getTransaction().begin();
//		entityManager.persist(endereco);  Se euder persiste nos dois, na hora de deletar, tenho que deletar um  por um, senão os dois ao mesmo tempo // ver tambem na classe funcionario, o atributo private.
		entityManager.persist(funcionario);
		entityManager.getTransaction().commit();

		Funcionario funcionarioSalvo = entityManager.find(Funcionario.class, funcionario.getCodigo());

		System.out.println(funcionarioSalvo.toString());
		entityManager.close();

	}

	private static void testaRelacionamentoManyToOne(EntityManager entityManager) {
		Departamento departamento = new Departamento();
		departamento.setNome("Financeiro");

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("maria");
		funcionario.setEmail("sdnkfhscv.lsnfvl.szjszçvld");
		funcionario.setDepartamento(departamento);

		entityManager.getTransaction().begin();
		entityManager.persist(departamento);
		entityManager.persist(funcionario);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Funcionario funcionarioSalvo = entityManager.find(Funcionario.class, funcionario.getCodigo());

		System.out.println(funcionarioSalvo);
		entityManager.close();
	}

	private static void testaRelacionamentoOneToMany(EntityManager entityManager) {
		Departamento departamento = new Departamento();
		departamento.setNome("vendas");

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Gabs");
		funcionario.setEmail("akjdhaodn");
		funcionario.setDepartamento(departamento);

		Funcionario funcionarioS = new Funcionario();
		funcionarioS.setNome("GabsSSSSSS");
		funcionarioS.setEmail("akjdhSSSSSaodn");
		funcionarioS.setDepartamento(departamento);

		entityManager.getTransaction().begin();
		entityManager.persist(departamento);
		entityManager.persist(funcionario);
		entityManager.persist(funcionarioS);
		entityManager.getTransaction().commit();

		entityManager.clear();
		
		
		Departamento departamentoSalvo = entityManager.find(Departamento.class, departamento.getCodigo());
		
		for (Funcionario f : departamentoSalvo.getFuncionarios()) {
			System.out.println(f.toString());
		}
	}

}
