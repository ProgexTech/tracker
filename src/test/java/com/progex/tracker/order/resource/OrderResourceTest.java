package com.progex.tracker.order.resource;

import com.progex.tracker.order.dto.OrderDto;
import com.progex.tracker.order.entity.Order;
import com.progex.tracker.order.service.OrderService;
import com.progex.tracker.utility.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.progex.tracker.utility.TestUtils.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author indunil
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderResourceTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderResource orderResource;

    private static final String BASE_URL_STR = "/api/orders/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderResource)
                .build();
    }

    @Test
    public void shouldCreateOrderWhenProvidingValidOrder() throws Exception {
        Order order = TestUtils.getMockOrder();
        when(orderService.insert(order)).thenReturn(order);

        OrderDto orderDto = mapToDTO(order);
        when(modelMapper.map(any(OrderDto.class), eq(Order.class))).thenReturn(order);
        when(modelMapper.map(any(Order.class), eq(OrderDto.class))).thenReturn(orderDto);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderDto)))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.name", is(orderDto.get())))
                //.andExpect(jsonPath("$.id", is(orderDto.getId())))
                /*.andExpect(jsonPath("$.items", hasSize(orderDto.getItemDtos().size())))
                .andExpect(jsonPath("$.items[0].name", is(orderDto.getItemDtos().iterator().next().getName())))
                .andExpect(jsonPath("$.items[0].calorie", is(orderDto.getItemDtos().iterator().next().getCalorie())))*/
                .andExpect(header().string("location", containsString(BASE_URL_STR + order.getId())));

        Mockito.verify(orderService, times(1)).insert(any());
        String s = asJsonString(orderDto);
        verifyNoMoreInteractions(orderService);
    }

    private OrderDto mapToDTO(Order itemEntity) {
        ModelMapper mMapper = new ModelMapper();
        return mMapper.map(itemEntity, OrderDto.class);
    }
}