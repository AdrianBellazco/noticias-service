package co.edu.uceva.noticiasservice.model.service;

import co.edu.uceva.noticiasservice.model.dao.NoticiaDao;
import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;


import java.util.List;

@Component
public class NoticiaServiceImpl implements NoticiaService {
    @Autowired
    NoticiaDao noticiaDao;

    @Override
    public Noticia save(Noticia noticia){
        return noticiaDao.save(noticia);
    }

    @Override
    public void delete(Noticia noticia){
        noticia.setEliminada(true);
        noticiaDao.save(noticia);
    }

    @Override
    public Noticia findById(int id){
        return noticiaDao.findById(id).orElse(null);
    }


    //listar noticias no eliminadas
    @Override
    public List<Noticia> listar() {
        return noticiaDao.findByEliminadaFalse();  // Solo devolvemos las no eliminadas
    }

    //listar todas las noticias
    public List<Noticia> listarTodas() {
        Iterable<Noticia> iterableNoticias = noticiaDao.findAll();
        List<Noticia> listaNoticias = new ArrayList<>();

        // Convertir el Iterable en una List
        iterableNoticias.forEach(listaNoticias::add);

        return listaNoticias;
    }


    @Override
    public Noticia update(Noticia noticia) { return noticiaDao.save(noticia);
    }

    @Override
    public List<Noticia> filterNoticia(String programa, String importancia, String lugar, boolean diurna, boolean nocturna, boolean evento, boolean noticia) {
        return List.of();
    }

    @Override
    public List<Noticia> filterNoticia(String programa, String importancia, String lugar, Boolean diurna, Boolean nocturna, Boolean evento, Boolean noticia) {
        // Si todos los filtros son nulos o false, retorna todas las noticias
        if ((programa == null || programa.isEmpty()) &&
                (importancia == null || importancia.isEmpty()) &&
                (lugar == null || lugar.isEmpty()) &&
                (diurna == null || !diurna) &&
                (nocturna == null || !nocturna) &&
                (evento == null || !evento) &&
                (noticia == null || !noticia)) {
            return (List<Noticia>) noticiaDao.findAll();
        }

        // Aquí asegúrate de que tu consulta es correcta
        return noticiaDao.filterNoticia(programa, importancia, lugar, diurna, nocturna, evento, noticia);
    }

    @Override
    public List<Noticia> findByNoticiaFavorita(boolean favorita){
        return noticiaDao.findByfavorita(favorita);
    }

}

