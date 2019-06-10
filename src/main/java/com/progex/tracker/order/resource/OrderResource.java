package com.progex.tracker.order.resource;

import com.progex.tracker.order.dto.OrderDto;
import com.progex.tracker.order.entity.Order;
import com.progex.tracker.order.service.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);
    private static final String BASE_URL_STR = "/api/orders/";
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> placeOrder(@Valid @RequestBody OrderDto orderDto) {

        if (Objects.nonNull(orderDto)){
            Order order = orderService.insert(modelMapper.map(orderDto, Order.class));
            OrderDto savedOrderDto = modelMapper.map(order, OrderDto.class);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    savedOrderDto.getId())).body(savedOrderDto);
        }
        LOGGER.warn("Failed to place the given OrderDto");

        return ResponseEntity.noContent().build();

    }


}
