package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.model.Artist;
import com.discography.demo.service.interf.IArtistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "artistServlet", value = "/artist")
public class ServletArtist extends HttpServlet {

    private IArtistService artistService;
    private AnnotationConfigApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Inicializamos el contexto de Spring una sola vez por ciclo de vida del servlet
            this.context = new AnnotationConfigApplicationContext("com.discography.demo");
            this.artistService = this.context.getBean(IArtistService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        String action = req.getParameter("action");
        if (action == null) action = "list";

        out.println("<!DOCTYPE html><html><head><title>Artist Management</title></head><body>");
        out.println("<nav>");
        out.println("<a href='artist?action=list'>List & Register Artists</a> | ");
        out.println("<a href='artist?action=search'>Search Artist</a> | ");
        out.println("<a href='artist?action=delete'>Delete Artist</a>");
        out.println("</nav><hr>");

        if (artistService == null) {
            out.println("<p style='color:red;'>Error: Spring Context failed to load IArtistService.</p>");
            out.println("</body></html>");
            return;
        }

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

            if (artistService != null && name != null && nationality != null) {
                artistService.addArtist(name, nationality);
            }
            resp.sendRedirect("artist?action=list");

        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (artistService != null && id != null) {
                artistService.removeArtistById(id);
            }
            resp.sendRedirect("artist?action=list");
        }
    }

    private void showListAndCreateForm(PrintWriter out) {
        out.println("<h2>Register New Artist</h2>");
        out.println("<form method='post' action='artist?action=create'>");
        out.println(" Name: <input type='text' name='name' required /><br/><br/>");
        out.println(" Nationality: <input type='text' name='nationality' required /><br/><br/>");
        out.println(" <button type='submit'>Save Artist</button>");
        out.println("</form>");
        
        out.println("<hr><h2>Artist Records</h2>");
        out.println("<ul>");
        try {
            List<Artist> artists = artistService.getAllTheArtist();
            if (artists != null && !artists.isEmpty()) {
                for (Artist a : artists) {
                    out.println("<li>ID: " + a.getIdArtist() + " - " + a.getName() + " (" + a.getNationality() + ")</li>");
                }
            } else {
                out.println("<li>No artists registered yet.</li>");
            }
        } catch (Exception e) {
            out.println("<li style='color:red;'>Error loading artists: " + e.getMessage() + "</li>");
        }
        out.println("</ul>");
    }

    private void showSearchForm(HttpServletRequest req, PrintWriter out) {
        out.println("<h2>Search Artist by Name</h2>");
        out.println("<form method='get' action='artist'>");
        out.println(" <input type='hidden' name='action' value='search' />");
        out.println(" Name: <input type='text' name='name' required />");
        out.println(" <button type='submit'>Search</button>");
        out.println("</form><br/>");

        String name = req.getParameter("name");
        if (name != null && !name.trim().isEmpty()) {
            Artist artist = artistService.findByName(name);
            if (artist != null) {
                out.println("<h3>Result:</h3>");
                out.println("<p><strong>ID:</strong> " + artist.getIdArtist() + "</p>");
                out.println("<p><strong>Name:</strong> " + artist.getName() + "</p>");
                out.println("<p><strong>Nationality:</strong> " + artist.getNationality() + "</p>");
            } else {
                out.println("<p style='color:red;'>No artist found with the name: " + name + "</p>");
            }
        }
    }

    private void showDeleteForm(PrintWriter out) {
        out.println("<h2>Delete Artist by ID</h2>");
        out.println("<form method='post' action='artist?action=delete'>");
        out.println(" Artist ID: <input type='text' name='id' required /><br/><br/>");
        out.println(" <button type='submit'>Delete</button>");
        out.println("</form>");
    }
}