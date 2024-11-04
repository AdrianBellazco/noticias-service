package co.edu.uceva.noticiasservice.model.dao;

import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticiaDao extends CrudRepository<Noticia, Integer> {


    List<Noticia> findByEliminadaFalse();

    List<Noticia> findByfavorita(boolean favorita);

    @Query("SELECT n FROM Noticia n WHERE " +
            "(:programa IS NULL OR n.programa LIKE %:programa%) " +
            "AND (:importancia IS NULL OR n.importancia = :importancia) " +
            "AND (:lugar IS NULL OR n.lugar = :lugar) " +
            "AND (:diurna IS NULL OR n.diurna = :diurna) " +
            "AND (:nocturna IS NULL OR n.nocturna = :nocturna) " +
            "AND (:evento IS NULL OR n.evento = :evento) " +
            "AND (:noticia IS NULL OR n.noticia = :noticia)")
    List<Noticia> filterNoticia(@Param("programa") String programa,
                                @Param("importancia") String importancia,
                                @Param("lugar") String lugar,
                                @Param("diurna") Boolean diurna,
                                @Param("nocturna") Boolean nocturna,
                                @Param("evento") Boolean evento,
                                @Param("noticia") Boolean noticia);

}
