package backend.restapp.controller;

import backend.restapp.model.Messages;
import backend.restapp.repo.MessagesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    private final MessagesRepo messagesRepo;

    public TestController(MessagesRepo messagesRepo) {
        this.messagesRepo = messagesRepo;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Hello to my web-page";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Messages>> userAccess(@RequestParam(required = false) String title) {
        try {
            List<Messages> messagesList = new ArrayList<>();
            messagesRepo.findAll();
            if (title == null)
                messagesList.addAll(messagesRepo.findAll());
            if (messagesList.isEmpty()) {
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.debug("ListUsers" + messagesList);
            return new ResponseEntity<>(messagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
