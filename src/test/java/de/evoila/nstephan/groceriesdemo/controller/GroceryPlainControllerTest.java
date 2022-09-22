package de.evoila.nstephan.groceriesdemo.controller;

import de.evoila.nstephan.groceriesdemo.mapping.GroceryItemMapper;
import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import de.evoila.nstephan.groceriesdemo.repository.GroceryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GroceryPlainController.class)
class GroceryPlainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroceryItemRepository repo;

    @MockBean
    private GroceryItemMapper mapper;

    private static final String BASE_PATH = "/plain/groceries";
    private static final int TOTAL_ITEMS = 10;
    private static final int PAGE_SIZE = 5;
    private static final Pageable UNPAGED = Pageable.unpaged();
    private static final Pageable FIRST_PAGE = PageRequest.ofSize(PAGE_SIZE);

    private static final long EXISTING_ID = 0L;
    private static final long NOT_EXISTING_ID = 1L;

    private final List<GroceryItem> items = new ArrayList<>();

    @BeforeEach
    void init() {
        for (int i = 0; i < TOTAL_ITEMS; i++) {
            var item = new GroceryItem();
            item.setId((long) i);
            items.add(item);
        }
        mockRepo();
    }

    void mockRepo() {
        doReturn(new PageImpl<>(items, UNPAGED, items.size())).when(repo)
                .findAll(argThat((ArgumentMatcher<Pageable>) Pageable::isUnpaged));
        doReturn(new PageImpl<>(items.subList(0, PAGE_SIZE), FIRST_PAGE, PAGE_SIZE)).when(repo)
                .findAll(
                        argThat((Pageable p) -> p.isPaged() && p.getPageSize() == PAGE_SIZE && p.getPageNumber() == 0));
        when(repo.findById(EXISTING_ID)).thenReturn(Optional.of(new GroceryItem()));
        when(repo.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());
    }

    @Test
    void getAllUnpaged() throws Exception {
        mockMvc.perform(get(BASE_PATH)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(TOTAL_ITEMS)));
    }

    @Test
    void getAllPaged() throws Exception {
        mockMvc.perform(get(String.format("%s?page=%d&size=%d", BASE_PATH, 0, PAGE_SIZE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(PAGE_SIZE)));
    }

    @Test
    void getExisting() throws Exception {
        mockMvc.perform(get(String.format("%s/%d", BASE_PATH, EXISTING_ID))).andExpect(status().isOk());
    }

    @Test
    void getNotExisting() throws Exception {
        mockMvc.perform(get(String.format("%s/%d", BASE_PATH, NOT_EXISTING_ID))).andExpect(status().isNotFound());
    }
}
