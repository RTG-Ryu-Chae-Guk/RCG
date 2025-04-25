package com.rcg.service;

import com.rcg.dto.BusinessStatusRequestDTO;
import com.rcg.dto.BusinessStatusResponseDTO;
import com.rcg.entity.BusinessUser;
import com.rcg.repository.BusinessUserRepository;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class BusinessStatusService {

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final String API_URL = "https://api.odcloud.kr/api/nts-businessman/v1/status";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BusinessUserRepository businessUserRepository;

    public BusinessStatusService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;
    }

    public BusinessStatusResponseDTO getBusinessStatus(BusinessStatusRequestDTO requestDTO) {
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(requestDTO);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize request DTO", e);
        }

        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json; charset=utf-8"));
        String fullUrl = API_URL + "?serviceKey=" + serviceKey;

        Request request = new Request.Builder()
                .url(fullUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }

            String responseBody = response.body().string();
            BusinessStatusResponseDTO result = objectMapper.readValue(responseBody, BusinessStatusResponseDTO.class);

            for (BusinessStatusResponseDTO.BusinessData data : result.getData()) {
                String bNo = data.getB_no();


                if (!businessUserRepository.existsById(bNo)) {
                    BusinessUser user = new BusinessUser();
                    user.setB_no(bNo);
                    user.setTaxType(data.getTax_type());
                    user.setStatus(data.getB_stt());
                    user.setVerifiedAt(LocalDateTime.now());
                    businessUserRepository.save(user);
                }
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to call external API", e);
        }
    }

}

