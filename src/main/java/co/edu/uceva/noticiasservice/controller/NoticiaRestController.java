package co.edu.uceva.noticiasservice.controller;


import co.edu.uceva.noticiasservice.model.entities.Noticia;
import co.edu.uceva.noticiasservice.model.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/noticia")
    public ResponseEntity<?> getAllCelulars() {
        Map<String, Object> response = new HashMap<>();
        List<Noticia> noticia = null;
        try {
            noticia = this.noticiaService.listar();
        } catch (Exception e) {
            response.put("mensaje", "Error al listar los celulares");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
        return new ResponseEntity<List<Noticia>>(noticia, HttpStatus.OK);
}




}
