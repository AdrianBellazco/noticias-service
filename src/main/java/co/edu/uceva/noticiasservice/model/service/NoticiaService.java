package co.edu.uceva.noticiasservice.model.service;

import co.edu.uceva.noticiasservice.model.entities.Noticia;

public interface NoticiaService {

    Noticia save(Noticia noticia);

    void delete(Noticia noticia);

    Noticia findById(int id);

}
