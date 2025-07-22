package testboard._core.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageLink {

    private int pageNumber;
    private int displayNumber;
    private boolean active;

    public PageLink(int pageNumber, int displayNumber, boolean active) {
        this.pageNumber = pageNumber;
        this.displayNumber = displayNumber;
        this.active = active;
    }
}
