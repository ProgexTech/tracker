package com.progex.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progex.tracker.dto.ItemDTO;
import com.progex.tracker.entity.Item;
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

import java.util.Optional;

import static com.progex.tracker.utility.TestUtils.getMockItem;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author indunil
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ItemController itemController;


    private static final String BASE_URL_STR = "/api/items/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .build();
    }


    @Test
    public void shouldCreateItemWhenProvidingValidItem() throws Exception {
        Item item = getMockItem();
        when(itemService.insert(item)).thenReturn(Optional.of(item));
        when(itemService.getCategoryById(item.getCategory().getId())).
                thenReturn(Optional.of(item.getCategory()));

        ItemDTO itemDTO = mapToDTO(item);
        when(modelMapper.map(any(ItemDTO.class), eq(Item.class))).thenReturn(item);
        when(modelMapper.map(any(Item.class), eq(ItemDTO.class))).thenReturn(itemDTO);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(itemDTO.getName())))
                .andExpect(jsonPath("$.calorie", is(itemDTO.getCalorie())))
                .andExpect(jsonPath("$.code", is(itemDTO.getCode())))
                .andExpect(jsonPath("$.origin", is(itemDTO.getOrigin())))
                .andExpect(jsonPath("$.description", is(itemDTO.getDescription())))
                .andExpect(jsonPath("$.id", is(itemDTO.getId())))
                .andExpect(header().string("location", containsString(BASE_URL_STR + "1")));

        verify(itemService, times(1)).getCategoryById(anyInt());
        verify(itemService, times(1)).insert(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void shouldReturnBadRequestWhenProvidingItemWithoutCategory() throws Exception {
        Item item = getMockItem();
        when(itemService.insert(item)).thenReturn(Optional.of(item));
        when(itemService.getCategoryById(item.getCategory().getId())).
                thenReturn(Optional.of(item.getCategory()));

        ItemDTO itemDTO = mapToDTO(item);
        itemDTO.setCategory(null);
        when(modelMapper.map(any(ItemDTO.class), eq(Item.class))).thenReturn(item);
        when(modelMapper.map(any(Item.class), eq(ItemDTO.class))).thenReturn(itemDTO);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTO)))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).getCategoryById(anyInt());
        verify(itemService, never()).insert(any(Item.class));
    }

    @Test
    public void shouldReturnBadRequestWhenProvidingInvalidContent() throws Exception {
        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).getCategoryById(anyInt());
        verify(itemService, never()).insert(any(Item.class));
    }


    @Test
    public void shouldReturnNotFoundWhenProvidedItemIsNotSavedAndCategoryIdIsInvalid() throws Exception {
        Item item = getMockItem();

        when(itemService.getCategoryById(item.getCategory().getId())).
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
        Item item = getMockItem();
        when(modelMapper.map(any(Item.class), eq(ItemDTO.class))).thenReturn(mapToDTO(item));

        when(itemService.getItemById(item.getId())).thenReturn(Optional.of(item));
        mockMvc.perform(get(BASE_URL_STR + item.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.calorie", is(item.getCalorie())))
                .andExpect(jsonPath("$.code", is(item.getCode())))
                .andExpect(jsonPath("$.origin", is(item.getOrigin())))
                .andExpect(jsonPath("$.description", is(item.getDescription())))
                .andExpect(jsonPath("$.id", is(item.getId())));
    }

    @Test
    public void shouldReturnNotFoundWhenProvidingAnInValidItemId() throws Exception {
        when(itemService.getItemById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get(BASE_URL_STR + "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ItemDTO mapToDTO(Item item) {
        ModelMapper mMapper = new ModelMapper();
        return mMapper.map(item, ItemDTO.class);
    }
}