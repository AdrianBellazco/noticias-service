package co.edu.uceva.noticiasservice;

import co.edu.uceva.noticiasservice.model.entities.Noticia;
import co.edu.uceva.noticiasservice.model.service.NoticiaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticiaRestControllerTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private NoticiaService noticiaService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCrearNoticia() throws Exception {
        Noticia noticia = new Noticia();
        noticia.setTitulo("Título de prueba");
        noticia.setTexto("Contenido de prueba");

        this.mockMvc.perform(post("/noticias/crear_noticia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(noticia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(noticia.getTitulo())));

        noticiaService.delete(noticia);
    }

    @Test
    public void testListarNoticias() throws Exception {
        Noticia noticia1 = new Noticia();
        noticia1.setTitulo("Primera Noticia");
        noticia1.setTexto("Contenido de la primera noticia");
        noticiaService.save(noticia1);

        Noticia noticia2 = new Noticia();
        noticia2.setTitulo("Segunda Noticia");
        noticia2.setTexto("Contenido de la segunda noticia");
        noticiaService.save(noticia2);

        this.mockMvc.perform(get("/noticias/mostrar_noticia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo", is(noticia1.getTitulo())))
                .andExpect(jsonPath("$[1].titulo", is(noticia2.getTitulo())));

        noticiaService.delete(noticia1);
        noticiaService.delete(noticia2);
    }

    @Test
    public void testEliminarNoticia() throws Exception {
        Noticia noticia = new Noticia();
        noticia.setTitulo("Noticia a eliminar");
        noticia.setTexto("Contenido de la noticia a eliminar");
        noticia = noticiaService.save(noticia);

        this.mockMvc.perform(delete("/noticias/eliminar_noticia/{id}", noticia.getId()))
                .andExpect(status().isOk());

        assertNull(noticiaService.findById(noticia.getId()));
    }

    @Test
    public void testActualizarNoticia() throws Exception {
        Noticia noticia = new Noticia();
        noticia.setTitulo("Noticia original");
        noticia.setTexto("Contenido original");
        noticiaService.save(noticia);

        noticia.setTitulo("Noticia actualizada");
        noticia.setTexto("Contenido actualizado");

        this.mockMvc.perform(put("/noticias/modificar_noticia/{id}", noticia.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(noticia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(noticia.getTitulo())));

        noticiaService.delete(noticia);
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
