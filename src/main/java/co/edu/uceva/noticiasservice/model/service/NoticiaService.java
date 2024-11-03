package co.edu.uceva.noticiasservice.model.service;

import co.edu.uceva.noticiasservice.model.entities.Noticia;

import java.util.List;

public interface NoticiaService {

    Noticia save(Noticia noticia);

    void delete(Noticia noticia);

    Noticia findById(int id);

    List<Noticia> listarTodas();


    List<Noticia> listar();

    Noticia update(Noticia noticia);


    List<Noticia> filterNoticia(String programa, String importancia, String lugar, boolean diurna, boolean nocturna, boolean evento, boolean noticia);

    List<Noticia> filterNoticia(String programa, String importancia, String lugar, Boolean diurna, Boolean nocturna, Boolean evento, Boolean noticia);
}
