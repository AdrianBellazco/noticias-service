package co.edu.uceva.noticiasservice.model.service;

import co.edu.uceva.noticiasservice.model.dao.NoticiaDao;
import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        noticiaDao.delete(noticia);
    }

    public Noticia findById(int id){
        return noticiaDao.findById(id).orElse(null);
    }



}
