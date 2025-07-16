package com.example.demo.Controller;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ResponseDto.BookmarkResponseDto;
import com.example.demo.Service.BookmarkService;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/{userId}/bookmarks") // 아이디 보고 북마크 가져오기
    public List<BookmarkResponseDto> getBookmarksByUser(@PathVariable Long userId) {
        return bookmarkService.getBookmarksByUser(userId);
    }

    // 북마크 추가
    @PostMapping("/{postId}")
    public ResponseEntity<?> bookmark(@PathVariable Long postId, @RequestParam Long userId) {
        bookmarkService.addBookmark(userId, postId);
        return ResponseEntity.ok("북마크 완료");
    }

    // 북마크 취소
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unbookmark(@PathVariable Long postId, @RequestParam Long userId) {
        bookmarkService.removeBookmark(userId, postId);
        return ResponseEntity.ok("북마크 취소됨");
    }
}
