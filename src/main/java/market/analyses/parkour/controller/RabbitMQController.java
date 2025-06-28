package market.analyses.parkour.controller;

import market.analyses.parkour.service.ParserCommandSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parser")
public class RabbitMQController {

    private final ParserCommandSender sender;

    public RabbitMQController(ParserCommandSender sender) {
        this.sender = sender;
    }

    @PostMapping("/run")
    public ResponseEntity<?> runParsing() {
        sender.sendRunCommand("run");
        return ResponseEntity.ok().body("Парсинг запущен");
    }
}
