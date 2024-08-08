package wemmy.global.config.error.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wemmy.global.config.error.ErrorResult;

@Slf4j
@RestControllerAdvice(basePackages = "")
public class ExceptionAdvice {

    // 토큰 예외 처리
    @ExceptionHandler(TokenValidateException.class)
    public ResponseEntity<ErrorResult> tokenExceptionHandler (TokenValidateException e) {
        log.info("com.token.config.error.exception.ExceptionAdvice.tokenExceptionHandler에서 예외처리");
        ErrorResult result = ErrorResult.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return new ResponseEntity<>(result, e.getErrorCode().getHttpStatus());
    }

    // 회원 컨트롤러 예외 처리
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResult> memberExceptionHandler(MemberException e) {
        log.info("member error handler");
        ErrorResult result = ErrorResult.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return new ResponseEntity<>(result, e.getErrorCode().getHttpStatus());
    }

    // 아기 컨트롤러 예외 처리
    @ExceptionHandler(BabyException.class)
    public ResponseEntity<ErrorResult> babyExceptionHandler(BabyException e) {
        log.info("baby error handler");
        ErrorResult result = ErrorResult.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return new ResponseEntity<>(result, e.getErrorCode().getHttpStatus());
    }

    // 컨트롤러 예외 처리
    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ErrorResult> controllerExceptionHandler(ControllerException e) {
        log.info("controller error handler");
        ErrorResult result = ErrorResult.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return new ResponseEntity<>(result, e.getErrorCode().getHttpStatus());
    }

    // runtime exception 예외처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResult> controllerExceptionHandler(RuntimeException e) {
        log.info("controller error handler");
        ErrorResult result = ErrorResult.of("E-000", e.getMessage() + "\n 서버 에러. 담당자에게 문의 바랍니다.");
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
