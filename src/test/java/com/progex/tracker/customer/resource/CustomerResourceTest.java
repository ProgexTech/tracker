package com.progex.tracker.customer.resource;

import com.progex.tracker.customer.service.CustomerService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerResourceTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerResource customerResource;

    private static final String BASE_URL_STR = "/api/customers/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerResource)
                .build();
    }

    public void shouldCreateCategoryWhenProvidingValidCategory() throws Exception {
       /* Customer customer = TestUtils.getMockCustomerEntity();
        when(customerService.insert(customer)).thenReturn(customer);

        CategoryDto category = mapToDTO(customer);
        when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(customer);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(category);

        mockMvc.perform(
                post(BASE_URL_STR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(category.getName())))
                .andExpect(jsonPath("$.id", is(category.getId())))
                .andExpect(jsonPath("$.items", hasSize(category.getItemDtos().size())))
                .andExpect(jsonPath("$.items[0].name", is(category.getItemDtos().iterator().next().getName())))
                .andExpect(jsonPath("$.items[0].calorie", is(category.getItemDtos().iterator().next().getCalorie())))
                .andExpect(header().string("location", containsString(BASE_URL_STR + customer.getId())));

        verify(customerService, times(1)).insert(any());
        verifyNoMoreInteractions(customerService);*/
    }

}