package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.config.AppConfig;
import com.discography.demo.model.Artist;
import com.discography.demo.service.interf.IArtistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "artistServlet", value = "/artist")
public class ServletArtist extends HttpServlet {
    
    @Autowired 
    private IArtistService artistService;

    @Override
    public void init() throws ServletException {

        super.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        this.artistService = (IArtistService) context.getBean("artistService");

    }

    //get all the artists and register a new artist
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Artist> artists = artistService.getAllTheArtist();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html><body>");
        out.println("<h2>Artist records</h2>");
        out.println("<ul>");
        for (Artist a : artists) {
            out.println("<li>" + a.getIdArtist() + " - " + a.getName() + " - " + a.getNationality() + "</li>");
        }
        out.println("</ul>");
        out.println("<hr>");
        out.println("<h3>Register new artist</h3>");
        out.println("<form method='post' action='demo/artist");
        out.println("  Nombre: <input type='text' name='name' required /><br/>");
        out.println("  Email:  <input type='email' name='nationality' required /><br/><br/>");
        out.println("  <button type='submit'>Guardar</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
