package com.example.demo.Service;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ResponseDto.BookmarkResponseDto;
import com.example.demo.domain.Bookmark;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    public List<BookmarkResponseDto> getBookmarksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookmarkRepository.findByUser(user).stream() // 한 사용자의 북마크 리스트 반환
                .map(BookmarkResponseDto::new)
                .toList();
    }

    // 북마크 추가
    public void addBookmark(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 이미 북마크된 경우 중복 방지
        bookmarkRepository.findByUserAndPost(user, post).ifPresentOrElse(
                b -> { throw new RuntimeException("Already bookmarked"); },
                () -> bookmarkRepository.save(new Bookmark(user, post))
        );
    }

    // 북마크 취소
    public void removeBookmark(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        bookmarkRepository.deleteByUserAndPost(user, post);
    }


}

