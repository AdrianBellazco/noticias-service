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
import java.util.Optional;

@RestController()
public class NoticiaRestController {

    @Autowired
    private NoticiaService noticiaService;

    //crear noticia
    @PostMapping("/crear_noticia")
    public Noticia createNoticia(@RequestBody Noticia noticia)
    {
        return this.noticiaService.save(noticia);
    }

    //eliminar noticia y controlar errores
    @DeleteMapping("/eliminar_noticia/{id}")
    public void deleteNoticia(@PathVariable int id) {
        Noticia noticia = this.noticiaService.findById(id);
        if(noticia != null) {
            this.noticiaService.delete(noticia);
        }
    }

    // Listar todas las noticias (para usuarios regulares, solo muestra las no eliminadas)
    @GetMapping("/mostrar_noticia")
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
    //Modificar y actualizar noticia
    @PutMapping("/modificar_noticia/{id}")
    public ResponseEntity<?> actualizarNoticia(@PathVariable int id, @RequestBody Noticia newnoticia){
        try {
            Optional<Noticia> noticiaOptional  = Optional.ofNullable(noticiaService.findById(id));
            if (noticiaOptional .isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticia no encontrada, ingrese de nuevo");
            }

            Noticia noticia = noticiaOptional.get();

            noticia.setTitulo(newnoticia.getTitulo());
            noticia.setTexto(newnoticia.getTexto());
            noticia.setAutor(newnoticia.getAutor());
            noticia.setImagen(newnoticia.getImagen());
            noticia.setFecha(newnoticia.getFecha());

            Noticia noticiaActualizada = noticiaService.save(noticia);

            return ResponseEntity.ok(noticiaActualizada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al actualizar la noticia");
        }
    }

    @GetMapping("/noticias/filter")
    public ResponseEntity<List<Noticia>> filterNoticias(
            @RequestParam(required = false) String programa,
            @RequestParam(required = false) String importancia,
            @RequestParam(required = false) String lugar,
            @RequestParam(required = false) Boolean diurna,
            @RequestParam(required = false) Boolean nocturna,
            @RequestParam(required = false) Boolean evento,
            @RequestParam(required = false) Boolean noticia) {

        List<Noticia> noticias = noticiaService.filterNoticia(programa, importancia, lugar, diurna, nocturna, evento, noticia);
        return ResponseEntity.ok(noticias);
    }


}
