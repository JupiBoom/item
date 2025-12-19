package com.qbk.web.controller;

import com.qbk.entity.CartItem;
import com.qbk.result.BaseResult;
import com.qbk.web.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车Controller
 */
@RestController
@RequestMapping("/cart")
@Api(tags = "购物车管理")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID（登录用户）", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识（未登录用户）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "quantity", value = "商品数量", required = true, dataType = "Integer", paramType = "query")
    })
    public BaseResult addToCart(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "tempCartKey", required = false) String tempCartKey,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        return cartService.addToCart(userId, tempCartKey, productId, quantity);
    }

    @PutMapping("/update")
    @ApiOperation("修改购物车商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID（登录用户）", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识（未登录用户）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "quantity", value = "新的商品数量", required = true, dataType = "Integer", paramType = "query")
    })
    public BaseResult updateCartItemQuantity(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "tempCartKey", required = false) String tempCartKey,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        return cartService.updateCartItemQuantity(userId, tempCartKey, productId, quantity);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID（登录用户）", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识（未登录用户）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "Long", paramType = "query")
    })
    public BaseResult deleteCartItem(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "tempCartKey", required = false) String tempCartKey,
            @RequestParam Long productId) {
        return cartService.deleteCartItem(userId, tempCartKey, productId);
    }

    @DeleteMapping("/clear")
    @ApiOperation("清空购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID（登录用户）", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识（未登录用户）", dataType = "String", paramType = "query")
    })
    public BaseResult clearCart(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "tempCartKey", required = false) String tempCartKey) {
        return cartService.clearCart(userId, tempCartKey);
    }

    @GetMapping("/items")
    @ApiOperation("查询购物车商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID（登录用户）", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识（未登录用户）", dataType = "String", paramType = "query")
    })
    public BaseResult<List<CartItem>> getCartItems(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "tempCartKey", required = false) String tempCartKey) {
        return cartService.getCartItems(userId, tempCartKey);
    }

    @GetMapping("/total")
    @ApiOperation("计算购物车总金额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID（登录用户）", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识（未登录用户）", dataType = "String", paramType = "query")
    })
    public BaseResult<Double> getCartTotalAmount(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "tempCartKey", required = false) String tempCartKey) {
        return cartService.getCartTotalAmount(userId, tempCartKey);
    }

    @PostMapping("/merge")
    @ApiOperation("合并登录前后购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tempCartKey", value = "临时购物车标识", required = true, dataType = "String", paramType = "query")
    })
    public BaseResult mergeCart(
            @RequestParam Long userId,
            @RequestParam String tempCartKey) {
        return cartService.mergeCart(userId, tempCartKey);
    }
}
