package com.progex.tracker.category.resource;

import com.progex.tracker.category.dto.CategoryDTO;
import com.progex.tracker.category.entity.Category;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.utility.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static com.progex.tracker.item.resource.ItemResourceTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author indunil
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryResourceTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryResource categoryController;

    private static final String BASE_URL_STR = "/api/categories/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .build();
    }

    @Test
    public void shouldCreateCategoryWhenProvidingValidCategory() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.createCategory(category)).thenReturn(Optional.of(category));

        CategoryDTO categoryDTO = mapToDTO(category);
        when(modelMapper.map(any(CategoryDTO.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(categoryDTO.getName())))
                .andExpect(jsonPath("$.id", is(categoryDTO.getId())))
                .andExpect(jsonPath("$.items", hasSize(categoryDTO.getItems().size())))
                .andExpect(jsonPath("$.items[0].name", is(categoryDTO.getItems().iterator().next().getName())))
                .andExpect(jsonPath("$.items[0].calorie", is(categoryDTO.getItems().iterator().next().getCalorie())))
                .andExpect(header().string("location", containsString(BASE_URL_STR + category.getId())));

        verify(categoryService, times(1)).createCategory(any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void shouldReturnListOfCategoriesWhenInvokingWithOffsetAndLimit() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.getAllCategories(anyInt(), anyInt())).thenReturn(Arrays.asList(category));

        CategoryDTO categoryDTO = mapToDTO(category);
        when(modelMapper.map(any(CategoryDTO.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(
                get("/api/categories?offset=0&limit=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

        verify(categoryService, times(1)).getAllCategories(anyInt(), anyInt());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void shouldReturnBadRequestWhenProvidingInvalidContent() throws Exception {
        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).createCategory(any(Category.class));
    }


    @Test
    public void shouldReturnNoContentWhenProvidedCategoryIsNotSaved() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.createCategory(any(Category.class))).
                thenReturn(Optional.empty());

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)))
                .andExpect(status().isNoContent());

        verify(categoryService, never()).createCategory(any(Category.class));
    }

    @Test
    public void shouldReturnCategoryWhenProvidingAValidCategoryId() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(modelMapper.map(any(Category.class), eq(CategoryDTO.class))).thenReturn(mapToDTO(category));

        when(categoryService.getCategoryById(category.getId())).thenReturn(Optional.of(category));
        mockMvc.perform(get(BASE_URL_STR + category.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(category.getName())))
                .andExpect(jsonPath("$.id", is(category.getId())));
    }

    @Test
    public void shouldReturnNotFoundWhenProvidingAnInValidCategoryId() throws Exception {
        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get(BASE_URL_STR + "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private CategoryDTO mapToDTO(Category category) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(category, CategoryDTO.class);
    }

}