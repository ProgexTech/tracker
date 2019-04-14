package com.progex.tracker.item.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progex.tracker.item.dto.Item;
import com.progex.tracker.item.entity.ItemEntity;
import com.progex.tracker.item.service.ItemService;
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

import java.util.Optional;

import static com.progex.tracker.utility.TestUtils.getMockItem;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ItemResourceTest {
    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ItemResource itemController;


    private static final String BASE_URL_STR = "/api/items/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .build();
    }


    @Test
    public void shouldCreateItemWhenProvidingValidItem() throws Exception {
        ItemEntity itemEntity = getMockItem();
        when(itemService.insert(itemEntity)).thenReturn(itemEntity);
        when(itemService.getCategoryById(itemEntity.getCategoryEntity().getId())).
                thenReturn(Optional.of(itemEntity.getCategoryEntity()));

        Item item = mapToDTO(itemEntity);
        when(modelMapper.map(any(Item.class), eq(ItemEntity.class))).thenReturn(itemEntity);
        when(modelMapper.map(any(ItemEntity.class), eq(Item.class))).thenReturn(item);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(item)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.calorie", is(item.getCalorie())))
                .andExpect(jsonPath("$.code", is(item.getCode())))
                .andExpect(jsonPath("$.origin", is(item.getOrigin())))
                .andExpect(jsonPath("$.description", is(item.getDescription())))
                .andExpect(jsonPath("$.id", is(item.getId())))
                .andExpect(header().string("location", containsString(BASE_URL_STR + "1")));

        verify(itemService, times(1)).getCategoryById(anyInt());
        verify(itemService, times(1)).insert(any(ItemEntity.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldReturnBadRequestWhenProvidingItemWithoutCategory() throws Exception {
        ItemEntity itemEntity = getMockItem();
        when(itemService.insert(itemEntity)).thenReturn(itemEntity);
        when(itemService.getCategoryById(itemEntity.getCategoryEntity().getId())).
                thenReturn(Optional.of(itemEntity.getCategoryEntity()));

        Item item = mapToDTO(itemEntity);
        item.setCategoryEntity(null);
        when(modelMapper.map(any(Item.class), eq(ItemEntity.class))).thenReturn(itemEntity);
        when(modelMapper.map(any(ItemEntity.class), eq(Item.class))).thenReturn(item);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(item)))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).getCategoryById(anyInt());
        verify(itemService, never()).insert(any(ItemEntity.class));
    }

    @Test
    public void shouldReturnBadRequestWhenProvidingInvalidContent() throws Exception {
        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).getCategoryById(anyInt());
        verify(itemService, never()).insert(any(ItemEntity.class));
    }

    @Test
    public void shouldReturnNotFoundWhenProvidedItemIsNotSavedAndCategoryIdIsInvalid() throws Exception {
        ItemEntity itemEntity = getMockItem();
        Item item = mapToDTO(itemEntity);
        when(itemService.getCategoryById(itemEntity.getCategoryEntity().getId())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(item)))
                .andExpect(status().isNotFound());

        verify(itemService, times(1)).getCategoryById(anyInt());
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldReturnItemWhenProvidingAValidItemId() throws Exception {
        ItemEntity itemEntity = getMockItem();
        when(modelMapper.map(any(ItemEntity.class), eq(Item.class))).thenReturn(mapToDTO(itemEntity));

        when(itemService.getItemById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));
        mockMvc.perform(get(BASE_URL_STR + itemEntity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(itemEntity.getName())))
                .andExpect(jsonPath("$.calorie", is(itemEntity.getCalorie())))
                .andExpect(jsonPath("$.code", is(itemEntity.getCode())))
                .andExpect(jsonPath("$.origin", is(itemEntity.getOrigin())))
                .andExpect(jsonPath("$.description", is(itemEntity.getDescription())))
                .andExpect(jsonPath("$.id", is(itemEntity.getId())));
    }

    @Test
    public void shouldReturnNotFoundWhenProvidingAnInValidItemId() throws Exception {
        when(itemService.getItemById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get(BASE_URL_STR + "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnOKWhenSuccessfullyDeleteGivenItem() throws Exception {
        ItemEntity itemEntity = TestUtils.getMockItem();
        when(itemService.getItemById(anyInt())).thenReturn(Optional.of(itemEntity));

        mockMvc.perform(
                delete(BASE_URL_STR+"1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(itemService, times(1)).getItemById(anyInt());
        verify(itemService, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldReturnNotFoundWhenGivenItemIsNotPresented() throws Exception {
        mockMvc.perform(
                delete(BASE_URL_STR+"1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(itemService, times(1)).getItemById(anyInt());
        verify(itemService, never()).deleteById(anyInt());
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldUpdateItemWhenProvidingValidItem() throws Exception {
        ItemEntity itemEntity = getMockItem();
        when(itemService.getItemById(anyInt())).thenReturn(Optional.of(itemEntity));
    /*    when(itemService.getCategoryById(itemEntity.getCategoryEntity().getId())).
                thenReturn(Optional.of(itemEntity.getCategoryEntity()));*/

        Item item = mapToDTO(itemEntity);
        when(modelMapper.map(any(Item.class), eq(ItemEntity.class))).thenReturn(itemEntity);
        when(modelMapper.map(any(ItemEntity.class), eq(Item.class))).thenReturn(item);

        mockMvc.perform(
                put(BASE_URL_STR+"1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(item)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).getItemById(anyInt());
        verify(itemService, times(1)).update(any(ItemEntity.class));
        verifyNoMoreInteractions(itemService);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Item mapToDTO(ItemEntity itemEntity) {
        ModelMapper mMapper = new ModelMapper();
        return mMapper.map(itemEntity, Item.class);
    }
}