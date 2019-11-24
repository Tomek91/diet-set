package pl.com.app.parsers.json;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.com.app.dto.UserDTO;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class UsersConverter extends JsonConverter<List<UserDTO>> {
    @Value("${USERS}")
    private String filePath;

    @PostConstruct
    private void init(){
        this.setJsonFilename(filePath);
    }
}
