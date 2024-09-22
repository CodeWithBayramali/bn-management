package com.codewithali.bnmanagement.service;

import com.codewithali.bnmanagement.dto.ManagementDto;
import com.codewithali.bnmanagement.dto.response.SuccessReponse;
import com.codewithali.bnmanagement.model.Management;
import com.codewithali.bnmanagement.repository.ManagementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManagementService {
    private final ManagementRepository managementRepository;
    private final ObjectMapper objectMapper;
    private final String UPLOAD_DIR = "uploads/";

    public ManagementService(ManagementRepository managementRepository, ObjectMapper objectMapper) {
        this.managementRepository = managementRepository;
        this.objectMapper = objectMapper;
    }

    public SuccessReponse createManagement(String dto, MultipartFile file) throws JsonProcessingException {

        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String newFileName = UUID.randomUUID() + fileExtension;
        try {
            ManagementDto managementDto = objectMapper.readValue(dto,ManagementDto.class);
            Path filePath = Paths.get(UPLOAD_DIR + newFileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            String fileUrl = "/uploads/" + newFileName;
            Management management = new Management(
                    managementDto.getId(),
                    managementDto.getCaseNumber(),
                    managementDto.getOperatorAdi(),
                    managementDto.getFirmaAdi(),
                    managementDto.getVakaIlcesi(),
                    managementDto.getVakaSehri(),
                    managementDto.getHizmet(),
                    managementDto.getMesafe(),
                    managementDto.getEkPoz(),
                    fileUrl,
                    managementDto.getDate(),
                    managementDto.getIlceler(),
                    managementDto.getPozSayisi(),
                    managementDto.getVakaSonlandiran(),
                    managementDto.getAciklama()
            );
            managementRepository.save(management);
            return new SuccessReponse("Oluşturuldu", HttpStatus.CREATED.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SuccessReponse updateManagement(ManagementDto dto) {
        Management management = managementRepository.findById(dto.getId()).orElse(null);
        if(management != null){
            Management updatedManagement = new Management(
                    dto.getId(),
                    dto.getCaseNumber(),
                    dto.getOperatorAdi(),
                    dto.getFirmaAdi(),
                    dto.getVakaIlcesi(),
                    dto.getVakaSehri(),
                    dto.getHizmet(),
                    dto.getMesafe(),
                    dto.getEkPoz(),
                    dto.getDocumentUrl(),
                    dto.getDate(),
                    dto.getIlceler(),
                    dto.getPozSayisi(),
                    dto.getVakaSonlandiran(),
                    dto.getAciklama()
            );
            managementRepository.save(updatedManagement);
            return new SuccessReponse("Güncellendi",HttpStatus.OK.value());
        }
        return new SuccessReponse("Beklenmedik bir hata oluştu", HttpStatus.BAD_REQUEST.value());
    }

    public List<ManagementDto> getAllManagementWithMonth(int month, int year) {
        return managementRepository.findByDateMonthAndYear(month,year)
                .stream().map(ManagementDto::convertToDto)
                .collect(Collectors.toList());
    }

    public SuccessReponse deleteManagement(String id) {
        managementRepository.deleteById(id);
        return new SuccessReponse("Silindi: "+ id, HttpStatus.OK.value());
    }

    public List<ManagementDto> getAllManagement(int page, int size) {
        return managementRepository.findAll(PageRequest.of(page,size))
                .map(ManagementDto::convertToDto)
                .stream().toList();
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

}
