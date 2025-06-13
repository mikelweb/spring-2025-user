package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.application.rest.request.CreateDigitalSessionRequest;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import edu.uoc.epcsd.user.domain.Alert;
import edu.uoc.epcsd.user.domain.DigitalItem;
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.service.DigitalSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/digital")
public class DigitalSessionRESTController {

    private final DigitalSessionService digitalSessionService;

    @GetMapping("/allDigital")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalSession> getAllDigitalSession() {
        log.trace("getAllDigitalSession");

        return digitalSessionService.findAllDigitalSession();
    }

    // Get digital session data by it ID
    @GetMapping("/{digitalSessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DigitalSession> getDigitalSessionById(@PathVariable @NotNull Long digitalSessionId) {
        // Log action
        log.info("getDigitalSessionById");
        // call service's method passing session ID to get session data and return as a ResponseEntity
        return digitalSessionService.getDigitalSessionById(digitalSessionId).map(digitalSession -> ResponseEntity.ok().body(digitalSession))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get digital session list by user
    @GetMapping("/digitalByUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DigitalSession>> findDigitalSessionByUser(@RequestParam @NotNull Long userId) {
        // Log action
        log.info("getDigitalSessionById");

        List<DigitalSession> digitalSessionList = digitalSessionService.findDigitalSessionByUser(userId);
        if (digitalSessionList != null && !digitalSessionList.isEmpty()) {
            return ResponseEntity.ok().body(digitalSessionList);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createDigital")
    public ResponseEntity<Long> createDigitalSession(@RequestBody @Valid CreateDigitalSessionRequest createDigitalSessionRequest) {
        log.trace("createDigitalSession");
        log.trace("Creating digital session " + createDigitalSessionRequest);

        try {
            Long digitalSessionId = digitalSessionService.createDigitalSession(DigitalSession.builder()
                    .userId(createDigitalSessionRequest.getUserId())
                    .description(createDigitalSessionRequest.getDescription())
                    .location(createDigitalSessionRequest.getLocation())
                    .link(createDigitalSessionRequest.getLink()).build());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(digitalSessionId)
                    .toUri();
            return ResponseEntity.created(uri).body(digitalSessionId);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified user id " + createDigitalSessionRequest.getUserId() + " does not exist.", e);
        }
    }

    @PatchMapping("/updateDigital/{digitalSessionId}")
    public ResponseEntity<Boolean> updateDigitalSession(@PathVariable @NotNull Long digitalSessionId, @RequestBody @Valid CreateDigitalSessionRequest updateDigitalSessionRequest) {
        log.trace("updateDigitalSession");
        log.trace("Updating digital session: " + digitalSessionId);

        try {
            digitalSessionService.updateDigitalSession(digitalSessionId, updateDigitalSessionRequest.getDescription(), updateDigitalSessionRequest.getLocation(), updateDigitalSessionRequest.getLink(), updateDigitalSessionRequest.getUserId());
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @DeleteMapping("/removeDigital/{digitalSessionId}")
    public ResponseEntity<Boolean> removeDigitalSession(@PathVariable @NotNull Long digitalSessionId) {
        log.trace("removeDigitalSession");
        log.trace("Removing digital session: " + digitalSessionId);

        try {
            digitalSessionService.removeDigitalSession(digitalSessionId);
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }

    }
}
