package com.example.smileiddemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("identity")
@RequiredArgsConstructor
public class IdentityController {
    private final IdentityService identityService;

    @PostMapping("trigger-verification")
    ResponseEntity<String> triggerDocumentVerification(@RequestParam(name = "userId") String userId) {
        identityService.verifyDocuments(userId);
        return ResponseEntity.ok("Triggered!");
    }

    @PostMapping("document-verification-callback")
    ResponseEntity<String> documentVerificationCallback() {
        return ResponseEntity.ok("Okay");
    }
}
