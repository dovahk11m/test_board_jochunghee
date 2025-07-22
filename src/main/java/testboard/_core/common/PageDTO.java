package testboard._core.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 페이징 응답을 위한 공용 DTO
 */
@Getter
public class PageDTO<T> {

    private final List<T> items;
    private final List<PageLink> pageLinks;

    private final int totalPages;
    private final long totalElements;
    private final boolean first;
    private final boolean last;
    private final int number;

    private final int prevPageNumber;
    private final int nextPageNumber;

    public <E> PageDTO(Page<E> entityPage, Function<E, T> dtoMapper) {

        this.items = entityPage.getContent().stream().map(dtoMapper).collect(Collectors.toList());

        int currentPage = entityPage.getNumber();
        int totalPages = entityPage.getTotalPages();
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);

        this.pageLinks = IntStream.rangeClosed(startPage, endPage)
                .mapToObj(i -> new PageLink(i, i + 1, i == currentPage))
                .collect(Collectors.toList());

        this.totalPages = entityPage.getTotalPages();
        this.totalElements = entityPage.getTotalElements();
        this.first = entityPage.isFirst();
        this.last = entityPage.isLast();
        this.number = entityPage.getNumber();

        this.prevPageNumber = currentPage -1;
        this.nextPageNumber = currentPage +1;
    }
}