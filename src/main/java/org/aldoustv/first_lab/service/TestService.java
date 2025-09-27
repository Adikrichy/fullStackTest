package org.aldoustv.first_lab.service;

import org.aldoustv.first_lab.dto.request.TestRequest;
import org.aldoustv.first_lab.dto.response.TestResponse;
import org.aldoustv.first_lab.entity.Test;
import org.aldoustv.first_lab.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class TestService {

    public final TestRepository testRepository;

    public TestService(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    public TestResponse createTest(TestRequest testRequest) {
        Test test = new Test();
        test.setName(testRequest.getName());
        test.setYear(testRequest.getYear());
        test.setId(RedGenerated(testRequest.getId()));
        testRepository.save(test);

        return toDTO(test);

    }

    @Transactional
    public void deleteById(Long id){
        testRepository.deleteById(id);
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Test not found"));
        test.setName(testRequest.getName());
        test.setYear(testRequest.getYear());

        testRepository.save(test);
        return toDTO(test);

    }

    public TestResponse getTestById(Long id){
        return testRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(()-> new RuntimeException("Test not found"));
    }

    private TestResponse toDTO(Test test){
        TestResponse dto = new TestResponse();
        dto.setId(test.getId());
        dto.setName(test.getName());
        dto.setYear(test.getYear());
        return dto;
    }

    public Long RedGenerated(Long id){
        Random random = new Random();
        id = id + random.nextInt(100);
        return id;
    }

}
