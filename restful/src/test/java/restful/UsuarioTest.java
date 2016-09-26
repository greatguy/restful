package restful;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

import br.com.medilab.model.Usuario;

public class UsuarioTest {

	@Test
	public void test() {
		
		 Client client = ClientBuilder.newClient();
		 
		
		 WebTarget target = client.target("http://localhost:8080/restful");	
		 
		 
		 Usuario usuario = (Usuario) target.path("/usuarios/1").request().header("login","raphael").header("senha","123456").get(Usuario.class);
		 Assert.assertEquals("Raphael Alves",usuario.getNome());
		
		 Usuario usuario2 = (Usuario) target.path("/usuarios/2").request().header("login","ricardo").header("senha","234567").get(Usuario.class);
		 Assert.assertEquals("ricardo",usuario2.getLogin());
		
	}

}
