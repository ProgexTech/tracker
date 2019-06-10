package com.progex.tracker.category.resource;

import com.progex.tracker.category.dto.CategoryDto;
import com.progex.tracker.category.entity.Category;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.exceptions.EntityNotFoundException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.progex.tracker.utility.TestUtils.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        when(categoryService.insert(category)).thenReturn(category);

        CategoryDto categoryDto = mapToDTO(category);
        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(categoryDto.getName())))
                .andExpect(jsonPath("$.id", is(categoryDto.getId())))
                .andExpect(jsonPath("$.items", hasSize(categoryDto.getItemEntities().size())))
                .andExpect(jsonPath("$.items[0].name", is(categoryDto.getItemEntities().iterator().next().getName())))
                .andExpect(jsonPath("$.items[0].calorie", is(categoryDto.getItemEntities().iterator().next().getCalorie())))
                .andExpect(header().string("location", containsString(BASE_URL_STR + category.getId())));

        verify(categoryService, times(1)).insert(any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void shouldReturnListOfCategoriesWhenInvokingWithOffsetAndLimit() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.getAllCategories(anyInt(), anyInt())).thenReturn(Arrays.asList(category));

        CategoryDto categoryDto = mapToDTO(category);
        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(
                get("/api/categories?offset=0&limit=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));

        verify(categoryService, times(1)).getAllCategories(anyInt(), anyInt());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void shouldNoContentWhenNoEntriesAvailableForTheGivenOffsetAndLimit() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.getAllCategories(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        CategoryDto categoryDto = mapToDTO(category);
        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(
                get("/api/categories?offset=0&limit=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

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

        verify(categoryService, never()).insert(any(Category.class));
    }


    @Test
    public void shouldReturnNoContentWhenProvidedCategoryIsNotSaved() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.insert(any(Category.class))).
                thenReturn(category);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)))
                .andExpect(status().isNoContent());

        verify(categoryService, never()).insert(any(Category.class));
    }

    @Test
    public void shouldReturnCategoryWhenProvidingAValidCategoryId() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(mapToDTO(category));

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

    @Test
    public void shouldReturnOKWhenSuccessfullyDeleteGivenCategory() throws Exception {
        Category category = TestUtils.getMockCategory();
        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.of(category));

        mockMvc.perform(
                delete(BASE_URL_STR+"1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getCategoryById(anyInt());
        verify(categoryService, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void shouldReturnNotFoundWhenGivenCategoryIsNotPresented() throws Exception {
        mockMvc.perform(
                delete(BASE_URL_STR+"1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(anyInt());
        verify(categoryService, never()).deleteById(anyInt());
        verifyNoMoreInteractions(categoryService);
    }

    private CategoryDto mapToDTO(Category category) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(category, CategoryDto.class);
    }

    @Test
    public void shouldUpdateCategoryWhenProvidingValidCategoryIdAndContent() throws Exception {
        Category category = TestUtils.getMockCategory();

        Category toBeUpdated = TestUtils.getMockCategory();
        toBeUpdated.setName("updated");
        CategoryDto toBeUpdatedDto = mapToDTO(toBeUpdated);
        CategoryDto categoryDto = mapToDTO(category);

        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(toBeUpdatedDto);


        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.of(category));
        when(categoryService.update(anyInt(), any(Category.class))).thenReturn(toBeUpdated);

        mockMvc.perform(
                put(BASE_URL_STR+ category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(toBeUpdatedDto.getName())))
                .andExpect(jsonPath("$.id", is(toBeUpdatedDto.getId())))
                .andExpect(jsonPath("$.items", hasSize(toBeUpdatedDto.getItemEntities().size())))
                .andExpect(jsonPath("$.items[0].name", is(toBeUpdatedDto.getItemEntities().iterator().next().getName())))
                .andExpect(jsonPath("$.items[0].calorie", is(toBeUpdatedDto.getItemEntities().iterator().next().getCalorie())));

        /*verify(categoryService, times(1)).getCategoryById(anyInt());
        verify(categoryService, times(1)).update(toBeUpdated.getId(), any(Category.class));
        verifyNoMoreInteractions(categoryService);*/
    }

    @Test
    public void shouldReturnNoContentWhenFailedToSaveTheGivenEntity() throws Exception {
        Category category = TestUtils.getMockCategory();

        Category toBeUpdated = TestUtils.getMockCategory();
        toBeUpdated.setName("updated name");

        CategoryDto toBeUpdatedDto = mapToDTO(toBeUpdated);
        CategoryDto categoryDto = mapToDTO(category);

        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(toBeUpdatedDto);


        when(categoryService.isExists(anyInt())).thenReturn(false);
        when(categoryService.update(anyInt(), any(Category.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                put(BASE_URL_STR+ category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundWhenFailedToFindTheGivenCategory() throws Exception {
        Category category = TestUtils.getMockCategory();

        Category toBeUpdated = TestUtils.getMockCategory();
        toBeUpdated.setName("updated");
        CategoryDto toBeUpdatedDto = mapToDTO(toBeUpdated);
        CategoryDto categoryDto = mapToDTO(category);

        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(toBeUpdatedDto);


        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.empty());
        when(categoryService.update(anyInt(), any(Category.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                put(BASE_URL_STR+ category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDto)))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(anyInt());
        verify(categoryService, never()).update(anyInt(), any(Category.class));
    }
}