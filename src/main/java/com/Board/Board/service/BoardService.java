package com.Board.Board.service;

import com.Board.Board.entity.Board;
import com.Board.Board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        // 저장할 경로 지정
        UUID uuid = UUID.randomUUID();
        // 파일 이름을 붙힐 랜덤 이름 생성
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        // File 이라는 클래스를 이용해서 빈 껍데기를 생성하여 경로와 파일이름 지정
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
        /*
        findAll 이라는 메서드를 이용해서 DB에 있는 모든 정보를 가져오게
        되면 말 그래도 DB에 보관하고있는 모든 정보를 가져오게 되지만,
        pageable이라는 클래스를 넘겨주게 되면 그 안에 페이지가 몇페이지인지
        그리고 한 페이지에 보여줄수 있는 게시물의 개수가 몇개인지 담아서 보내줄 수 있다.

         */
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }

}
