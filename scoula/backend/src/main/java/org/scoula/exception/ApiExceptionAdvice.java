package org.scoula.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Order(2)
public class ApiExceptionAdvice {
    // 404 에러 - 리소스를 찾을 수 없음
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<String> handleNoSuchElementException(
            NoSuchElementException e
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)                    // 404 상태
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body("해당 ID의 요소가 없습니다.");
    }
}
