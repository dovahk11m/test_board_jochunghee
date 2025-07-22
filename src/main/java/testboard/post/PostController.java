package testboard.post;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import testboard._core.common.PageDTO;
import testboard.category.Category;
import testboard.category.CategoryRepository;
import testboard.member.Member;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final CategoryRepository categoryRepository;

    /**
     * 메인화면 요청
     */
    @GetMapping("/posts")
    public String list(Model model,
                        @PageableDefault(
                                size = 10,
                                sort = "id",
                                direction = Sort.Direction.DESC) Pageable pageable) {

        PageDTO<PostResponse.ListDTO> responseDTO = postService.getPostList(pageable);

        model.addAttribute("pageData", responseDTO);

        return "post/index";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/posts";
    }

    /**
     * 게시글 상세보기
     */
    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        Member sessionUser = (Member) session.getAttribute("sessionUser");

        Long sessionUserId = sessionUser != null ? sessionUser.getId() : null;

        PostResponse.DetailDTO responseDTO = postService.getPostDetail(id, sessionUserId);

        model.addAttribute("post", responseDTO);

        return "post/detail";
    }

    /**
     * 게시글 작성 페이지 요청
     */
    @GetMapping("/posts/save-form")
    public String saveForm(Model model, HttpSession session) {
        // 로그인 확인
        if (session.getAttribute("sessionUser") == null) {
            return "redirect:/login-form";
        }
        // 폼에 카테고리 목록 전달
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "post/save-form";
    }

    /**
     * 게시글 작성 처리
     */
    @PostMapping("/posts/save")
    public String save(@Valid PostRequest.SaveDTO requestDTO, Errors errors,
                       HttpSession session) {
        Member sessionUser = (Member) session.getAttribute("sessionUser");
        // 로그인 확인
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        Post post = postService.savePost(requestDTO, sessionUser);
        return "redirect:/posts/" + post.getId();
    }

    /**
     * 게시글 수정 페이지 요청
     */
    @GetMapping("/posts/{id}/update-form")
    public String updateForm(@PathVariable Long id, Model model,
                             HttpSession session) {
        Member sessionUser = (Member) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        PostResponse.UpdateFormDTO responseDTO = postService.getPostForUpdate(id, sessionUser.getId());
        model.addAttribute("post", responseDTO);
        return "post/update-form";
    }

    /**
     * 게시글 수정 처리
     */
    @PostMapping("/posts/{id}/update")
    public String update(@PathVariable Long id, @Valid PostRequest.UpdateDTO requestDTO, Errors errors,
                         HttpSession session) {
        Member sessionUser = (Member) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        postService.updatePost(id, requestDTO, sessionUser.getId());
        return "redirect:/posts/" + id;
    }

    /**
     * 게시글 삭제 처리
     */
    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpSession session) {
        Member sessionUser = (Member) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        postService.deletePost(id, sessionUser.getId());
        return "redirect:/posts";
    }





}
