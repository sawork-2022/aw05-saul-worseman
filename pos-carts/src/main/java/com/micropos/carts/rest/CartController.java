package com.micropos.carts.rest;

import com.micropos.carts.api.CartApi;
import com.micropos.carts.dto.CartDto;
import com.micropos.carts.dto.ItemDto;
import com.micropos.carts.mapper.CartsMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class CartController implements CartApi {
    private final CartsMapper cartsMapper;
    private final CartService cartService;

    public CartController(CartsMapper cartsMapper, CartService cartService) {
        this.cartsMapper = cartsMapper;
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<List<ItemDto>> getCart() {
        List<Item> q = cartService.getCart().getItems();
        List<ItemDto>  list = new ArrayList<>(cartsMapper.toItemsDto(cartService.getCart().getItems()));
        var t = new ResponseEntity<List<ItemDto>>(list, HttpStatus.OK);
        return new ResponseEntity<List<ItemDto>>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto> >addProduct(
            @Parameter(
                    name = "productId",
                    description = "Product ID",
                    required = true,
                    schema = @Schema(description = ""))
            @PathVariable("productId") String productId
    ) {
        cartService.add(new Item(productId, 1));
        //System.out.println(cartService.getCart().getItems().isEmpty());
        return new ResponseEntity<List<ItemDto>>((List<ItemDto>) cartsMapper.toItemsDto(cartService.getCart().getItems()), HttpStatus.OK);

    }
}
