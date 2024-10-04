package co.edu.uceva.noticiasservice.model.dao;

import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.data.repository.CrudRepository;

public interface NoticiaDao extends CrudRepository<Noticia, Integer> {

}
