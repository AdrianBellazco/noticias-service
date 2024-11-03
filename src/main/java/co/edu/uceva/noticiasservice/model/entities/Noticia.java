package co.edu.uceva.noticiasservice.model.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String texto;
    private String autor;
    private String imagen;
    private String fecha;
    private boolean eliminada;
    //Categorias usadas para filtrar******
    //@ElementCollection
    private String programa;
    //private ArrayList<String> programa; // Nueva categoría
    private String importancia; // Nueva categoría
    private String lugar;       // Nueva categoría
    private boolean diurna;     // Nueva categoría
    private boolean nocturna;   // Nueva categoría
    private boolean evento;     // Nueva categoría
    private boolean noticia;    // Nueva categoría

    // Constructor inicializando la lista de programa vacía
    // public Noticia() {
    //    this.programa = new ArrayList<>();
    //}

    // Métodos para agregar a la lista de programa
    //public void addPrograma(String programa) {
    // this.programa.add(programa);
    //}
    //*****************

}

