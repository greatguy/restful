package br.com.medilab.resources;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.medilab.model.Usuario;

@Path("/usuarios")
public class UsuarioResource {

	
	static private Map<Integer, Usuario> usuarios;

	static {
		
		usuarios = new HashMap<Integer, Usuario>();

		Usuario usuario1 = new Usuario();
		usuario1.setId(1);
		usuario1.setNome("Raphael Alves");
		usuario1.setLogin("raphael");
		usuario1.setSenha("123456");

		usuarios.put(usuario1.getId(), usuario1);

		
		Usuario usuario2 = new Usuario();
		usuario2.setId(2);
		usuario2.setNome("Ricardo Bastos");
		usuario2.setLogin("ricardo");
		usuario2.setSenha("234567");

		usuarios.put(usuario2.getId(), usuario2);

		Usuario usuario3 = new Usuario();
		usuario3.setId(3);
		usuario3.setNome("Beatriz Silva");
		usuario3.setLogin("bia");
		usuario3.setSenha("456789");

		usuarios.put(usuario3.getId(), usuario3);
		
		Usuario usuario4 = new Usuario();
		usuario4.setId(4);
		usuario4.setNome("Ana Botafogo");
		usuario4.setLogin("ana");
		usuario4.setSenha("121212");

		usuarios.put(usuario4.getId(), usuario4);

	}

	@GET
	@Produces("text/xml")
	public List<Usuario> getUsuarios() {
		return new ArrayList<Usuario>(usuarios.values());
	}

	@Produces("text/xml")
	public Usuario getUsuario(@PathParam("id") int id)  {
		
		return usuarios.get(id);
	}

	
	@Path("{id}")
	@GET
	@Produces("text/xml")
	public Usuario getUsuario(@PathParam("id") int id, @HeaderParam("login") String login, @HeaderParam("senha") String senha) throws URISyntaxException {
		
		Boolean auth = false;
		
		for(Map.Entry<Integer, Usuario> elemento : usuarios.entrySet()) {
		    
			Integer chave = elemento.getKey();
		    Usuario usuario = elemento.getValue();
		    
		    if(usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
		    	auth = true;
		    }
		}
		
		if(auth) return getUsuario(id); else throw new NotAuthorizedException("Autenticação inválida.");
		
	}
	
	@POST
	@Consumes("text/xml")
	@Produces("text/plain")
	public String adicionaUsuario(Usuario usuario) {
		usuario.setId(usuarios.size() + 1);
		usuarios.put(usuario.getId(), usuario);
		return usuario.getNome() + " adicionado";
	}

	@Path("{id}")
	@PUT
	@Consumes("text/xml")
	@Produces("text/plain")
	public String atualizaUsuario(Usuario usuario, @PathParam("id") int id) {
		Usuario ant = getUsuario(usuario.getId());
		ant.setNome(usuario.getNome());
		ant.setLogin(usuario.getLogin());
		ant.setSenha(usuario.getSenha());
		return usuario.getNome() + " atualizado";
	}

	@Path("{id}")
	@DELETE
	@Produces("text/plain")
	public String removeUsuario(@PathParam("id") int id) {
		usuarios.remove(id);
		return "Usuário excluido.";
	}
}
