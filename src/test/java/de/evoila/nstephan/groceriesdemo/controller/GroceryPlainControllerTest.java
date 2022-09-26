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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private static final ArgumentMatcher<GroceryItemDTO> DTO_CONTAINS_ID = dto -> dto.getId() != null;

    private static final long EXISTING_ID = 0L;
    private static final long NOT_EXISTING_ID = -1L;

    private List<GroceryItem> items;

    @BeforeEach
    void init() {
        items = new ArrayList<>();
        for (int i = 0; i < TOTAL_ITEMS; i++) {
            var item = new GroceryItem();
            item.setId((long) i);
            items.add(item);
        }

        stubRepo();
        stubMapper();
    }

    void stubRepo() {
        doReturn(new PageImpl<>(items, UNPAGED, items.size())).when(repo)
                .findAll(argThat((ArgumentMatcher<Pageable>) Pageable::isUnpaged));
        doAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(0);
            int page = pageable.getPageNumber();
            int size = pageable.getPageSize();
            int start = page * size;
            int end = Math.min(start + size, items.size());
            List<GroceryItem> content = items.subList(start, end);
            return new PageImpl<>(content, pageable, content.size());
        }).when(repo).findAll(argThat((ArgumentMatcher<Pageable>) Pageable::isPaged));

        when(repo.findById(anyLong())).thenAnswer(invocation -> {
            int index = Math.toIntExact(invocation.getArgument(0));
            if (0 <= index && index < items.size())
                return Optional.of(items.get(index));
            else
                return Optional.empty();
        });

        when(repo.save(any(GroceryItem.class))).thenAnswer(invocation -> {
            GroceryItem item = invocation.getArgument(0);
            if (item.getId() == null)
                item.setId((long) items.size());
            items.add(item);
            return item;
        });
        when(repo.existsById(EXISTING_ID)).thenReturn(true);
        when(repo.existsById(NOT_EXISTING_ID)).thenReturn(false);
    }

    void stubMapper() {
        doReturn(new GroceryItem()).when(mapper).dtoToEntity(not(argThat(DTO_CONTAINS_ID)));
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

    @Test
    void putSameIdInBody() throws Exception {
        long id = 0L;
        var path = String.format("%s/%d", BASE_PATH, id);
        var body = String.format("{\"id\":\"%d\", \"description\":\"%s\"}", id, "some replaced item");
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk());
    }

    @Test
    void putDifferentIdInBody() throws Exception {
        long idPath = 0L;
        long idBody = 1L;
        var path = String.format("%s/%d", BASE_PATH, idPath);
        var body = String.format("{\"id\":\"%d\", \"description\":\"%s\"}", idBody, "some replaced item");
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putNoIdInBody() throws Exception {
        var path = String.format("%s/%d", BASE_PATH, 0);
        var body = String.format("{\"description\":\"%s\"}", "some replaced item");
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk());
    }
}
