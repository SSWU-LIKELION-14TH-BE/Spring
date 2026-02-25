package com.likelion.session.repository;

import com.likelion.session.entity.Board;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardRepository extends CrudRepository<Board, Long> {
    // 게시글 가져오기 read(=GET)
    Optional<Board> findByBoardId(Long boardId);

    // 게시글 작성하기 create(=POST)
    // void save(); // 이건 명명하지 않고 바로 적어도 자동 지원됨

    // 게시글 삭제하기 delete(=DELETE)
    void deleteByBoardId(Long boardId);
}