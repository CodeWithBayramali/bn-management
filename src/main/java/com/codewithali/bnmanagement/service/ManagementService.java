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
import java.util.Comparator;
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
        try {
            // DTO'yu deseralize et
            ManagementDto managementDto = objectMapper.readValue(dto, ManagementDto.class);

            // Eğer dosya varsa işlemlerini yap, yoksa fileUrl boş bırak
            String fileUrl = null;
            if (file != null && !file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                String fileExtension = getFileExtension(originalFileName);
                String newFileName = UUID.randomUUID() + fileExtension;

                Path filePath = Paths.get(UPLOAD_DIR + newFileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                fileUrl = "/uploads/" + newFileName;
            }

            // Management nesnesi oluşturuluyor
            Management management = new Management(
                    managementDto.getId(),
                    managementDto.getCaseNumber(),
                    managementDto.getOperatorAdi().toUpperCase(),
                    managementDto.getFirmaAdi().toUpperCase(),
                    managementDto.getVakaIlcesi(),
                    managementDto.getVakaSehri(),
                    managementDto.getHizmet().toUpperCase(),
                    managementDto.getMesafe(),
                    managementDto.getProjeFitat(),
                    managementDto.getProjeFiyatTotal(),
                    managementDto.getPozBirimFiyat(),
                    managementDto.getKmBirimFiyat(),
                    managementDto.getTotal(),
                    managementDto.getEkPoz(),
                    fileUrl, // Eğer dosya yoksa null olabilir
                    managementDto.getDate(),
                    managementDto.getPozSayisi(),
                    managementDto.getVakaSonlandiran().toUpperCase(),
                    managementDto.getAciklama()
            );

            // Veritabanına kaydet
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
                    dto.getProjeFitat(),
                    dto.getProjeFiyatTotal(),
                    dto.getPozBirimFiyat(),
                    dto.getKmBirimFiyat(),
                    dto.getTotal(),
                    dto.getEkPoz(),
                    dto.getDocumentUrl(),
                    dto.getDate(),
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
                .sorted(Comparator.comparing(ManagementDto::getDate).reversed())
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
