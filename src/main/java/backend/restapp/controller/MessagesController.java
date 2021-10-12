package backend.restapp.controller;

import backend.restapp.model.Messages;
import backend.restapp.repo.MessagesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class MessagesController {
    private final MessagesRepo messagesRepo;

    @GetMapping
    public ResponseEntity<List<Messages>> getAllMessages(@RequestParam(required = false) String title) {
        try {
            List<Messages> messagesList = new ArrayList<>();
            messagesRepo.findAll();
            if (title == null)
                messagesList.addAll(messagesRepo.findAll());
            if (messagesList.isEmpty()) {
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(messagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Messages> createMessage(@RequestBody Messages message) {
        try {
            message.setCreationDate(LocalDateTime.now());
            return new ResponseEntity<>(messagesRepo.save(message), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
