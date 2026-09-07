package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.model.Artist;
import com.discography.demo.model.Track;
import com.discography.demo.service.interf.IArtistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "artistServlet", value = "/artist")
public class ServletArtist extends HttpServlet {

    private ConfigurableApplicationContext context;
    private IArtistService artistService;

    @Override
    public void init() throws ServletException {
        super.init();

        // SELECCIONA LA OPCIÓN QUE QUIERAS PROBAR (Descomenta solo una):

        // Opción 1: Configuración basada en Java Config (@Configuration)
        this.context = new AnnotationConfigApplicationContext("com.discography.demo");

        // Opción 2: Configuración basada en archivo XML
        // this.context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Opción 3: Configuración basada en Anotaciones (@Service, @Repository y escaneo de paquetes)
        // this.context = new AnnotationConfigApplicationContext("com.discography.demo");

        this.artistService = this.context.getBean(IArtistService.class);
    }

    @Override
    public void destroy() {
        if (this.context != null) {
            this.context.close();
        }
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        String action = req.getParameter("action");
        if (action == null) action = "list";

        out.println("<!DOCTYPE html><html><body>");
        out.println("<nav>");
        out.println("<a href='artist?action=list'>List Artists</a> | ");
        out.println("<a href='artist?action=search'>Search Artist</a> | ");
        out.println("<a href='artist?action=delete'>Delete Artist</a>");
        out.println("</nav><hr>");

        switch (action) {
            case "search":
                showSearchForm(req, out);
                break;
            case "delete":
                showDeleteForm(out);
                break;
            case "list":
            default:
                showListAndCreateForm(out);
                break;
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            String name = req.getParameter("name");
            String nationality = req.getParameter("nationality");

            artistService.addArtist(name, nationality);
            resp.sendRedirect("artist?action=list");

        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            artistService.removeArtistById(id);
            resp.sendRedirect("artist?action=list");
        }
    }

    private void showListAndCreateForm(PrintWriter out) {
        List<Artist> artists = artistService.getAllTheArtist();

        out.println("<h2>Artist Records</h2>");
        out.println("<ul>");
        for (Artist a : artists) {
            out.println("<li>ID: " + a.getIdArtist() + " - " + a.getName() + " - " + a.getNationality() + "</li>");
        }
        out.println("</ul>");
        out.println("<hr>");
        out.println("<h3>Register New Artist</h3>");
        out.println("<form method='post' action='artist?action=create'>");
        out.println("  Name: <input type='text' name='name' required /><br/>");
        out.println("  Nationality: <input type='text' name='nationality' required /><br/><br/>");
        out.println("  <button type='submit'>Save</button>");
        out.println("</form>");
    }

    private void showSearchForm(HttpServletRequest req, PrintWriter out) {
        out.println("<h2>Search Artist by Name</h2>");
        out.println("<form method='get' action='artist'>");
        out.println("  <input type='hidden' name='action' value='search' />");
        out.println("  Name: <input type='text' name='name' required />");
        out.println("  <button type='submit'>Search</button>");
        out.println("</form><br/>");

        String name = req.getParameter("name");
        if (name != null && !name.trim().isEmpty()) {
            Artist artist = artistService.findByName(name);
            if (artist != null) {
                out.println("<h3>Result:</h3>");
                out.println("<p><strong>ID:</strong> " + artist.getIdArtist() + "</p>");
                out.println("<p><strong>Name:</strong> " + artist.getName() + "</p>");
                out.println("<p><strong>Nationality:</strong> " + artist.getNationality() + "</p>");
                
                out.println("<h4>Associated Tracks:</h4>");
                out.println("<ul>");
                if (artist.getTracks() != null && !artist.getTracks().isEmpty()) {
                    for (Track t : artist.getTracks()) {
                        out.println("<li>" + t.getTitle() + " (" + t.getDuration() + " sec)</li>");
                    }
                } else {
                    out.println("<li>No associated tracks found.</li>");
                }
                out.println("</ul>");
            } else {
                out.println("<p style='color:red;'>No artist found with the name: " + name + "</p>");
            }
        }
    }

    private void showDeleteForm(PrintWriter out) {
        out.println("<h2>Delete Artist by ID</h2>");
        out.println("<form method='post' action='artist?action=delete'>");
        out.println("  Artist ID: <input type='text' name='id' required /><br/><br/>");
        out.println("  <button type='submit'>Delete</button>");
        out.println("</form>");
    }
}