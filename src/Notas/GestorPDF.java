package Notas;
import java.util.ArrayList;
/**
 * 
 * FUNCINONA!
 * 
 * 
 * 
 * */
public class GestorPDF {
	private ArrayList<Asignatura> asignaturas;
	private String stringComparacion = "UNIVERSIDAD POLITÉCNICA DE MADRID";
	private boolean isExpediente;
	
	public GestorPDF(){
		asignaturas = new ArrayList<Asignatura>();
		isExpediente = true;
	}

	public boolean deteccionExpediente(String[] spliteado){
		if(!spliteado[0].equals(stringComparacion)){
			this.isExpediente = false;
		}
		return this.isExpediente;
	}
	
	public boolean getIsExpediente(){
		return this.isExpediente;
	}
	
	public ArrayList<Asignatura> getAsignaturas(String texto){
		//String[] spliteado = new String[2000];
		String[] spliteado = texto.split("\n");
		if(deteccionExpediente(spliteado)){
			for(int i=0; i<spliteado.length; i++){

				if((spliteado[i].startsWith("1") || spliteado[i].startsWith("2") || spliteado[i].startsWith("3")
						|| spliteado[i].startsWith("4"))){

					String[] palabra = spliteado[i].split(" ");
					boolean seguir = false;
					for(String linea : palabra){
						if (linea.contains("(")){
							seguir = true;
						}
						else
							seguir = false;
					}
					if(palabra[0].length()==1 && seguir){
						int curso = Integer.parseInt(palabra[0]);
						ArrayList<String> nombrePorPalabras = new ArrayList<String>();

						String nombre = "";
						float creditos = 0;

						//OBTENER NOMBRE ASIGNATURA
						int j=1;
						while((!palabra[j].equals("Semestral")) && (!palabra[j].equals("Cuatrimestral")) 
								&& (!palabra[j].equals("Anual"))){
							nombrePorPalabras.add(palabra[j]);
							j++;
						}
						for(String elemento : nombrePorPalabras){
							if(!(elemento.equals(nombrePorPalabras.get(nombrePorPalabras.size()-1))))
								nombre += elemento + " ";
							else
								nombre += elemento;
						}
						//Obtener creditos
						j++;
						creditos = Float.parseFloat(palabra[j].replace(",", "."));


						//Obtener calificacion
						int k=0;
						ArrayList<String> calificacionPorLetras = new ArrayList<String>();
						float calificacion = 0;
						while(!palabra[k].contains("(")){
							k++;
						}
						String[] trozoCalificacion = palabra[k].split("");
						int l = 0;
						while(!trozoCalificacion[l].equals("(")){
							l++;
						}
						l++;
						while(!trozoCalificacion[l].equals(")")){
							if(trozoCalificacion[l].equals(","))
								trozoCalificacion[l]=".";
							calificacionPorLetras.add(trozoCalificacion[l]);
							l++;
						}
						String numero="";
						for(String elemento :calificacionPorLetras){
							numero += elemento;
						}
						calificacion = Float.parseFloat(numero);


						k=0;
						l=0;
						asignaturas.add(new Asignatura(curso, nombre, creditos, calificacion));
					}


				}
			}
		}
		return asignaturas;
	}

	public float mediaPonderada(ArrayList<Asignatura> listaAsignaturas){
		float media=0;
		float acumulado=0;
		float creditos=0;
		for(Asignatura asignatura : listaAsignaturas){
			acumulado += asignatura.getCalificacion()*asignatura.getCreditos();
			creditos += asignatura.getCreditos();
		}
		media = acumulado / creditos;
		return media;
	}
}

