package backend.restapp.controller;

import backend.restapp.model.Messages;
import backend.restapp.repo.MessagesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor

public class MessagesController {
    private final MessagesRepo messagesRepo;

    @GetMapping("/")
    public ResponseEntity<List<Messages>> getTools(@RequestParam(required = false) String title) {
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


    @PostMapping("/")
    public ResponseEntity<Messages> createMessage(@RequestBody Messages message) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        try {
            Messages newMessage = messagesRepo.save(new Messages (
                    message.getMessage(),
                    message.getCreationDate()));
            return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
