package com.example.smileiddemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import smile.identity.core.WebApi;
import smile.identity.core.enums.ImageType;
import smile.identity.core.enums.JobType;
import smile.identity.core.models.IdInfo;
import smile.identity.core.models.ImageDetail;
import smile.identity.core.models.Options;
import smile.identity.core.models.PartnerParams;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdentityService {
    @Value("${smileid.apikey}")
    private String apiKey;
    @Value("${smileid.partnerid}")
    private String partnerId;
    @Value("${smileid.callbackurl}")
    private String defaultCallback;
    @Value("${smileid.sidserver}")
    private String sidServer;

    private final ResourceLoader resourceLoader;

    public void verifyDocuments(String userId) {
        var jobId = UUID.randomUUID().toString();
        var imageDetails = setImageDetails();

        WebApi connection = new WebApi(partnerId, apiKey, defaultCallback, sidServer);
        Map<String, Object> optionalInfo = new HashMap<>();
        PartnerParams params = new PartnerParams(JobType.DOCUMENT_VERIFICATION, userId, jobId, optionalInfo);
        IdInfo idInfo = new IdInfo("KE", "NATIONAL_ID");
        Options options = new Options(false, false, false, defaultCallback);

        try {
            connection.submitJob(params, imageDetails, idInfo, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ImageDetail> setImageDetails() {
        List<ImageDetail> imageDetails = new ArrayList<>();

        ImageDetail selfieDocument = new ImageDetail(ImageType.SELFIE, null,"selfie.jpg");
        ImageDetail idDocument = new ImageDetail(ImageType.ID_CARD, null, "id_front.jpg");
        ImageDetail backOfIdDocument = new ImageDetail(ImageType.ID_CARD_BACK, null, "id_back.jpg");

        imageDetails.add(selfieDocument);
        imageDetails.add(idDocument);
        imageDetails.add(backOfIdDocument);

        return imageDetails;
    }
}
