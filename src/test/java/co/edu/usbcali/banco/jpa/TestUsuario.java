package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.modelo.Usuario;
import co.edu.usbcali.banco.modelo.TipoUsuario;
import co.edu.usbcali.banco.modelo.Transaccion;

class TestUsuario {

private final static Logger log=LoggerFactory.getLogger(TestUsuario.class);
	
	static EntityManagerFactory entityManagerFactory;
	static EntityManager entityManager=null;
	
	String usuUsuario = "andresfelipe";
	
	
	@Test
	@DisplayName("ConsultarUsuarioxTransaccions")
	void etest() {
		assertNotNull(entityManager,"El entitymanager es nulo");
		String jpql = "SELECT usu FROM Usuario usu";
		
		List<Usuario> lstUsuarios = entityManager.createQuery(jpql).getResultList();
		
		lstUsuarios.forEach(Usuario->{
			log.info("Id: "+Usuario.getUsuUsuario());
			log.info("Nombre: "+Usuario.getNombre());
			log.info("-------------------------------------");
			
			
//			String jpql2 = "SELECT tra FROM Transaccion tra where tra.usu.usuUsuario =" +Usuario.getUsuUsuario();
//			
//			List<Transaccion> lstTransaccion = entityManager.createQuery(jpql2).getResultList();
//			
//			lstTransaccion.forEach(Transaccion->{
//				log.info("Transacccion: "+Transaccion.getTranId());
//			});
//			
//			log.info("-------------------------------------");
		});		
		
		
//		for(Usuario Usuario:lstUsuarios) {
//			
//			log.info("Id: "+Usuario.getClieId());
//			log.info("Nombre: "+Usuario.getNombre());
//		}
	}
	
	@Test
	@DisplayName("BorrarUsuario")
	void dtest() {
		assertNotNull(entityManager,"El entitymanager es nulo");
		Usuario Usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(Usuario,"El Usuario no existe");

		entityManager.getTransaction().begin();
			entityManager.remove(Usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ModificarUsuario")
	void ctest() {
		assertNotNull(entityManager,"El entitymanager es nulo");
		Usuario Usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(Usuario,"El Usuario no existe");
		
		Usuario.setActivo('S');
		Usuario.setUsuUsuario(usuUsuario);
		Usuario.setClave("dhsgdakshd");
		Usuario.setIdentificacion(new BigDecimal(3));
		Usuario.setNombre("ingeniero");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsuario,"El tipo de usuario no existe");
		Usuario.setTipoUsuario(tipoUsuario);

		entityManager.getTransaction().begin();
		entityManager.merge(Usuario);
		entityManager.getTransaction().commit();
	}
	
	
	@Test
	@DisplayName("CrearUsuario")
	void atest() {
		assertNotNull(entityManager,"El entitymanager es nulo");
		Usuario Usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNull(Usuario,"El Usuario ya existe");
		Usuario = new Usuario();
		Usuario.setActivo('S');
		Usuario.setUsuUsuario(usuUsuario);
		Usuario.setClave("dhsgdakshd");
		Usuario.setIdentificacion(new BigDecimal(3));
		Usuario.setNombre("mecanico");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsuario,"El tipo de usuario no existe");
		Usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
		entityManager.persist(Usuario);
		entityManager.getTransaction().commit();
	}
	
	@BeforeAll
	public static void iniciar() {
		log.info("Ejecuto el @BeforeAll");
		entityManagerFactory = Persistence.createEntityManagerFactory("banco-logica");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterAll
	public static void finalizar() {
		log.info("Ejecuto el @AfterAll");
		entityManagerFactory.close();
		entityManager.close();
	}

	@Test
	@DisplayName("ConsultarUsuarioPorId")
	void btest() {
		assertNotNull(entityManager,"El entitymanager es nulo");
		Usuario Usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(Usuario,"El Usuario ya existe");
		
		log.info("Id "+Usuario.getUsuUsuario());
		log.info("Nombre "+Usuario.getNombre());
	}

	@BeforeEach
	public void antes() {
		log.info("Antes de la prueba");
	}
	
	@AfterEach
	public void despues() {
		log.info("Despues de la prueba");
	}

}
