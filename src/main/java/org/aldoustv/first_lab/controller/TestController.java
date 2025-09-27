package org.aldoustv.first_lab.controller;

import org.aldoustv.first_lab.dto.request.TestRequest;
import org.aldoustv.first_lab.dto.response.TestResponse;
import org.aldoustv.first_lab.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService){
        this.testService = testService;
    }

    @PostMapping("/create")
    public ResponseEntity<TestResponse> createTest(@RequestBody TestRequest testRequest){
        TestResponse dto = testService.createTest(testRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id){
        testService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TestResponse> updateTest(@PathVariable Long id, @RequestBody TestRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(testService.updateTest(id, request));
    }

    @GetMapping("{id}")
    public ResponseEntity<TestResponse> getTest(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(testService.getTestById(id));
    }
    
}
