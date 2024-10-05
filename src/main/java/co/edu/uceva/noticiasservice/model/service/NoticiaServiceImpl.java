package co.edu.uceva.noticiasservice.model.service;

import co.edu.uceva.noticiasservice.model.dao.NoticiaDao;
import co.edu.uceva.noticiasservice.model.entities.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        noticiaDao.delete(noticia);
    }

    @Override
    public Noticia findById(int id){
        return noticiaDao.findById(id).orElse(null);
    }

    @Override
    public List<Noticia> listar() {
        return (List<Noticia>) noticiaDao.findAll();
    }

    @Override
    public Noticia update(Noticia noticia) { return noticiaDao.save(noticia);
    }

}
