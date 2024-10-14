package co.edu.uceva.noticiasservice.model.dao;

import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NoticiaDao extends CrudRepository<Noticia, Integer> {


    List<Noticia> findByEliminadaFalse();

    List<Noticia> findByGuardar(boolean guardar);
}
