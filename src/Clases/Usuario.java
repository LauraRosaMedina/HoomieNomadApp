package Clases;
import java.util.*;
public class Usuario {
	
	//atributos//
	private String nombre;	
	private String dni;
	private String email;
	private String telefono;
	private Perfil perfil;
	private ArrayList<Casa> casas;
	
	/*constructor con datos: cuando instanciamos este objeto, llamamos implícitamente a este constructor
	y le introducimos los valores que especificamos aquí*/
	
	public Usuario (String nombre, String dni, String email, String telefono, Perfil perfil) {
		this.nombre=nombre;
		this.dni=dni;
		this.email=email;
		this.telefono=telefono;
		this.perfil=perfil;
		this.casas=new ArrayList<>();
	}
	
	//Getters: para obtener la información cuando la necesitemos//
	
	public String getNombre () {
		return this.nombre;
	}
	public String getDni () {
		return this.dni;
	}
	public String getEmail () {
		return this.email;
	}
	public String getTelefono () {
		return this.telefono;
	}
	public Perfil getPerfil () {
		return this.perfil;
	}
	public ArrayList<Casa> getCasas() {
		return this.casas;
	}	
	
	//Setters: sólo de teléfono, email, perfil//
	
	public void setTelefono (String nuevoTelefono) {
		this.telefono=nuevoTelefono;
	}
	public void setEmail (String nuevoEmail) {
		this.email=nuevoEmail;
	}
	public void setPerfil (Perfil nuevoPerfil) {
		this.perfil=nuevoPerfil;
	}
	
	//Métodos propios de esta clase//
	
	
	

	
	
	
}
