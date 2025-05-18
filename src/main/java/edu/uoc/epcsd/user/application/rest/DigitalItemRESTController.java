package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.application.rest.request.CreateDigitalItemRequest;
import edu.uoc.epcsd.user.application.rest.request.CreateDigitalSessionRequest;
import edu.uoc.epcsd.user.domain.Alert;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.service.DigitalItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/digitalItem")
public class DigitalItemRESTController {

    private final DigitalItemService digitalItemService;

    @GetMapping("/allItems")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalItem> getAllDigitalItem() {
        log.trace("getAllDigitalItem");

        return digitalItemService.findAllDigitalItem();
    }

    @GetMapping("/{digitalItemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DigitalItem> getDigitalItemById(@PathVariable @NotNull Long digitalItemId) {
        log.info("digitalItemId");

        return digitalItemService.getDigitalItemById(digitalItemId).map(digitalItem -> ResponseEntity.ok().body(digitalItem))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/digitalItemBySession")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DigitalItem>> findDigitalItemBySession(@RequestParam @NotNull Long digitalSessionId) {
        log.info("digitalItemBySession");

        List<DigitalItem> itemList = digitalItemService.findDigitalItemBySession(digitalSessionId);
        if (itemList != null && !itemList.isEmpty()) {
            return ResponseEntity.ok().body(itemList);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addItem")
    public ResponseEntity<Long> addDigitalItem(@RequestBody @Valid CreateDigitalItemRequest createDigitalItemRequest) {
        log.trace("addDigitalItem");
        log.trace("Creating digital item " + createDigitalItemRequest);

        try {
            Long digitalSessionId = digitalItemService.addDigitalItem(DigitalItem.builder()
                    .digitalSessionId(createDigitalItemRequest.getDigitalSessionId())
                    .description(createDigitalItemRequest.getDescription())
                    .lat(createDigitalItemRequest.getLat())
                    .lon(createDigitalItemRequest.getLon())
                    .link(createDigitalItemRequest.getLink()).build());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(digitalSessionId)
                    .toUri();
            return ResponseEntity.created(uri).body(digitalSessionId);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified session id " + createDigitalItemRequest.getDigitalSessionId() + " does not exist.", e);
        }
    }

    @PatchMapping("/updateItem/{digitalItemId}")
    public ResponseEntity<Boolean> updateDigitalItem(@PathVariable @NotNull Long digitalItemId, @RequestBody @Valid CreateDigitalItemRequest updateDigitalItemRequest) {
        // Log action
        log.trace("updateDigitalItem");
        // Log id
        log.trace("Updating digital item: " + digitalItemId);

        try {
            digitalItemService.updateDigitalItem(digitalItemId, updateDigitalItemRequest.getDescription(), updateDigitalItemRequest.getLink(), updateDigitalItemRequest.getLat(), updateDigitalItemRequest.getLon());
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/reviewDigitalItem/{digitalItemId}")
    public ResponseEntity<Boolean> setDigitalItemForReview(@PathVariable @NotNull Long digitalItemId) {
        // Log action
        log.trace("setDigitalItemForReview");
        // Log id
        log.trace("Updating digital item status to REVIEW_PENDING on itemId: " + digitalItemId);

        try {
            digitalItemService.setDigitalItemForReview(digitalItemId);
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/approveDigitalItem/{digitalItemId}")
    public ResponseEntity<Boolean> approvePendingDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        // Log action
        log.trace("approvePendingDigitalItem");
        // Log id
        log.trace("Updating digital item status to AVAILABLE on itemId: " + digitalItemId);

        try {
            digitalItemService.approvePendingDigitalItem(digitalItemId);
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/rejectDigitalItem/{digitalItemId}")
    public ResponseEntity<Boolean> rejectPendingDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        // Log action
        log.trace("rejectPendingDigitalItem");
        // Log id
        log.trace("Updating digital item status to REJECTED on itemId: " + digitalItemId);

        try {
            digitalItemService.rejectPendingDigitalItem(digitalItemId);
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @DeleteMapping("/dropItem/{digitalItemId}")
    public ResponseEntity<Boolean> dropDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        // Log action
        log.trace("dropDigitalItem");
        // Log id
        log.trace("Removing digital item: " + digitalItemId);

        try {
            digitalItemService.dropDigitalItem(digitalItemId);
            return ResponseEntity.ok().body(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }

    }
}
