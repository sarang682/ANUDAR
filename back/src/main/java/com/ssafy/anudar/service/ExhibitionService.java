package com.ssafy.anudar.service;

import com.ssafy.anudar.dto.DocentDto;
import com.ssafy.anudar.dto.ExhibitionDto;
import com.ssafy.anudar.exception.BadRequestException;
import com.ssafy.anudar.exception.response.ExceptionStatus;
import com.ssafy.anudar.model.Docent;
import com.ssafy.anudar.model.Exhibition;
import com.ssafy.anudar.model.User;
import com.ssafy.anudar.repository.DocentRepository;
import com.ssafy.anudar.repository.ExhibitionRepository;
import com.ssafy.anudar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final UserRepository userRepository;
    private final DocentRepository docentRepository;

    @Transactional
    public ExhibitionDto saveExhibition (String name, String detail, String start_time, String end_time, String username, String docent_start, String docent_end) {
        // 사용자 ID로 사용자 정보를 조회
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 사용자 ID로 사용자 정보를 조회
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException(ExceptionStatus.USER_NOT_FOUND));

        // 전시회 저장
        Exhibition exhibition = new Exhibition(name, detail, LocalDateTime.parse(start_time,formatter), LocalDateTime.parse(end_time,formatter), user);
        ExhibitionDto.fromEntity(exhibitionRepository.save(exhibition));

        // 도슨트 저장
        Docent docent = new Docent(LocalDateTime.parse(docent_start,formatter), LocalDateTime.parse(docent_end,formatter), exhibition);
        DocentDto.fromEntity(docentRepository.save(docent));

        // 도슨트 url 저장
        exhibition.setDocent_url("https://anudar.com/docent/" + exhibition.getId());  // 도슨트 URL을 직접 저장하도록 수정

        return ExhibitionDto.fromEntity(exhibition);
    }

    public List<Exhibition> getAllExhibitions() {
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        return  exhibitionRepository.findAll();
    }
}