package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.model.Artist;
import com.discography.demo.model.Track;
import com.discography.demo.service.interf.IArtistService;
import com.discography.demo.service.interf.IPublisherService;
import com.discography.demo.service.interf.ITrackService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "trackServlet", value = "/tracks")
public class ServletTrack extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private ITrackService trackService;
    private IArtistService artistService;
    private IPublisherService publisherService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.context = new AnnotationConfigApplicationContext("com.discography.demo");
            this.trackService = this.context.getBean(ITrackService.class);
            this.artistService = this.context.getBean(IArtistService.class);
            this.publisherService = this.context.getBean(IPublisherService.class);
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

        out.println("<!DOCTYPE html><html><head><title>Track Management</title></head><body>");
        out.println("<nav>");
        out.println("<a href='artist'>Manage Artists</a> | ");
        out.println("<a href='tracks?action=list'>List & Register Tracks</a> | ");
        out.println("<a href='tracks?action=delete'>Delete Track</a>");
        out.println("</nav><hr>");

        if (trackService == null || artistService == null) {
            out.println("<p style='color:red;'>Error: Spring Context failed to load services.</p>");
            out.println("</body></html>");
            return;
        }

        switch (action) {
            case "delete":
                showDeleteForm(out);
                break;
            case "list":
            default:
                showCreateForm(out);
                break;
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            try {
                String title = req.getParameter("title");
                String genre = req.getParameter("genre");
                String albumTitle = req.getParameter("albumTitle");
                int duration = Integer.parseInt(req.getParameter("duration"));
                String[] artistIds = req.getParameterValues("artistIds");

                Track newTrack = new Track("T" + System.currentTimeMillis() % 100000, title, genre, duration, albumTitle);
                Track savedTrack = trackService.save(newTrack);

                if (artistIds != null && savedTrack != null) {
                    for (String artistId : artistIds) {
                        Artist artist = artistService.findById(artistId);
                        if (artist != null) {
                            publisherService.addTrackToArtist(artist, savedTrack);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect("tracks?action=list");

        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (trackService != null && id != null) {
                trackService.removeTrackById(id);
            }
            resp.sendRedirect("tracks?action=list");
        }
    }

    private void showCreateForm(PrintWriter out) {
        out.println("<h2>Register New Track</h2>");
        out.println("<form method='post' action='tracks?action=create'>");
        out.println(" Title: <input type='text' name='title' required /><br/><br/>");
        out.println(" Genre: <input type='text' name='genre' required /><br/><br/>");
        out.println(" Album: <input type='text' name='albumTitle' required /><br/><br/>");
        out.println(" Duration (seconds): <input type='number' name='duration' required /><br/><br/>");
        
        out.println(" <label><strong>Select Artists for this Track:</strong></label><br/>");
        try {
            List<Artist> artists = artistService.getAllTheArtist();
            if (artists != null && !artists.isEmpty()) {
                for (Artist a : artists) {
                    out.println(" <input type='checkbox' name='artistIds' value='" + a.getIdArtist() + "' /> " + a.getName() + "<br/>");
                }
            } else {
                out.println(" <p style='color:red;'>No artists available. Please create an artist first.</p>");
            }
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error loading artists.</p>");
        }
        
        out.println("<br/><button type='submit'>Save Track</button>");
        out.println("</form>");

        out.println("<hr><h2>Track Records</h2>");
        out.println("<ul>");
        try {
            List<Track> tracks = trackService.getAllTracks();
            if (tracks != null && !tracks.isEmpty()) {
                for (Track t : tracks) {
                    out.println("<li>ID: " + t.getIdTrack() + " - " + t.getTitle() + " [" + t.getGenre() + "] (" + t.getDuration() + "s) - Album: " + t.getAlbumTitle() + "</li>");
                }
            } else {
                out.println("<li>No tracks registered yet.</li>");
            }
        } catch (Exception e) {
            out.println("<li style='color:red;'>Error loading tracks.</li>");
        }
        out.println("</ul>");
    }

    private void showDeleteForm(PrintWriter out) {
        out.println("<h2>Delete Track by ID</h2>");
        out.println("<form method='post' action='tracks?action=delete'>");
        out.println(" Track ID: <input type='text' name='id' required /><br/><br/>");
        out.println(" <button type='submit'>Delete</button>");
        out.println("</form>");
    }
}