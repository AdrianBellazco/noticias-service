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

    public Boolean getId(){
        return eliminada;
    }

    public  void setID(boolean eliminada){
        this.eliminada = eliminada;
    }
}



