import {ProductService} from '@/base/service/product.service';
import {AuthService} from "@/base/service/auth.service";
import {UserService} from "@/base/service/user.service";
import {CategoryService} from "@/base/service/category.service";
import {BrandService} from "@/base/service/brand.service";
import {ColorService} from "@/base/service/color.service";
import {SizeService} from "@/base/service/size.service";
import {WishlistService} from "@/base/service/wishlist.service";
import {CartService} from "@/base/service/cart.service";
import {OrderService} from "@/base/service/order.service";
import {Voucher} from "@/base/model/voucher.model";
import {VoucherService} from "@/base/service/voucher.service";
import {ReviewService} from "@/base/service/review.service";
export const DependencyInjection = {
    install: (app: any,store : any) => {
        app.provide('productService', new ProductService(store))
        app.provide('authService', new AuthService(store))
        app.provide('userService', new UserService(store))
        app.provide('categoryService', new CategoryService(store))
        app.provide('brandService', new BrandService(store))
        app.provide('colorService', new ColorService(store))
        app.provide('sizeService', new SizeService(store))
        app.provide('wishlistService', new WishlistService(store))
        app.provide('cartService', new CartService(store))
        app.provide('orderService', new OrderService(store))
        app.provide('voucherService', new VoucherService(store))
        app.provide('reviewService', new ReviewService(store))
    }
};