package me.ryzeon.reservations.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Hidden
@RestController
@RequestMapping("/documentation")
public class DocumentationController {

    @GetMapping
    public void redirectToDocumentation(HttpServletResponse response) {
        try {
            response.sendRedirect("swagger-ui.html");
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html>");
            stringBuilder.append("<head>");
            stringBuilder.append("<title>Something went wrong</title>");
            stringBuilder.append("</head>");
            stringBuilder.append("<body>");
            stringBuilder.append("<h1>Something went wrong</h1>");
            if (e.getMessage() != null) {
                stringBuilder.append("<p>").append(e.getMessage()).append("</p>");
            }
            stringBuilder.append("<p>Could not redirect to the documentation</p>");
            stringBuilder.append("</body>");
            stringBuilder.append("</html>");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html");
            try {
                response.getWriter().write(stringBuilder.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
