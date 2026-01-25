package by.katekhreshkova.notification.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateService {
    public String render(String template, Map<String, Object> ctx) {
        try {
            String html = Files.readString(Paths.get("templates/" + template));
            for (var entry : ctx.entrySet()) {
                html = html.replace("${" + entry.getKey() + "}", entry.getValue().toString());
            }
            return html;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
