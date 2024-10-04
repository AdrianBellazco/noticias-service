package co.edu.uceva.noticiasservice.controller;


import co.edu.uceva.noticiasservice.model.entities.Noticia;
import co.edu.uceva.noticiasservice.model.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class NoticiaRestController {

    @Autowired
    private NoticiaService noticiaService;

    //crear noticia
    @PostMapping("/noticia")
    public Noticia createNoticia(@RequestBody Noticia noticia) {
        return this.noticiaService.save(noticia);
    }

    //eliminar noticia y controlar errores
    @DeleteMapping("/noticia/{id}")
    public void deleteNoticia(@PathVariable int id) {
        Noticia noticia = this.noticiaService.findById(id);
        this.noticiaService.delete(noticia);
    }






}
