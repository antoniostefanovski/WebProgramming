package mk.ukim.finki.wp.laboratory1.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.laboratory1.service.MovieService;
import mk.ukim.finki.wp.laboratory1.service.MovieServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("")
public class MovieServlet extends HttpServlet {

    private final MovieServiceImpl movieService;
    private final SpringTemplateEngine springTemplateEngine;

    public MovieServlet(MovieServiceImpl movieService, SpringTemplateEngine springTemplateEngine) {
        this.movieService = movieService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);

        String movieTitle = req.getParameter("movie_title");
        String ratingPar = req.getParameter("rating");
        Long rating = Long.parseLong("0");

        if(movieTitle == null)
            movieTitle = "";

        if(ratingPar != null && !ratingPar.isEmpty())
            rating = Long.parseLong(ratingPar);

        var movies = movieService.searchMovies(movieTitle, rating);

        webContext.setVariable("movies", movies);

        Integer userViews = (Integer) getServletContext().getAttribute("userViews");
        getServletContext().setAttribute("userViews", ++userViews);
        webContext.setVariable("userViews", getServletContext().getAttribute("userViews"));


        springTemplateEngine.process("listMovies.html", webContext, resp.getWriter());



    }
}
