package com.Board.Board.repository;

import com.Board.Board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
    // 컬럼에서 키워드가 포함된 것을 찾음 => 키워드가 포함된 모든 데이터 검색
}
