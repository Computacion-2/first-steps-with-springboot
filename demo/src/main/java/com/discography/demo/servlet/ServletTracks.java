package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.config.AppConfig;
import com.discography.demo.model.Artist;
import com.discography.demo.model.Track;
import com.discography.demo.service.interf.IArtistService;
import com.discography.demo.service.interf.ITrackService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "trackServlet", value = "/tracks")
public class ServletTracks extends HttpServlet {

    private ConfigurableApplicationContext context;
    private ITrackService trackService;
    private IArtistService artistService;

    @Override
    public void init() throws ServletException {
        super.init();

        // SELECCIONA LA OPCIÓN QUE QUIERAS PROBAR (Descomenta solo una):

        // Opción 1: Configuración basada en Java Config (@Configuration)
        this.context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Opción 2: Configuración basada en archivo XML
        // this.context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Opción 3: Configuración basada en Anotaciones (@Service, @Repository y escaneo de paquetes)
        // this.context = new AnnotationConfigApplicationContext("com.discography.demo");

        this.trackService = this.context.getBean(ITrackService.class);
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
        out.println("<a href='tracks?action=list'>List Tracks</a> | ");
        out.println("<a href='tracks?action=create'>Create Track</a> | ");
        out.println("<a href='tracks?action=delete'>Delete Track</a>");
        out.println("</nav><hr>");

        switch (action) {
            case "create":
                showCreateForm(out);
                break;
            case "delete":
                showDeleteForm(out);
                break;
            case "list":
            default:
                showTrackList(out);
                break;
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            String title = req.getParameter("title");
            int duration = Integer.parseInt(req.getParameter("duration"));
            String[] artistIdsStr = req.getParameterValues("artistIds");

            List<Artist> selectedArtists = new ArrayList<>();
            if (artistIdsStr != null) {
                for (String idStr : artistIdsStr) {
                    Artist artist = artistService.findById(idStr);
                    if (artist != null) {
                        selectedArtists.add(artist);
                    }
                }
            }

            Track track = new Track();
            track.setTitle(title);
            track.setDuration(duration);
            track.setArtists(selectedArtists);

            trackService.save(track);
            resp.sendRedirect("tracks?action=list");

        } else if ("delete".equals(action)) {
        String id = req.getParameter("id");
        trackService.removeTrackById(id);
        resp.sendRedirect("tracks?action=list");
}
    }

    private void showTrackList(PrintWriter out) {
    List<Track> tracks = trackService.getAllTracks(); // Llamar a getAllTracks()

    out.println("<h2>Track Records</h2>");
    out.println("<table border='1' cellpadding='5'>");
    out.println("<tr><th>ID</th><th>Title</th><th>Genre</th><th>Duration (sec)</th><th>Album</th><th>Associated Artists</th></tr>");

    for (Track t : tracks) {
        out.println("<tr>");
        out.println("<td>" + t.getIdTrack() + "</td>");
        out.println("<td>" + t.getTitle() + "</td>");
        out.println("<td>" + t.getGenre() + "</td>");
        out.println("<td>" + t.getDuration() + "</td>");
        out.println("<td>" + t.getAlbumTitle() + "</td>");
        
        out.println("<td><ul>");
        if (t.getArtists() != null && !t.getArtists().isEmpty()) {
            for (Artist a : t.getArtists()) {
                out.println("<li>" + a.getName() + "</li>");
            }
        } else {
            out.println("<li>No artists assigned</li>");
        }
        out.println("</ul></td>");
        out.println("</tr>");
    }

    out.println("</table>");
}

    private void showCreateForm(PrintWriter out) {
        List<Artist> artists = artistService.getAllTheArtist();

        out.println("<h2>Create Track</h2>");
        out.println("<form method='post' action='tracks?action=create'>");
        out.println("  Title: <input type='text' name='title' required /><br/><br/>");
        out.println("  Duration (seconds): <input type='number' name='duration' required /><br/><br/>");
        
        out.println("  <label>Select Artists:</label><br/>");
        if (artists != null && !artists.isEmpty()) {
            for (Artist a : artists) {
                out.println("  <input type='checkbox' name='artistIds' value='" + a.getIdArtist() + "' /> " + a.getName() + "<br/>");
            }
        } else {
            out.println("  <p style='color:red;'>No artists available to assign. Please create an artist first.</p>");
        }
        
        out.println("  <br/><button type='submit'>Save Track</button>");
        out.println("</form>");
    }

    private void showDeleteForm(PrintWriter out) {
        out.println("<h2>Delete Track by ID</h2>");
        out.println("<form method='post' action='tracks?action=delete'>");
        out.println("  Track ID: <input type='text' name='id' required /><br/><br/>");
        out.println("  <button type='submit'>Delete</button>");
        out.println("</form>");
}
}