package testboard.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testboard._core.common.PageDTO;
import testboard._core.errors.exception.Exception403;
import testboard._core.errors.exception.Exception404;
import testboard.category.Category;
import testboard.category.CategoryRepository;
import testboard.member.Member;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 게시글 목록 조회
     */
    public PageDTO<PostResponse.ListDTO> getPostList(Pageable pageable) {

        Sort originalSort = pageable.getSort();
        Sort noticePinSort = Sort.by(Sort.Direction.ASC, "postType");
        Sort finalSort = noticePinSort.and(originalSort);

        Pageable finalPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), finalSort);

        Page<Post> postPage = postRepository.findAllWithDetails(finalPageable);

        return new PageDTO<>(postPage, PostResponse.ListDTO::new);
    }

    /**
     * 게시글 상세보기
     */
    public PostResponse.DetailDTO getPostDetail(Long postId, Long sessionUserId) {
        Post post = postRepository.findByIdWithDetails(postId)
                .orElseThrow(() -> new Exception404("존재하지 않는 게시글입니다"));

        return new PostResponse.DetailDTO(post, sessionUserId);
    }

    /**
     * 게시글 작성
     */
    @Transactional
    public Post savePost(PostRequest.SaveDTO requestDTO, Member sessionUser) {
        // 1. 카테고리가 존재하는지 확인
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new Exception404("존재하지 않는 카테고리입니다."));

        // 2. DTO를 엔티티로 변환하여 저장
        Post post = requestDTO.toEntity(sessionUser, category);
        return postRepository.save(post);
    }

    /**
     * 게시글 수정 폼에 필요한 데이터 조회
     */
    public PostResponse.UpdateFormDTO getPostForUpdate(Long postId, Long sessionUserId) {
        // 1. 게시글 존재 여부 및 권한 확인
        Post post = findPostByIdAndCheckOwner(postId, sessionUserId);
        // 2. 모든 카테고리 목록 조회
        List<Category> categories = categoryRepository.findAll();
        // 3. DTO로 변환하여 반환
        return new PostResponse.UpdateFormDTO(post, categories);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, PostRequest.UpdateDTO requestDTO, Long sessionUserId) {
        // 1. 게시글 존재 여부 및 권한 확인
        Post post = findPostByIdAndCheckOwner(postId, sessionUserId);

        // 2. 카테고리 존재 여부 확인
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new Exception404("존재하지 않는 카테고리입니다."));

        // 3. 게시글 수정 (더티 체킹)
        post.update(requestDTO.getTitle(), requestDTO.getContent(), category);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId, Long sessionUserId) {
        // 1. 게시글 존재 여부 및 권한 확인
        Post post = findPostByIdAndCheckOwner(postId, sessionUserId);
        // 2. 게시글 삭제
        postRepository.delete(post);
    }

    /**
     * [내부 로직] 게시글 조회 및 소유권 확인 (중복 코드 제거)
     */
    private Post findPostByIdAndCheckOwner(Long postId, Long sessionUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception404("존재하지 않는 게시글입니다."));

        if (!post.isOwner(sessionUserId)) {
            throw new Exception403("게시글을 수정/삭제할 권한이 없습니다.");
        }
        return post;
    }
}
