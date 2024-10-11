package co.edu.uceva.noticiasservice.model.dao;
import org.springframework.data.domain.Sort;

import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NoticiaDao extends CrudRepository<Noticia, Integer> {


    List<Noticia> findByEliminadaFalse(Sort sort);
    List<Noticia> findAll(Sort sort);


}
