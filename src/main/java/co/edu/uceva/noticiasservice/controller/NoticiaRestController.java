package co.edu.uceva.noticiasservice.controller;


import co.edu.uceva.noticiasservice.model.entities.Noticia;
import co.edu.uceva.noticiasservice.model.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class NoticiaRestController {

    @Autowired
    private NoticiaService noticiaService;

    //crear noticia
    @PostMapping("/noticia")
    public Noticia createNoticia(@RequestBody Noticia noticia)
    {
        return this.noticiaService.save(noticia);
    }

    //eliminar noticia y controlar errores
    @DeleteMapping("/noticia/{id}")
    public void deleteNoticia(@PathVariable int id) {
        Noticia noticia = this.noticiaService.findById(id);
        if(noticia != null) {
            this.noticiaService.delete(noticia);
        }
    }

    // Listar todas las noticias (para usuarios regulares, solo muestra las no eliminadas)
    @GetMapping("/noticia")
    public ResponseEntity<?> getAllNoticias() {
        List<Noticia> noticias = this.noticiaService.listar();
        return new ResponseEntity<List<Noticia>>(noticias, HttpStatus.OK);
    }

    // Listar historial de noticias (para administrador, muestra todas las noticias)
    @GetMapping("/noticia/historial")
    public ResponseEntity<?> getHistorialNoticias() {
        List<Noticia> noticias = this.noticiaService.listarTodas();  // Incluye eliminadas
        return new ResponseEntity<List<Noticia>>(noticias, HttpStatus.OK);
    }

}
