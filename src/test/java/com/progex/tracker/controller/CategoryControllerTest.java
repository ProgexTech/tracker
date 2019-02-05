package com.progex.tracker.controller;

import com.progex.tracker.dto.CategoryDTO;
import com.progex.tracker.dto.ItemDTO;
import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import com.progex.tracker.service.CategoryService;
import com.progex.tracker.service.ItemService;
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
import java.util.stream.Collectors;

import static com.progex.tracker.controller.ItemControllerTest.asJsonString;
import static com.progex.tracker.utility.TestUtils.getMockItem;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author indunil
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryController categoryController;

    private static final String BASE_URL_STR = "/api/categories/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .build();
    }

    @Test
    public void shouldCreateCategoryWhenProvidingValidCategory() throws Exception {
        Category category = getMockCategory();
        when(categoryService.save(category)).thenReturn(Optional.of(category));

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
                .andExpect(jsonPath("$.items[0].name", is(categoryDTO.getItems().iterator().next().getName())))
                .andExpect(jsonPath("$.items[0].calorie", is(categoryDTO.getItems().iterator().next().getCalorie())))
                .andExpect(header().string("location", containsString(BASE_URL_STR + category.getId())));

        verify(categoryService, times(1)).save(any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void shouldReturnBadRequestWhenProvidingInvalidContent() throws Exception {
        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).save(any(Category.class));
    }


    @Test
    public void shouldReturnNoContentWhenProvidedEntityIsNotSaved() throws Exception {
        Category category = getMockCategory();
        when(categoryService.save(any(Category.class))).
                thenReturn(Optional.empty());
        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)))
                .andExpect(status().isNoContent());

        verify(categoryService, never()).save(any(Category.class));
    }

    private CategoryDTO mapToDTO(Category category) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category getMockCategory() {
        Item item = getMockItem();
        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setItems(Arrays.asList(item));
        return category;
    }
}