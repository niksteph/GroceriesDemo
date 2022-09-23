package de.evoila.nstephan.groceriesdemo.controller;

import de.evoila.nstephan.groceriesdemo.dto.GroceryItemDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private static final ArgumentMatcher<GroceryItemDTO> DTO_CONTAINS_ID = dto -> dto.getId() != null;

    private static final long EXISTING_ID = 0L;
    private static final long NOT_EXISTING_ID = 1L;

    private List<GroceryItem> items;
    private GroceryItem itemWithoutId;
    private GroceryItem itemWithId;

    @BeforeEach
    void init() {
        items = new ArrayList<>();
        for (int i = 0; i < TOTAL_ITEMS; i++) {
            var item = new GroceryItem();
            item.setId((long) i);
            items.add(item);
        }

        itemWithoutId = new GroceryItem();
        itemWithId = new GroceryItem();
        itemWithId.setId(0L);
        stubRepo();
        stubMapper();
    }

    void stubRepo() {
        doReturn(new PageImpl<>(items, UNPAGED, items.size())).when(repo)
                .findAll(argThat((ArgumentMatcher<Pageable>) Pageable::isUnpaged));
        doReturn(new PageImpl<>(items.subList(0, PAGE_SIZE), FIRST_PAGE, PAGE_SIZE)).when(repo)
                .findAll(
                        argThat((Pageable p) -> p.isPaged() && p.getPageSize() == PAGE_SIZE && p.getPageNumber() == 0));
        when(repo.findById(EXISTING_ID)).thenReturn(Optional.of(itemWithId));
        when(repo.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());
        when(repo.save(any(GroceryItem.class))).thenReturn(itemWithId);
        when(repo.existsById(EXISTING_ID)).thenReturn(true);
        when(repo.existsById(NOT_EXISTING_ID)).thenReturn(false);
    }

    void stubMapper() {
        doReturn(itemWithoutId).when(mapper).dtoToEntity(not(argThat(DTO_CONTAINS_ID)));
        doAnswer(invocationOnMock -> {
            GroceryItemDTO dto = invocationOnMock.getArgument(0);
            var item = new GroceryItem();
            item.setId(dto.getId());
            return item;
        }).when(mapper).dtoToEntity(argThat(DTO_CONTAINS_ID));
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

    @Test
    void postSuccess() throws Exception {
        mockMvc.perform(post(BASE_PATH).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void postFail() throws Exception {
        mockMvc.perform(post(BASE_PATH).contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"0\"}"))
                .andExpect(status().isBadRequest());
    }
}
