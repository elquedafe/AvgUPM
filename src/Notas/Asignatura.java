package Notas;
public class Asignatura {
	private int curso;
	private String nombre;
	private float creditos;
	private float calificacion;
	
	public Asignatura(){
		
	}
	
	public Asignatura(int curso, String nombre, float creditos, float calificacion){
		this.curso = curso;
		this.nombre = nombre;
		this.creditos = creditos;
		this.calificacion = calificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getCreditos() {
		return creditos;
	}

	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}

	public float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(float calificacion) {
		this.calificacion = calificacion;
	}
	
	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String toString(){
		return this.nombre;
		
	}
}
