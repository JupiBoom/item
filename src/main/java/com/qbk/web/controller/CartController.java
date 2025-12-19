package com.qbk.web.controller;

import com.qbk.entity.param.CartParam;
import com.qbk.entity.vo.CartVo;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 购物车 Controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 添加商品到购物车
     * @param cartParam 购物车参数
     * @return 响应结果
     */
    @PostMapping("/add")
    public BaseResult<CartVo> addToCart(@Valid @RequestBody CartParam cartParam) {
        // 这里需要从登录信息中获取用户ID，暂时模拟
        Long userId = null; // 未登录用户
        CartVo cartVo = cartService.addToCart(cartParam, userId);
        return BaseResultGenerator.success(cartVo);
    }

    /**
     * 修改购物车商品数量
     * @param cartItemId 购物车商品项ID
     * @param quantity 新的数量
     * @param tempCartKey 临时购物车标识
     * @return 响应结果
     */
    @PutMapping("/update/{cartItemId}")
    public BaseResult<CartVo> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String tempCartKey) {
        // 这里需要从登录信息中获取用户ID，暂时模拟
        Long userId = null; // 未登录用户
        CartVo cartVo = cartService.updateCartItemQuantity(cartItemId, quantity, userId, tempCartKey);
        return BaseResultGenerator.success(cartVo);
    }

    /**
     * 删除购物车商品
     * @param cartItemId 购物车商品项ID
     * @param tempCartKey 临时购物车标识
     * @return 响应结果
     */
    @DeleteMapping("/delete/{cartItemId}")
    public BaseResult<CartVo> deleteCartItem(
            @PathVariable Long cartItemId,
            @RequestParam(required = false) String tempCartKey) {
        // 这里需要从登录信息中获取用户ID，暂时模拟
        Long userId = null; // 未登录用户
        CartVo cartVo = cartService.deleteCartItem(cartItemId, userId, tempCartKey);
        return BaseResultGenerator.success(cartVo);
    }

    /**
     * 清空购物车
     * @param tempCartKey 临时购物车标识
     * @return 响应结果
     */
    @DeleteMapping("/clear")
    public BaseResult<CartVo> clearCart(@RequestParam(required = false) String tempCartKey) {
        // 这里需要从登录信息中获取用户ID，暂时模拟
        Long userId = null; // 未登录用户
        CartVo cartVo = cartService.clearCart(userId, tempCartKey);
        return BaseResultGenerator.success(cartVo);
    }

    /**
     * 查询购物车商品列表
     * @param tempCartKey 临时购物车标识
     * @return 响应结果
     */
    @GetMapping("/list")
    public BaseResult<CartVo> getCartList(@RequestParam(required = false) String tempCartKey) {
        // 这里需要从登录信息中获取用户ID，暂时模拟
        Long userId = null; // 未登录用户
        CartVo cartVo = cartService.getCartList(userId, tempCartKey);
        return BaseResultGenerator.success(cartVo);
    }

    /**
     * 合并购物车
     * @param tempCartKey 临时购物车标识
     * @return 响应结果
     */
    @PostMapping("/merge")
    public BaseResult<CartVo> mergeCart(@RequestParam String tempCartKey) {
        // 这里需要从登录信息中获取用户ID，暂时模拟
        Long userId = 1L; // 登录用户
        CartVo cartVo = cartService.mergeCart(tempCartKey, userId);
        return BaseResultGenerator.success(cartVo);
    }
}