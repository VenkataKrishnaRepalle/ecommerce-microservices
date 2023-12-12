package com.pm.spring.ema.analytics.service.api;

import com.pm.spring.ema.analytics.service.business.AnalyticsService;
import com.pm.spring.ema.analytics.service.model.MessageAnalyticsResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/", produces = "application/vnd.api.v1+json")
public class AnalyticsController {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsController.class);

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService service) {
        this.analyticsService = service;
    }

    @GetMapping("/get-word-count-by-word/{word}")
    @Operation(summary = "Get analytics by word.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success.", content = {
                    @Content(mediaType = "application/vnd.api.v1+json",
                            schema = @Schema(implementation = MessageAnalyticsResponseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected error.")})
    public @ResponseBody
    ResponseEntity<MessageAnalyticsResponseModel> getWordCountByWord(@PathVariable @NotEmpty String word) {
        Optional<MessageAnalyticsResponseModel> response = analyticsService.getWordAnalytics(word);
        if (response.isPresent()) {
            LOG.info("Analytics data returned with id {}", response.get().getId());
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.ok(MessageAnalyticsResponseModel.builder().build());
    }
}
