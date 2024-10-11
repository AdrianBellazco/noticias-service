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


}
